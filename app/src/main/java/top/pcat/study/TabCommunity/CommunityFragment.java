package top.pcat.study.TabCommunity;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import ezy.ui.layout.LoadingLayout;
import top.pcat.study.R;


public class CommunityFragment extends Fragment {
    private final List<Dynamic> fruitList = new ArrayList<>();
    private RefreshLayout mRefreshLayout;
    private ClassicsHeader mClassicsHeader;
    private Drawable mDrawableProgress;
    private static boolean isFirstEnter = true;

    private final Random random = new Random();
    private DynamicAdapter adapter;
    private LoadingLayout mLoadingLayout;

    public CommunityFragment() {
    }

    public static CommunityFragment newInstance() {
        return new CommunityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewf = inflater.inflate(R.layout.fragment_com, container, false);
        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.kk));
        mRefreshLayout = viewf.findViewById(R.id.refreshLayout);
        mRefreshLayout.setDragRate(1.0f);

        int delta = new Random().nextInt(7 * 24 * 60 * 60 * 1000);
        mClassicsHeader = (ClassicsHeader) mRefreshLayout.getRefreshHeader();
        mClassicsHeader.setLastUpdateTime(new Date(System.currentTimeMillis() - delta));
        mClassicsHeader.setTimeFormat(new SimpleDateFormat("????????? MM-dd HH:mm", Locale.CHINA));
        mClassicsHeader.setTimeFormat(new DynamicTimeFormat("????????? %s"));
        mDrawableProgress = ((ImageView) mClassicsHeader.findViewById(ClassicsHeader.ID_IMAGE_PROGRESS)).getDrawable();
        if (mDrawableProgress instanceof LayerDrawable) {
            mDrawableProgress = ((LayerDrawable) mDrawableProgress).getDrawable(0);
        }
        initFruits();

        RecyclerView recyclerView = viewf.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DynamicAdapter(fruitList);
        recyclerView.setAdapter(adapter);


        mRefreshLayout.setOnRefreshListener(this::refresh);
        mRefreshLayout.setOnLoadMoreListener(this::loadMore);

        if (isFirstEnter) {
            isFirstEnter = false;
            mRefreshLayout.autoRefresh();
        }
        return viewf;
    }


    private void initFruits() {
        for (int i = 0; i < 6; i++) {
            Dynamic apple = new Dynamic("??????", R.drawable.touxiang,"??????????????????","??????????????????????????????????????????????????????????????????????????????v?????????????????????????????????????????????????????????????????????????????????????????????");
            fruitList.add(apple);

        }
    }


    private void loadMore(RefreshLayout layout) {
        Toast.makeText(getActivity(), "loadMore", Toast.LENGTH_SHORT).show();
        layout.getLayout().postDelayed(() -> {
            if (random.nextBoolean()) {
                //??????????????????
                initFruits();
                adapter.notifyDataSetChanged();
                //adapter.notifyListDataSetChanged();
                if (adapter.getItemCount() <= 30) {
                    //??????????????????
                    layout.finishLoadMore();
                } else {
                    //?????????????????????????????????????????????????????????????????????
                    Toast.makeText(getActivity(), "????????????????????????", Toast.LENGTH_SHORT).show();
                    layout.finishLoadMoreWithNoMoreData();//???????????????????????????????????????
                }
            } else {
                //????????????
                layout.finishLoadMore(false);
            }
        }, 2000);
    }

    private void refresh(RefreshLayout refresh) {
        Toast.makeText(getActivity(), "refresh", Toast.LENGTH_SHORT).show();
        refresh.getLayout().postDelayed(() -> {
            if (random.nextBoolean()) {
                //??????????????????
                initFruits();
                adapter.notifyDataSetChanged();
                if (adapter.getItemCount() <= 30) {
                    //??????????????????
                    refresh.finishRefresh();
                } else {
                    //?????????????????????????????????????????????????????????????????????
                    refresh.finishRefreshWithNoMoreData();
                }
            } else {
                //????????????
                refresh.finishRefresh(false);
                if (fruitList.size() == 0) {
                    mLoadingLayout.showError();
                    mLoadingLayout.setErrorText("?????????????????????????????????");
                }
            }
        }, 2000);
    }


}