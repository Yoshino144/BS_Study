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
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apkfuns.logutils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import es.dmoral.toasty.Toasty;
import ezy.ui.layout.LoadingLayout;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import top.pcat.study.Curriculum.Adapter.CurItemAdapter;
import top.pcat.study.Curriculum.Adapter.OCurItemAdapter;
import top.pcat.study.Pojo.Subject;
import top.pcat.study.R;
import top.pcat.study.TabCommunity.DynamicTimeFormat;

/**
 * A fragment representing a list of Items.
 */
public class OItemFragment extends Fragment {

    private RefreshLayout mRefreshLayout;
    private ClassicsHeader mClassicsHeader;
    private Drawable mDrawableProgress;
    private static boolean isFirstEnter = true;

    private String uuid;
    private String url;
    private final Random random = new Random();
    private OCurItemAdapter adapter;
    private LoadingLayout mLoadingLayout;
    private List<Subject> subjectList = new ArrayList<>();
    private String flag;

    private Handler handler =  new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what == 0){
                LogUtils.d(flag+"????????????");
                RecyclerView();
            }
            return false;
        }
    });

    public OItemFragment(){

    }

    public OItemFragment(String uuid, String flag, String url) {
        this.uuid = uuid;
        this.url = url;
        this.flag = flag;

    }

    public static OItemFragment newInstance(String uuid, String flag, String url) {
        OItemFragment fragment = new OItemFragment();
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
        View viewf = inflater.inflate(R.layout.fragment_ocom, container, false);
        uuid = getArguments().getString("uuid");
        url = getArguments().getString("url");
        flag = getArguments().getString("flag");

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

        mRefreshLayout.setOnRefreshListener(this::refresh);
        mRefreshLayout.setOnLoadMoreListener(this::loadMore);

        if (isFirstEnter) {
            isFirstEnter = false;
            mRefreshLayout.autoRefresh();
        }
        return viewf;
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
                LogUtils.d(flag+"??????????????????"+url);
                Toasty.error(getContext(), "??????????????????", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String rr = response.body().string();
                LogUtils.d("??????"+"????????????" + rr);



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
                LogUtils.d(flag+"??????????????????"+url);
                Toasty.error(getContext(), "??????????????????", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                String rr = response.body().string();
                LogUtils.d("??????"+"????????????" + rr);



            }
        });
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
        RecyclerView recyclerView = getActivity().findViewById(R.id.recyclerView_o);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new OCurItemAdapter(subjectList);
        recyclerView.setAdapter(adapter);


        adapter.buttonSetOnclick((isChecked, subjectId) -> {
            if (isChecked){
                LogUtils.d("?????????"+subjectId);
                upChoose(getResources().getString(R.string.network_url)+"/subjects/"+subjectId+"/users/"+uuid);
            }else{
                LogUtils.d("?????????"+subjectId);
                delChoose(getResources().getString(R.string.network_url)+"/subjects/"+subjectId+"/users/"+uuid);
            }
        });
    }


    private void loadMore(RefreshLayout layout) {
        //Toast.makeText(getActivity(), "loadMore", Toast.LENGTH_SHORT).show();
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
        //Toasty.info(getActivity(), "refresh").show();
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
//                if (fruitList.size() == 0) {
//                    mLoadingLayout.showError();
//                    mLoadingLayout.setErrorText("?????????????????????????????????");
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
                LogUtils.d(flag+"??????????????????"+url);
                Toasty.error(getContext(), "??????????????????", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                String rr = response.body().string();
                LogUtils.d(flag+url+"????????????" + rr);

                subjectList = gson.fromJson(rr,new TypeToken<List<Subject>>() {}.getType());
                Message message = new Message();
                message.what = 0;
                handler.sendMessage(message);

            }
        });
    }
}