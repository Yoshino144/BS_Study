package top.pcat.study.User;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Looper;
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

import androidx.appcompat.app.AppCompatActivity;

import net.sqlcipher.database.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;
import top.pcat.study.MainActivity;
import top.pcat.study.R;
import top.pcat.study.Utils.StatusBarUtil;
import top.pcat.study.View.LogUtils;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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

    private int page = 3;
    private LinearLayout loginWechat;
    private LinearLayout loginSina;
    private LinearLayout loginQQ;
    RelativeLayout mTouchLayout;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        StatusBarUtil.setStatusBarMode(this, true, R.color.write_fan);
        InitializeSQLCipher();
        Touch();

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

        mTouchLayout = findViewById(R.id.main_touch_layout);

        final int[] pageIndex = {0};

        mTouchLayout.setOnTouchListener(new View.OnTouchListener() {
            float startX;
            float startY;//没有用到
            float endX;
            float endY;//没有用到

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
                        if (startX - endX >= (width / 8)) {// startX - endX 大于0 且大于宽的1/8 可以往后翻页
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
                        } else if (endX - startX >= (width / 8)) { // endX - startX   大于0 且大于宽的1/8 可以往前翻页
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

        //获取页数
        Intent intent = getIntent();
        page = intent.getIntExtra("page", 0);

        EditText editText1 = findViewById(R.id.username);
        EditText editText2 = findViewById(R.id.password);
        Button go = findViewById(R.id.gores);
        go.setOnClickListener(v -> {
            Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent1);
        });

        //点击登录
        Button cc = findViewById(R.id.come);
        cc.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                // TODO LOGIN

                String username = editText1.getText().toString();
                String password = editText2.getText().toString();


                try {
                    //DialogUIUtils dialogUIUtils = null;
                    //DialogUIUtils.showLoading(SignActivity.this, "登录中...", true, true, true, true).show();
                    Login(username, password);
                    //DialogUIUtils.dismiss();
                    Intent it = new Intent(LoginActivity.this, MainActivity.class);
                    it.putExtra("page", page);
                    startActivity(it);
                } catch (IOException e) {
                    Toast.makeText(LoginActivity.this, "登录失败  请联系开发者", Toast.LENGTH_SHORT);
                    e.printStackTrace();
                }
            }
        });

        ImageView img = findViewById(R.id.loginback);
        img.setOnClickListener(v -> {
            finish();
            Intent it = new Intent(LoginActivity.this, MainActivity.class);
            it.putExtra("page", page);
            startActivity(it);
        });

        loginWechat = findViewById(R.id.login_wechat);
        loginSina = findViewById(R.id.login_sina);
        loginQQ = findViewById(R.id.login_qq);
        loginWechat.setOnClickListener(this);
        loginSina.setOnClickListener(this);
        loginQQ.setOnClickListener(this);

    }

    private void Touch() {


    }

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
        //plat.authorize();	//要功能，不要数据
        plat.showUser(null);    //要数据不要功能，主要体现在不会重复出现授权界面
    }

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
        //plat.authorize();	//要功能，不要数据
        plat.showUser(null);    //要数据不要功能，主要体现在不会重复出现授权界面
    }

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
        //plat.authorize();	//要功能，不要数据
        plat.showUser(null);    //要数据不要功能，主要体现在不会重复出现授权界面
    }

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

    public void GetImg(String username) throws IOException {

        new Thread(() -> {
            try {
                URL url = new URL("http://192.168.31.238/web/GetImg.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setUseCaches(false);
                connection.connect();

                String body = "username=" + username;
                //Log.d(username,password);
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
                    if (response.toString().indexOf("/9j/") != -1) {

                        Bitmap bitmap = base64ToBitmap(response.toString());
                        saveImg(bitmap);
                    }

                    //saveData(response.toString());
                    //finish();
//                        Looper.prepare();
//                        Toast.makeText(SignActivity.this,"同步成功",Toast.LENGTH_SHORT).show();
//                        Looper.loop();
                } else {
                    Looper.prepare();
                    //Toast.makeText(SignActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Looper.prepare();
                // Toast.makeText(SignActivity.this,"同步失败  请反馈开发者",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();

    }

    public void saveImg(Bitmap bitmap) {
        String FILENAME = "UserImg.png";
        FileOutputStream fos = null;
        try {
            //文件路径  /data/data/com.example.myapplication/files/
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public void UpData(String username) throws IOException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.31.238/web/GetUserData.php");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.connect();

                    String body = "username=" + username;
                    //Log.d(username,password);
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

                        saveData(response.toString());
                        finish();
                        Looper.prepare();
                        Toast.makeText(LoginActivity.this, "同步成功", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } else {
                        Looper.prepare();
                        Toast.makeText(LoginActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Looper.prepare();
                    Toast.makeText(LoginActivity.this, "同步失败  请反馈开发者", Toast.LENGTH_SHORT).show();
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

    public void save(String temp) {
        String FILENAME = "Login.txt";
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

    public void saveInfo(String temp) {
        String FILENAME = "UserInfo";
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

    public String md5Decode32(String content) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(content.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException", e);
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    //执行登录
    public void Login(String username, String pass) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    LogUtils.d("进行登录："+"http://192.168.31.238:12345/users/"+username+"/"+md5Decode32(pass));
                    URL url = new URL("http://192.168.31.238:12345/users/"+username+"/"+md5Decode32(pass));
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();

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
                        //Log.d("pccp", response.toString());

                        if (response.toString().contains(username)) {

                            //com.apkfuns.logutils.LogUtils.d("++++++++++++++++"+response.toString());
                            JSONObject jsonObject1 = new JSONObject(response.toString());

                            //com.apkfuns.logutils.LogUtils.d("++++++++++++++++"+jsonObject1.toString());
                            //com.apkfuns.logutils.LogUtils.d("++++++++++++++++"+jsonObject1.getJSONObject("data").toString());

                            JSONObject jsonObject2 = new JSONObject(jsonObject1.getJSONObject("data").toString());

                            String id = jsonObject2.getString("id");

                            //保存头像
                            GetImg(id);
                            //保存id
                            save(id);
                            //保存用户信息
                            saveInfo(jsonObject2.toString());

                            UpData(id);

                            finish();
                            Looper.prepare();
                            Toasty.success(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            Looper.loop();


                        } else {
                            Looper.prepare();
                            Toasty.warning(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    } else {
                        Looper.prepare();
                        Toasty.error(LoginActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Looper.prepare();
                    Toast.makeText(LoginActivity.this, "登录失败  请联系开发者", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        }).start();

    }
//初始化数据库
    private void InitializeSQLCipher() {
        SQLiteDatabase.loadLibs(this);
        File databaseFile = getDatabasePath("user.db");
        databaseFile.mkdirs();
        databaseFile.delete();
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(databaseFile, "ppcc", null);
        database.execSQL("CREATE TABLE `user`  ( id varchar(40)  ,\n" +
                "  `name` varchar(40)  ,\n" +
                "  `password` varchar(255)  ,\n" +
                "  `phone` varchar(20)  ,\n" +
                "  `sex` int  ,\n" +
                "  `birthday` datetime(0)  ,\n" +
                "  `city` varchar(50) ,\n" +
                "  `school` varchar(20),\n" +
                "  `college` varchar(20) ,\n" +
                "  `major` varchar(20) ,\n" +
                "  `grade` int ,\n" +
                "  `position` varchar(50) ,\n" +
                "  `delete` int ,\n" +
                "  `pic` varchar(255) ,\n" +
                "  `registration_time` datetime(0) ,\n" +
                "  `text` varchar(255) ,\n" +
                "  PRIMARY KEY (`id`) )");
//        database.execSQL("insert into t1(a, b) values(?, ?)", new Object[]{"one for the money",
//                "two for the show"});
    }

    private void InsertSql(){
        SQLiteDatabase.loadLibs(this);
        File databaseFile = getDatabasePath("user.db");
        databaseFile.mkdirs();
        databaseFile.delete();
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(databaseFile, "ppcc", null);
        database.execSQL("CREATE TABLE `user`  ( id varchar(40)  ,\n" +
                "  `name` varchar(40)  ,\n" +
                "  `password` varchar(255)  ,\n" +
                "  `phone` varchar(20)  ,\n" +
                "  `sex` int  ,\n" +
                "  `birthday` datetime(0)  ,\n" +
                "  `city` varchar(50) ,\n" +
                "  `school` varchar(20),\n" +
                "  `college` varchar(20) ,\n" +
                "  `major` varchar(20) ,\n" +
                "  `grade` int ,\n" +
                "  `position` varchar(50) ,\n" +
                "  `delete` int ,\n" +
                "  `pic` varchar(255) ,\n" +
                "  `registration_time` datetime(0) ,\n" +
                "  `text` varchar(255) ,\n" +
                "  PRIMARY KEY (`id`) )");
    }


    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//        hashMap.put("userTags", platform.getDb().get("userTags")); //SDK+ tags
//        Log.e("SDK+", " SdkTagsMainActivity platform: " + platform +
//                " i: " + i + " hashMap " + hashMap);
////        Intent inent = new Intent();
////        SerializableHashMap serMap = new SerializableHashMap();
////        serMap.setMap(hashMap);
////        Bundle bundle = new Bundle();
////        bundle.putSerializable("serMap", serMap);

        Log.d("=========", " _QQ: -->> onComplete: Platform:" + platform.toString());
        Log.d("=========", " _QQ: -->> onComplete: hashMap:" + hashMap);
        Log.d("=========", " _QQ: -->> onComplete: token:" + platform.getDb().getToken());
        String userId = platform.getDb().getUserId();
        Log.d("getUserId============", userId);

        Log.d("Sign类登录后信息获取======", String.valueOf(hashMap));

        JSONObject jsonObject = new JSONObject(hashMap);
        String name = null;
        String sex = null;
        String year = null;
        try {
            name = jsonObject.getString("nickname");
            sex = jsonObject.getString("gender");
            year = jsonObject.getString("year") + "-01-01 00:00:00";
            Log.d("-============", name + sex + year);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        checkQQUser(userId, name, sex, year);
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

    void checkQQUser(String userId, String name, String sex, String year) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.31.238/web/CheckUser.php");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.connect();

                    String body = "appName=QQ&" + "userAppId=" + userId + "&username=" + name + "&sex=" + sex + "&year=" + year;
                    //Log.d(username,password);
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
                        String username = readSj(response.toString(), 0);
                        GetImg(username);
                        save(username);
                        saveInfo(response.toString());
                        UpData(username);

                        Intent it = new Intent(LoginActivity.this, MainActivity.class);
                        it.putExtra("page", 3);
                        startActivity(it);

                        finish();
                        Looper.prepare();
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } else {
                        Looper.prepare();
                        Toast.makeText(LoginActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Looper.prepare();
                    Toast.makeText(LoginActivity.this, "登录失败  请联系开发者", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        }).start();
    }

    public String readSj(String tempInfo, int postion) {
        try {
            JSONArray jsonArray = new JSONArray(tempInfo);
            JSONObject jsonObject = jsonArray.getJSONObject(postion);

            return jsonObject.getString("user_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tempInfo;
    }


    public void sendCode(Context context) {
        RegisterPage page = new RegisterPage();
        //如果使用我们的ui，没有申请模板编号的情况下需传null
        page.setTempCode(null);
        page.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 处理成功的结果
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    // 国家代码，如“86”
                    String country = (String) phoneMap.get("country");
                    // 手机号码，如“13800138000”
                    String phone = (String) phoneMap.get("phone");
                    // TODO 利用国家代码和手机号码进行后续的操作

                    sendPhone(phone);


                } else {
                    // TODO 处理错误的结果
                }
            }
        });
        page.show(context);
    }

    private void sendPhone(String phone) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();//新建一个OKHttp的对象
                    Request request = new Request.Builder()
                            .url("http://192.168.31.238:8888/pccp_war/phone?phone=" + phone)
                            .build();//创建一个Request对象
                    Response response = client.newCall(request).execute();//发送请求获取返回数据
                    String responseData = response.body().string();//处理返回的数据
                    LogUtils.d(responseData);
                    if (responseData.contains("null user")) {
                        Log.d("=======" + phone, "手机号登录失败-未注册" + responseData);
                    } else if (responseData.contains("request null error")) {
                        Log.d("=======" + phone, "请求异常" + responseData);
                    } else {
                        Log.d("=======" + phone, "手机号登录成功" + responseData);

                        String username = readSjph(responseData, 0);
                        GetImg(username);
                        save(username);
                        saveInfo(responseData);
                        UpData(username);

                        Intent it = new Intent(LoginActivity.this, MainActivity.class);
                        it.putExtra("page", 3);
                        startActivity(it);

                        finish();
                        Looper.prepare();
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        Looper.loop();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
