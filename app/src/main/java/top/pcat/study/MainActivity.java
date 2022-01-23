package top.pcat.study;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.apkfuns.logutils.LogUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.mob.MobSDK;
import com.mob.OperationCallback;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import io.rong.imkit.RongIM;
import io.rong.imkit.utils.RouteUtils;
import io.rong.imlib.RongIMClient;
import top.pcat.study.Fragment.NoScrollViewPager;
import top.pcat.study.Utils.FileTool;
import top.pcat.study.Fragment.BlankFragment2;
import top.pcat.study.Fragment.BlankFragment3;
import top.pcat.study.Fragment.BlankFragment4;
import top.pcat.study.Utils.StatusBarUtil;

import org.apache.http.util.EncodingUtils;
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
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Delayed;


public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
    private int pageId = 0;
    private static boolean loginflag;
    private NoScrollViewPager mViewPager;
    private RadioGroup mTabRadioGroup;
    private List<Fragment> mFragments;
    private FragmentPagerAdapter mAdapter;
    private String name;
    private String lv;
    public String user_id;
    private int page;
    private LinearLayout testbar;
    private long exitTime = 0;
    private LinearLayout pppccc;
    private ImageView bar_wei;
    private ImageView bar_wei2;
    private LinearLayout shiying;

    private int nowPage = 0;

    public int getNowPage() {
        return nowPage;
    }
    private Integer imageRes;
    private String startco = "#ffffff";
    private String endco = "#ffffff";
    private BlankFragment2 fragment2;

    public void setNowPage(Integer imageRes) {
        LogUtils.d("yeshu=="+ imageRes);
        this.imageRes = imageRes;
        if (pageId == 1) {
            //shiying.setAccessibilityLiveRegion();



            //bar_wei.setImageResource(imageRes);
            //bar_wei2.
            if(imageRes == R.drawable.image55){

                ObjectAnimator.ofFloat(bar_wei2, "alpha",  0, 1).setDuration(400).start();
            }else{

                ObjectAnimator.ofFloat(bar_wei2, "alpha",  1, 0).setDuration(400).start();
            }
            //ObjectAnimator.ofFloat(bar_wei2, "alpha", 1, 0, 1).setDuration(2500).start();
            pppccc.setBackgroundColor(Color.parseColor("#00ffffff"));
            bar_wei.setVisibility(View.VISIBLE);
            StatusBarUtil.setTranslucentStatus(this);
            //System.out.println(findViewById(R.id.cnm).getLayoutParams());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(0, getStatusBarHeight(this), 0, 0);
            findViewById(R.id.shiyingxing).setLayoutParams(params);

        }
    }

    public void ch(View view, String start, String end) {
        ValueAnimator animator = ObjectAnimator.ofInt(view, "backgroundColor", Color.parseColor(start), Color.parseColor(end));//对背景色颜色进行改变，操作的属性为"backgroundColor",此处必须这样写，不能全小写,后面的颜色为在对应颜色间进行渐变
        animator.setDuration(500);
        animator.setEvaluator(new ArgbEvaluator());//如果要颜色渐变必须要ArgbEvaluator，来实现颜色之间的平滑变化，否则会出现颜色不规则跳动
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.start();
    }


    private SharedPreferences mSpf;
    private AppBarLayout appBarLayout;

    private FileTool ft;

    private Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.i("====", "执行了");
                    //需要执行的代码放这里
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String token = "gZWy3AOlXHmZjzEH0rIK0+JuIhWSI2mOw/z1dKtSMNQi7H5LCbg9Ln4be8dxcjSmeTsOmCDEo0Q=@7zkh.cn.rongnav.com;7zkh.cn.rongcfg.com";
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onSuccess(String userId) {
                Toasty.success(MainActivity.this,userId);
                LogUtils.e("融云--登录成功"+String.valueOf(userId));
                //RouteUtils.routeToConversationListActivity(MainActivity.this, "");
            }

            @Override
            public void onError(RongIMClient.ConnectionErrorCode connectionErrorCode) {
                LogUtils.e("融云--登录失败"+String.valueOf(connectionErrorCode.getValue()));
                Toasty.error(MainActivity.this,String.valueOf(connectionErrorCode.getValue()));
            }

            @Override
            public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus databaseOpenStatus) {

                LogUtils.e("融云--登录失败"+String.valueOf(databaseOpenStatus.getValue()));
            }
        });

        bar_wei = findViewById(R.id.bar_wei_main);
        bar_wei2 = findViewById(R.id.bar_wei_main2);
