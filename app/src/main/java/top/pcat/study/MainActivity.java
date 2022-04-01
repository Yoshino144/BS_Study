package top.pcat.study;

import android.animation.ArgbEvaluator;
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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.apkfuns.logutils.LogUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mob.MobSDK;
import com.mob.OperationCallback;

import de.hdodenhof.circleimageview.CircleImageView;
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;
import es.dmoral.toasty.Toasty;
import io.rong.imkit.RongIM;
import io.rong.imkit.userinfo.RongUserInfoManager;
import io.rong.imkit.userinfo.UserDataProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;
import top.pcat.study.TabChat.Fragment.ClassItemFragment;
import top.pcat.study.TabCommunity.CommunityFragment;
import top.pcat.study.Fragment.NoScrollViewPager;
import top.pcat.study.TabHome.HomeFragment;
import top.pcat.study.Utils.FileTool;
import top.pcat.study.TabStudy.StudyFragment;
import top.pcat.study.TabChat.ChatFragment;
import top.pcat.study.TabMine.MineFragment;
import top.pcat.study.Utils.GetUser;
import top.pcat.study.Utils.PxToDp;

import org.apache.http.util.EncodingUtils;

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
    private float toumingdu = 0;
    private int nowPage = 0;
    private ImageView main_sousuo;

    private BlurView bottomBlurView;
    private BlurView topBlurView;
    private BottomNavigationView bottomNavigationView;

    public int getNowPage() {
        return nowPage;
    }

    private Integer imageRes;
    private String startco = "#ffffff";
    private String endco = "#ffffff";
    private StudyFragment fragment2;

    public void setNowPage(Integer imageRes) {
        this.imageRes = imageRes;
    }


    private SharedPreferences mSpf;
    private AppBarLayout appBarLayout;

    private FileTool ft;

    private Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //Log.i("====", "执行了");
                    //需要执行的代码放这里
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BarUtils.setStatusBarLightMode(MainActivity.this,true);
        FrameLayout.LayoutParams state = (FrameLayout.LayoutParams) findViewById(R.id.ppccc).getLayoutParams();
        state.setMargins(0, getStatusBarHeight(MainActivity.this), 0, 0);//left,top,right,bottom
        //state.height=getStatusBarHeight(MainActivity.this);
        findViewById(R.id.ppccc).setLayoutParams(state);

        bottomBlurView = findViewById(R.id.bottomBlurView);
        //testbar = findViewById(R.id.testbar);
        final Drawable windowBackground = getWindow().getDecorView().getBackground();


        RelativeLayout root = findViewById(R.id.root);
        bottomBlurView.setupWith(root)
                .setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(new RenderScriptBlur(this))
                .setBlurRadius(15f)
                .setHasFixedTransformationMatrix(true);


        topBlurView = findViewById(R.id.topBlurView);
        //testbar = findViewById(R.id.testbar);

        topBlurView.setupWith(root)
                .setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(new RenderScriptBlur(this))
                .setBlurRadius(15f)
                .setHasFixedTransformationMatrix(true);


        topBlurView.setBlurRadius((float) 17);
        topBlurView.setOverlayColor(Color.argb((int) 0, 255, 255, 255));


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.home:
                            mViewPager.setCurrentItem(0);
                            return true;
                        case R.id.shiyan:
                            mViewPager.setCurrentItem(1);
                            return true;
                        case R.id.kecheng:
                            mViewPager.setCurrentItem(2);
                            return true;
                        case R.id.mes:
                            mViewPager.setCurrentItem(3);
                            return true;
                        case R.id.own:
                            mViewPager.setCurrentItem(4);
                            return true;
                    }
                    return false;
                }
        );

        UserDataProvider.UserInfoProvider userInfoProvider = new UserDataProvider.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String userId) {
                UserInfo userInfo = new UserInfo(userId, "root", Uri.parse("https://s3.bmp.ovh/imgs/2022/01/f2af36fd8a17eb69.jpeg"));
                return userInfo;
            }
        };
        RongUserInfoManager.getInstance().setUserInfoProvider(userInfoProvider, true);

        String token = "gZWy3AOlXHmZjzEH0rIK0+JuIhWSI2mOw/z1dKtSMNQi7H5LCbg9Ln4be8dxcjSmeTsOmCDEo0Q=@7zkh.cn.rongnav.com;7zkh.cn.rongcfg.com";
        token = GetUser.getRongToken(this);
        LogUtils.d("融云token"+token);
        if(token != null || "".equals(token)){
            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                @Override
                public void onSuccess(String userId) {
                    Toasty.success(MainActivity.this, userId);
                    LogUtils.d("融云--登录成功" + String.valueOf(userId));
                    //RouteUtils.routeToConversationListActivity(MainActivity.this, "");
                }

                @Override
                public void onError(RongIMClient.ConnectionErrorCode connectionErrorCode) {
                    LogUtils.e("融云--登录失败" + String.valueOf(connectionErrorCode.getValue()));
                    Toasty.error(MainActivity.this, String.valueOf(connectionErrorCode.getValue()));
                }

                @Override
                public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus databaseOpenStatus) {

                    LogUtils.d("融云--数据库" + String.valueOf(databaseOpenStatus.getValue())
                            + databaseOpenStatus.toString());
                }
            });
        }else{

            LogUtils.e("融云token读取失败");
        }


