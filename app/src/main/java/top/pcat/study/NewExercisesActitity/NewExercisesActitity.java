package top.pcat.study.NewExercisesActitity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.apkfuns.logutils.LogUtils;

import top.pcat.study.R;
import top.pcat.study.Utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class NewExercisesActitity extends AppCompatActivity implements View.OnClickListener{
    private Handler handler;

    private ViewPager mViewPager;
    private List<Fragment> mFragments;
    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_exercises);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        StatusBarUtil.setStatusBarMode(this,true,R.color.touming);
        initView();
        Button b = findViewById(R.id.bbbb);
        b.setOnClickListener(v->{
            Message message = new Message();
            Bundle bundle = new Bundle();
            int size = mViewPager.getCurrentItem();
            bundle.putSerializable(String.valueOf(size),"b");
            message.setData(bundle);//这里模拟的是传递对象数据
            handler.sendMessage(message);
        });
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.left_fragment, NewFragment.newInstance("", ""));
//        ft.commit();
    }

    private void initView() {
        // find view
        mViewPager = findViewById(R.id.fragment_vp);
        // init fragment
        mFragments = new ArrayList<>(10);
        for(int i = 1 ; i <= 2; i++)
            mFragments.add(NewFragment.newInstance(i,"a"));
        // init view pager
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);

        mViewPager.addOnPageChangeListener(mPageChangeListener);
        // register listener
//        mViewPager.addOnPageChangeListener(mPageChangeListener);
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

    @Override
    public void onClick(View v) {
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putSerializable("hehe","gfhj");
        message.setData(bundle);//这里模拟的是传递对象数据
        handler.sendMessage(message);
    }

    public void setHandler(Handler handler){
        this.handler = handler;
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            LogUtils.d("当前页数", String.valueOf(position));
            if(position == 0){

//                LinearLayout layouttwo=(LinearLayout)MainActivity.this.findViewById(R.id.barcolor);
//                layouttwo.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.one_bar_color));

//                Window window = getWindow();
//                View decorView = window.getDecorView();
//                window.setStatusBarColor(getResources().getColor(R.color.one_bar_color));

//                ImageView serimg =findViewById(R.id.ser_img);
//                serimg.setImageResource(R.drawable.ser_w);

//                ImageView mes = findViewById(R.id.mes_w);
//                mes.setImageResource(R.drawable.mes_w);

//                RadioGroup radioGroup=findViewById(R.id.tabs_rg);
//                radioGroup.getBackground().mutate().setAlpha(0);

            }else{
//                LinearLayout layouttwo=(LinearLayout)MainActivity.this.findViewById(R.id.barcolor);
//                layouttwo.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.bar));
//
//                Window window = getWindow();
//                View decorView = window.getDecorView();
//                window.setStatusBarColor(getResources().getColor(R.color.bar));

//                ImageView serimg =findViewById(R.id.ser_img);
//                serimg.setImageResource(R.drawable.sec);

//                ImageView mes = findViewById(R.id.mes_w);
//                mes.setImageResource(R.drawable.mes);

//                RadioGroup radioGroup=findViewById(R.id.tabs_rg);
//                radioGroup.getBackground().setAlpha(255);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}