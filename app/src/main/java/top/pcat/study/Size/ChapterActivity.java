package top.pcat.study.Size;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.apkfuns.logutils.LogUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.flyco.tablayout.SlidingTabLayout;

import es.dmoral.toasty.Toasty;
import top.pcat.study.Utils.FileTool;
import top.pcat.study.Fragment.BlankFragment5;
import top.pcat.study.MainActivity;
import top.pcat.study.R;
import top.pcat.study.SpeechRecognition.ActivityOnlineRecog;
import top.pcat.study.Utils.StatusBarUtil;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static top.pcat.study.MainActivity.getStatusBarHeight;

public class ChapterActivity extends AppCompatActivity {
    private LinearLayout bar;
    private int bar_height;
    private float x1;
    private float x2;
    private float y1;
    private int result;
    private float y2;
    private FileTool ft;
    private List<Chap> chapList = new ArrayList<>();
    private Handler handler;
    String timu_name;
    private int timu_size;
    private String subject_name;
    private String zhang_name;
    private String item = "123";
    private int sum = 0;
    private LinearLayout kaoshi;
    int pageIndex = 0;
    private SlidingTabLayout mTab;
    private ViewPager mVp;
    private ArrayList<Fragment> mFragments;
    private String[] mTitlesArrays = {"全部", "未完成", "已完成"};
    RelativeLayout mTouchLayout;
    private String subject_id;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        LogUtils.d("启动ChapterActivity");
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);

