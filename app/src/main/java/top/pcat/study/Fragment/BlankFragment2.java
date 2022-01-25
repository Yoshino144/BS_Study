package top.pcat.study.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import top.pcat.study.Banner.DataBean;
import top.pcat.study.Banner.ImageAdapter;
import top.pcat.study.Curriculum.Collection;
import top.pcat.study.Curriculum.Curriculum;
import top.pcat.study.Utils.FileTool;
import top.pcat.study.MainActivity;
import top.pcat.study.R;
import top.pcat.study.Size.ChapterActivity;
import top.pcat.study.Size.DisplayUtil;
import top.pcat.study.View.F2BangAdapter;
import top.pcat.study.View.F2BangItemAdapter;
import top.pcat.study.View.Fragment2Adapter;
import top.pcat.study.View.HengRecyclerView;
import top.pcat.study.View.ItemF2Bang;
import top.pcat.study.View.ItemF2BangItem;
import top.pcat.study.View.ItemFragment2;
import top.pcat.study.View.LogUtils;
import top.pcat.study.user.SignActivity;

import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.indicator.RoundLinesIndicator;
import com.youth.banner.listener.OnPageChangeListener;
import com.youth.banner.util.BannerUtils;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class BlankFragment2 extends Fragment implements OnPageChangeListener {
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
    private Handler handler = new Handler();
    private int result;
    private DisplayUtil dipToPix;
    private String interres;
    private String tempTest;
    private int nowPage = 0;
    private boolean signFlag;
    private final Handler cwjHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
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
                            GetData(bb, subject_name, "http://192.168.31.238:12345/chapters/getBySubjectId/" + bb);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                    Toast.makeText(getActivity(), bb, Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(getActivity(), "456", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private final Runnable mUpdateResults = this::updata2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //是否第一次加载
    private boolean isFirstLoading = true;

    private OnFragmentInteractionListener mListener;
    private NestedScrollView nsv;

    public BlankFragment2() {
    }

    public static BlankFragment2 newInstance(String param1, String param2) {
        BlankFragment2 fragment = new BlankFragment2();
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
        if (!FileTool.isFileExists(path.toString())) {
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

        File path = new File(getActivity().getFilesDir().getAbsolutePath() + "/Login.txt");
        if (ft.isFileExists(path.toString())) {
            //已登陆
            signFlag = true;
            //读取string判断课程是否选中
            tempTest = "cpp,java";
            try {
                GetYixuan("http://192.168.31.238:12345/subjects/" + readInfo(readId()));
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
                LogUtils.d(" x" + String.valueOf(scrollX) + " y" + String.valueOf(scrollY)
                        + " ox" + String.valueOf(oldScrollX) + " oy" + String.valueOf(oldScrollY));
                if (scrollY <= 200) {

                    Double i = ((double) scrollY) / ((double) 200);
                    int a = (int) (i * 255);
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.setcolor(a);
                } else {

                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.setcolor(255);
                }

                if ((scrollY+100) >= (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()+10)) {
                    ((MainActivity) requireActivity()).Hide();
                }
                if (scrollY < oldScrollY) {
                    ((MainActivity) requireActivity()).Display();
                }

            }
        });

        updata_bang();
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
        Fragment2Adapter adapter = new Fragment2Adapter(itemFragment2s, cwjHandler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

    private void updata_bang() {
        List<ItemF2Bang> itemFragment3 = new ArrayList<>();

        try {
            initBang(itemFragment3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_kechngbangdan);
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
        F2BangAdapter adapter = new F2BangAdapter(itemFragment3, cwjHandler) {

        };
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

    private void initBang(List<ItemF2Bang> itemFragment3) throws JSONException {

        try {
            for (int i = 0; i < 1; i++) {
                ItemF2Bang apple = new ItemF2Bang(
                        "人数榜", 2);
                itemFragment3.add(apple);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }

//    private void updata_bang_item(){
//        List<ItemF2BangItem> itemFragment4 = new ArrayList<>();
//
//        try {
//            initBang_item(itemFragment4);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_kechngbangdan_item);
//        StaggeredGridLayoutManager layoutManager = new
//                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
//        recyclerView.setLayoutManager(layoutManager);
//        F2BangItemAdapter adapter = new F2BangItemAdapter(itemFragment4,cwjHandler);
//        recyclerView.setAdapter(adapter);
//    }


//    private void initBang_item(List<ItemF2BangItem> itemFragment3) throws JSONException {
//
//        try {
//            for(int i =0 ; i < 2;i++){
//                ItemF2BangItem apple = new ItemF2BangItem(
//                        "人数榜",2);
//                itemFragment3.add(apple);
//            }
//        } catch(Exception exception){
//            exception.printStackTrace();
//        }
//
//
//
//    }

    public String readId() {
        String result = "";
        try {
            FileInputStream fin = getActivity().openFileInput("UserInfo");
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            result = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //从读取的文件获取用户ID
    public String readInfo(String tempInfo) {
        try {
            JSONObject jsonArray = new JSONObject(tempInfo);
            String id = jsonArray.getString("id");

            //String lv ="10";
            return id;
        } catch (JSONException e) {
            //Toast.makeText(getActivity(),"信息读取错误",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return "error";
    }


    public void saveccc(String temp) {
        String FILENAME = "c++.json";
        FileOutputStream fos = null;
        try {
            fos = getActivity().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(temp.getBytes());
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public Integer activityChangeFragment(String data) {
        return DataBean.getTestData().get(nowPage).imageUrl;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
                Log.d("Internet类", "url=============" + uu);
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
                    Log.d("列表内容传递======", interres);


                    Intent intent01 = new Intent();
                    intent01.setClass(getActivity(), ChapterActivity.class);
                    intent01.putExtra("subject_name", subject_name);
                    intent01.putExtra("subject_id", subject_id);
                    intent01.putExtra("item", interres);
                    Log.d("列表内容传递======", interres);
                    //getActivity().finish();
                    startActivity(intent01);
                    //getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    getActivity().finish();

//                    progressDialog.dismiss();
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
                Log.d("获取已选", "url=============" + url);
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
                    cwjHandler.post(mUpdateResults);

                    // Message message = null;
                    //message.what = 0x11;
                    // cwjHandler.sendMessage(message);

                    Log.d("用户已选======", tempTest);


//                    progressDialog.dismiss();
                } else {
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!isFirstLoading) {
            //如果不是第一次加载，刷新数据
            if (signFlag) {
                try {
                    GetYixuan("http://192.168.31.238:12345/subjects/getById/" + readInfo(readId()));
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

                ItemFragment2 apple = new ItemFragment2(jsonObject.getString("idSubject"),
                        getRandomLengthName(jsonObject.getString("nameSubject")), R.drawable.image11, jsonObject.getString("sizeSubject"));
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


}
