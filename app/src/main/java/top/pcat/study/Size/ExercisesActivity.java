package top.pcat.study.Size;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.apkfuns.logutils.LogUtils;
import com.baidu.aip.asrwakeup3.core.inputstream.InFileStream;
import com.baidu.aip.asrwakeup3.core.util.MyLogger;
import com.blankj.utilcode.util.FileIOUtils;
import com.google.android.material.tabs.TabLayout;
import top.pcat.study.Dialog.GoodsDialogFragment;
import top.pcat.study.Dialog.ICallBack;
import top.pcat.study.Utils.FileTool;
import top.pcat.study.R;
import top.pcat.study.Utils.StatusBarUtil;
import top.pcat.study.View.PcAdapterViewpager;
import top.pcat.study.View.PcViewPager;

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

public abstract class ExercisesActivity extends AppCompatActivity   {
    private PcViewPager viewPager;
    private List<View> list = new ArrayList<View>();
    private PcAdapterViewpager adapterView;
    private TabLayout mTabLayout;
    private LinearLayout back;
    private String pc="";
    private TextView wrongview;
    private TextView trueview;
    private String[] flag =  new String[5000];
    private int wrongSize = 0;
    private GoodsDialogFragment goodsDialogFragment;
    private int trueSize = 0;
    private int allSize = 0;
    private String json="[{}]";
    private boolean[] Done = new boolean[5000];
    private FileTool ft;
    private LinearLayout alllist;
    int PageNew;
    private String kemu_name = " ";
    private String zhang_size = " ";
    private int timu_size = 0;
    private String kemu_right_name;
    private String interres;
    private boolean mShow=true;
    //语音识别---------------------------------------------------
    protected TextView txtLog;
    protected Button btn;
    protected Button setting;
    protected TextView txtResult;
    private  LinearLayout dev;
    protected Handler handler;
    private LinearLayout mark;

    protected int layout;
    private int textId;

    long[] mHits = null;

    protected int textViewLines = 0;
    private Handler handlerXuan = new Handler();

    public ExercisesActivity() {}

    public ExercisesActivity(int textId) {
        this(textId, R.layout.activity_exercises);
    }

    public ExercisesActivity(int textId, int layout) {
        super();
        this.textId = textId;
        this.layout = layout;
    }

    //语音识别-结束-------------------------------------

    public Handler handler2 = new Handler(){
        public void handleMessage(Message msg){

            Toast.makeText(ExercisesActivity.this,"123"+msg,Toast.LENGTH_SHORT).show();
            int pos = msg.what;
            setCurrentView(pos);
        }
    };


    private String subject_id;
    private String chapter_id;
    private String file_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        StatusBarUtil.setStatusBarMode(this,true,R.color.touming);

        mark = findViewById(R.id.mark);
        mark.setOnClickListener(v->{
            setXuanXiang(1);
//            list.add(getLayoutInflater().inflate(R.layout.lastview,null));
//            adapterView.notifyDataSetChanged();
        });

