package top.pcat.study.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import top.pcat.study.Utils.FileTool;
import top.pcat.study.PushService.FragAdapter;
import top.pcat.study.PushService.Fragment2;
import top.pcat.study.PushService.Fragment3;
import top.pcat.study.R;
import top.pcat.study.Ranking.ClassAdapter;
import top.pcat.study.Ranking.ClassList;
import top.pcat.study.Ranking.GuanList;
import top.pcat.study.Ranking.MesList;

import java.util.ArrayList;
import java.util.List;

public class BlankFragment6 extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private BlankFragment5ViewModel mViewModel;

    private List<ClassList> classList = new ArrayList<>();
    private List<MesList> mesList = new ArrayList<>();
    private List<GuanList> guanList = new ArrayList<>();

    private BlankFragment3.OnFragmentInteractionListener mListener;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private FileTool ft;
    private RadioButton radioButton4;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private ClassAdapter adapter;
    private TextView iddddd;
    public int user_id;
    private ViewPager vp;

    private int new_size = 0;

    public static BlankFragment6 newInstance() {
        return new BlankFragment6();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.blank_fragment6_fragment, container, false);
        List<Fragment> fragments=new ArrayList<Fragment>();
        fragments.add(new Fragment2());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());
        FragAdapter adapter = new FragAdapter(getChildFragmentManager(), fragments);

        //设定适配器
        vp = view.findViewById(R.id.viewpager);
        vp.setAdapter(adapter);
        vp.setOffscreenPageLimit(3);
        vp.setOnPageChangeListener(this);

        radioButton1 = view.findViewById(R.id.bang_one);
        radioButton2 = view.findViewById(R.id.bang_two);
        radioButton3 = view.findViewById(R.id.bang_four);
        radioButton1.setOnClickListener(this);
        radioButton2.setOnClickListener(this);
        radioButton3.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BlankFragment5ViewModel.class);

//            initGuan();
//
//            recyclerView3 = (RecyclerView) getActivity().findViewById(R.id.rank_recycler_view_guan);
//            LinearLayoutManager layoutManager3 = new LinearLayoutManager(getActivity());
//            recyclerView3.setLayoutManager(layoutManager3);
//            GuanAdapter adapter3 = new GuanAdapter(guanList);
//            recyclerView3.setNestedScrollingEnabled(false);
//            recyclerView3.setAdapter(adapter3);

    }





    private void initGuan(){
        for(int i = 0; i <1;i++){
            GuanList apple = new GuanList(i,"添加联系人", R.drawable.add_fen,null,null);
            guanList.add(apple);
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bang_one:
                // your code
                vp.setCurrentItem(0);
                break;
            case R.id.bang_two:
                // your code
                vp.setCurrentItem(1);
                break;
//            case R.id.bang_three:
//                // your code
//                recyclerView.setVisibility(View.GONE);
//                recyclerView2.setVisibility(View.GONE);
//                break;
            case R.id.bang_four:
                // your code
                vp.setCurrentItem(2);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(position == 0){
            radioButton1.setChecked(true);
        }else if (position == 1){
            radioButton2.setChecked(true);
        }else if(position == 2){
            radioButton3.setChecked(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