//        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        StatusBarUtil.setStatusBarMode(this, true, R.color.cw);

        //testbar = findViewById(R.id.testbar);
        mSpf = super.getSharedPreferences("yejian", MODE_PRIVATE);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        Date date = new Date(System.currentTimeMillis());
        String hour = simpleDateFormat.format(date);
        int hourNum = Integer.parseInt(hour);
        Log.d("当前时间", hour + hourNum);

        shiying = findViewById(R.id.shiyingxing);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler2.sendEmptyMessage(1);
            }
        };
        Timer timer = new Timer(true);
        timer.schedule(task, strToDateLong("2020-06-14 15:33:30"));
        appBarLayout = findViewById(R.id.appbar);

        appBarLayout.addOnOffsetChangedListener(this);


        RadioButton today_tab = findViewById(R.id.today_tab);
        RadioButton record_tab = findViewById(R.id.record_tab);
        RadioButton settings_tab = findViewById(R.id.pai_tab);
        RadioButton pai_tab = findViewById(R.id.settings_tab);
        Drawable drawable_news = getResources().getDrawable(R.drawable.tab_sign_selector);
        drawable_news.setBounds(0, 0, 60, 60);
        today_tab.setCompoundDrawables(null, drawable_news, null, null);

        Drawable drawable_news2 = getResources().getDrawable(R.drawable.tab_ke_selector);
        drawable_news2.setBounds(0, 0, 60, 60);
        record_tab.setCompoundDrawables(null, drawable_news2, null, null);

        Drawable drawable_news3 = getResources().getDrawable(R.drawable.tab_pai_selector);
        drawable_news3.setBounds(0, 0, 60, 60);
        settings_tab.setCompoundDrawables(null, drawable_news3, null, null);

        Drawable drawable_news4 = getResources().getDrawable(R.drawable.tab_me_selector);
        drawable_news4.setBounds(0, 0, 60, 60);
        pai_tab.setCompoundDrawables(null, drawable_news4, null, null);

        initView();

        File path = new File(getFilesDir().getAbsolutePath()+"/Login.txt");
        if(ft.isFileExists(path.toString())){
            File path2 = new File(getFilesDir().getAbsolutePath()+"/UserImg.png");
            if(ft.isFileExists(path2.toString())){
                //ImageView headImage = getActivity().findViewById(R.id.hearImg);

                Bitmap bitmap = BitmapFactory.decodeFile(getFilesDir().getAbsolutePath()+"/UserImg.png");
//            headImage.setImageBitmap(bitmap);

                CircleImageView img = findViewById(R.id.main_hearImg);
                img.setImageBitmap(bitmap);
            }
            else{
            }
        }

        //获取页数
        Intent intent = getIntent();
        page = intent.getIntExtra("page", 0);

        submitPrivacyGrantResult(true);

        if (FileTool.isFileExists(path.toString())) {
            lv = readInfo(readlv());
            name = read();
            try {
                UpData(String.valueOf(user_id));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        //初始化
        mViewPager.setCurrentItem(page);
        mViewPager.setNoScroll(false);
        //隐藏标题栏


        int barsize = 0;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 0, 0, 0);
        findViewById(R.id.shiyingxing).setLayoutParams(params);

        pppccc = findViewById(R.id.ppccc);

        pppccc.setBackgroundColor(Color.parseColor("#ffffff"));

        int i = 0X008080FF;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), 0X008080FF, 0XFFFFFFFF);

                        valueAnimator.setDuration(5000);

                        valueAnimator.start();

                        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override

                            public void onAnimationUpdate(ValueAnimator animation) {
                                //findViewById(R.id.ppccc).setBackgroundColor((Integer)animation.getAnimatedValue());

                            }

                        });
                    }
                });

            }
        }, 100);


    }

    public void writeInfo(String val) {
        SharedPreferences.Editor editor = mSpf.edit();
        editor.putString("name", val);
        editor.apply();
    }

    public String readSPInfo() {
        return mSpf.getString("name", "");
    }

    public static Date strToDateLong(String strDate) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public void UpData(String userid) throws IOException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.31.238:12345/userdates/1/" + userid);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();

                    LogUtils.d("/userdates/1/", url.toString());

                    int responseCode = connection.getResponseCode();
                    LogUtils.d("/userdates/1/+responseCode", responseCode);
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
                        LogUtils.d("/userdates/1/", response.toString());

                        saveDatai(response.toString());
