package top.pcat.study.Curriculum;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dou361.dialogui.DialogUIUtils;

import top.pcat.study.R;
import top.pcat.study.Utils.StatusBarUtil;
import top.pcat.study.View.Item;
import top.pcat.study.View.ItemAdapter;
import top.pcat.study.View.LogUtils;

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

import static top.pcat.study.MainActivity.getStatusBarHeight;

public class Curriculum extends AppCompatActivity {
    private LinearLayout back_but;
    private List<Item> itemList = new ArrayList<>();
    private List<Item> itemList2 = new ArrayList<>();
    private TextView one;
    private TextView two;
    private LinearLayout one_list;
    private LinearLayout two_list;
    private String subject_json;
    private String res;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private Context mContext;
    private String tempTest;
    private final Handler cwjHandler = new Handler();
    private final Runnable mUpdateResults = this::first;

    private LinearLayout ooo;
    private LinearLayout ttt;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_yixuan);

        //获得控件
        WebView webView = (WebView) findViewById(R.id.wv_webview);
        //访问网页
        webView.loadUrl("http://192.168.31.238:12345/yixuan/df6f4977e1711b31ff6481403cd6de2b");
        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        webView.setWebViewClient(new WebViewClient() {
                                     @Override
                                     public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                         //使用WebView加载显示url
                                         view.loadUrl(url);
                                         //返回true
                                         return true;
                                     }
                                 });


                //setContentView(R.layout.curriculum);
