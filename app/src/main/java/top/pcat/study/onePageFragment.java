package top.pcat.study;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import top.pcat.study.Fragment.BlankFragment;
import top.pcat.study.Fragment.BlankFragment3;
import top.pcat.study.Fragment.BlankFragment6;
import top.pcat.study.Fragment.BlankFragment7;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link onePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class onePageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public onePageFragment() {
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
    public static onePageFragment newInstance(String param1, String param2) {
        onePageFragment fragment = new onePageFragment();
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

    private void initView() {
        mTab = (SlidingTabLayout) getActivity().findViewById(R.id.tab);
        mVp = (ViewPager) getActivity().findViewById(R.id.vp);

        mFragments = new ArrayList<>();
        mFragments.add(new BlankFragment());
        mFragments.add(new BlankFragment7());


        BlankFragment3.MyPagerAdapter pagerAdapter = new BlankFragment3.MyPagerAdapter(getActivity().getSupportFragmentManager());
        mVp.setAdapter(pagerAdapter);

        mTab.setViewPager(mVp,mTitlesArrays,getActivity(),mFragments);//tab和ViewPager进行关联
    }
}