//                        Looper.prepare();
//                        Toast.makeText(getActivity(), "同步成功", Toast.LENGTH_SHORT).show();
//                        Looper.loop();
                    } else {
                        Looper.prepare();
                        Toast.makeText(MainActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void saveDatai(String temp) {
        String FILENAME = "UserData";
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(temp.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public String readlv() {
        String result = "";
        try {
            FileInputStream fin = openFileInput("UserInfo");
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            result = EncodingUtils.getString(buffer, "UTF-8");
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

    public String read() {
        String result = "";
        try {
            FileInputStream fin = openFileInput("Login.txt");
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            result = EncodingUtils.getString(buffer, "UTF-8");
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

    private void initView() {
        // find view
        mViewPager = findViewById(R.id.fragment_vp);
        mTabRadioGroup = findViewById(R.id.tabs_rg);
        // init fragment
        fragment2 = new BlankFragment2();
        mFragments = new ArrayList<>(4);
        //mFragments.add(BlankFragment.newInstance("456","789"));
        mFragments.add(OnePageFragment.newInstance("456", "789"));
        mFragments.add(fragment2.newInstance("456", "789"));
        mFragments.add(BlankFragment3.newInstance("123", "1"));
        mFragments.add(BlankFragment4.newInstance(name, lv));
        // init view pager
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(4);
        // register listener
        mViewPager.addOnPageChangeListener(mPageChangeListener);
        mTabRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.removeOnPageChangeListener(mPageChangeListener);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            RadioButton radioButton = (RadioButton) mTabRadioGroup.getChildAt(position);
            radioButton.setChecked(true);
            LogUtils.d("当前页数", String.valueOf(position));
            pageId = position;
            if (position != 1) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params.setMargins(0, 0, 0, 0);
                findViewById(R.id.shiyingxing).setLayoutParams(params);

                pppccc.setBackgroundColor(Color.parseColor("#ffffff"));
                //appBarLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                bar_wei.setVisibility(View.GONE);
                StatusBarUtil.setStatusBarMode(MainActivity.this, true, R.color.cw);

            }
            else{
                int res = fragment2.activityChangeFragment("data");
                pppccc.setBackgroundColor(Color.parseColor("#00ffffff"));
                bar_wei.setVisibility(View.VISIBLE);
                if(imageRes == null) imageRes =res;
                bar_wei.setImageResource(imageRes);
                StatusBarUtil.setTranslucentStatus(MainActivity.this);
                //System.out.println(findViewById(R.id.cnm).getLayoutParams());
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params.setMargins(0, getStatusBarHeight(MainActivity.this), 0, 0);
                findViewById(R.id.shiyingxing).setLayoutParams(params);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }


    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            for (int i = 0; i < group.getChildCount(); i++) {
                if (group.getChildAt(i).getId() == checkedId) {
                    mViewPager.setCurrentItem(i);
                    return;
                }
            }
        }
    };

    public String readInfo(String tempInfo) {
        try {
            JSONObject jsonArray = new JSONObject(tempInfo);
            //lv = jsonArray.getString("lv");
            lv = "10";
            user_id = jsonArray.getString("id");
            return lv;
        } catch (JSONException e) {
            Toast.makeText(MainActivity.this, "信息读取错误", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return "error";
    }

    private void submitPrivacyGrantResult(boolean granted) {
        MobSDK.submitPolicyGrantResult(granted, new OperationCallback<Void>() {
            @Override
            public void onComplete(Void data) {
                Log.d("yinsiquanx", "隐私协议授权结果提交：成功");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("yinsiquanx", "隐私协议授权结果提交：失败");
            }
        });
    }


    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mList;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.mList = list;
        }

        @Override
        public Fragment getItem(int position) {
            return this.mList == null ? null : this.mList.get(position);
        }

        @Override
        public int getCount() {
            return this.mList == null ? 0 : this.mList.size();
        }
    }

    public static void setStatusBarMode(Activity activity, boolean bDark) {
        //6.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            View decorView = window.getDecorView();

            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();

                if (bDark) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                    window.setStatusBarColor(activity.getResources().getColor(R.color.bg_huidi));

                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                    window.setStatusBarColor(activity.getResources()
                            .getColor(R.color.bg_huidi));
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }


    public void UpData(String username, String json, int allSize) throws IOException {

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

                    String body = "username=" + username + "&json=" + json + "&allSize=" + allSize;
                    Log.d("======================", body);
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

                        saveData(response.toString());

                        Looper.prepare();
                        //Toast.makeText(MainActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } else {
                        Looper.prepare();
                        Toast.makeText(MainActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Looper.prepare();
                    Toast.makeText(MainActivity.this, "上传失败  请反馈开发者", Toast.LENGTH_SHORT).show();
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

    public void GetJson() throws IOException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String uu = "http://192.168.31.238/web/Doc/timu_size.json";
                    Log.d("url", "url=============" + uu);
                    URL url = new URL(uu);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        String result1 = String.valueOf(inputStream);//将流转换为字符串。
                        Log.d("kwwl", "result=============" + result1);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }

                        Log.d("as", "aaaaaaaaaaaaaaaaa=============" + response.toString());
                        saveccc(response.toString());
                    } else {
                        Looper.prepare();
                        //Toast.makeText(ChapterActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Looper.prepare();
                    // Toast.makeText(SignActivity.this,"登录失败  请联系开发者",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        }).start();

    }

    public void saveccc(String temp) {
        String FILENAME = "timu_size.json";
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

    public void Hide() {
        LogUtils.d("隐藏了导航栏");
        testbar.animate().translationY(testbar.getHeight());
    }

    public void Display() {
        LogUtils.d("显示了导航栏");
        testbar.animate().translationY(0);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        LogUtils.d("ver" + verticalOffset +" pageId"+String.valueOf(pageId));
        if(pageId !=1) {
            int pp = -appBarLayout.getTotalScrollRange();
            View view = findViewById(R.id.tiaotiao);
            LogUtils.d(verticalOffset > pp);
            if (verticalOffset > pp) view.setVisibility(View.GONE);
            else view.setVisibility(View.VISIBLE);
        }
//        SlidingTabLayout tabb = findViewById(R.id.tabb);
//        if(verticalOffset<-50)
//        tabb.setBackgroundColor(getResources().getColor(R.color.gray_color));
//        else
//            tabb.setBackgroundColor(getResources().getColor(R.color.cw));
    }


}