//        mTouchLayout = findViewById(R.id.main_touch_layout);
//
//        mTouchLayout.setOnTouchListener(new View.OnTouchListener() {
//            float startX;
//            float startY;//没有用到
//            float endX;
//            float endY;//没有用到
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        startX = event.getX();
//                        startY = event.getY();
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        endX = event.getX();
//                        endY = event.getY();
//                        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
//                        Point size = new Point();
//                        windowManager.getDefaultDisplay().getSize(size);
//                        int width = size.x;
//                        if (startX - endX >= (width / 8)){// startX - endX 大于0 且大于宽的1/8 可以往后翻页
//                            if (pageIndex == 0){
//                                mVp.setCurrentItem(1);
////                                mTextPager.setCurrentItem(1, true);
//                            }else if (pageIndex == 1){
//                                mVp.setCurrentItem(2);
////                                mTextPager.setCurrentItem(2, true);
//                            }
//                        }else if (endX - startX  >= (width / 8)){ // endX - startX   大于0 且大于宽的1/8 可以往前翻页
//                            if (pageIndex == 2){
//                                mVp.setCurrentItem(1);
////                                mTextPager.setCurrentItem(1, true);
//                            }else if (pageIndex == 1){
//                                mVp.setCurrentItem(0);
////                                mTextPager.setCurrentItem(0, true);
//                            }
//                        }
//
//                        break;
//                }
//                return true;
//            }
//        });

        initView();

        //状态栏高度
        int barsize = getStatusBarHeight(this);
        LinearLayout bar_wei = findViewById(R.id.barheight);
        bar_wei.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, barsize));

        //返回
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            finish();
            Intent it = new Intent(this, MainActivity.class);
            it.putExtra("page", 1);
            startActivity(it);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        //获取科目名称
        Intent intent = getIntent();
        subject_name = intent.getStringExtra("subject_name");
        subject_id = intent.getStringExtra("subject_id");

        //获取章节JSON
        Intent intent2 = getIntent();
        item = intent2.getStringExtra("item");
        try {
            initItem();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //设置题目总数
        TextView allSize = findViewById(R.id.timusize);
        allSize.setText(String.valueOf(sum));

        //设置页面标题
        TextView bar_name = findViewById(R.id.kemu_name);
        bar_name.setText(subject_name);

        ChAdapter adapter = new ChAdapter(ChapterActivity.this,
                R.layout.chap_item, chapList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setDividerHeight(0);
        listView.setDivider(null);
        listView.setAdapter(adapter);

        //考试按钮
        kaoshi = findViewById(R.id.kaoshi);
        kaoshi.setOnClickListener(v -> {
            Intent intent01 = new Intent();
            intent01.setClass(ChapterActivity.this, AHHHHHH.class);
            intent01.putExtra("timu_size", String.valueOf(sum));
            intent01.putExtra("subject_name", subject_name);
            startActivity(intent01);
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Chap posion = chapList.get(position);

            Log.d("===", "===\n");
            //Toast.makeText(ChapterActivity.this, String.valueOf(position+1),
            //Toast.LENGTH_SHORT).show();
            //Log.d("ChapterActivity.this","你点击了第"+String.valueOf(position+1)+"章，科目id："+posion.getChapter_id());
            String chapter_id = posion.getChapter_id();

            timu_name = subject_id + "_" + chapter_id + "_" + posion.getChapter_version();
            LogUtils.d("你点击了第" + String.valueOf(timu_name));

            try {
                timu_size = getTiMuSize(position);
                LogUtils.d("题目数量===========" + String.valueOf(timu_size));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //String aa = readsizefile();
            File path = new File(ChapterActivity.this.getFilesDir().getAbsolutePath() + "/problems/" + timu_name + ".json");
            if (ft.isFileExists(path.toString())) {
                Intent intent01 = new Intent();
                intent01.setClass(ChapterActivity.this, ActivityOnlineRecog.class);
                intent01.putExtra("subject_name", subject_name + (position + 1));
                intent01.putExtra("subject_id", subject_id);
                intent01.putExtra("file_name", timu_name);
                intent01.putExtra("chapter_id", chapter_id);
                intent01.putExtra("zhang_size", String.valueOf(position + 1));
                intent01.putExtra("timu_size", String.valueOf(timu_size));
                intent01.putExtra("kemu_right_name", subject_name);
                finish();
                startActivity(intent01);
                //LogUtils.d("跳转========","JAVA"+(position+1)+String.valueOf(position+1));
                //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            } else {
                ProgressDialog progressDialog = new ProgressDialog(ChapterActivity.this);
                progressDialog.setTitle("请稍等");
                progressDialog.setMessage("首次加载数据...");
                progressDialog.setCancelable(true);
                progressDialog.show();


                Intent intent01 = new Intent();
                intent01.setClass(ChapterActivity.this, ActivityOnlineRecog.class);
                intent01.putExtra("subject_name", subject_name + (position + 1));
                intent01.putExtra("zhang_size", String.valueOf(position + 1));
                intent01.putExtra("timu_size", String.valueOf(timu_size));
                intent01.putExtra("file_name", timu_name);
                intent01.putExtra("kemu_right_name", subject_name);

                handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        //所有的事情处理完成后要退出looper，即终止Looper循环
                        //这两个方法都可以，有关这两个方法的区别自行寻找答案
                        finish();
                        startActivity(intent01);
                    }
                };

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //加载数据
                        result = 0;
                        try {
                            //要耗时运行的程序
//                                GetJson(timu_name);
                            Log.d("", "开始请求题目" + getResources().getString(R.string.network_url)+"/problems/" + subject_id + "/" + chapter_id + " ");
                            GetData("subject_name", subject_name, "zhang_size",
                                    String.valueOf(position + 1), getResources().getString(R.string.network_url)+"/problems/" + subject_id + "/" + chapter_id);
                            //saveItem(item);
                        } catch (Exception ex) {
                            result = -1;
                            Log.d("ChapterActivity:网络请求异常", String.valueOf(ex));
                        }

                        //更新界面fgjh

                        // Update the progress bar


//                        handler.post(new Runnable() {
//                            public void run() {
//                                if (result == 1) {
//                                    finish();
//                                    startActivity(intent01);
//                                    // overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//                                } else
//                                    Toast.makeText(ChapterActivity.this, "下载文件失败,请检查网络连接", Toast.LENGTH_SHORT).show();
//                            }
//                        });
                        progressDialog.dismiss();
                    }
                }).start();
                //Toast.makeText(ChapterActivity.this, timu_name, 0).show();

            }

