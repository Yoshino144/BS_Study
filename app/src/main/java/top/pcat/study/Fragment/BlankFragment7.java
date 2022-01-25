package top.pcat.study.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import top.pcat.study.MainActivity;
import top.pcat.study.R;
import top.pcat.study.Ranking.RankingAdapter;
import top.pcat.study.Ranking.RankingList;

import java.util.ArrayList;
import java.util.List;

public class BlankFragment7 extends Fragment {

    private BlankFragment5ViewModel mViewModel;

    private List<RankingList> rankingList = new ArrayList<>();

    public static BlankFragment7 newInstance() {
        return new BlankFragment7();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.blank_fragment7_fragment, container, false);
        NestedScrollView scrollone=view.findViewById(R.id.scrollone2);
        scrollone.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if ((scrollY+100) >= (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()+10)) {
                    ((MainActivity) requireActivity()).Hide();
                }
                if (scrollY < oldScrollY) {
                    ((MainActivity) requireActivity()).Display();
                }}


        });
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BlankFragment5ViewModel.class);
        // TODO: Use the ViewModel


        initRanking();
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.rank_recycler_view7);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        RankingAdapter adapter = new RankingAdapter(rankingList);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

    private void initRanking() {
        for(int i = 1 ; i < 50;i++){

            RankingList apple = new RankingList(i,"root", R.drawable.aa1);
            rankingList.add(apple);
        }
    }

}
