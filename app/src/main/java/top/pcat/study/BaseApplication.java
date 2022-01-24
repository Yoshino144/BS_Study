package top.pcat.study;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import io.rong.imkit.RongIM;
import top.pcat.study.Utils.FileTool;
import com.tencent.bugly.crashreport.CrashReport;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class BaseApplication extends Application {

    private FileTool ft;
    private int user_id;
    @Override
    public void onCreate() {
        super.onCreate();



        RongIM.init(this, "pvxdm17jpdhyr", true);


        Context context = getApplicationContext();
// 获取当前包名
        String packageName = context.getPackageName();
// 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
// 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        //bugly 崩溃上传
        CrashReport.initCrashReport(getApplicationContext(), "627bd6c51d", false);
//        readInfo(readlv());
//        File path = new File(getFilesDir().getAbsolutePath()+"/Login.txt");
//        if(ft.isFileExists(path.toString())){
//            JMessageClient.init(this,true);
//            JMessageClient.setDebugMode(BuildConfig.DEBUG);
////            JMessageClient.register(String.valueOf(user_id),"ppppcccc5555",null, null);
//            JMessageClient.login(String.valueOf(user_id), "ppppcccc5555", new BasicCallback() {
//                @Override
//                public void gotResult(int i, String s) {
//                    if (i == 0) {
//                        LogUtils.d("登录=====pushcallback:" + String.valueOf(user_id) + i + " " + s);
//                    }else{
//                        JMessageClient.login(String.valueOf(user_id), "ppppcccc5555", new BasicCallback() {
//                            @Override
//                            public void gotResult(int i, String s) {
//                                if (i == 0) {
//                                    LogUtils.d("登录=====pushcallback:" + String.valueOf(user_id) + i + " " + s);
//                                }else{
//
//                                }
//                            }
//                        });
//                    }
//                }
//            });
//        }
//        else{;
//        }

//        JMessageClient.setDebugMode(true);
//        JMessageClient.init(this);


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

    public void readInfo(String tempInfo){
        try {
            JSONArray jsonArray = new JSONArray(tempInfo);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                user_id = jsonObject.getInt("user_id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

}
