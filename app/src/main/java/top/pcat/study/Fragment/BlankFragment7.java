package top.pcat.study.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        return inflater.inflate(R.layout.blank_fragment7_fragment, container, false);
    }

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
        RankingList apple = new RankingList(1,"root", R.drawable.aa1);
        rankingList.add(apple);
        apple = new RankingList(2,"张嘀咕", R.drawable.aa2);
        rankingList.add(apple);
        apple = new RankingList(3,"武小松", R.drawable.aa3);
        rankingList.add(apple);
        apple = new RankingList(4,"怜雪", R.drawable.aa4);
        rankingList.add(apple);
        apple = new RankingList(5,"藏小航", R.drawable.aa5);
        rankingList.add(apple);
        apple = new RankingList(6,"李嘉琪", R.drawable.aa6);
        rankingList.add(apple);
        apple = new RankingList(7,"张快", R.drawable.aa7);
        rankingList.add(apple);
        apple = new RankingList(8,"吴凯心", R.drawable.aa8);
        rankingList.add(apple);
    }

}
