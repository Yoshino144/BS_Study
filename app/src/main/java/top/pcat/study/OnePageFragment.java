package top.pcat.study;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import top.pcat.study.Fragment.HomeFragment;
import top.pcat.study.Fragment.BlankFragment7;
import top.pcat.study.Utils.DisplayUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OnePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OnePageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private SlidingTabLayout mTab;
    private ViewPager mVp;
    private ArrayList<Fragment> mFragments;
    private String[] mTitlesArrays ={"数据","排行榜"};

    public OnePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment onePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OnePageFragment newInstance(String param1, String param2) {
        OnePageFragment fragment = new OnePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one_page, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        initView();



    }


    private void initView() {
        mTab = (SlidingTabLayout) getActivity().findViewById(R.id.tabb);
        mVp = (ViewPager) getActivity().findViewById(R.id.vpp);

        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
        mFragments.add(new BlankFragment7());


        OnePageFragment.MyPagerAdapter pagerAdapter = new OnePageFragment.MyPagerAdapter(getActivity().getSupportFragmentManager());
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
//            return position;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}