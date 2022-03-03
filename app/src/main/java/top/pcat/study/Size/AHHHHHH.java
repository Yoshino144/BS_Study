package top.pcat.study.Size;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.apkfuns.logutils.LogUtils;

import top.pcat.study.Utils.FileTool;
import top.pcat.study.R;
import top.pcat.study.Utils.StatusBarUtil;
import top.pcat.study.View.CircleRippleView;
import top.pcat.study.View.OclockView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class AHHHHHH extends AppCompatActivity {

    private int set_size = 10;
    private FileTool ft;
    private OclockView oclockView;
    private TextView timus;
    private TextView timun;
    private LinearLayout one;
    private LinearLayout two;
    private LinearLayout three;
    private LinearLayout four;
    private int set_time = 10;
    private LinearLayout exa_ok;
    private TextView one_text;
    private TextView two_text;
    private TextView three_text;
    private TextView four_text;
    private LinearLayout view_one;
    private LinearLayout view_two;
    private String timuSize;
    private String timuName;
    private TextView exa_time;
    public static final int UPDATA = 0x1;
    private int StartTime = 10*60*1000;
    private LinearLayout back;
    private ImageView mHeadImage;
    private ImageView mRadarImage;
    private CircleRippleView mCircleRippleView;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATA:
                    ShowUi();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ahhhhh);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        StatusBarUtil.setStatusBarMode(this,true,R.color.touming);
        File path = new File(getFilesDir().getAbsolutePath()+"/randomFlag.json");

        FileTool.deleteFile(path);

        initUI();

        Intent intent2 = getIntent();
        timuSize = intent2.getStringExtra("timu_size");
        timuName = intent2.getStringExtra("kemu_name");

        one_text = findViewById(R.id.one_text);
        two_text = findViewById(R.id.two_text );
        three_text = findViewById(R.id.three_text);
        four_text = findViewById(R.id.four_text);
        exa_ok = findViewById(R.id.exa_ok);
        view_one = findViewById(R.id.view_one);
        view_two = findViewById(R.id.view_two);

        one = findViewById(R.id.one);
        one.setOnClickListener(v -> {
            //Toast.makeText(this,"1",Toast.LENGTH_SHORT).show();
            set_time = 10;
            StartTime = set_time*60*1000;
            set_size = 10;
            one_text.setTextColor(getResources().getColor(R.color.black));
            two_text.setTextColor(getResources().getColor(R.color.text_hui));
            three_text.setTextColor(getResources().getColor(R.color.text_hui));
            four_text.setTextColor(getResources().getColor(R.color.text_hui));
        });
        two = findViewById(R.id.two);
        two.setOnClickListener(v -> {
            //Toast.makeText(this,"1",Toast.LENGTH_SHORT).show();
            set_time = 20;
            StartTime = set_time*60*1000;
            set_size = 20;
            one_text.setTextColor(getResources().getColor(R.color.text_hui));
            two_text.setTextColor(getResources().getColor(R.color.black));
            three_text.setTextColor(getResources().getColor(R.color.text_hui));
            four_text.setTextColor(getResources().getColor(R.color.text_hui));

        });
        three = findViewById(R.id.three);
        three.setOnClickListener(v -> {
            //Toast.makeText(this,"1",Toast.LENGTH_SHORT).show();
            set_time = 30;
            StartTime = set_time*60*1000;
            set_size = 30;
            one_text.setTextColor(getResources().getColor(R.color.text_hui));
            two_text.setTextColor(getResources().getColor(R.color.text_hui));
            three_text.setTextColor(getResources().getColor(R.color.black));
            four_text.setTextColor(getResources().getColor(R.color.text_hui));

        });
        four = findViewById(R.id.four);
        four.setOnClickListener(v -> {
            //Toast.makeText(this,"1",Toast.LENGTH_SHORT).show();
            set_time = Integer.parseInt(timuSize);
            set_size = Integer.parseInt(timuSize);
            one_text.setTextColor(getResources().getColor(R.color.text_hui));
            two_text.setTextColor(getResources().getColor(R.color.text_hui));
            three_text.setTextColor(getResources().getColor(R.color.text_hui));
            four_text.setTextColor(getResources().getColor(R.color.black));

        });

        exa_ok.setOnClickListener(v->{
            view_one.setVisibility(View.GONE);
            view_two.setVisibility(View.VISIBLE);
            GetData("kemu_name",timuName,"size",String.valueOf(set_size),"http://192.168.137.1/web/Random.php");

        });
    }

    private void ShowUi(){
        view_one.setVisibility(View.GONE);
        view_two.setVisibility(View.GONE);
        Intent intent01 = new Intent();
        intent01.setClass(AHHHHHH.this, Examination.class);
        intent01.putExtra("timu_size", String.valueOf(set_size));
        intent01.putExtra("kemu_name", timuName);
        intent01.putExtra("StartTime",String.valueOf(StartTime));
        startActivity(intent01);
        finish();
    }

    private void initUI() {
        mRadarImage = (ImageView) findViewById(R.id.radar_image);
        Animation mAnimation = AnimationUtils.loadAnimation(this, R.anim.radar_search_animation);
        mRadarImage.startAnimation(mAnimation);

        mCircleRippleView = (CircleRippleView) findViewById(R.id.radar_bg_image);

        mHeadImage = (ImageView)findViewById(R.id.radar_head_image);

        File path2 = new File(getFilesDir().getAbsolutePath()+"/UserImg.png");
        if(ft.isFileExists(path2.toString())){
            //ImageView headImage = getActivity().findViewById(R.id.hearImg);

            Bitmap bitmap = BitmapFactory.decodeFile(getFilesDir().getAbsolutePath()+"/UserImg.png");
//            headImage.setImageBitmap(bitmap);
            mHeadImage.setImageBitmap(bitmap);
        }
//        mHeadImage.setOnClickListener(v->{
//            mCircleRippleView.startRipple();
////            ScaleAnimation animation =new ScaleAnimation(1f, 1.5f, 1f, 1.5f,
////                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
////            animation.setDuration(1500);
////            mHeadImage.startAnimation(animation);
//            Animation mAnimation2 = AnimationUtils.loadAnimation(this, R.anim.radar_image_scale);
//            mHeadImage.startAnimation(mAnimation2);
//        });

    }

    public void GetData(String key, String val,String key1, String val1, String url)  {

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

                String body = key + "="+val + "&" + key1 + "=" + val1;
                //LogUtils.d(username,password);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                writer.write(body);
                writer.close();

                int responseCode = connection.getResponseCode();
                if(responseCode == HttpURLConnection.HTTP_OK){
                    InputStream inputStream = connection.getInputStream();
                    String result2 = String.valueOf(inputStream);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while((line = reader.readLine())!= null){
                        response.append(line);
                    }

                    saveccc(response.toString());
                    LogUtils.d("列表内容传递======",response.toString());

//                    progressDiaLogUtils.dismiss();
                }
                else{
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void saveccc(String temp) {
        String FILENAME = "random" + ".json";
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(temp.getBytes());
            fos.close();
            new Thread(){
                @Override
                public void run() {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message message = new Message();
                    message.what = UPDATA;
                    handler.sendMessage(message);
                }
            }.start();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