        int barsize=getStatusBarHeight(this);
        LinearLayout bar_wei = findViewById(R.id.bar_wei);
        bar_wei.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, barsize));

        Intent intent = getIntent();
        kemu_name = intent.getStringExtra("kemu_name");
        zhang_size = intent.getStringExtra("zhang_size");
        String aa = intent.getStringExtra("timu_size");
        kemu_right_name=intent.getStringExtra("kemu_right_name");


        subject_id=intent.getStringExtra("subject_id");
        file_name=intent.getStringExtra("file_name");
        chapter_id=intent.getStringExtra("chapter_id");
        kemu_name=file_name;
        timu_size = Integer.parseInt(aa);

        //语音识别-----------------

        InFileStream.setContext(this);
        //setContentView(layout);
        initView();

        handler = new Handler() {

            /*
             * @param msg
             */
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                handleMsg(msg);
            }

        };
        MyLogger.setHandler(handler);
        initPermission();

        //语音识别-结束-------------------------------------

        LogUtils.d("当前科目=="+kemu_name+"当前章节=="+zhang_size+"题目数量=="+aa);
        File flagpath = new File(getFilesDir().getAbsolutePath()+"/"+kemu_name+"Flag.json");
        if(ft.isFileExists(flagpath.toString())){
        }
        else{
            String json = getFromAssets("Flag.json");
            save(json);
        }

        json = read(kemu_name+"Flag.json");
        LogUtils.d("加载题目"+kemu_name);
        initView2();
        back();
        size();
        alllist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGoodsDialogFragment(v);
            }
        });

        LinearLayout asb = findViewById(R.id.abs);
        asb.setOnClickListener(v -> {
            if (mHits == null) {
                mHits = new long[5];
            }
            System. arraycopy(mHits, 1, mHits, 0, mHits.length-1);
            mHits[ mHits. length-1] = SystemClock. uptimeMillis();
            if (SystemClock.uptimeMillis() - mHits[0] <= 3000) {
                mHits = null;
                if (mShow) {
                    Toast.makeText(this,"开启调试模式",Toast.LENGTH_SHORT).show();
                    dev.setVisibility(View.VISIBLE);
                    mShow = false;
                } else {
                    Toast.makeText(this,"关闭调试模式",Toast.LENGTH_SHORT).show();
                    dev.setVisibility(View.GONE);
                    mShow = true;
                }
            }
        });
    }

    public void setFlag(int pos ,String val){
        flag[pos] = val;
    }

    public void setDone(int pos){
        Done[pos] = true;
    }

    public void setXuanXiang(int num){
        if(num == 1){
            adapterView = new PcAdapterViewpager(ExercisesActivity.this,Done,flag,list,kemu_name,pc,String.valueOf(PageNew+1),"a",handlerXuan);
            viewPager.setOffscreenPageLimit(3);
            viewPager.setAdapter(adapterView);
            setCurrentView(PageNew);

            HandlerXuan("a");
        }else if(num == 2){
            adapterView = new PcAdapterViewpager(ExercisesActivity.this,Done,flag,list,kemu_name,pc,String.valueOf(PageNew+1),"b",handlerXuan);
            viewPager.setOffscreenPageLimit(3);
            viewPager.setAdapter(adapterView);
            setCurrentView(PageNew);

            HandlerXuan("b");
        }else if(num == 3){
            adapterView = new PcAdapterViewpager(ExercisesActivity.this,Done,flag,list,kemu_name,pc,String.valueOf(PageNew+1),"c",handlerXuan);
            viewPager.setOffscreenPageLimit(3);
            viewPager.setAdapter(adapterView);
            setCurrentView(PageNew);

            HandlerXuan("c");
        }else if(num == 4){
            adapterView = new PcAdapterViewpager(ExercisesActivity.this,Done,flag,list,kemu_name,pc,String.valueOf(PageNew+1),"d",handlerXuan);
            viewPager.setOffscreenPageLimit(3);
            viewPager.setAdapter(adapterView);
            setCurrentView(PageNew);

            HandlerXuan("d");
        }
    }

    public void HandlerXuan(String tt){
        Bundle bundle = new Bundle();
        bundle.putString("msg", tt);
//发送数据
        Message message = Message.obtain();
        message.setData(bundle);   //message.obj=bundle  传值也行
        message.what = 0x11;
        LogUtils.d("===============","send");
        handler.sendMessage(message);
    }

    public void openGoodsDialogFragment(View view) {

//        if (goodsDialogFragment == null) {
            goodsDialogFragment = new GoodsDialogFragment();
//        }

        if (goodsDialogFragment.isVisible()) {
            goodsDialogFragment.dismiss();
        } else {

//            intent01.putExtra("subject_id", subject_id);
//            intent01.putExtra("file_name", timu_name);
//            intent01.putExtra("chapter_id", chapter_id);

            Bundle bundle = new Bundle();
            bundle.putInt("size", timu_size);
            bundle.putString("name", kemu_name);
//
//            bundle.putString("subject_id", subject_id);
//            bundle.putString("file_name", file_name);
//            bundle.putString("chapter_id", chapter_id);

            goodsDialogFragment.setArguments(bundle);
            goodsDialogFragment.show(getSupportFragmentManager(), null);
        }


        goodsDialogFragment.sendMessage(new ICallBack() {
            @Override
            public void get_message_from_Fragment(String string) {
                String msg = string;
                Toast.makeText(ExercisesActivity.this,"sghdfjhg"+msg,Toast.LENGTH_SHORT).show();
            }
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


    @SuppressLint("ResourceType")
    public void initView2() {
        alllist = findViewById(R.id.alllist);
        wrongview = findViewById(R.id.wrongsize);
        trueview = findViewById(R.id.truesize);
        mTabLayout = findViewById(R.id.tab_layout);
        viewPager = (PcViewPager) findViewById(R.id.viewpager);
        mTabLayout.setupWithViewPager(viewPager);
        dev = findViewById(R.id.develop);
        long startTime = System.currentTimeMillis();
        for(int i =0; i < timu_size ;i++){
            list.add(getLayoutInflater().inflate(R.layout.lastview,null));
        }
        long endTime = System.currentTimeMillis();
        //LogUtils.d("time1===============",(endTime - startTime) + "ms");
        pc= read(file_name+".json");
        adapterView = new PcAdapterViewpager(ExercisesActivity.this,Done,flag,list,file_name,pc,"null","null",handlerXuan);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapterView);
        mTabLayout.addOnTabSelectedListener(mTabSelectedListener);
        viewPager.addOnPageChangeListener(mPageChangeListener);
        long endTime2 = System.currentTimeMillis();
        //LogUtils.d("time2===============",(endTime2 - endTime) + "ms");
    }


    public void size(){
        wrongview.setText(String.valueOf(wrongSize));
        trueview.setText(String.valueOf(trueSize));
    }

    public void addwrong(int id){
        wrongSize += 1;
        allSize +=1;
        size();
        LogUtils.d("错误"+String.valueOf(id),"================");
        saveRes(String.valueOf(id),"wrong");
    }

    public void addtrue(int id){
        trueSize += 1;
        allSize +=1;
        size();
        LogUtils.d("正确"+String.valueOf(id),"==============");
        saveRes(String.valueOf(id),"true");
    }

    public void saveRes(String id,String flag){
        saveJson(id,flag);
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

    public String read(String file) {
        return FileIOUtils.readFile2String(getFilesDir().getAbsolutePath() + "/problems/"+file);
    }

    public void save(String temp) {
        String FILENAME = kemu_name+"Flag.json";
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){

                    String username = readname();
                    String json = read(kemu_name+"Flag.json");
                    try {
                        UpData(username,json,allSize,kemu_name);
                        GetData("kemu_name",kemu_right_name,"http://192.168.31.238/web/GetChapter.php");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        return super.onKeyDown(keyCode, event);
    }

    public void back(){
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = readname();
                String json = read(kemu_name+"Flag.json");
                try {
                    UpData(username,json,allSize,kemu_name);
                    GetData("kemu_name",kemu_right_name,"http://192.168.31.238/web/GetChapter.php");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void GetData(String key, String val, String url) throws IOException {

//        ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("请稍等");
//        progressDialog.setMessage("首次加载数据...");
//        progressDialog.setCancelable(true);
//        progressDialog.show();

        new Thread(() -> {

            try {
                URL uu = new URL(url);
                LogUtils.d("Internet类","url============="+uu);
                HttpURLConnection connection = (HttpURLConnection) uu.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setUseCaches(false);
                connection.connect();

                String body = key + "="+val;
                //LogUtils.d(username,password);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                writer.write(body);
                writer.close();

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
                    LogUtils.d("列表内容传递======",interres);



                    Intent it = new Intent(ExercisesActivity.this, ChapterActivity.class);
                    it.putExtra("page",1);
                    it.putExtra("kemu_name", kemu_right_name);
                    it.putExtra("item", interres);
                    startActivity(it);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                    //progressDiaLogUtils.dismiss();
                }
                else{
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public String readname() {
        String result = "";
        try {
            FileInputStream fin = openFileInput("Login.txt");
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

    public void UpData(String username,String json,int allSize,String kemu_name) throws IOException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.31.238/web/UpUserData.php");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.connect();

                    String body = "username="+username+"&json="+json+"&allSize="+allSize+"&kemu_name="+kemu_name+"&id="+readInfo(readId());
                    LogUtils.d("======================",body);
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                    writer.write(body);
                    writer.close();

                    int responseCode = connection.getResponseCode();
                    if(responseCode == HttpURLConnection.HTTP_OK){
                        InputStream inputStream = connection.getInputStream();
                        String result = String.valueOf(inputStream);//将流转换为字符串。
                        //LogUtils.d("kwwl","result============="+result);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while((line = reader.readLine())!= null){
                            response.append(line);
                        }
                        //LogUtils.d("pccp",response.toString());

                            saveData(response.toString());
//                            finish();
//                            Intent it = new Intent(ExercisesActivity.this, MainActivity.class);
//                            it.putExtra("page",1);
//                            startActivity(it);
                            //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            //ma.gotopage(1);
                            Looper.prepare();
                            Toast.makeText(ExercisesActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                    }
                    else{
                        Looper.prepare();
                        Toast.makeText(ExercisesActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Looper.prepare();
                    Toast.makeText(ExercisesActivity.this,"上传失败  请反馈开发者",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        }).start();

    }

    public void saveData(String temp) {
        String FILENAME = "UserData";
        FileOutputStream fos = null;
        try {
            //文件路径  /data/data/com.example.myapplication/files/
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(temp.getBytes());
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setCurrentView(int index) {
        viewPager.setCurrentItem(index);
    }

    public void changeCurrentView(int index) {
        viewPager.setCurrentItem(PageNew+index);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager.removeOnPageChangeListener(mPageChangeListener);
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

    //语音识别-----------------

    void setpage(){

    }

    protected void handleMsg(Message msg) {
        if (txtLog != null && msg.obj != null) {
            textViewLines++;
            if (textViewLines > 100) {
                textViewLines = 0;
                txtLog.setText("");
            }
            txtLog.append(msg.obj.toString() + "\n");
        }
    }

    protected void initView() {
        txtResult =  findViewById(R.id.txtResult);
        txtLog =  findViewById(R.id.txtLog);
        btn =  findViewById(R.id.btn);
        setting = findViewById(R.id.setting);

        try {
            InputStream is = getResources().openRawResource(textId);
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            String descText = new String(bytes);
            txtLog.setText(descText);
        } catch (IOException e) {
            e.printStackTrace();
        }
        txtLog.append("\n");
    }

    /**
     * android 6.0 以上需要动态申请权限
     */
    private void initPermission() {
        String[] permissions = {
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                /* 下面是蓝牙用的，可以不申请
                Manifest.permission.BROADCAST_STICKY,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN
                */
        };

        ArrayList<String> toApplyList = new ArrayList<>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限.

            }
        }
        String[] tmpList = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
    }

    private void setStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());

    }


    //语音识别-结束-------------------------------------


    public String readId() {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String readInfo(String tempInfo){
        try {
            JSONArray jsonArray = new JSONArray(tempInfo);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String lv = jsonObject.getString("user_id");
                return lv;
            }
        } catch (JSONException e) {
            Toast.makeText(this,"信息读取错误",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return "error";
    }
}