package top.pcat.study.TabHome;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import top.pcat.study.Fragment.BlankFragment5ViewModel;
import top.pcat.study.MainActivity;
import top.pcat.study.R;
import top.pcat.study.Ranking.RankingAdapter;
import top.pcat.study.Ranking.RankingList;
import top.pcat.study.Utils.PxToDp;

import java.util.ArrayList;
import java.util.List;

public class RankFragment extends Fragment {

    private BlankFragment5ViewModel mViewModel;

    private List<RankingList> rankingList = new ArrayList<>();

    public static RankFragment newInstance() {
        return new RankFragment();
    }

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
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

        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) view.findViewById(R.id.pai_bar).getLayoutParams();
        params2.setMargins(0, getStatusBarHeight(view.getContext()) + PxToDp.dip2px(view.getContext(), 59), 0, 0);//left,top,right,bottom
        view.findViewById(R.id.pai_bar).setLayoutParams(params2);

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
