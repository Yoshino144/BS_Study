package top.pcat.study.TabStudy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import top.pcat.study.Banner.DataBean;
import top.pcat.study.Banner.ImageAdapter;
import top.pcat.study.Curriculum.Adapter.CurItemAdapter;
import top.pcat.study.Curriculum.Curriculum;
import top.pcat.study.Pojo.Subject;
import top.pcat.study.Utils.FileTool;
import top.pcat.study.MainActivity;
import top.pcat.study.R;
import top.pcat.study.Size.ChapterActivity;
import top.pcat.study.Size.DisplayUtil;
import top.pcat.study.Utils.GetUser;
import top.pcat.study.View.F2BangAdapter;
import top.pcat.study.View.F2BangItemAdapter;
import top.pcat.study.View.Fragment2Adapter;
import top.pcat.study.View.ItemF2Bang;
import top.pcat.study.View.ItemFragment2;

import com.apkfuns.logutils.LogUtils;
import com.blankj.utilcode.util.FileUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.indicator.RoundLinesIndicator;
import com.youth.banner.listener.OnPageChangeListener;
import com.youth.banner.util.BannerUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class StudyFragment extends Fragment implements OnPageChangeListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Banner myBanner;
    List<Integer> ImageUrlData;
    List<String> ContentData;
    private boolean allFlag = false;

    private Banner banner;
    private FileTool ft;
    private MainActivity ma;
    private LinearLayout one;
    private LinearLayout two;
    private LinearLayout three;
    private LinearLayout four;
    private LinearLayout oneSize;
    private LinearLayout twoSize;
    private LinearLayout threeSize;
    private LinearLayout fourSize;
    private LinearLayout fiveSize;
    private LinearLayout sixSize;
    private int result;
    private DisplayUtil dipToPix;
    private String interres;
    private String tempTest;
    private List<Subject> subjectList = new ArrayList<>();
    private int nowPage = 0;
    private boolean signFlag;

    private final Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Bundle bundle = msg.getData();
                    String bb = (String) bundle.get("Subject_id");
                    String subject_name = (String) bundle.get("Subject_name");
                    if (bb.indexOf("0") != -1) {
                        if (signFlag) {
                            Intent intent01 = new Intent();
                            intent01.setClass(getActivity(), Curriculum.class);
                            startActivity(intent01);
                            Toast.makeText(getActivity(), "全部", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getActivity(), "请先登录后操作", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        try {
                            GetData(bb, subject_name, "http://172.17.128.1:12345/chapters/" + bb);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                    Toast.makeText(getActivity(), bb, Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(getActivity(), "456", Toast.LENGTH_SHORT).show();
                    break;

                case 2:
                    updata_bang();
                    break;
            }
            return false;
        }
    });
    private final Runnable mUpdateResults = this::updata2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //是否第一次加载
    private boolean isFirstLoading = true;

    private OnFragmentInteractionListener mListener;
    private NestedScrollView nsv;

    public StudyFragment() {
    }

    public static StudyFragment newInstance(String param1, String param2) {
        StudyFragment fragment = new StudyFragment();
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

    View blan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        blan = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_blank_fragment2, container, false);

        initBanner();
        //initBanner();
        File path = new File(requireActivity().getFilesDir().getAbsolutePath() + "/Login.txt");
        if (FileUtils.isFileExists(getActivity().getFilesDir().getAbsolutePath() + "/userToken")) {

            try {
                initBang();
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }

//            LinearLayout qwe = blan.findViewById(R.id.loginFlag);
//            qwe.setVisibility(View.VISIBLE);
//            LogUtils.d("=============未登录-显示登录框=============");
//            qwe.setOnClickListener(v -> {
//                Intent intent01 = new Intent();
//                intent01.setClass(getActivity(), SignActivity.class);
//                intent01.putExtra("page", 0);
//                getActivity().finish();
//                startActivity(intent01);
//            });
        }


        return blan;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        if (FileUtils.isFileExists(getActivity().getFilesDir().getAbsolutePath() + "/userToken")) {
            //已登陆
            signFlag = true;
            //读取string判断课程是否选中
            tempTest = "cpp,java";
            try {
                com.apkfuns.logutils.LogUtils.d("获取该id的已选的科目：" + GetUser.getUserId(getContext()));
                GetYixuan("http://172.17.128.1:12345/subjects/" + GetUser.getUserId(getContext()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {//未登录
            signFlag = false;
        }

        nsv = getActivity().findViewById(R.id.nsv);
        nsv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {


            int now_pos = 0;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //LogUtils.d(" x" + String.valueOf(scrollX) + " y" + String.valueOf(scrollY)
                //  + " ox" + String.valueOf(oldScrollX) + " oy" + String.valueOf(oldScrollY));
                if (scrollY <= 200) {

                    Double i = ((double) scrollY) / ((double) 200);
                    float a = (float) (i * 10);
                    MainActivity mainActivity = (MainActivity) getActivity();
                    if (a == 0.0) {
                        mainActivity.setcolor((float) 0.1);
                    } else {
                        mainActivity.setcolor(a);
                    }
                } else {

                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.setcolor(10);
                }

                if ((scrollY + 100) >= (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight() + 10)) {
                    ((MainActivity) requireActivity()).Hide();
                }
                if (scrollY < oldScrollY) {
                    ((MainActivity) requireActivity()).Display();
                }

            }
        });

        //updata_bang();
        //updata_bang_item();


        three = getActivity().findViewById(R.id.twoThree);
        three.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (signFlag) {
                    Intent intent01 = new Intent();
                    intent01.setClass(getActivity(), Curriculum.class);
                    startActivity(intent01);
                    //Toast.makeText(getActivity(), "全部", 0).show();

                } else {
                    Toast.makeText(getActivity(), "请先登录后操作", 0).show();

                }


            }
        });


    }

    //刷新界面
    private void updata2() {
        List<ItemFragment2> itemFragment2s = new ArrayList<>();

        try {
            initFruits(itemFragment2s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new
                LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) {
                    @Override
                    public boolean canScrollHorizontally() {
                        return false;
                    }

                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };
        recyclerView.setLayoutManager(layoutManager);
        Fragment2Adapter adapter = new Fragment2Adapter(itemFragment2s, handler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

    private void updata_bang() {

        LogUtils.d("加载课程页榜单数据");
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_kechngbangdan_item);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

        };
        recyclerView.setLayoutManager(layoutManager);
        F2BangAdapter adapter = new F2BangAdapter(subjectList, handler) {

        };
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);

    }

    private void initBang() throws JSONException, IOException {
        LogUtils.d("初始化课程页榜单");
        getData("http://172.17.128.1:12345/subjects/"+ GetUser.getUserId(getContext()) + "/official");

    }


    private void initBanner() {

        //CircleIndicator circleIndicator = new CircleIndicator(this.getActivity());
        RoundLinesIndicator indicator = blan.findViewById(R.id.indicatorrr);
        myBanner = (Banner) blan.findViewById(R.id.banner);

        //默认直接设置adapter就行了
        myBanner.setAdapter(new ImageAdapter(DataBean.getTestData()));
        myBanner.isAutoLoop(true);
        myBanner.addOnPageChangeListener(this);

        myBanner.setIndicator(indicator, false);
        myBanner.setIndicatorSelectedWidth((int) BannerUtils.dp2px(15));
        myBanner.setLoopTime(10000);
//myBanner.setUserInputEnabled(false);
        myBanner.start();


    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //nowPage = position;
        //LogUtils.d("tag", "第"+position+"张轮播图");
    }

    @Override
    public void onPageSelected(int position) {
        //LogUtils.d("tag", "2"+position+"张轮播图");
        nowPage = position;
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setNowPage(DataBean.getTestData().get(position).imageUrl);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void GetData(String subject_id, String subject_name, String url) throws IOException {


        new Thread(() -> {

            try {
                URL uu = new URL(url);
                LogUtils.d("Internet类", "url=============" + uu);
                HttpURLConnection connection = (HttpURLConnection) uu.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();


                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    String result = String.valueOf(inputStream);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    interres = response.toString();
                    LogUtils.d("列表内容传递======", interres);


                    Intent intent01 = new Intent();
                    intent01.setClass(getActivity(), ChapterActivity.class);
                    intent01.putExtra("subject_name", subject_name);
                    intent01.putExtra("subject_id", subject_id);
                    intent01.putExtra("item", interres);
                    LogUtils.d("列表内容传递======", interres);
                    //getActivity().finish();
                    startActivity(intent01);
                    //getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    getActivity().finish();

//                    progressDiaLogUtils.dismiss();
                } else {
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    //获取已选课程
    public void GetYixuan(String url) throws IOException {

        new Thread(() -> {

            try {
                URL uu = new URL(url);
                //LogUtils.d("获取已选"+"url=============" + url);
                HttpURLConnection connection = (HttpURLConnection) uu.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();


                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    String result = String.valueOf(inputStream);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    tempTest = response.toString();
                    handler.post(mUpdateResults);

                    // Message message = null;
                    //message.what = 0x11;
                    // cwjHandler.sendMessage(message);

                    LogUtils.d("用户已选======"+tempTest);


//                    progressDiaLogUtils.dismiss();
                } else {
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 从新加载数据
     */
    @Override
    public void onResume() {
        super.onResume();

        if (!isFirstLoading) {
            //如果不是第一次加载，刷新数据
            if (signFlag) {
                try {
                    GetYixuan("http://172.17.128.1:12345/subjects/" + GetUser.getUserId(getContext()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //updateUI();
        }

        isFirstLoading = false;
    }

    private void initFruits(List<ItemFragment2> itemFragment2s) throws JSONException {

        try {
            LogUtils.d("获取到的已选列表===" + tempTest);
            JSONArray jsonArray = new JSONArray(tempTest);
            int subject_size = jsonArray.length();
            LogUtils.d("获取到的科目数量===" + subject_size);
            for (int i = 0; i < subject_size; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                ItemFragment2 apple = new ItemFragment2(jsonObject.getString("subjectId"),
                        getRandomLengthName(jsonObject.getString("subjectName")), R.drawable.image11, jsonObject.getString("subjectSize"));
                itemFragment2s.add(apple);
                //itemFragment2s.add(apple);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }

    private String getRandomLengthName(String name) {
        Random random = new Random();
        int length = random.nextInt(20) + 1;
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        return builder.toString();
    }

    /**
     * 模拟榜单
     * @param url
     * @throws IOException
     */
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
                LogUtils.d("网络连接失败"+url);
                Toasty.error(getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                String rr = response.body().string();
                LogUtils.d("课程页榜单请求成功"+rr);

                subjectList = gson.fromJson(rr,new TypeToken<List<Subject>>() {}.getType());
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);

            }
        });
    }


}
