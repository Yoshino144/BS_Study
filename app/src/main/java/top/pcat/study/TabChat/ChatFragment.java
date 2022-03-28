package top.pcat.study.TabChat;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import io.rong.imkit.conversationlist.ConversationListFragment;
import top.pcat.study.MainActivity;
import top.pcat.study.R;
import top.pcat.study.Ranking.RankingList;
import top.pcat.study.TabChat.Fragment.ClassItemFragment;
import top.pcat.study.TabHome.RankFragment;
import top.pcat.study.Utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;


public class ChatFragment extends Fragment {

    private String mParam1;
    private String mParam2;
    private SlidingTabLayout mTab;
    private ViewPager mVp;
    private ArrayList<Fragment> mFragments;

    private List<RankingList> rankingList = new ArrayList<>();
    private CommonTabLayout mTabLayout_6;

    private OnFragmentInteractionListener mListener;

    private String[] mTitlesArrays ={"消息","班级"};

    public ChatFragment() {}

    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
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

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_blank_fragment3, container, false);
        mTab = (SlidingTabLayout) view.findViewById(R.id.tab);
        mVp = (ViewPager) view.findViewById(R.id.vp);

        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) view.findViewById(R.id.f3_bar).getLayoutParams();
        params2.setMargins(0, getStatusBarHeight(view.getContext()) , 0, 0);//left,top,right,bottom
        view.findViewById(R.id.f3_bar).setLayoutParams(params2);

        mFragments = new ArrayList<>();
        mFragments.add(new ConversationListFragment());
        mFragments.add(new ClassItemFragment());

        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        mVp.setAdapter(pagerAdapter);

        mTab.setViewPager(mVp,mTitlesArrays,getActivity(),mFragments);//tab和ViewPager进行关联

        TextView title = mTab.getTitleView(0);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX,DisplayUtil.sp2px(getContext(),18));
        title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        mTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                updateTabView(position);

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
                ((MainActivity) requireActivity()).Display();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void updateTabView(int position) {
        int tabCount = mTab.getTabCount();
        for (int i=0;i<tabCount;i++){
            TextView title = mTab.getTitleView(i);
            if (i==position){
//                        TextView title = child.findViewById(com.flyco.tablayout.R.id.tv_tab_title);
                title.setTextSize(TypedValue.COMPLEX_UNIT_PX, DisplayUtil.sp2px(getContext(),18));
                title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }else {
//                        TextView title = child.findViewById(com.flyco.tablayout.R.id.tv_tab_title);
                title.setTextSize(TypedValue.COMPLEX_UNIT_PX,DisplayUtil.sp2px(getContext(),17));
                title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }
        }
    }
}
