package top.pcat.study.Http;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.google.gson.Gson;

import java.io.IOException;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import top.pcat.study.Pojo.Msg;
import top.pcat.study.R;
import top.pcat.study.User.LoginActivity;

public class GetUserInfoToFile {

    public static void getUserInfo(String uuid, Context context) throws IOException {
        Request request = new Request.Builder()
                .url(context.getResources().getString(R.string.network_url)+"/users/" + uuid + "/infos")
                .get()
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toasty.error(context, "网络连接失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                String rr = response.body().string();
                LogUtils.d("获取用户信息返回内容" + rr);
                Msg msg = gson.fromJson(rr, Msg.class);
                if (msg.getStatus() == 200) {


                    LogUtils.d(FileIOUtils.writeFileFromString(
                            context.getFilesDir().getAbsolutePath() + "/userInfo", String.valueOf(msg.getData())));


                    Looper.prepare();
                    LogUtils.d("用户信息成功");
                    Toasty.success(context, "用户信息" + msg.getMessage(), Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } else {
                    Looper.prepare();
                    Toasty.error(context, "用户信息" + msg.getMessage(), Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        });
    }

}
