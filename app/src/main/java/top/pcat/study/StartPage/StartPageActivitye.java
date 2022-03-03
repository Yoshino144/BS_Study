package top.pcat.study.StartPage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.apkfuns.logutils.LogUtils;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.mob.MobSDK;
import com.mob.OperationCallback;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import io.rong.imkit.RongIM;
import top.pcat.study.R;
import top.pcat.study.Utils.DatabaseHelper;
import top.pcat.study.Utils.FileTool;
import top.pcat.study.MainActivity;
import top.pcat.study.Utils.FontManager;
import top.pcat.study.privacy.CustomDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class StartPageActivitye extends AppCompatActivity {
    private String[] sentences = new String[]{"What is design?", "Design", "Design is not just", "what it looks like", "and feels like.", "Design", "is how it works.", "- Steve Jobs", "Older people", "sit down and ask,", "'What is it?'", "but the boy asks,", "'What can I do with it?'.", "- Steve Jobs", "Swift", "Objective-C", "iPhone", "iPad", "Mac Mini", "MacBook Pro", "Mac Pro", "爱老婆", "老婆和女儿"};

    private FileTool ft;
    private Calendar calendar = Calendar.getInstance();
private TextView top;
    private TextView hello;
    private TextView bot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        int week = calendar.get(Calendar.DAY_OF_WEEK);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        top = findViewById(R.id.start_top);
        hello = findViewById(R.id.start_hello);
        bot = findViewById(R.id.start_bot);
        Typeface type = Typeface.createFromAsset(this.getAssets(), "fonts/Jua-Regular.ttf");
        top.setTypeface(type);
        hello.setTypeface(type);
        Typeface type2 = Typeface.createFromAsset(this.getAssets(), "fonts/FZMWFont.ttf");
        bot.setTypeface(type);
        top.setText(hour+":"+minute + " | " + week + " | " + day + "TH");
        hello.setText("HELLO");
        bot.setText("Miao~");

        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) == 0) {
            File path = new File(getFilesDir().getAbsolutePath() + "/privacyFlag");
            if (ft.isFileExists(path.toString())) {
                Thread myThread = new Thread() {//创建子线程
                    @Override
                    public void run() {
                        try {
                            sleep(1000);//使程序休眠一秒
                            save(" ");
                            //广告
                            //Intent it = new Intent(getApplicationContext(), InitAdvActivity.class);
                            Intent it = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(it);
                            finish();//关闭当前活动
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                myThread.start();//启动线程
            } else {
                CustomDialog.Builder builder = new CustomDialog.Builder(this);
                builder.setMessage("    欢迎您使用本软件，在登录注册、语音服务、短信验证、快速登录等场景。我们将依据《隐私政策》来帮助你了解我们需要收集哪些数据。请您详细查看我们的隐私政策，详见：");
                builder.setTitle("服务协议与隐私政策");
                builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        submitPrivacyGrantResult(false);
                        finish();
                        //设置你的操作事项
                    }
                });

                builder.setNegativeButton("同意",
                        new android.content.DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                submitPrivacyGrantResult(true);
                                dialog.dismiss();
                                Thread myThread = new Thread() {//创建子线程
                                    @Override
                                    public void run() {
                                        try {
                                            sleep(80);//使程序休眠一秒
                                            save(" ");
                                            //广告
                                            //Intent it = new Intent(getApplicationContext(), InitAdvActivity.class);
                                            Intent it = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(it);
                                            finish();//关闭当前活动
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                myThread.start();//启动线程
                            }
                        });

                builder.create().show();
            }
        } else {
            finish();
        }

    }


    private void submitPrivacyGrantResult(boolean granted) {
        MobSDK.submitPolicyGrantResult(granted, new OperationCallback<Void>() {
            @Override
            public void onComplete(Void data) {
                LogUtils.d("", "隐私协议授权结果提交：成功");
            }

            @Override
            public void onFailure(Throwable t) {
                LogUtils.d("", "隐私协议授权结果提交：失败");
            }
        });
    }

    public void save(String temp) {
        String FILENAME = "privacyFlag";
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

}
