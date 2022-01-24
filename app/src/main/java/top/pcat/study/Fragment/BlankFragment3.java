package top.pcat.study.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.ConversationAction;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SlidingTabLayout;

import io.rong.imkit.GlideKitImageEngine;
import io.rong.imkit.config.RongConfigCenter;
import io.rong.imkit.conversation.ConversationFragment;
import io.rong.imkit.conversation.RongConversationActivity;
import io.rong.imkit.conversationlist.ConversationListFragment;
import io.rong.imkit.conversationlist.model.BaseUiConversation;
import io.rong.imkit.conversationlist.provider.PrivateConversationProvider;
import io.rong.imkit.widget.adapter.ProviderManager;
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

//        ProviderManager<BaseUiConversation> providerManager = RongConfigCenter.conversationListConfig().getProviderManager(); //获取会话模板管理器
//        providerManager.replaceProvider(PrivateConversationProvider.class, new CustomConversationProvider()); //用自定义模板替换 SDK 原有模板

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