//                Intent intent01=new Intent();
//                intent01.setClass(ChapterActivity.this, ExercisesActivity.class);
//                intent01.putExtra("subject_name","JAVA"+(position+1));
//                intent01.putExtra("zhang_size",String.valueOf(position+1));
//                finish();
//                startActivity(intent01);
//                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }

    private void initItem() throws JSONException {
        //JSON读取各个题目名称-----一类
        JSONArray jsonArray = new JSONArray(item);
        LogUtils.d("加载科目列表：" + item);
        Log.d("", "");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int size = jsonObject.getInt("chapterSize");
            //String subject_name = jsonObject.getString("chapterName");
            sum += size;
            //LogUtils.d("一类科目===========",size + subject_name);
            //initChaps(subject_name);

            Chap one = new Chap(jsonObject.getString("chapterId"), jsonObject.getString("chapterName"), R.drawable.do_do, jsonObject.getString("chapterVersion"));
//            if(i>1){
//                one = new Chap(jsonObject.getString("chapterId"),jsonObject.getString("chapterName"),R.drawable.do_do );
//
//            }
            chapList.add(one);
        }

    }

    private void initView() {
        mTab = (SlidingTabLayout) findViewById(R.id.tab);
        mVp = (ViewPager) findViewById(R.id.vp);

        mFragments = new ArrayList<>();
        mFragments.add(new BlankFragment5());
        mFragments.add(new BlankFragment5());
        mFragments.add(new BlankFragment5());


        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mVp.setAdapter(pagerAdapter);

        mTab.setViewPager(mVp, mTitlesArrays, this, mFragments);//tab和ViewPager进行关联
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

    private int getTiMuSize(int pos) throws JSONException {
        //JSON读取各个题目名称-----一类

        JSONArray jsonArray = new JSONArray(item);
        return jsonArray.getJSONObject(pos).getInt("chapterSize");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            Intent it = new Intent(this, MainActivity.class);
            it.putExtra("page", 1);
            startActivity(it);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
        return super.onKeyDown(keyCode, event);
    }

    public void GetJson(String name) throws IOException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String uu = "http://192.168.137.1:8888/pccp_war/getProblem";
                    LogUtils.d("url" + "url=============" + uu);
                    URL url = new URL(uu);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        String result1 = String.valueOf(inputStream);//将流转换为字符串。
                        LogUtils.d("kwwl" + "result=============" + result1);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }

                        LogUtils.d("as" + "aaaaaaaaaaaaaaaaa=============" + response.toString());
                        saveccc(response.toString());
                        result = 1;
                    } else {
                        Looper.prepare();
                        //Toast.makeText(ChapterActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
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

    public void GetData(String key, String val, String key1, String val1, String url) {

//        ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("请稍等");
//        progressDialog.setMessage("首次加载数据...");
//        progressDialog.setCancelable(true);
//        progressDialog.show();

        new Thread(() -> {

            try {
                URL uu = new URL(url);
                LogUtils.d("获取题目by章节科目" + "url=============" + uu);
                HttpURLConnection connection = (HttpURLConnection) uu.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();


                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    String result2 = String.valueOf(inputStream);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    saveccc(response.toString());
                    result = 1;
                    LogUtils.d("列表内容传递======" + response.toString());

                    Message msg = Message.obtain();
                    msg.what = 0;
                    //创建Bundle
                    Bundle bundle = new Bundle();
                    bundle.putString("key","我是一笑消息");
                    //为Message设置Bundle数据
                    msg.setData(bundle);
                    //发送消息
                    handler.sendMessage(msg);
//                    progressDiaLogUtils.dismiss();
                } else {
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("ChapterActivity:网络请求异常2", String.valueOf(e));
            }
        }).start();
    }

    public void saveccc(String temp) {

        boolean saveFlag = FileIOUtils.writeFileFromString(getFilesDir().getAbsolutePath() + "/problems/" + timu_name, temp);
        LogUtils.d("保存文件:" + getFilesDir().getAbsolutePath() + "/problems/" + timu_name + ":" + saveFlag);
        //Toasty.success(ChapterActivity.this,"保存文件:"+getFilesDir().getAbsolutePath() + "/problems/"+timu_name+":"+saveFlag);
    }

    public void saveItem(String temp) {
        String FILENAME = "chapter" + ".json";
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(temp.getBytes());
            fos.close();
            result = 1;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    private void initChaps(String aa) {
//        //for (int i = 0; i < 1; i++) {
//            Chap one = new Chap(aa, R.drawable.do_do);
//            chapList.add(one);
//        //}
//    }

    public String readsizefile() {
        String result = "";
        try {
            FileInputStream fin = openFileInput("timu_size.json");
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

    //读取chapter.json
    public String readitemfile() {
        String result = "";
        try {
            FileInputStream fin = openFileInput("chapter.json");
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

    private int getItemSize(String temp, int po) throws JSONException {
        LogUtils.d(po);
        JSONArray jsonArray = new JSONArray(temp);
//        for(int i=0;i<jsonArray.length();i++){
//            JSONObject jsonObject = jsonArray.getJSONObject(po);
//            int id = jsonObject.getInt("changeFlag");
        return po;
//        }
    }

    private int getNewItemSize(String temp, int po) throws JSONException {
        JSONArray jsonArray = new JSONArray(item);
//        for(int i=0;i<jsonArray.length();i++){
//        JSONObject jsonObject = jsonArray.getJSONObject(po);
//        int id = jsonObject.getInt("change_flag");
        return po;
//        }
    }

    private boolean updateFlag(int po) throws JSONException {
        String temp = readitemfile();
        int flag = getItemSize(temp, po);
        int newflag = getNewItemSize(temp, po);
        LogUtils.d("旧题目版本====" + flag + "新题目版本====" + newflag);
        if (flag == newflag) return true;
        else return false;

    }
}