//        mContext = getApplication();
//        DialogUIUtils.init(mContext);
//        one = findViewById(R.id.one);
//        two = findViewById(R.id.two);
//        one_list = findViewById(R.id.one_list);
//        two_list = findViewById(R.id.two_list);
//        ooo = findViewById(R.id.ooo);
//        ttt = findViewById(R.id.ttt);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        StatusBarUtil.setStatusBarMode(this,true,R.color.write_fan);
//        //状态栏高度
//        int barsize=getStatusBarHeight(this);
//        LinearLayout bar_wei = findViewById(R.id.bar_id);
//        bar_wei.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, barsize));
//
//        //返回
//        back_but = findViewById(R.id.back);
//        back_but.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    UpYixuan("id",readInfo(readId()),"all",tempTest,"http://192.168.31.238:8888/pccp_war/upYixuan");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        //网络加载科目名称
//        try {
//            GetData("null","null","http://192.168.31.238:12345/subjects");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //一类菜单
//        //first();
//
//        one.setOnClickListener(v -> {
//            first();
//            //点击切换---------一类
//            one_list.setVisibility(View.VISIBLE);
//            two_list.setVisibility(View.GONE);
//            one.setTextColor(Color.parseColor("#000000"));
//            one.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
//            ooo.setVisibility(View.VISIBLE);
//            ttt.setVisibility(View.GONE);
//
//            two.setTextColor(Color.parseColor("#777777"));
//            two.setTextSize(TypedValue.COMPLEX_UNIT_DIP,13);
//
//        });
//
//        two.setOnClickListener(v -> {
//            //二类菜单
//            RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.recycler_view2);
//            LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
//            recyclerView2.setLayoutManager(layoutManager2);
//            ItemAdapter adapter2 = new ItemAdapter(this,itemList2);
//            recyclerView2.setAdapter(adapter2);
//            //点击切换---------二类
//            one_list.setVisibility(View.GONE);
//            two_list.setVisibility(View.VISIBLE);
//            one.setTextColor(Color.parseColor("#777777"));
//            one.setTextSize(TypedValue.COMPLEX_UNIT_DIP,13);
//
//            ooo.setVisibility(View.GONE);
//            ttt.setVisibility(View.VISIBLE);
//
//            two.setTextColor(Color.parseColor("#000000"));
//            two.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
//        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            try {
                UpYixuan("id", readInfo(readId()), "all", tempTest, "http://192.168.31.238:8888/pccp_war/upYixuan");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void first() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(Curriculum.this);
        recyclerView.setLayoutManager(layoutManager);
        ItemAdapter adapter = new ItemAdapter(this, itemList);
        recyclerView.setAdapter(adapter);
        progressDialog.dismiss();
    }

    private void Do() throws JSONException {
        Log.d("题目加载结果===========", subject_json);

        //专业课类
        initItem();
        //外课
        initItem2();
    }

    public void change(String kemu, boolean set) {
        if (kemu.equals("C")) {
            if (set) {
                tempTest += "C--";
                Log.d("选课结果+++=========", tempTest);
            } else {
                tempTest = deleteSubString(tempTest, "C--");
                Log.d("选课结果---=========", tempTest);
            }
        } else if (kemu.equals("C++")) {
            if (set) {
                tempTest += "Cpp";
                Log.d("选课结果+++=========", tempTest);
            } else {
                tempTest = deleteSubString(tempTest, "Cpp");
                Log.d("选课结果---=========", tempTest);
            }
        } else {
            if (set) {
                tempTest += kemu;
                Log.d("选课结果+++=========", tempTest);
            } else {
                tempTest = deleteSubString(tempTest, kemu);
                Log.d("选课结果---=========", tempTest);
            }
        }
        //修改已选科目
    }

    public String deleteSubString(String str1, String str2) {
        StringBuffer sb = new StringBuffer(str1);

        while (true) {
            int index = sb.indexOf(str2);
            if (index == -1) {
                break;
            }
            sb.delete(index, index + str2.length());

        }
        return sb.toString();
    }

    private void initItem() throws JSONException {
        //JSON读取各个题目名称-----一类
        JSONArray jsonArray = new JSONArray(subject_json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int id = jsonObject.getInt("subjectId");
            if (id < 1000) {
                String subject_name = jsonObject.getString("subjectName");
                Log.d("一类科目===========", id + subject_name);
                Item pear;
                if (subject_name.equals("C")) {
                    if (tempTest.contains("C--")) {
                        pear = new Item(subject_name, true);
                    } else {
                        pear = new Item(subject_name, false);
                    }
                } else {
                    LogUtils.d(tempTest + "  " + subject_name);
                    if (tempTest.contains(subject_name)) {
                        pear = new Item(subject_name, true);
                    } else {
                        pear = new Item(subject_name, false);
                    }
                }
                itemList.add(pear);
            }
        }

    }

    private void initItem2() throws JSONException {
        //JSON读取各个题目名称-----二类
        JSONArray jsonArray = new JSONArray(subject_json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int id = jsonObject.getInt("subjectId");
            if (id > 1000) {
                String subject_name = jsonObject.getString("subjectName");
                Log.d("二类科目===========", id + subject_name);
                Item aa;
                if (tempTest.contains(subject_name)) {
                    aa = new Item(subject_name, true);
                } else {
                    aa = new Item(subject_name, false);
                }
                itemList2.add(aa);
            }
        }
    }

    public void GetData(String key, String val, String url) throws IOException {

        progressDialog = new ProgressDialog(Curriculum.this);
        progressDialog.setTitle("请稍等");
        progressDialog.setMessage("数据载入中...");
        progressDialog.setCancelable(true);
        progressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL uu = new URL(url);
                    Log.d("Internet类", "url=============" + uu);
                    HttpURLConnection connection = (HttpURLConnection) uu.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.connect();

                    String body = key + "=" + val;
                    //Log.d(username,password);
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                    writer.write(body);
                    writer.close();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        String result = String.valueOf(inputStream);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }

                        res = response.toString();
                        subject_json = res;
                        LogUtils.d(subject_json);
                        GetYixuan("id", readInfo(readId()), "http://192.168.31.238:8888/pccp_war/getYixuan");


                    } else {
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //获取已选课程
    public void GetYixuan(String key, String val, String url) throws IOException {

//        ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle("请稍等");
//        progressDialog.setMessage("首次加载数据...");
//        progressDialog.setCancelable(true);
//        progressDialog.show();

        new Thread(() -> {

            try {
                URL uu = new URL(url);
                Log.d("Internet类", "url=============" + uu);
                HttpURLConnection connection = (HttpURLConnection) uu.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setUseCaches(false);
                connection.connect();

                String body = key + "=" + val;
                Log.d("key=========val====", body);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                writer.write(body);
                writer.close();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    String result = String.valueOf(inputStream);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    tempTest = response.toString();
                    LogUtils.d(tempTest);
                    Log.d("已选题目列表", tempTest);
                    Do();
                    cwjHandler.post(mUpdateResults);

                    Log.d("用户已选======", tempTest);


//                    progressDialog.dismiss();
                } else {
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public String readId() {
        String result = "";
        try {
            FileInputStream fin = openFileInput("UserInfo");
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            result = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String readInfo(String tempInfo) {
        try {
            JSONObject jsonArray = new JSONObject(tempInfo);
            String lv = jsonArray.getString("id");
            return lv;
        } catch (JSONException e) {
            Toast.makeText(this, "信息读取错误", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return "error";
    }


    public void UpYixuan(String key, String val, String key2, String val2, String url) throws IOException {

        new Thread(() -> {

            try {
                URL uu = new URL(url);
                Log.d("Internet类", "url=============" + uu);
                HttpURLConnection connection = (HttpURLConnection) uu.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setUseCaches(false);
                connection.connect();

                String body = key + "=" + val + "&" + key2 + "=" + val2;
                Log.d("key=========val====", body);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                writer.write(body);
                writer.close();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    String result = String.valueOf(inputStream);//将流转换为字符串。
                    //Log.d("kwwl","result============="+result);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    //Log.d("pccp",response.toString());

                    //saveData(response.toString());
//                            finish();
//                            Intent it = new Intent(ExercisesActivity.this, MainActivity.class);
//                            it.putExtra("page",1);
//                            startActivity(it);
                    //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    //ma.gotopage(1);
                    finish();
                    Looper.prepare();
                    //Toast.makeText(this,"上传成功",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } else {
                    Looper.prepare();
                    Toast.makeText(this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }

                finish();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
