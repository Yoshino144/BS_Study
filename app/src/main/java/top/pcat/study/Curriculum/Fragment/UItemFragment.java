package top.pcat.study.Curriculum.Fragment;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apkfuns.logutils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import es.dmoral.toasty.Toasty;
import ezy.ui.layout.LoadingLayout;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import top.pcat.study.Curriculum.Adapter.CurItemAdapter;
import top.pcat.study.Curriculum.Adapter.UCurItemAdapter;
import top.pcat.study.Pojo.Subject;
import top.pcat.study.R;
import top.pcat.study.TabCommunity.DynamicTimeFormat;

/**
 * A fragment representing a list of Items.
 */
public class UItemFragment extends Fragment {

    private RefreshLayout mRefreshLayout;
    private ClassicsHeader mClassicsHeader;
    private Drawable mDrawableProgress;
    private static boolean isFirstEnter = true;

    private String uuid;
    private String url;
    private final Random random = new Random();
    private UCurItemAdapter adapter;
    private LoadingLayout mLoadingLayout;
    private List<Subject> subjectList = new ArrayList<>();
    private String flag;

    private Handler handler =  new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what == 0){
                LogUtils.d(flag+"请求成功");
                RecyclerView();
            }
            return false;
        }
    });

    public UItemFragment(){

    }

    public UItemFragment(String uuid, String flag, String url) {
        this.uuid = uuid;
        this.url = url;
        this.flag = flag;

    }

    public static UItemFragment newInstance(String uuid, String flag, String url) {
        UItemFragment fragment = new UItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("uuid", uuid);
        bundle.putString("url", url);
        bundle.putString("flag", flag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewf = inflater.inflate(R.layout.fragment_ucom, container, false);
        uuid = getArguments().getString("uuid");
        url = getArguments().getString("url");
        flag = getArguments().getString("flag");

        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.kk));
        mRefreshLayout = viewf.findViewById(R.id.refreshLayout);
        mRefreshLayout.setDragRate(1.0f);

        int delta = new Random().nextInt(7 * 24 * 60 * 60 * 1000);
        mClassicsHeader = (ClassicsHeader) mRefreshLayout.getRefreshHeader();
        mClassicsHeader.setLastUpdateTime(new Date(System.currentTimeMillis() - delta));
        mClassicsHeader.setTimeFormat(new SimpleDateFormat("更新于 MM-dd HH:mm", Locale.CHINA));
        mClassicsHeader.setTimeFormat(new DynamicTimeFormat("更新于 %s"));
        mDrawableProgress = ((ImageView) mClassicsHeader.findViewById(ClassicsHeader.ID_IMAGE_PROGRESS)).getDrawable();
        if (mDrawableProgress instanceof LayerDrawable) {
            mDrawableProgress = ((LayerDrawable) mDrawableProgress).getDrawable(0);
        }
        initFruits();

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refresh(refreshLayout);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(this::loadMore);

        if (isFirstEnter) {
            isFirstEnter = false;
            mRefreshLayout.autoRefresh();
        }
        return viewf;
    }


    private void initFruits() {

        subjectList.clear();
        try {
            getData(url);
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.d(e.toString());
        }

    }

    private void RecyclerView() {
        RecyclerView recyclerView = getActivity().findViewById(R.id.recyclerView_u);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new UCurItemAdapter(subjectList);
        recyclerView.setAdapter(adapter);


        adapter.buttonSetOnclick((isChecked, subjectId) -> {
            if (isChecked){
                LogUtils.d("选择了"+subjectId);
                upChoose(getResources().getString(R.string.network_url)+"/subjects/"+subjectId+"/users/"+uuid);
            }else{
                LogUtils.d("取消了"+subjectId);
                delChoose(getResources().getString(R.string.network_url)+"/subjects/"+subjectId+"/users/"+uuid);
            }
        });
    }

    public void upChoose(String url) throws IOException {
        RequestBody requestBody = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Looper.prepare();
                LogUtils.d(flag+"网络连接失败"+url);
                Toasty.error(getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String rr = response.body().string();
                LogUtils.d("选择"+"返回内容" + rr);



            }
        });
    }

    public void delChoose(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Looper.prepare();
                LogUtils.d(flag+"网络连接失败"+url);
                Toasty.error(getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                String rr = response.body().string();
                LogUtils.d("删除"+"返回内容" + rr);



            }
        });
    }


    private void loadMore(RefreshLayout layout) {
        //Toast.makeText(getActivity(), "loadMore", Toast.LENGTH_SHORT).show();
        layout.getLayout().postDelayed(() -> {
            if (random.nextBoolean()) {
                //如果刷新成功
                initFruits();
                adapter.notifyDataSetChanged();
                //adapter.notifyListDataSetChanged();
                if (adapter.getItemCount() <= 30) {
                    //还有多的数据
                    layout.finishLoadMore();
                } else {
                    //没有更多数据（上拉加载功能将显示没有更多数据）
                    Toast.makeText(getActivity(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
                    layout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                }
            } else {
                //刷新失败
                layout.finishLoadMore(false);
            }
        }, 2000);
    }

    private void refresh(RefreshLayout refresh) {
        //Toasty.info(getActivity(), "refresh").show();
        refresh.getLayout().postDelayed(() -> {
            if (random.nextBoolean()) {
                //如果刷新成功
                initFruits();
                adapter.notifyDataSetChanged();
                if (adapter.getItemCount() <= 30) {
                    //还有多的数据
                    refresh.finishRefresh();
                } else {
                    //没有更多数据（上拉加载功能将显示没有更多数据）
                    refresh.finishRefreshWithNoMoreData();
                }
            } else {
                //刷新失败
                refresh.finishRefresh(false);
//                if (fruitList.size() == 0) {
//                    mLoadingLayout.showError();
//                    mLoadingLayout.setErrorText("随机触发刷新失败演示！");
//                }
            }
        }, 200);
    }

    public void getData(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Looper.prepare();
                LogUtils.d(flag+"网络连接失败"+url);
                Toasty.error(getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                String rr = response.body().string();
                LogUtils.d(flag+url+"返回内容" + rr);

                subjectList = gson.fromJson(rr,new TypeToken<List<Subject>>() {}.getType());
                Message message = new Message();
                message.what = 0;
                handler.sendMessage(message);

            }
        });
    }
}