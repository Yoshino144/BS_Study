package top.pcat.study.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import top.pcat.study.Banner.DataBean;
import top.pcat.study.Banner.ImageAdapter;
import top.pcat.study.Curriculum.Collection;
import top.pcat.study.Curriculum.Curriculum;
import top.pcat.study.Utils.FileTool;
import top.pcat.study.MainActivity;
import top.pcat.study.R;
import top.pcat.study.Size.ChapterActivity;
import top.pcat.study.Size.DisplayUtil;
import top.pcat.study.View.Fragment2Adapter;
import top.pcat.study.View.ItemFragment2;
import top.pcat.study.View.LogUtils;
import top.pcat.study.user.SignActivity;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.indicator.RoundLinesIndicator;
import com.youth.banner.listener.OnPageChangeListener;
import com.youth.banner.util.BannerUtils;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * aaa simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragment2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment2 extends Fragment implements OnPageChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Banner myBanner;
    List<Integer> ImageUrlData;//注意坑在这里 我之前写的是 List<String> ImageUrlData因为
    List<String> ContentData;
    private boolean allFlag = false;

    private Banner banner;
    private FileTool ft;
    private MainActivity ma;
    private LinearLayout one;
    private LinearLayout two;
    private LinearLayout three;
    private LinearLayout four;
    private LinearLayout oneSize;
    private LinearLayout twoSize;
    private LinearLayout threeSize;
    private LinearLayout fourSize;
    private LinearLayout fiveSize;
    private LinearLayout sixSize;
    private LinearLayout add;
    private Handler handler = new Handler();
    private int result;
    private DisplayUtil dipToPix;
    private String interres;
    private String tempTest;
    private int nowPage = 0;
    private boolean signFlag;
    private LinearLayout cpp;
    private LinearLayout c;
    private LinearLayout sjjg;
    private LinearLayout sjk;
    private LinearLayout sf;
    private LinearLayout yy;
    private final Handler cwjHandler = new Handler(){
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:
                    Bundle bundle = msg.getData();
                    String bb = (String) bundle.get("Subject_id");
                    String subject_name = (String) bundle.get("Subject_name");
                    if(bb.indexOf("0") !=-1){
                        if(signFlag){
                            Intent intent01 = new Intent();
                            intent01.setClass(getActivity(), Curriculum.class);
                            startActivity(intent01);
                            Toast.makeText(getActivity(), "全部", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(getActivity(), "请先登录后操作",  Toast.LENGTH_SHORT).show();

                        }
                    }
                    else{
                            try {
                                GetData(bb,subject_name,"http://192.168.31.238:12345/chapters/getBySubjectId/"+bb);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                    }


                    Toast.makeText(getActivity(),bb,Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(getActivity(),"456",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private final Runnable mUpdateResults = this::updata2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //是否第一次加载
    private boolean isFirstLoading = true;

    private OnFragmentInteractionListener mListener;
    //private BannerComponent bannerComponent;

    public BlankFragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return aaa new instance of fragment BlankFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment2 newInstance(String param1, String param2) {
        BlankFragment2 fragment = new BlankFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    View blan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        blan = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_blank_fragment2, container, false);

        initBanner();
        //initBanner();
        File path = new File(requireActivity().getFilesDir().getAbsolutePath()+"/Login.txt");
        if(!FileTool.isFileExists(path.toString())){
            LinearLayout qwe = blan.findViewById(R.id.loginFlag);
            qwe.setVisibility(View.VISIBLE);
            LogUtils.d("=============未登录-显示登录框=============");
            qwe.setOnClickListener(v -> {
                Intent intent01=new Intent();
                intent01.setClass(getActivity(), SignActivity.class);
                intent01.putExtra("page",0);
                getActivity().finish();
                startActivity(intent01);
            });
        }


        return blan;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        File path = new File(getActivity().getFilesDir().getAbsolutePath()+"/Login.txt");
        if(ft.isFileExists(path.toString())){
            //已登陆
            signFlag=true;
            //读取string判断课程是否选中
            tempTest = "cpp,java";
            try {
                GetYixuan("http://192.168.31.238:12345/subjects/"+readInfo(readId()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{//未登录
            signFlag=false;
//            cpp.setVisibility(View.GONE);
//            c.setVisibility(View.GONE);
//            add.setVisibility(View.GONE);
//            sjjg.setVisibility(View.GONE);
//            sjk.setVisibility(View.GONE);
//            sf.setVisibility(View.GONE);
//            yy.setVisibility(View.GONE);
        }

//        initFruits();
//        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
//        StaggeredGridLayoutManager layoutManager = new
//                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//        Fragment2Adapter adapter = new Fragment2Adapter(itemFragment2s);
//        recyclerView.setAdapter(adapter);

        cpp = getActivity().findViewById(R.id.cpp);
        c = getActivity().findViewById(R.id.c);
        add = getActivity().findViewById(R.id.add);
        sjjg = getActivity().findViewById(R.id.sjjg);
        sjk = getActivity().findViewById(R.id.sjk);
        sf = getActivity().findViewById(R.id.sf);
        yy = getActivity().findViewById(R.id.yy);


        add.setOnClickListener(v -> {
            if(signFlag){
                Intent intent01 = new Intent();
                intent01.setClass(getActivity(), Curriculum.class);
                startActivity(intent01);
                //Toast.makeText(getActivity(), "全部", 0).show();

            }else{
                    Toast.makeText(getActivity(), "请先登录后操作", 0).show();

            }
        });


//        one = getActivity().findViewById(R.id.twoOne);
//        one.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("WrongConstant")
//            @Override
//            public void onClick(View v) {
//
//                if(signFlag){
//
//                    try {
//                        GetYixuan("http://192.168.31.238:12345/subjects/"+readInfo(readId()));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
////                    // TODO Auto-generated method stub
////                    if (tempTest.contains("C++")) {
////                        cpp.setVisibility(View.VISIBLE);
////                    }
////                    if (tempTest.contains("C--")) {
////                        c.setVisibility(View.VISIBLE);
////                    }
//                    //add.setVisibility(View.VISIBLE);
////                    Toast.makeText(getActivity(), "收藏", 0).show();
//
//                }else{
//                    Toast.makeText(getActivity(), "请先登录后操作", 0).show();
//
//                }
//            }
//        });

//        two = getActivity().findViewById(R.id.twoTwo);
//        two.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("WrongConstant")
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
////                Intent intent01=new Intent();
////                intent01.setClass(getActivity(), Recommend.class);
////                startActivity(intent01);
////                Toast.makeText(getActivity(), "推荐", 0).show();
//                    updata2();
////                c.setVisibility(View.GONE);
////                cpp.setVisibility(View.VISIBLE);
////                add.setVisibility(View.GONE);
////                    sjjg.setVisibility(View.GONE);
////                    sjk.setVisibility(View.GONE);
////                    sf.setVisibility(View.GONE);
////                    yy.setVisibility(View.GONE);
//            }
//        });

        three = getActivity().findViewById(R.id.twoThree);
        three.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if(signFlag){
                    Intent intent01 = new Intent();
                    intent01.setClass(getActivity(), Curriculum.class);
                    startActivity(intent01);
                    //Toast.makeText(getActivity(), "全部", 0).show();

                }else{
                    Toast.makeText(getActivity(), "请先登录后操作", 0).show();

                }


            }
        });

//        four = getActivity().findViewById(R.id.twoFour);
//        four.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("WrongConstant")
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//
//                if(signFlag){
//                    Intent intent01 = new Intent();
//                    intent01.setClass(getActivity(), Collection.class);
//                    startActivity(intent01);
//                    //Toast.makeText(getActivity(), "收藏", 0).show();
//
//                }else{
//                    Toast.makeText(getActivity(), "请先登录后操作", 0).show();
//
//                }
//
//
//            }
//        });
//
//        oneSize = getActivity().findViewById(R.id.oneSize);
//        oneSize.setOnClickListener(v -> {
//            if(signFlag) {
//                try {
//                    GetData("kemu_name", "Cpp", "http://192.168.31.238/web/GetChapter.php");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }else{
//                Toast.makeText(getActivity(), "请先登录后操作", 0).show();
//
//            }
//        });
//
//        threeSize = getActivity().findViewById(R.id.threeSize);
//        threeSize.setOnClickListener(v -> {
//            if(signFlag) {
//            try {
//                GetData("kemu_name","sjjg","http://192.168.31.238/web/GetChapter.php");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            }else{
//                Toast.makeText(getActivity(), "请先登录后操作", 0).show();
//
//            }
//        });
//
//        fourSize = getActivity().findViewById(R.id.fourSize);
//        fourSize.setOnClickListener(v -> {
//            if(signFlag) {
//            try {
//                GetData("kemu_name","sjk","http://192.168.31.238/web/GetChapter.php");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }else{
//            Toast.makeText(getActivity(), "请先登录后操作", 0).show();
//
//        }
//        });
//
//        fiveSize = getActivity().findViewById(R.id.fiveSize);
//        fiveSize.setOnClickListener(v -> {
//            if(signFlag) {
//            try {
//                GetData("kemu_name","sf","http://192.168.31.238/web/GetChapter.php");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            }else{
//                Toast.makeText(getActivity(), "请先登录后操作", 0).show();
//
//            }
//        });
//
//        sixSize = getActivity().findViewById(R.id.sixSize);
//        sixSize.setOnClickListener(v -> {
//
//            if(signFlag) {
//            try {
//                GetData("kemu_name","yy","http://192.168.31.238/web/GetChapter.php");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }else{
//            Toast.makeText(getActivity(), "请先登录后操作", 0).show();
//
//        }
//        });
//
//        twoSize = getActivity().findViewById(R.id.twoSize);
//        twoSize.setOnClickListener(v -> {
//
//            if(signFlag) {
//            try {
//                GetData("kemu_name","C","http://192.168.31.238/web/GetChapter.php");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            }else{
//                Toast.makeText(getActivity(), "请先登录后操作", 0).show();
//
//            }
//        });

    }

    //刷新界面
    private void updata2(){
        List<ItemFragment2> itemFragment2s = new ArrayList<>();

        try {
            initFruits(itemFragment2s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        Fragment2Adapter adapter = new Fragment2Adapter(itemFragment2s,cwjHandler);
        recyclerView.setAdapter(adapter);
    }

    private void updateUI() {

        if (tempTest.contains("Cpp")) {
            cpp.setVisibility(View.VISIBLE);
        }else {
            cpp.setVisibility(View.GONE);
        }

        if (tempTest.contains("C--")) {
            c.setVisibility(View.VISIBLE);
        }else {
            c.setVisibility(View.GONE);
        }

        if (tempTest.contains("数据结构")) {
            sjjg.setVisibility(View.VISIBLE);
        }else {
            sjjg.setVisibility(View.GONE);
        }

        if (tempTest.contains("数据库")) {
            sjk.setVisibility(View.VISIBLE);
        }else {
            sjk.setVisibility(View.GONE);
        }

        if (tempTest.contains("算法分析与设计")) {
            sf.setVisibility(View.VISIBLE);
        }else {
            sf.setVisibility(View.GONE);
        }

        if (tempTest.contains("英语")) {
            yy.setVisibility(View.VISIBLE);
        }else {
            yy.setVisibility(View.GONE);
        }
    }

    public String readId() {
        String result = "";
        try {
            FileInputStream fin = getActivity().openFileInput("UserInfo");
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            result = EncodingUtils.getString(buffer,"UTF-8");
            fin.close();
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //从读取的文件获取用户ID
    public String readInfo(String tempInfo){
        try {
            JSONObject jsonArray = new JSONObject(tempInfo);
                String id = jsonArray.getString("id");

            //String lv ="10";
                return id;
        } catch (JSONException e) {
            //Toast.makeText(getActivity(),"信息读取错误",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return "error";
    }

    /**
     * Android原生分享功能
     * 默认选取手机所有可以分享的APP
     */
    public void allShare() {
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
        share_intent.setType("text/plain");//设置分享内容的类型
        share_intent.putExtra(Intent.EXTRA_SUBJECT, "分享");//添加分享内容标题
        share_intent.putExtra(Intent.EXTRA_TEXT, "share with you:" + "android");//添加分享内容
        //创建分享的Dialog
        share_intent = Intent.createChooser(share_intent, "share");
        startActivity(share_intent);
    }

    /**
     * Android原生分享功能
     *
     * @param appName:要分享的应用程序名称
     */
    private void share(String appName) {
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);
        share_intent.setType("text/plain");
        share_intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        share_intent.putExtra(Intent.EXTRA_TEXT, "推荐您使用一款软件:" + appName);
        share_intent = Intent.createChooser(share_intent, "分享");
        startActivity(share_intent);
    }

    public void GetJson(String name) throws IOException {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    String uu = "http://192.168.31.238/web/Doc/" + name + ".json";
                    Log.d("url", "url=============" + uu);
                    URL url = new URL(uu);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        String result = String.valueOf(inputStream);//将流转换为字符串。
                        Log.d("kwwl", "result=============" + result);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }

                        Log.d("as", "aaaaaaaaaaaaaaaaa=============" + response.toString());
                        saveccc(response.toString());
                    } else {
                        Looper.prepare();
                        //Toast.makeText(SignActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Looper.prepare();
                    // Toast.makeText(SignActivity.this,"登录失败  请联系开发者",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        }).start();

    }

    public void saveccc(String temp) {
        String FILENAME = "c++.json";
        FileOutputStream fos = null;
        try {
            fos = getActivity().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(temp.getBytes());
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initBanner() {

        //CircleIndicator circleIndicator = new CircleIndicator(this.getActivity());
        RoundLinesIndicator indicator = blan.findViewById(R.id.indicatorrr);
        myBanner = (Banner) blan.findViewById(R.id.banner);

        //默认直接设置adapter就行了
        myBanner.setAdapter(new ImageAdapter(DataBean.getTestData()));
        myBanner.setUserInputEnabled(true);
        myBanner.isAutoLoop(true);
        myBanner.addOnPageChangeListener(this);

        myBanner.setIndicator(indicator, false);
        myBanner.setIndicatorSelectedWidth((int) BannerUtils.dp2px(15));
        //myBanner.setDelayTime(5000);
        myBanner.start();


    }

    public Integer activityChangeFragment(String data) {
//        if (myBanner == null){
//            System.out.println("banner==null");
//        }
        //myBanner.setCurrentItem(0);
        return DataBean.getTestData().get(nowPage).imageUrl;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //nowPage = position;
        //LogUtils.d("tag", "第"+position+"张轮播图");
    }

    @Override
    public void onPageSelected(int position) {
        //LogUtils.d("tag", "2"+position+"张轮播图");
        nowPage = position;
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setNowPage(DataBean.getTestData().get(position).imageUrl);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void GetData(String subject_id,String subject_name,String url) throws IOException {


        new Thread(() -> {

            try {
                URL uu = new URL(url);
                Log.d("Internet类","url============="+uu);
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

                    interres = response.toString();
                    Log.d("列表内容传递======",interres);


                    Intent intent01 = new Intent();
                    intent01.setClass(getActivity(), ChapterActivity.class);
                    intent01.putExtra("subject_name", subject_name);
                    intent01.putExtra("subject_id", subject_id);
                    intent01.putExtra("item", interres);
                    Log.d("列表内容传递======",interres);
                    //getActivity().finish();
                    startActivity(intent01);
                    //getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    getActivity().finish();

//                    progressDialog.dismiss();
                }
                else{
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    //获取已选课程
    public void GetYixuan( String url) throws IOException {

        new Thread(() -> {

            try {
                URL uu = new URL(url);
                Log.d("获取已选","url============="+url);
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

                    tempTest  = response.toString();
                    cwjHandler.post(mUpdateResults);

                   // Message message = null;
                    //message.what = 0x11;
                   // cwjHandler.sendMessage(message);

                    Log.d("用户已选======",tempTest);


//                    progressDialog.dismiss();
                }
                else{
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!isFirstLoading) {
            //如果不是第一次加载，刷新数据
            if(signFlag) {
                try {
                    GetYixuan( "http://192.168.31.238:12345/subjects/getById/"+readInfo(readId()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //updateUI();
        }

        isFirstLoading = false;
    }

    private void initFruits(List<ItemFragment2> itemFragment2s) throws JSONException {

        try {
            LogUtils.d("获取到的已选列表==="+tempTest);
            JSONArray jsonArray = new JSONArray(tempTest);
            int subject_size = jsonArray.length();
            LogUtils.d("获取到的科目数量==="+subject_size);
            for(int i =0 ; i < subject_size;i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                ItemFragment2 apple = new ItemFragment2(jsonObject.getString("idSubject"),
                        getRandomLengthName(jsonObject.getString("nameSubject")), R.drawable.cpp,jsonObject.getString("sizeSubject"));
                itemFragment2s.add(apple);
            }
        } catch(Exception exception){
            exception.printStackTrace();
        }

        ItemFragment2 apple = new ItemFragment2("0",
                getRandomLengthName("添加课程"), R.drawable.aaaaaaadddddd,"0");
        itemFragment2s.add(apple);

    }
    private String getRandomLengthName(String name) {
        Random random = new Random();
        int length = random.nextInt(20) + 1;
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        return builder.toString();
    }


}
