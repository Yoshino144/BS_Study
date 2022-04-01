package top.pcat.study.Utils;

import android.content.Context;

import com.apkfuns.logutils.LogUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.google.gson.Gson;

import top.pcat.study.Pojo.UserInfo;

public class GetUser {

    public static boolean isLogin(Context context){
        return FileUtils.isFileExists(context.getFilesDir().getAbsolutePath() + "/userInfo");
    }

    public static String getUserId(Context context){
        return getUserInfo(context).getId();
    }

    public static UserInfo getUserInfo(Context context){
        String userInfoJson = FileIOUtils.readFile2String(context.getFilesDir().getAbsolutePath() + "/userInfo");
        //LogUtils.d("读取的用户信息"+userInfoJson);
        Gson gson = new Gson();
        return  gson.fromJson(userInfoJson, UserInfo.class);

    }

    public static String getRongToken(Context context){
        return FileIOUtils.readFile2String(context.getFilesDir().getAbsolutePath() + "/rongToken");
    }
}