//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        StatusBarUtil.setStatusBarMode(this, true, R.color.cw);

        //透明状态栏
        BarUtils.transparentStatusBar(this);
        //StatusBarUtil.setTranslucentStatus(MainActivity.this);
        findViewById(R.id.ppccc).setBackgroundColor(Color.argb(0, 255, 255, 255));
//        //testbar = findViewById(R.id.testbar);

        CoordinatorLayout.LayoutParams params2 = (CoordinatorLayout.LayoutParams) findViewById(R.id.fragment_vp).getLayoutParams();
        params2.setMargins(0, -getStatusBarHeight(MainActivity.this) - PxToDp.dip2px(MainActivity.this, 49), 0, 0);//left,top,right,bottom
        findViewById(R.id.fragment_vp).setLayoutParams(params2);

        mSpf = super.getSharedPreferences("yejian", MODE_PRIVATE);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        Date date = new Date(System.currentTimeMillis());
        String hour = simpleDateFormat.format(date);
        int hourNum = Integer.parseInt(hour);
        LogUtils.d("当前时间" + hour + hourNum);

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

        initView();

        if (FileUtils.isFileExists(getFilesDir().getAbsolutePath() + "/userToken")) {
            File path2 = new File(getFilesDir().getAbsolutePath() + "/UserImg.png");
            if (ft.isFileExists(path2.toString())) {

                Bitmap bitmap = BitmapFactory.decodeFile(getFilesDir().getAbsolutePath() + "/UserImg.png");


                CircleImageView img = findViewById(R.id.main_hearImg);
                img.setImageBitmap(bitmap);
            } else {
            }
        }


        submitPrivacyGrantResult(true);

        //获取页数
        Intent intent = getIntent();
        page = intent.getIntExtra("page", 0);


        LogUtils.d("设置页数" + String.valueOf(page));
        bottomNavigationView.getMenu().getItem(page).setChecked(true);
        mViewPager.setCurrentItem(page);
        mViewPager.setNoScroll(false);
        //隐藏标题栏

        int barsize = 0;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 0, 0, 0);
        findViewById(R.id.shiyingxing).setLayoutParams(params);

        pppccc = findViewById(R.id.ppccc);

        //pppccc.setBackgroundColor(Color.parseColor("#ffffff"));

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
                    URL url = new URL(getResources().getString(R.string.network_url)+"/userdates/week/" + userid);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();

                    LogUtils.d("/userdates/week/", url.toString());

                    int responseCode = connection.getResponseCode();
                    LogUtils.d("/userdates/week/+responseCode", responseCode);
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        String result = String.valueOf(inputStream);//将流转换为字符串。
                        LogUtils.d("kwwl", "result=============" + result);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        LogUtils.d("/userdates/week/", response.toString());

                        FileIOUtils.writeFileFromString(getFilesDir().getAbsolutePath() + "/UserData", response.toString());
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
        main_sousuo = findViewById(R.id.main_sousuo);
        main_sousuo.setOnClickListener(v->{
            LogUtils.d("dianji "+pageId);
            if (pageId==3){
//创建弹出式菜单对象（最低版本11）
                PopupMenu popup = new PopupMenu(this, v);//第二个参数是绑定的那个view
                //获取菜单填充器
                MenuInflater inflater = popup.getMenuInflater();
                //填充菜单
                inflater.inflate(R.menu.chat, popup.getMenu());
                //绑定菜单项的点击事件
                popup.setOnMenuItemClickListener(item->{
                    switch (item.getItemId()) {
                        case R.id.exit:
                            Toast.makeText(this, "退出", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.set:
                            //ChatFragment fragment = (ChatFragment) getSupportFragmentManager().findFragmentById(3);
                            ChatFragment fragment = (ChatFragment) mFragments.get(3);
                            ClassItemFragment framer2 = (ClassItemFragment) fragment.getFramer();
                            framer2.createClassBut();
                            break;
                        case R.id.account:
                            ChatFragment fragment2 = (ChatFragment) mFragments.get(3);
                            ClassItemFragment framer3 = (ClassItemFragment) fragment2.getFramer();
                            framer3.joinClassBut();
                            break;
                        default:
                            break;
                    }
                    return false;
                });
                //显示(这一行代码不要忘记了)
                popup.show();
            }
        });
        // init fragment
        fragment2 = new StudyFragment();
        mFragments = new ArrayList<>(4);
        //mFragments.add(BlankFragment.newInstance("456","789"));
        mFragments.add(HomeFragment.newInstance("456", "789"));//0
        mFragments.add(fragment2.newInstance("456", "789"));//1
        mFragments.add(new CommunityFragment());//2
        mFragments.add(ChatFragment.newInstance("123", "1"));//3
        mFragments.add(MineFragment.newInstance("123", "1"));//4
        // init view pager
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(4);
        // register listener
        mViewPager.addOnPageChangeListener(mPageChangeListener);
        //mTabRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
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

        @SuppressLint("WrongConstant")
        @Override
        public void onPageSelected(int position) {
            //RadioButton radioButton = (RadioButton) mTabRadioGroup.getChildAt(position);
            //radioButton.setChecked(true);
            pageId = position;
            LogUtils.d("当前页数，" + String.valueOf(position));
            pppccc = findViewById(R.id.ppccc);

            findViewById(R.id.main_edit).setVisibility(View.VISIBLE);

            findViewById(R.id.main_hearImg).setVisibility(View.VISIBLE);
            if (position == 1) {
                LogUtils.d("更换主题11，题目页");
                if(toumingdu == 0){

                    topBlurView.setBlurRadius((float) 0.1);
                    topBlurView.setOverlayColor(Color.argb((int) (0.1 * 5.5), 255, 255, 255));
                }else{

                    topBlurView.setBlurRadius((float) toumingdu);
                    topBlurView.setOverlayColor(Color.argb((int) (toumingdu * 5.5), 255, 255, 255));
                }
                findViewById(R.id.state).setVisibility(View.INVISIBLE);
                topBlurView.setBlurEnabled(true);
                BarUtils.setStatusBarLightMode(MainActivity.this,true);

            } else if (position == 2) {
                LogUtils.d("更换主题22，社区页"+toumingdu);

                findViewById(R.id.state).setVisibility(View.INVISIBLE);
                topBlurView.setBlurRadius((float) 15);
                topBlurView.setOverlayColor(Color.argb((int) 0, 255, 255, 255));

                topBlurView.setBlurEnabled(true);
                BarUtils.setStatusBarLightMode(MainActivity.this,true);

            } else if (position == 3) {
                LogUtils.d("更换主题33,聊天页");
                //main_sousuo.setVisibility(View.GONE);
                BarUtils.setStatusBarLightMode(MainActivity.this, true);
                topBlurView.setBlurRadius((float) 0.1);
                topBlurView.setBlurEnabled(false);
                findViewById(R.id.main_edit).setVisibility(View.INVISIBLE);
                topBlurView.setOverlayColor(Color.argb((int) 0, 255, 255, 255));

            } else if (position == 4) {
                LogUtils.d("更换主题44，我的页");
                BarUtils.setStatusBarLightMode(MainActivity.this, true);
                topBlurView.setBlurRadius((float) 15);
                topBlurView.setOverlayColor(Color.argb((int) 0, 255, 255, 255));

                findViewById(R.id.main_edit).setVisibility(View.INVISIBLE);
                findViewById(R.id.main_hearImg).setVisibility(View.INVISIBLE);

            } else {
                LogUtils.d("更换主题**，首页");
                findViewById(R.id.state).setVisibility(View.INVISIBLE);
                topBlurView.setBlurRadius((float) 17);
                topBlurView.setOverlayColor(Color.argb((int) 0, 255, 255, 255));

                topBlurView.setBlurEnabled(true);
                BarUtils.setStatusBarLightMode(MainActivity.this,true);
            }
//
////            pageId = position;
////            if (position != 1) {
////                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
////                params.setMargins(0, 0, 0, 0);
////                findViewById(R.id.shiyingxing).setLayoutParams(params);
////
////                pppccc.setBackgroundColor(Color.parseColor("#ffffff"));
////                //appBarLayout.setBackgroundColor(Color.parseColor("#ffffff"));
////                bar_wei.setVisibility(View.GONE);
////                StatusBarUtil.setStatusBarMode(MainActivity.this, true, R.color.cw);
////
////            }
////            else{
////                int res = fragment2.activityChangeFragment("data");
////                pppccc.setBackgroundColor(Color.parseColor("#00ffffff"));
////                bar_wei.setVisibility(View.VISIBLE);
////                if(imageRes == null) imageRes =res;
////                bar_wei.setImageResource(imageRes);
////                StatusBarUtil.setTranslucentStatus(MainActivity.this);
////                //System.out.println(findViewById(R.id.cnm).getLayoutParams());
////                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
////                params.setMargins(0, getStatusBarHeight(MainActivity.this), 0, 0);
////                findViewById(R.id.shiyingxing).setLayoutParams(params);
////            }

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


    private void submitPrivacyGrantResult(boolean granted) {
        MobSDK.submitPolicyGrantResult(granted, new OperationCallback<Void>() {
            @Override
            public void onComplete(Void data) {
                LogUtils.d("隐私协议授权结果提交：成功");
            }

            @Override
            public void onFailure(Throwable t) {
                LogUtils.d("隐私协议授权结果提交：失败");
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
                    URL url = new URL("http://192.168.137.1/web/UpUserData.php");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.connect();

                    String body = "username=" + username + "&json=" + json + "&allSize=" + allSize;
                    LogUtils.d("======================", body);
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                    writer.write(body);
                    writer.close();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        String result = String.valueOf(inputStream);//将流转换为字符串。
                        //LogUtils.d("kwwl","result============="+result);
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
                    String uu = "http://192.168.137.1/web/Doc/timu_size.json";
                    LogUtils.d("url", "url=============" + uu);
                    URL url = new URL(uu);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        String result1 = String.valueOf(inputStream);//将流转换为字符串。
                        LogUtils.d("kwwl", "result=============" + result1);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }

                        LogUtils.d("as", "aaaaaaaaaaaaaaaaa=============" + response.toString());
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
        //LogUtils.d("隐藏了导航栏");
        //testbar.animate().translationY(testbar.getHeight());
    }

    public void Display() {
        //LogUtils.d("显示了导航栏");
        //testbar.animate().translationY(0);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //LogUtils.d("ver" + verticalOffset + " pageId" + String.valueOf(pageId) + "TotalScrollRange" + String.valueOf(appBarLayout.getTotalScrollRange()));
        if (pageId == 0) {
            int pp = -appBarLayout.getTotalScrollRange();
            View view = findViewById(R.id.tiaotiao);
            //LogUtils.d(verticalOffset > pp);
            if (verticalOffset > pp) view.setVisibility(View.GONE);
            else view.setVisibility(View.VISIBLE);
        } else if (pageId == 1) {


        }
//        SlidingTabLayout tabb = findViewById(R.id.tabb);
//        if(verticalOffset<-50)
//        tabb.setBackgroundColor(getResources().getColor(R.color.gray_color));
//        else
//            tabb.setBackgroundColor(getResources().getColor(R.color.cw));
    }

    @SuppressLint("WrongConstant")
    public void setcolor(float a) {
        //LogUtils.d("模糊程度" + a);
        toumingdu = a;
        topBlurView.setBlurRadius(a);
        topBlurView.setOverlayColor(Color.argb((int) (a * 5.5), 255, 255, 255));
        //pppccc.setBackgroundColor(Color.argb((int) a, 255, 255, 255));
        //StatusBarUtil.setStatusBarColor2(this, Color.argb((int) a, 255, 255, 255));
        //io.rong.imkit.utils.StatusBarUtil.setStatusBarFontIconDark(this, 5, true);
    }

}
