package top.pcat.study.User;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.apkfuns.logutils.LogUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import top.pcat.study.MainActivity;
import top.pcat.study.Pojo.LoginReq;
import top.pcat.study.Pojo.UserInfo;
import top.pcat.study.R;
import top.pcat.study.Pojo.Msg;
import top.pcat.study.Utils.GetUser;
import top.pcat.study.Utils.StatusBarUtil;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, PlatformActionListener {

    //??????????????????
    private int page = 4;
    private String uuid = "";
    private LinearLayout loginWechat;
    private LinearLayout loginSina;
    private LinearLayout loginQQ;
    RelativeLayout mTouchLayout;
    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    LogUtils.d("????????????id:" + uuid);
                    try {
                        getUserInfo();
                        getToken();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @SuppressLint({"ClickableViewAccessibility", "CheckResult"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        StatusBarUtil.setStatusBarMode(this, true, R.color.write_fan);


        TextView wx = findViewById(R.id.wx);
        wx.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        wx.getPaint().setAntiAlias(true);

        TextView wb = findViewById(R.id.wb);
        wb.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        wb.getPaint().setAntiAlias(true);

        LinearLayout phone = findViewById(R.id.phone);
        LinearLayout userpas = findViewById(R.id.userpas);
        LinearLayout aaaaa = findViewById(R.id.aaaaaa);
        LinearLayout bbbbb = findViewById(R.id.bbbbb);
        LinearLayout ccccc = findViewById(R.id.cccccc);
        LinearLayout ddddd = findViewById(R.id.dddddd);
        LinearLayout eeeee = findViewById(R.id.eeeeee);

        phone.setOnClickListener(v -> {
            sendCode(this);
//            phone.setBackgroundResource(R.drawable.shape_sign_son);
//            userpas.setBackgroundResource(R.drawable.shape_sign);
//            aaaaa.setVisibility(View.GONE);
//            ccccc.setVisibility(View.GONE);
//            ddddd.setVisibility(View.VISIBLE);
//            eeeee.setVisibility(View.VISIBLE);
        });
        userpas.setOnClickListener(v -> {
            phone.setBackgroundResource(R.drawable.shape_sign);
            userpas.setBackgroundResource(R.drawable.shape_sign_son);
            aaaaa.setVisibility(View.VISIBLE);
            ccccc.setVisibility(View.VISIBLE);
            ddddd.setVisibility(View.GONE);
            eeeee.setVisibility(View.GONE);
        });

        //?????????????????????
        mTouchLayout = findViewById(R.id.main_touch_layout);
        final int[] pageIndex = {0};
        mTouchLayout.setOnTouchListener(new View.OnTouchListener() {
            float startX;
            float startY;//????????????
            float endX;
            float endY;//????????????

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        endX = event.getX();
                        endY = event.getY();
                        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                        Point size = new Point();
                        windowManager.getDefaultDisplay().getSize(size);
                        int width = size.x;
                        if (startX - endX >= (width / 8)) {// startX - endX ??????0 ???????????????1/8 ??????????????????
                            if (pageIndex[0] == 0) {
                                pageIndex[0] = 1;
                                phone.setBackgroundResource(R.drawable.shape_sign_son);
                                userpas.setBackgroundResource(R.drawable.shape_sign);
                                aaaaa.setVisibility(View.GONE);
                                ccccc.setVisibility(View.GONE);
                                ddddd.setVisibility(View.VISIBLE);
                                eeeee.setVisibility(View.VISIBLE);
//                                mVp.setCurrentItem(1);
//                                mTextPager.setCurrentItem(1, true);
                            }
                        } else if (endX - startX >= (width / 8)) { // endX - startX   ??????0 ???????????????1/8 ??????????????????
                            if (pageIndex[0] == 1) {
//                                mVp.setCurrentItem(0);
                                pageIndex[0] = 0;
                                phone.setBackgroundResource(R.drawable.shape_sign);
                                userpas.setBackgroundResource(R.drawable.shape_sign_son);
                                aaaaa.setVisibility(View.VISIBLE);
                                ccccc.setVisibility(View.VISIBLE);
                                ddddd.setVisibility(View.GONE);
                                eeeee.setVisibility(View.GONE);
//                                mTextPager.setCurrentItem(1, true);
                            }
                        }

                        break;
                }
                return true;
            }
        });

        //????????????
        Intent intent = getIntent();
        page = intent.getIntExtra("page", 0);

        EditText editText1 = findViewById(R.id.username);
        EditText editText2 = findViewById(R.id.password);
        Button go = findViewById(R.id.gores);
        go.setOnClickListener(v -> {
            Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent1);
        });

        //????????????
        Button cc = findViewById(R.id.come);
        cc.setOnClickListener(v -> {
            String phoneCode = editText1.getText().toString();
            String password = editText2.getText().toString();
            try {
                LogUtils.d("?????????+????????????:code:" + phoneCode + " pw:" + password);
                Login(phoneCode, password);
            } catch (IOException e) {
                Toasty.error(LoginActivity.this, "???????????? ??????????????????", Toast.LENGTH_SHORT);
                e.printStackTrace();
            }
        });

        //????????????
        ImageView img = findViewById(R.id.loginback);
        img.setOnClickListener(v -> {
            finish();
            Intent it = new Intent(LoginActivity.this, MainActivity.class);
            it.putExtra("page", page);
            startActivity(it);
        });

        //???????????????
        loginWechat = findViewById(R.id.login_wechat);
        loginSina = findViewById(R.id.login_sina);
        loginQQ = findViewById(R.id.login_qq);
        loginWechat.setOnClickListener(this);
        loginSina.setOnClickListener(this);
        loginQQ.setOnClickListener(this);

    }

    /**
     * ????????????
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_wechat: {
                WeChat();
            }
            break;
            case R.id.login_sina: {
                Sina();
            }
            break;
            case R.id.login_qq:
                QQ();
                break;
            default:
                break;
        }
    }

    /**
     * qq??????
     */
    private void QQ() {
        Platform plat = ShareSDK.getPlatform(QQ.NAME);
        ShareSDK.setEnableAuthTag(true);
        plat.removeAccount(true);
        //plat.SSOSetting(false);
        plat.setPlatformActionListener(this);
        if (plat.isClientValid()) {

        }
        if (plat.isAuthValid()) {
        }
        //plat.authorize();	//????????????????????????
        plat.showUser(null);    //?????????????????????????????????????????????????????????????????????
    }

    /**
     * ??????
     */
    private void WeChat() {
        Platform plat = ShareSDK.getPlatform(Wechat.NAME);
        ShareSDK.setEnableAuthTag(true);
        plat.removeAccount(true);
        //plat.SSOSetting(false);
        plat.setPlatformActionListener(this);
        if (plat.isClientValid()) {

        }
        if (plat.isAuthValid()) {

        }
        //plat.authorize();	//????????????????????????
        plat.showUser(null);    //?????????????????????????????????????????????????????????????????????
    }

    /**
     * ????????????
     */
    private void Sina() {
        ShareSDK.setEnableAuthTag(true);
        Platform plat = ShareSDK.getPlatform("SinaWeibo");
        plat.removeAccount(true);
        //plat.SSOSetting(false);
        plat.setPlatformActionListener(this);
        if (plat.isClientValid()) {

        }
        if (plat.isAuthValid()) {

        }
        //plat.authorize();	//????????????????????????
        plat.showUser(null);    //?????????????????????????????????????????????????????????????????????
    }

    /**
     * ?????????
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            Intent it = new Intent(LoginActivity.this, MainActivity.class);
            it.putExtra("page", page);
            startActivity(it);
        }
        return super.onKeyDown(keyCode, event);
    }


    //????????????
    public void Login(String username, String pass) throws IOException {

        String pwMd5 = EncryptUtils.encryptMD5ToString(pass);
        LogUtils.d("???????????????" + getResources().getString(R.string.network_url)+"/users/" + username + "/" + pwMd5);
        Request request = new Request.Builder()
                .url(getResources().getString(R.string.network_url)+"/users/" + username + "/" + pwMd5)
                .get()
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toasty.error(LoginActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                String rr = response.body().string();
                LogUtils.d("????????????????????????" + rr);
                Msg msg = gson.fromJson(rr, Msg.class);
                if (msg.getStatus() == 200) {
                    LoginReq loginReq = gson.fromJson(msg.getData().toString(), LoginReq.class);
                    LogUtils.d("????????????" + loginReq);
                    Looper.prepare();
                    LogUtils.d("????????????");
                    LogUtils.d(FileIOUtils.writeFileFromString(
                            getFilesDir().getAbsolutePath() + "/userToken", gson.toJson(msg.getData())));

                    Message m = new Message();
                    m.what = 0;
                    uuid = loginReq.getUuid();
                    handler.sendMessage(m);

                    Toasty.success(LoginActivity.this, msg.getMessage(), Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } else {
                    Looper.prepare();
                    Toasty.error(LoginActivity.this, msg.getMessage(), Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        });
    }

    //??????token
    public void getToken() throws IOException {

        LogUtils.d("??????token???" + getResources().getString(R.string.network_url)+"/users/rongToken");
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formBodyBuilder.add("userId", uuid);
        Request request = new Request.Builder()
                .url(getResources().getString(R.string.network_url)+"/users/rongToken")
                .post(formBodyBuilder.build())
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toasty.error(LoginActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String rr = response.body().string();
                    LogUtils.d(FileIOUtils.writeFileFromString(
                            getFilesDir().getAbsolutePath() + "/rongToken", rr));

                }else{
                    Looper.prepare();
                    Toasty.error(LoginActivity.this, "??????????????????????????????????????????", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }

            }
        });
    }

    /**
     * ??????????????????
     *
     * @throws IOException
     */
    public void getUserInfo() throws IOException {
        Request request = new Request.Builder()
                .url(getResources().getString(R.string.network_url)+"/users/" + uuid + "/infos")
                .get()
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toasty.error(LoginActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                String rr = response.body().string();
                LogUtils.d("??????????????????????????????" + rr);
                Msg msg = gson.fromJson(rr, Msg.class);
                if (msg.getStatus() == 200) {

                    LogUtils.d(FileIOUtils.writeFileFromString(
                            getFilesDir().getAbsolutePath() + "/userInfo", gson.toJson(msg.getData())));


                    Looper.prepare();
                    LogUtils.d("??????????????????");
                    Intent it = new Intent(LoginActivity.this, MainActivity.class);
                    it.putExtra("page", page);
                    LogUtils.d("??????:MainActivity???page???" + page);
                    startActivity(it);
                    finish();
                    Toasty.success(LoginActivity.this, "????????????" + msg.getMessage(), Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } else {
                    Looper.prepare();
                    Toasty.error(LoginActivity.this, "????????????" + msg.getMessage(), Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        });
    }


    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

        LogUtils.d("=========", " _QQ: -->> onComplete: Platform:" + platform.toString());
        LogUtils.d("=========", " _QQ: -->> onComplete: hashMap:" + hashMap);
        LogUtils.d("=========", " _QQ: -->> onComplete: token:" + platform.getDb().getToken());
        String userId = platform.getDb().getUserId();
        LogUtils.d("getUserId============", userId);

        LogUtils.d("Sign????????????????????????======", String.valueOf(hashMap));

        JSONObject jsonObject = new JSONObject(hashMap);
        String name = null;
        String sex = null;
        String year = null;
        try {
            name = jsonObject.getString("nickname");
            sex = jsonObject.getString("gender");
            year = jsonObject.getString("year") + "-01-01 00:00:00";
            LogUtils.d("-============", name + sex + year);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //checkQQUser(userId, name, sex, year);
        //inent.putExtras(bundle);
        //inent.setClass(this, TagsItemActivity.class);
        //startActivity(inent);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Log.e("SDK+", " SdkTagsMainActivity onError platform: " + platform +
                " i: " + i + " throwable " + throwable.getMessage());
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Log.e("SDK+", " SdkTagsMainActivity onCancel platform: " + platform +
                " i: " + i);
    }
//
//    void checkQQUser(String userId, String name, String sex, String year) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    URL url = new URL("http://192.168.137.1/web/CheckUser.php");
//                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                    connection.setRequestMethod("POST");
//                    connection.setDoOutput(true);
//                    connection.setDoInput(true);
//                    connection.setUseCaches(false);
//                    connection.connect();
//
//                    String body = "appName=QQ&" + "userAppId=" + userId + "&username=" + name + "&sex=" + sex + "&year=" + year;
//                    //LogUtils.d(username,password);
//                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
//                    writer.write(body);
//                    writer.close();
//
//                    int responseCode = connection.getResponseCode();
//                    if (responseCode == HttpURLConnection.HTTP_OK) {
//                        InputStream inputStream = connection.getInputStream();
//                        String result = String.valueOf(inputStream);//???????????????????????????
//                        //LogUtils.d("kwwl","result============="+result);
//                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//                        StringBuilder response = new StringBuilder();
//                        String line;
//                        while ((line = reader.readLine()) != null) {
//                            response.append(line);
//                        }
//                        //LogUtils.d("pccp",response.toString());
//                        String username = readSj(response.toString(), 0);
//                        GetImg(username);
//                        save(username);
//                        saveInfo(response.toString());
//                        UpData(username);
//
//                        Intent it = new Intent(LoginActivity.this, MainActivity.class);
//                        it.putExtra("page", 3);
//                        startActivity(it);
//
//                        finish();
//                        Looper.prepare();
//                        Toast.makeText(LoginActivity.this, "????????????", Toast.LENGTH_SHORT).show();
//                        Looper.loop();
//                    } else {
//                        Looper.prepare();
//                        Toast.makeText(LoginActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
//                        Looper.loop();
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Looper.prepare();
//                    Toast.makeText(LoginActivity.this, "????????????  ??????????????????", Toast.LENGTH_SHORT).show();
//                    Looper.loop();
//                }
//            }
//        }).start();
//    }


    /**
     * ???????????????
     *
     * @param context
     */
    public void sendCode(Context context) {
        RegisterPage page = new RegisterPage();
        //?????????????????????ui?????????????????????????????????????????????null
        page.setTempCode(null);
        page.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // ?????????????????????
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    // ?????????????????????86???
                    String country = (String) phoneMap.get("country");
                    // ?????????????????????13800138000???
                    String phone = (String) phoneMap.get("phone");
                    // TODO ??????????????????????????????????????????????????????

                    sendPhone(phone);


                } else {
                    // TODO ?????????????????????
                }
            }
        });
        page.show(context);
    }

    /**
     * ???????????????
     *
     * @param phone
     */
    private void sendPhone(String phone) {
        new Thread(() -> {
            try {
                Gson gson = new Gson();
                OkHttpClient client = new OkHttpClient();//????????????OKHttp?????????
                Request request = new Request.Builder()
                        .url(getResources().getString(R.string.network_url)+"/users/" + phone)
                        .get()
                        .build();//????????????Request??????
                Response response = client.newCall(request).execute();//??????????????????????????????
                String responseData = response.body().string();//?????????????????????
                Msg msg = gson.fromJson(responseData, Msg.class);
                LogUtils.d(responseData);
                if (msg.getStatus() != 200) {
                    LogUtils.d("=======" + phone, "?????????????????????-?????????" + responseData);
                    Looper.prepare();
                    Toasty.error(LoginActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } else {
                    LogUtils.d("=======" + phone, "?????????????????????" + responseData);


                    LoginReq loginReq = gson.fromJson(msg.getData().toString(), LoginReq.class);
                    Message m = new Message();
                    m.what = 0;
                    uuid = loginReq.getUuid();
                    handler.sendMessage(m);

                    Looper.prepare();
                    Toasty.success(LoginActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                    Looper.loop();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public String readSjph(String tempInfo, int postion) {
        try {
            JSONObject jsonObject = new JSONObject(tempInfo);

            return jsonObject.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tempInfo;
    }
}
