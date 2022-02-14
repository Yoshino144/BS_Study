package top.pcat.study.User;

import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.apkfuns.logutils.LogUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.google.gson.Gson;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import top.pcat.study.R;
import top.pcat.study.Pojo.Msg;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private String realCode;
    private EditText name;
    private EditText password;
    private EditText passworder;
    private EditText code;
    private EditText phone;
    private ImageView cc;
    private ImageView showcode;
    private ImageView back;
    private boolean fff = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
        phone = findViewById(R.id.phone);
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
                registerPost();

                break;
            default:
                break;
        }
    }

    /**
     * 提取内容+注册
     */
    public void registerPost() {
        String username = name.getText().toString().trim();
        //获取用户输入的用户名、密码、验证码
        String passwordt = password.getText().toString().trim();
        String passwordert = passworder.getText().toString().trim();
        String phoneCode = code.getText().toString().toLowerCase();
        String phonet = phone.getText().toString().trim();

        if(!RegexUtils.isMobileExact(phonet)){
            Toasty.error(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
            return ;
        }
        //注册验证
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(passwordt) && !TextUtils.isEmpty(phoneCode)) {
            if (phoneCode.equals(realCode)) {
                if (passwordt.matches(passwordert)) {
                    String md5 = EncryptUtils.encryptMD5ToString(passwordt);
                    String qwe = "username=" + username + "&password=" + md5;

                    try {
                        PutFwq2(username, md5, phonet);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toasty.error(this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        LogUtils.w(e.toString());
                    }
                } else {
                    Toasty.warning(this, "两次密码不相同", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toasty.error(this, "验证码错误,注册失败", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toasty.warning(this, "未完善信息，注册失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 登录请求
     *
     * @param username
     * @param md5
     * @param phone
     * @throws IOException
     */
    public void PutFwq2(String username, String md5, String phone) throws IOException {
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        Request request = new Request.Builder()
                .url("http://192.168.31.238:12345/users/" + phone + "/" + md5 + "/" + "username")
                .post(RequestBody.Companion.create(username, mediaType))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toasty.error(RegisterActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                String rr = response.body().string();
                LogUtils.d("注册请求返回内容" + rr);
                Msg r = gson.fromJson(rr, Msg.class);
                if (r.getStatus() == 200) {
                    Looper.prepare();
                    LogUtils.d("注册成功 请登录");
                    Toasty.success(RegisterActivity.this, "注册成功 请登录", Toast.LENGTH_SHORT).show();
                    finish();
                    Looper.loop();
                } else {
                    Looper.prepare();
                    Toasty.error(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        });
    }



}
