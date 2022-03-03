package top.pcat.study.Curriculum;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.BarUtils;
import com.dou361.dialogui.DialogUIUtils;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;
import top.pcat.study.Curriculum.Fragment.ItemFragment;
import top.pcat.study.Curriculum.Fragment.OItemFragment;
import top.pcat.study.Curriculum.Fragment.UItemFragment;
import top.pcat.study.MainActivity;
import top.pcat.study.R;
import top.pcat.study.TabHome.DataFragment;
import top.pcat.study.TabHome.HomeFragment;
import top.pcat.study.TabHome.RankFragment;
import top.pcat.study.Utils.DisplayUtil;
import top.pcat.study.Utils.GetUser;
import top.pcat.study.Utils.StatusBarUtil;
import top.pcat.study.View.Item;
import top.pcat.study.View.ItemAdapter;
import com.apkfuns.logutils.LogUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static top.pcat.study.MainActivity.getStatusBarHeight;

public class Curriculum extends AppCompatActivity {
    private final Handler handler = new Handler();


    private SlidingTabLayout mTab;
    private ViewPager mVp;
    private String uuid;
    private ArrayList<Fragment> mFragments= new ArrayList<Fragment>();
    private String[] mTitlesArrays ={"已选","官方题库","个人题库"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_yixuan);
uuid = GetUser.getUserId(this);

        BarUtils.transparentStatusBar(this);
        BarUtils.setStatusBarLightMode(this,true);

        BlurView bottomBlurView = findViewById(R.id.cur_blu);
        //testbar = findViewById(R.id.testbar);
        final Drawable windowBackground = getWindow().getDecorView().getBackground();


        RelativeLayout root = findViewById(R.id.root);
        bottomBlurView.setupWith(root)
                .setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(new RenderScriptBlur(this))
                .setBlurRadius(15f)
                .setHasFixedTransformationMatrix(true);

        initView();
    }


    private void initView() {
        mTab = (SlidingTabLayout) findViewById(R.id.cur_tab);
        mVp = (ViewPager) findViewById(R.id.cur_vp);

        mFragments.add(ItemFragment.newInstance(uuid,"已选","http://127.0.0.1:12345/subjects/" + uuid));
        mFragments.add(OItemFragment.newInstance(uuid,"官方题库","http://127.0.0.1:12345/subjects/"+ uuid + "/official"));
        mFragments.add(UItemFragment.newInstance(uuid,"个人题库","http://127.0.0.1:12345/subjects/"+ uuid  + "/un_official"));

        mVp.setOffscreenPageLimit(5);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(),mFragments);
        mVp.setAdapter(pagerAdapter);

        mTab.setViewPager(mVp,mTitlesArrays,this,mFragments);//tab和ViewPager进行关联

        TextView title = mTab.getTitleView(0);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, DisplayUtil.sp2px(this,18));
        title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        mTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateTabView(position);
               // ((MainActivity) requireActivity()).Display();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    private void updateTabView(int position) {
        int tabCount = mTab.getTabCount();
        for (int i=0;i<tabCount;i++){
            TextView title = mTab.getTitleView(i);
            if (i==position){
//                        TextView title = child.findViewById(com.flyco.tablayout.R.id.tv_tab_title);
                title.setTextSize(TypedValue.COMPLEX_UNIT_PX, DisplayUtil.sp2px(this,18));
                title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }else {
//                        TextView title = child.findViewById(com.flyco.tablayout.R.id.tv_tab_title);
                title.setTextSize(TypedValue.COMPLEX_UNIT_PX,DisplayUtil.sp2px(this,17));
                title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }
        }
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> list;

        public MyPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
            super(fm);
            this.list = list;
        }

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitlesArrays[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
