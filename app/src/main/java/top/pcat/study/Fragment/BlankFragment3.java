package top.pcat.study.Fragment;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.apkfuns.logutils.LogUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SlidingTabLayout;

import es.dmoral.toasty.Toasty;
import io.rong.imkit.RongIM;
import io.rong.imkit.conversationlist.ConversationListFragment;
import io.rong.imkit.utils.RouteUtils;
import io.rong.imlib.RongIMClient;
import top.pcat.study.R;
import top.pcat.study.Ranking.RankingList;

import java.util.ArrayList;
import java.util.List;


public class BlankFragment3 extends Fragment {

    private String mParam1;
    private String mParam2;
    private SlidingTabLayout mTab;
    private ViewPager mVp;
    private ArrayList<Fragment> mFragments;

    private List<RankingList> rankingList = new ArrayList<>();
    private CommonTabLayout mTabLayout_6;

    private OnFragmentInteractionListener mListener;

    private String[] mTitlesArrays ={"消息","排名"};

    public BlankFragment3() {}

    public static BlankFragment3 newInstance(String param1, String param2) {
        BlankFragment3 fragment = new BlankFragment3();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        initView();
    }

    private void initView() {
        mTab = (SlidingTabLayout) getActivity().findViewById(R.id.tab);
        mVp = (ViewPager) getActivity().findViewById(R.id.vp);

        mFragments = new ArrayList<>();
        mFragments.add(new ConversationListFragment());
        mFragments.add(new BlankFragment7());

        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        mVp.setAdapter(pagerAdapter);

        mTab.setViewPager(mVp,mTitlesArrays,getActivity(),mFragments);//tab和ViewPager进行关联
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_blank_fragment3, container, false);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
