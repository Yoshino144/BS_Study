package top.pcat.study.Size;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.apkfuns.logutils.LogUtils;
import com.google.android.material.tabs.TabLayout;
import top.pcat.study.Utils.FileTool;
import top.pcat.study.R;
import top.pcat.study.Utils.StatusBarUtil;
import top.pcat.study.View.CircleRippleView;
import top.pcat.study.View.OclockView;
import top.pcat.study.View.PcAdapterViewpager_two;
import top.pcat.study.Exercises.PcViewPager;

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
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Examination extends AppCompatActivity {
    private float mHourDegree;
    private float mMinuteDegree;
    private float mSecondDegree;
    private String timuSize;
    private String timuName;
    private int set_time = 10;
    private int set_size = 10;
    private FileTool ft;
    private OclockView oclockView;
    private TextView timus;
    private TextView timun;
    private LinearLayout one;
    private LinearLayout two;
    private LinearLayout three;
    private LinearLayout four;
    private LinearLayout exa_ok;
    private TextView one_text;
    private TextView two_text;
    private TextView three_text;
    private TextView four_text;
    private LinearLayout view_one;
    private LinearLayout view_two;
    private TextView exa_time;
    public static final int UPDATA = 0x1;
    private int StartTime = 90000;
    private LinearLayout back;
    private long exitTime = 0;
    private String json="[{}]";

    private ImageView mHeadImage;
    private ImageView mRadarImage;
    private CircleRippleView mCircleRippleView;
    private CountDownTimer timer;private PcViewPager viewPager;
    private List<View> list = new ArrayList<View>();
    private PcAdapterViewpager_two adapterView;
    private TabLayout mTabLayout;
    private String pc="";
    private Handler handlerXuan = new Handler();
    private boolean[] Done = new boolean[5000];
    private String[] flag =  new String[5000];
    int PageNew;
    private TextView wrongview;
    private TextView trueview;
    private int wrongSize = 0;
    private int trueSize = 0;
    long mLastTime=0;
    long mCurTime=0;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 123:
                    timer.cancel();
                    StartTime(StartTime);
                    break;
                case 1:
                    Toast.makeText(Examination.this,"再按一次提交考试",Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    Intent intent01 = new Intent();
                    intent01.setClass(Examination.this, OHHHHHH.class);
//                intent01.putExtra("timu_size", String.valueOf(sum));
//                intent01.putExtra("kemu_name", kemu_name );
                    intent01.putExtra("size", timuSize );
                    startActivity(intent01);
                    finish();
                    Toast.makeText(Examination.this,"这是双击事件",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        StatusBarUtil.setStatusBarMode(this,true,R.color.touming);

        Intent intent2 = getIntent();
        timuSize = intent2.getStringExtra("timu_size");
        timuName = intent2.getStringExtra("kemu_name");
        String Time = intent2.getStringExtra("StartTime");
        StartTime = Integer.parseInt(Time);


        setContentView(R.layout.activity_exa);
        LogUtils.d("当前科目=="+timuName,"当前章节=="+timuSize+"题目数量=="+timuSize);
        File flagpath = new File(getFilesDir().getAbsolutePath()+"/"+"random"+"Flag.json");
        if(ft.isFileExists(flagpath.toString())){
        }
        else{
            String json = getFromAssets("Flag.json");
            save(json);
        }

        //json = read("random"+"Flag.json");
        LogUtils.d("ExerActi============","开始加载"+timuName);
        initView2();
        timus = findViewById(R.id.textView);
        timun = findViewById(R.id.textView2);
        exa_time = findViewById(R.id.exa_time);
        back = findViewById(R.id.back);

//        OclockView view = new OclockView(this,StartTime);
        OclockView view = (OclockView)findViewById(R.id.clock);
//        view.findViewById(R.id.clock);

        StartTime(StartTime);

        timun.setText(timuName);
        timus.setText(timuSize);

        back.setOnClickListener(v->{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mLastTime=mCurTime;
                    mCurTime= System.currentTimeMillis();
                    if(mCurTime-mLastTime<2000){//双击事件
                        mCurTime =0;
                        mLastTime = 0;
                        handler.removeMessages(1);
                        handler.sendEmptyMessage(2);
                    }else{//单击事件
                        handler.sendEmptyMessageDelayed(1, 310);
                    }

                }
            }).start();
        });

    }

    public String getFromAssets(String fileName){
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            while((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void save(String temp) {
        String FILENAME = "random"+"Flag.json";
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(temp.getBytes());
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("ResourceType")
    public void initView2() {
        //wrongview = findViewById(R.id.wrongsize);
        //trueview = findViewById(R.id.truesize);
        mTabLayout = findViewById(R.id.tab_layout);
        viewPager = (PcViewPager) findViewById(R.id.viewpager);
        mTabLayout.setupWithViewPager(viewPager);
        //dev = findViewById(R.id.develop);
        long startTime = System.currentTimeMillis();
        for(int i =0; i < Integer.parseInt(timuSize) ;i++){
            list.add(getLayoutInflater().inflate(R.layout.lastview_two,null));
        }
        long endTime = System.currentTimeMillis();
        LogUtils.d("time1===============",(endTime - startTime) + "ms");
        pc= read("random"+".json");
        adapterView = new PcAdapterViewpager_two(this,Done,flag,list,"random",pc,"null","null",handlerXuan);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapterView);
        mTabLayout.addOnTabSelectedListener(mTabSelectedListener);
        viewPager.addOnPageChangeListener(mPageChangeListener);
        long endTime2 = System.currentTimeMillis();
        LogUtils.d("time2===============",(endTime2 - endTime) + "ms");
    }

    private int allSize = 0;
    public void addwrong(int id,String res){
        wrongSize += 1;
        allSize +=1;
        //size();
        LogUtils.d("错误"+String.valueOf(id),"================");
        saveRes(String.valueOf(id),"wrong "+res);
    }

    public void addtrue(int id){
        trueSize += 1;
        allSize +=1;
        //size();
        LogUtils.d("正确"+String.valueOf(id),"==============");
        saveRes(String.valueOf(id),"true");
    }

    public void saveJson(String id,String flag){
        try {
            JSONObject jsonObject = null;
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                jsonObject.put(id,flag);
                LogUtils.d("jsonObject=============", String.valueOf(jsonObject));
            }
            String zxc = "["+ jsonObject +"]";
            json = zxc;
            save(zxc);
            LogUtils.d("=============", zxc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setFlag(int pos ,String val){
        flag[pos] = val;
    }

    public void setDone(int pos){
        Done[pos] = true;
    }

    public void saveRes(String id,String flag){
        saveJson(id,flag);
    }

    public String read(String file) {
        String result = "";
        try {
            FileInputStream fin = openFileInput(file);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            result = EncodingUtils.getString(buffer,"UTF-8");
            fin.close();
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "error";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    private PcViewPager.OnPageChangeListener mPageChangeListener = new PcViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //Log. d( "dml", "onPageScrolled:::::position = " + position);
        }

        @Override
        public void onPageSelected(int position) {
            Log. d( "dml", "onPageSelected:::::select ============ " + position) ;
            PageNew = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //Log. d( "dml", "onPageScrollStateChanged:::::state = " + state) ;
        }
    };

    private  TabLayout.OnTabSelectedListener mTabSelectedListener = new TabLayout.OnTabSelectedListener(){

        @Override
        public void onTabSelected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    public Handler GetHandler(){
        return handler;
    }
    /**
     * 倒计时显示
     */
    private void StartTime(long time) {

        timer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                float milliSecond = millisUntilFinished;
                //秒
                float second = milliSecond / 1000;
                //分
                float minute = second / 60;
                //时
                float hour = minute / 60;
                float mSecond = second - (int)hour*60 - (int)minute*60;
                float mMinute = minute - (int)hour*60;
                float mHour = hour;
                //LogUtils.d("second = " + (int)mSecond + "  min = " + (int)mMinute + "  hour = " + (int)mHour);
                String time_show = (int)mHour + ":" + (int)mMinute + ":" + (int)mSecond;
                exa_time.setText(time_show);

                //LogUtils.d("jsonObject=============", json);

                mSecondDegree = mSecond;
                mMinuteDegree = mMinute;
                mHourDegree = mHour;
            }

            @Override
            public void onFinish() {
            }
        }.start();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出考试", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                Intent intent01 = new Intent();
                intent01.setClass(Examination.this, OHHHHHH.class);
//                intent01.putExtra("timu_size", String.valueOf(sum));
//                intent01.putExtra("kemu_name", kemu_name );
                intent01.putExtra("size", timuSize );
                startActivity(intent01);
                finish();
                timer.cancel();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public int GetSecond(){
        return (int) mSecondDegree;
    }
    public int GetMinute(){
        return (int) mMinuteDegree;
    }
    public int GetHour(){
        return (int) mHourDegree;
    }
    public int GetTime(){ return StartTime;}
}
