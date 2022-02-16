package top.pcat.study.WrongQuestion;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apkfuns.logutils.LogUtils;
import com.chad.library.adapter.base.entity.node.BaseNode;
import top.pcat.study.Curriculum.BaseActivity2;
import top.pcat.study.MainActivity;
import top.pcat.study.R;
import top.pcat.study.Size.ChAdapter;
import top.pcat.study.Size.Chap;
import top.pcat.study.Size.ChapterActivity;
import top.pcat.study.Utils.StatusBarUtil;
import top.pcat.study.WrongQuestion.node.FirstNode;
import top.pcat.study.WrongQuestion.node.SecondNode;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WrongQuestion extends BaseActivity2 {
    private RecyclerView mRecyclerView;
    private NodeTreeAdapter adapter = new NodeTreeAdapter(this);
    private String alljson;
    private String ke;
    private List<Chap> chapList = new ArrayList<>();
    private ArrayList<Fragment> mFragments;
    private String[] mTitlesArrays ={"全部","未完成","已完成"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_question);
//        Objects.requireNonNull(getSupportActionBar()).hide();
        setBackBtn();
        setTitle("错题本");
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        StatusBarUtil.setStatusBarMode(this,true,R.color.touming);
         
        mRecyclerView = findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        //TODO 增加用户ID，识别每个用户错题

        try {
            GetData("http://192.168.31.238:12345/wrongProblems/"+readInfo(readlv())+"/0");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ChAdapter adapter = new ChAdapter(WrongQuestion.this,
                R.layout.chap_item, chapList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setDividerHeight(0);
        listView.setDivider(null);
        listView.setAdapter(adapter);
        //mTab.setViewPager(mVp,mTitlesArrays,this,mFragments);//tab和ViewPager进行关联

        // 模拟新增node
//        mRecyclerView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                SecondNode seNode = new SecondNode(new ArrayList<BaseNode>(), "Second Node(This is added)");
//                SecondNode seNode2 = new SecondNode(new ArrayList<BaseNode>(), "Second Node(This is added)");
//                List<SecondNode> nodes = new ArrayList<>();
//                nodes.add(seNode);
//                nodes.add(seNode2);
//                //第一个夫node，位置为子node的3号位置
//                adapter.nodeAddData(adapter.getData().get(0), 2, nodes);
////                adapter.nodeSetData(adapter.getData().get(0), 2, seNode2);
////                adapter.nodeReplaceChildData(adapter.getData().get(0), nodes);
//                //Tips.show("新插入了两个node", Toast.LENGTH_LONG);
//            }
//        }, 2000);
    }

    public String readlv() {
        String result = "";
        try {
            FileInputStream fin = openFileInput("UserInfo");
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            result = EncodingUtils.getString(buffer,"UTF-8");
            fin.close();
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "未登录 | 点击登录";
        } catch (IOException e) {
            e.printStackTrace();
            return "未登录 | 点击登录";
        }
    }

    private void initItem(String item) throws JSONException {
        //JSON读取各个题目名称-----一类
        JSONArray jsonArray = new JSONArray(item);
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            //int size= jsonObject.getInt("chapterSize");
            //String subject_name = jsonObject.getString("chapterName");
            //sum += size;
            //LogUtils.d("一类科目===========",size + subject_name);
            //initChaps(subject_name);

            Chap one = new Chap(jsonObject.getString("chapterId"),jsonObject.getString("chapterName"), R.drawable.do_do,jsonObject.getString("chapterVersion"));
//            if(i>1){
//                one = new Chap(jsonObject.getString("chapterId"),jsonObject.getString("chapterName"),R.drawable.do_do );
//
//            }
            chapList.add(one);
        }

        LogUtils.d(chapList);

    }

    public String readInfo(String tempInfo){
        try {
            JSONObject jsonArray = new JSONObject(tempInfo);
            //lv = jsonArray.getString("lv");
            return jsonArray.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "error";
    }

    private List<BaseNode> getEntity() {
        List<BaseNode> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {

            List<BaseNode> secondNodeList = new ArrayList<>();

            if(ke.contains("Cpp") && i==0){
                boolean flagFirst = true;

                for(int q=1;q>0;q++){

                    if(alljson.contains("C++"+q) && readInfo(alljson,"C++"+q) != null){
                        List<BaseNode> thirdNodeList = new ArrayList<>();
                        String aajson = readInfo(alljson,"C++"+q) ;
                        LogUtils.d("=======",aajson);
                            if(aajson.indexOf("error") !=-1) break;
                            if(aajson==null || aajson.indexOf("null") !=-1) continue;
                            try {
                                JSONArray jsonArray = new JSONArray(aajson);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                LogUtils.d("================","C++"+q+"===="+jsonObject.length());
                                if(jsonObject.length()==0) break;
                                boolean flag = true;
                                for(int o=1;o<=jsonObject.length();o++){
                                    String res = jsonObject.getString(String.valueOf(o));
                                    if(res.indexOf("wrong") !=-1){
                                        flag = false;
                                        flagFirst=false;
                                        SecondNode seNode = new SecondNode(thirdNodeList, "第"+q+"章  "+" 第 "+String.valueOf(o)+" 题");
                                        secondNodeList.add(seNode);
                                    }
                                }
                                if(flag) break;

                            } catch (JSONException e) {
                                //Toast.makeText(WrongQuestion.this,"信息读取错误",Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }


                    }else{
                        break;
                    }
                }
                if(flagFirst) break;

                FirstNode entity = new FirstNode(secondNodeList, "C++");
                entity.setExpanded(true);
                list.add(entity);
            }
            if(ke.contains("C--")&& i==1){

                boolean flagFirst = true;

                for(int q=1;q>0;q++){

                    if(alljson.contains("C--"+q) && readInfo(alljson,"C--"+q) != null){
                        List<BaseNode> thirdNodeList = new ArrayList<>();
                        String aajson = readInfo(alljson,"C--"+q) ;
                        LogUtils.d("=======",aajson);
                        if(aajson.indexOf("error") !=-1) break;
                        if(aajson==null || aajson.indexOf("null") !=-1) continue;
                        try {
                            JSONArray jsonArray = new JSONArray(aajson);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            LogUtils.d("================","C--"+q+"===="+jsonObject.length());
                            if(jsonObject.length()==0) break;
                            boolean flag = true;
                            for(int o=1;o<=jsonObject.length();o++){
                                String res = jsonObject.getString(String.valueOf(o));
                                if(res.indexOf("wrong") !=-1){
                                    flag = false;
                                    flagFirst=false;
                                    SecondNode seNode = new SecondNode(thirdNodeList, "C 第"+q+"章"+" 第 "+String.valueOf(o)+" 题");
                                    secondNodeList.add(seNode);
                                }
                            }
                            if(flag) break;

                        } catch (JSONException e) {
                            //Toast.makeText(WrongQuestion.this,"信息读取错误",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }else{
                        break;
                    }
                }
                if(flagFirst) break;

                FirstNode entity = new FirstNode(secondNodeList, "C");
                entity.setExpanded(true);
                list.add(entity);
            }
            if(ke.contains("数据结构")&& i==2){
                boolean flagFirst = true;

                for(int q=1;q>0;q++){

                    if(alljson.contains("sjjg"+q) && readInfo(alljson,"sjjg"+q) != null){
                        List<BaseNode> thirdNodeList = new ArrayList<>();
                        String aajson = readInfo(alljson,"sjjg"+q) ;
                        LogUtils.d("=======",aajson);
                        if(aajson.indexOf("error") !=-1) break;
                        if(aajson==null || aajson.indexOf("null") !=-1) continue;
                        try {
                            JSONArray jsonArray = new JSONArray(aajson);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            LogUtils.d("================","sjjg"+q+"===="+jsonObject.length());
                            if(jsonObject.length()==0) break;
                            boolean flag = true;
                            for(int o=1;o<=jsonObject.length();o++){
                                String res = jsonObject.getString(String.valueOf(o));
                                if(res.indexOf("wrong") !=-1){
                                    flag = false;
                                    flagFirst=false;
                                    SecondNode seNode = new SecondNode(thirdNodeList, "第 "+q+"章"+" 第 "+String.valueOf(o)+" 题");
                                    //seNode.setExpanded(true);
                                    secondNodeList.add(seNode);
                                }
                            }
                            if(flag) break;

                        } catch (JSONException e) {
                            //Toast.makeText(WrongQuestion.this,"信息读取错误",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }


                    }else{
                        break;
                    }
                }
                if(flagFirst) break;

                FirstNode entity = new FirstNode(secondNodeList, "数据结构");
                entity.setExpanded(true);
                list.add(entity);
            }
            if(ke.contains("数据库")&& i==3){
                boolean flagFirst = true;

                for(int q=1;q>0;q++){

                    if(alljson.contains("sjk"+q) && readInfo(alljson,"sjk"+q) != null){
                        List<BaseNode> thirdNodeList = new ArrayList<>();
                        String aajson = readInfo(alljson,"sjk"+q) ;
                        LogUtils.d("=======",aajson);
                        if(aajson.indexOf("error") !=-1) break;
                        if(aajson==null || aajson.indexOf("null") !=-1) continue;
                        try {
                            JSONArray jsonArray = new JSONArray(aajson);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            LogUtils.d("================","sjk_"+q+"===="+jsonObject.length());
                            if(jsonObject.length()==0) break;
                            boolean flag = true;
                            for(int o=1;o<=jsonObject.length();o++){
                                String res = jsonObject.getString(String.valueOf(o));
                                if(res.indexOf("wrong") !=-1){
                                    flag = false;
                                    flagFirst=false;
                                    SecondNode seNode = new SecondNode(thirdNodeList, "第 "+q+" 章"+" 第 "+String.valueOf(o)+"题");
                                    //seNode.setExpanded(true);
                                    secondNodeList.add(seNode);
                                }
                            }
                            if(flag) break;

                        } catch (JSONException e) {
                            //Toast.makeText(WrongQuestion.this,"信息读取错误",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }


                    }else{
                        break;
                    }
                }
                if(flagFirst) break;

                FirstNode entity = new FirstNode(secondNodeList, "数据库");
                entity.setExpanded(true);
                list.add(entity);
            }
            if(ke.contains("算法分析与设计")&& i==4){
                boolean flagFirst = true;

                for(int q=1;q>0;q++){

                    if(alljson.contains("sf"+q) && readInfo(alljson,"sf"+q) != null){
                        List<BaseNode> thirdNodeList = new ArrayList<>();
                        String aajson = readInfo(alljson,"sf"+q) ;
                        LogUtils.d("=======",aajson);
                        if(aajson.indexOf("error") !=-1) break;
                        if(aajson==null || aajson.indexOf("null") !=-1) continue;
                        try {
                            JSONArray jsonArray = new JSONArray(aajson);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            LogUtils.d("================","sf"+q+"===="+jsonObject.length());
                            if(jsonObject.length()==0) break;
                            boolean flag = true;
                            for(int o=1;o<=jsonObject.length();o++){
                                String res = jsonObject.getString(String.valueOf(o));
                                if(res.indexOf("wrong") !=-1){
                                    flag = false;
                                    flagFirst=false;
                                    SecondNode seNode = new SecondNode(thirdNodeList, "第 "+q+"章"+" 第 "+String.valueOf(o)+" 题");
                                    //seNode.setExpanded(true);
                                    secondNodeList.add(seNode);
                                }
                            }
                            if(flag) break;

                        } catch (JSONException e) {
                            //Toast.makeText(WrongQuestion.this,"信息读取错误",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }


                    }else{
                        break;
                    }
                }
                if(flagFirst) break;

                FirstNode entity = new FirstNode(secondNodeList, "gfh"+1);
                entity.setExpanded(true);
                list.add(entity);
            }
            if(ke.contains("英语")&& i==5){
                boolean flagFirst = true;

                for(int q=1;q>0;q++){

                    if(alljson.contains("yy"+q) && readInfo(alljson,"yy"+q) != null){
                        List<BaseNode> thirdNodeList = new ArrayList<>();
                        String aajson = readInfo(alljson,"yy"+q) ;
                        LogUtils.d("=======",aajson);
                        if(aajson.indexOf("error") !=-1) break;
                        if(aajson==null || aajson.indexOf("null") !=-1) continue;
                        try {
                            JSONArray jsonArray = new JSONArray(aajson);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            LogUtils.d("================","yy"+q+"===="+jsonObject.length());
                            if(jsonObject.length()==0) break;
                            boolean flag = true;
                            for(int o=1;o<=jsonObject.length();o++){
                                String res = jsonObject.getString(String.valueOf(o));
                                if(res.indexOf("wrong") !=-1){
                                    flag = false;
                                    flagFirst=false;
                                    SecondNode seNode = new SecondNode(thirdNodeList, "第 "+q+"章"+" 第 "+String.valueOf(o)+" 题");
                                    //seNode.setExpanded(true);
                                    secondNodeList.add(seNode);
                                }
                            }
                            if(flag) break;

                        } catch (JSONException e) {
                            //Toast.makeText(WrongQuestion.this,"信息读取错误",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }


                    }else{
                        break;
                    }
                }
                if(flagFirst) break;

                FirstNode entity = new FirstNode(secondNodeList, "英语");
                entity.setExpanded(true);
                list.add(entity);
            }
        }
        return list;
    }

    public void GetData(String url) throws IOException {

        ProgressDialog progressDialog = new ProgressDialog(WrongQuestion.this);
        progressDialog.setTitle("请稍等");
        progressDialog.setMessage("首次加载数据...");
        progressDialog.setCancelable(true);
        progressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL uu = new URL(url);
                    LogUtils.d("Internet类","url============="+uu);
                    HttpURLConnection connection = (HttpURLConnection) uu.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();


                    int responseCode = connection.getResponseCode();
                    if(responseCode == HttpURLConnection.HTTP_OK){
                        InputStream inputStream = connection.getInputStream();
                        String result = String.valueOf(inputStream);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while((line = reader.readLine())!= null){
                            response.append(line);
                        }

                        progressDialog.dismiss();

                        String res = response.toString();
                        alljson =res;
                        LogUtils.d("alljson========",alljson);
                        ke=readInfo(alljson,"yixuan");
                        initItem(response.toString());

                        //adapter.setList(getEntity());
                    }
                    else{
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public String readInfo(String tempInfo,String key){
        try {
            JSONArray jsonArray = new JSONArray(tempInfo);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                return jsonObject.getString(key);
            }
        } catch (JSONException e) {
            //Toast.makeText(WrongQuestion.this,"信息读取错误",Toast.LENGTH_SHORT).show();
            return "error";
        }
        return tempInfo;
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitlesArrays[position];
//            return position;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

}