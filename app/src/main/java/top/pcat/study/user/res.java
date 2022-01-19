package top.pcat.study.user;

import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.apkfuns.logutils.LogUtils;
import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import top.pcat.study.R;
import top.pcat.study.Utils.Req;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class res extends AppCompatActivity implements View.OnClickListener {

    private String realCode;
    private EditText name;
    private EditText password;
    private EditText passworder;
    private EditText code;
    private ImageView cc;
    private ImageView showcode;
    private ImageView back;
    private boolean fff = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res);
        initView();

        showcode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();
    }

    public void initView() {
        name = findViewById(R.id.username);
        password = findViewById(R.id.password);
        passworder = findViewById(R.id.passworder);
        code = findViewById(R.id.code);
        cc = findViewById(R.id.come);
        showcode = findViewById(R.id.iv_registeractivity_showCode);
        back = findViewById(R.id.iv_registeractivity_back);

        back.setOnClickListener(this);
        showcode.setOnClickListener(this);
        cc.setOnClickListener(this);
        password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_registeractivity_back:
                finish();
                break;
            case R.id.iv_registeractivity_showCode:    //改变随机验证码的生成
                showcode.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode().toLowerCase();
                break;
            case R.id.come:    //注册按钮

                String username = name.getText().toString().trim();
                resaa();

                break;
            default:
                break;
        }
    }

    public void resaa() {
        String username = name.getText().toString().trim();
        //获取用户输入的用户名、密码、验证码
        String passwordt = password.getText().toString().trim();
        String passwordert = passworder.getText().toString().trim();
        String phoneCode = code.getText().toString().toLowerCase();
        String phone = "12";
        //注册验证
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(passwordt) && !TextUtils.isEmpty(phoneCode)) {
            if (phoneCode.equals(realCode)) {
                if (passwordt.matches(passwordert)) {
                    String md5 = md5Decode32(passwordt);
                    String qwe = "username=" + username + "&password=" + md5;

                    try {
                        PutFwq2(username, md5, phone);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "两次密码不相同", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "验证码错误,注册失败", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "未完善信息，注册失败", Toast.LENGTH_SHORT).show();
        }
    }


    public void Putname(String key) throws IOException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.31.238:8888/pccp_war/checkName");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.connect();

                    String body = "name=" + key;
                    //Log.d(username,password);
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                    writer.write(body);
                    writer.close();

                    Log.d("PUT====================", body);

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        String result = String.valueOf(inputStream);//将流转换为字符串。
                        Log.d("kwwl", "result=============" + result);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        Log.d("kwwl", "result=============" + response.toString());
                        if (response.toString().matches("no")) {
                            fff = false;
                            Looper.prepare();
                            Toast.makeText(res.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        } else {
                            resaa();
                        }
                    } else {
                        Looper.prepare();
                        Toast.makeText(res.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Looper.prepare();
                    Toast.makeText(res.this, "登录失败  请联系开发者", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        }).start();
    }

    public void PutFwq2(String username, String md5, String phone) throws IOException {
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        String requestBody = "I am Jdqm.";
        Request request = new Request.Builder()
                .url("http://192.168.31.238:12345/users/" + username + "/" + md5 + "/" + phone)
                .post(RequestBody.create(mediaType, requestBody))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TAG", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d("TAG", headers.name(i) + ":" + headers.value(i));
                }
                Gson gson = new Gson();
                String rr = response.body().string();
                LogUtils.d("++++++++++++++++"+rr);
                Req r = gson.fromJson(rr, Req.class);

                LogUtils.d("++++++++++++++++"+String.valueOf(r.getData()));
                if (((String)r.getData()).equals("true")){
                    Looper.prepare();
                    Toast.makeText(res.this, "注册成功 请登录", Toast.LENGTH_SHORT).show();
                    finish();
                    Looper.loop();
                }
                else{
                    Looper.prepare();
                    Toast.makeText(res.this, "注册失败" +
                            "", Toast.LENGTH_SHORT).show();

                    Looper.loop();
                }
            }
        });
    }

    public void PutFwq(String username, String md5, String phone) throws IOException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.31.238:12345/users/" + username + "/" + md5 + "/" + phone);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    Log.d("111", String.valueOf(responseCode));
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        String result = String.valueOf(inputStream);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }

                        Looper.prepare();
                        Toast.makeText(res.this, "注册成功 请登录", Toast.LENGTH_SHORT).show();
                        finish();
                        Looper.loop();
                    } else {
                        Looper.prepare();
                        Toast.makeText(res.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Looper.prepare();
                    Toast.makeText(res.this, "登录失败  请联系开发者", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        }).start();

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
}
