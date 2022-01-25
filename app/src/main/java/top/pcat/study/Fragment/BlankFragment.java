package top.pcat.study.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import top.pcat.study.Fresh.CircleRefreshLayout;
import top.pcat.study.MainActivity;
import top.pcat.study.Utils.FileTool;
import top.pcat.study.R;
import top.pcat.study.Utils.PxToDp;
import top.pcat.study.View.CircleProgressView;
import top.pcat.study.View.LogUtils;
import top.pcat.study.user.SignActivity;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.TimeZone;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * aaa simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragment2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private JSONObject threeSize;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView a;
    private TextView b;
    private TextView c;
    private TextView d;
    private TextView e;
    private TextView f;
    private TextView g;
    private TextView meiri;
    private TextView meiri_f;
    private TextView meiri_b;
    private TextView meiri_fb;
    private CircleProgressView meiri_t;
    private CircleProgressView meiri_t2;
    private CircleRefreshLayout circleRefreshLayout;
    private LinearLayout lin;
    private LinearLayout qwe;
    private ImageView asd;
    private Button btn;

    private String Sunday = "0";
    private String Moonday = "0";
    private String Tuesday = "0";
    private String Wednesday = "0";
    private String Thursday = "0";
    private String Friday = "0";
    private String Saturday = "0";
    private String onesize;
    private String allsize = "0";
    private String timusize = "0";
    private String usertext;
    private Button sign;
    private Button resg;

    private static String mYear;
    private static String mMonth;
    private static String mDay;
    private static String mWay;
    private FileTool ft;
    private LinearLayout baitiao;
    String todaySize;
    private String username;
    private String userId;
    private String userdata;
    private LinearLayout  tt;
    private TextView todaysize;
    private NestedScrollView scrollone;

    private TextView baifenbitext;
    private TextView timusizetext;
    private TextView allsizetext;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:

                    try {
                        allsize = threeSize.getString("yiXuanAllSize");

                    timusize = threeSize.getString("yiXuanDoSize");
                    LogUtils.d(allsize + " fghdrfcgvbn " + timusize);


                    allsizetext.setText(allsize + " 道");
                    timusizetext.setText(timusize + " 道");
                    todaysize.setText("+ " + threeSize.getString("todaySize") + " 道");
                    if (allsize == "null") {
                        allsize = "1";
                    }
                    if (timusize == "null") {
                        timusize = "1";
                    }
                    Double all = Double.valueOf(allsize);
                    Double timu = Double.valueOf(timusize);
                    double baifen = timu / all * 100;
                    NumberFormat nf = NumberFormat.getNumberInstance();
                    nf.setMaximumFractionDigits(2);
                    baifenbitext.setText(nf.format(baifen) + " %");
                    double ww = baitiao.getWidth()/100;
                    tt.setLayoutParams(new RelativeLayout.LayoutParams((int) (ww*baifen), PxToDp.dip2px(getActivity(),10)));

                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }

                    break;

            }
        }
    };

    private OnFragmentInteractionListener mListener;

    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return aaa new instance of fragment BlankFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);

        meiri = rootView.findViewById(R.id.meiri);
        meiri_b = rootView.findViewById(R.id.meiri_bai);
        meiri_f = rootView.findViewById(R.id.meiri_fen);
        meiri_fb = rootView.findViewById(R.id.meiri_fen_bai);
        meiri_t = rootView.findViewById(R.id.meiri_q);
        meiri_t2 =  rootView.findViewById(R.id.meiri_q2);
        File path = new File(getActivity().getFilesDir().getAbsolutePath() + "/Login.txt");
        if (ft.isFileExists(path.toString())) {

            try {
                UpData(userId);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            //登录
            Log.d("登录状态=============", "已登录");
            baifenbitext = rootView.findViewById(R.id.baifenbi);
            timusizetext = rootView.findViewById(R.id.timusize);
            allsizetext = rootView.findViewById(R.id.allsizetext);
            todaysize = rootView.findViewById(R.id.todaysize);
            tt= rootView.findViewById(R.id.lantiao);
            baitiao = rootView.findViewById(R.id.baitiao);
            scrollone = rootView.findViewById(R.id.scrollone);
            TextView usertext2 = rootView.findViewById(R.id.usertext);
            todaySize = read("UserInfo");
            readSj(todaySize);

            scrollone.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if ((scrollY+100) >= (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    ((MainActivity) requireActivity()).Hide();
                }
                if (scrollY < oldScrollY) {
                    ((MainActivity) requireActivity()).Display();
                }}


            });

            userdata = read("UserData");
            readtb(userdata);
//
//            Log.d("今天日期=============",mWay);
//            if(mWay.matches("Monday")){
//                todaysize.setText("+ "+Moonday+" 道");
//            }else if(mWay.matches("Tuesday")){
//                todaysize.setText("+ "+Tuesday+" 道");
//            }else if(mWay.matches("Wednesday")){
//                todaysize.setText("+ "+Wednesday+" 道");
//            }else if(mWay.matches("Thursday")){
//                todaysize.setText("+ "+Thursday+" 道");
//            }else if(mWay.matches("Friday")){
//                todaysize.setText("+ "+Friday+" 道");
//                Log.d("今日题数===========",Friday);
//            }else if(mWay.matches("Saturday")){
//                todaysize.setText("+ "+Saturday+" 道");
//            }else if(mWay.matches("Sunday")){
//                todaysize.setText("+ "+Sunday+" 道");
//            }
            getSize(userId);

            usertext2.setText("成功的决心远胜于任何东西");
            usertext2.setText(usertext);
        } else {
            Log.d("登录状态=============", "未登录");
            TextView baifenbitext = rootView.findViewById(R.id.baifenbi);
            TextView timusizetext = rootView.findViewById(R.id.timusize);
            TextView allsizetext = rootView.findViewById(R.id.allsizetext);
            TextView todaysize = rootView.findViewById(R.id.todaysize);
            TextView usertext2 = rootView.findViewById(R.id.usertext);
            qwe = rootView.findViewById(R.id.loginFlag);
            qwe.setVisibility(View.VISIBLE);
            todaysize.setText("+ " + "0 道");
            allsizetext.setText("0 道");

            meiri.setText("0 / 50 道");
            meiri_b.setText("0%");
            meiri_f.setText("0 / 45 min");
            meiri_fb.setText("0%");
            meiri_t.setProgress(0);
            meiri_t2.setProgress(0);
            timusizetext.setText("0 道");
            baifenbitext.setText("0.0 %");
            usertext2.setText("成功的决心远胜于任何东西");
        }



        return rootView;
    }


    private void getSize(String userId) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();//新建一个OKHttp的对象
                Request request = new Request.Builder()
                        .url("http://192.168.31.238:12345/userdates/4/" + userId)
                        .get()
                        .build();//创建一个Request对象
                Response response = client.newCall(request).execute();//发送请求获取返回数据
                String responseData = response.body().string();//处理返回的数据
                LogUtils.d(responseData);

                String res = responseData.toString();

                threeSize = new JSONObject(res);


                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void readtb(String tempInfo) {
        try {
            JSONObject jsonObject = new JSONObject(tempInfo);

            todaysize.setText("+ " + jsonObject.getString(LocalDate.now().toString()) + " 道");

            //ToDo gai
            meiri.setText(jsonObject.getString(LocalDate.now().toString())+" / 50 道");
            meiri_b.setText("25%");
            meiri_f.setText("33 / 45 min");
            meiri_fb.setText("56%");
            meiri_t.setProgress(25);
            meiri_t2.setProgress(56);

            Sunday = jsonObject.getString(LocalDate.now().plusDays(-6).toString());
            Moonday = jsonObject.getString(LocalDate.now().plusDays(0).toString());
            Tuesday = jsonObject.getString(LocalDate.now().plusDays(-1).toString());
            Wednesday = jsonObject.getString(LocalDate.now().plusDays(-2).toString());
            Thursday = jsonObject.getString(LocalDate.now().plusDays(-3).toString());
            Friday = jsonObject.getString(LocalDate.now().plusDays(-4).toString());
            Saturday = jsonObject.getString(LocalDate.now().plusDays(-5).toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        circleRefreshLayout = getActivity().findViewById(R.id.refresh_layout);
        circleRefreshLayout.setOnRefreshListener(
                new CircleRefreshLayout.OnCircleRefreshListener() {
                    @Override
                    public void refreshing() {
                        // do something when refresh starts
                        LogUtils.d("加载refreshing()");
                        Thread myThread = new Thread() {//创建子线程
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void run() {
                                try {
                                    UpData(userId);
                                    todaySize = read("UserInfo");
                                    readSj(todaySize);
                                    StringData();

                                    userdata = read("UserData");
                                    readtb(userdata);
                                    getBaiFenBi();

                                    LogUtils.d("停止刷新");
                                    sleep(1000);
                                    circleRefreshLayout.finishRefreshing();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        myThread.start();

                    }

                    @Override
                    public void completeRefresh() {
                        // do something when refresh complete
                        LogUtils.d("加载completeRefresh()");

                    }
                });


        Button mStop = (Button) getActivity().findViewById(R.id.stop_refresh);
        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleRefreshLayout.finishRefreshing();
            }
        });

        qwe = getActivity().findViewById(R.id.loginFlag);
        qwe.setOnClickListener(v -> {
            Intent intent01 = new Intent();
            intent01.setClass(getActivity(), SignActivity.class);
            intent01.putExtra("page", 0);
            getActivity().finish();
            startActivity(intent01);
        });

        asd = getActivity().findViewById(R.id.editText);
        asd.setOnClickListener(v -> {
            onCreateNameDialog();
        });

        LinearLayout sun2 = getActivity().findViewById(R.id.sun2);
        LinearLayout mon2 = getActivity().findViewById(R.id.mon2);
        LinearLayout tue2 = getActivity().findViewById(R.id.tue2);
        LinearLayout wed2 = getActivity().findViewById(R.id.wed2);
        LinearLayout thu2 = getActivity().findViewById(R.id.thu2);
        LinearLayout fri2 = getActivity().findViewById(R.id.fri2);
        LinearLayout sat2 = getActivity().findViewById(R.id.sat2);

        getBaiFenBi();
        try {
            initCubic();
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }

    private void onCreateNameDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View nameView = layoutInflater.inflate(R.layout.layout_settext, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(nameView);

        final EditText userInput = (EditText) nameView.findViewById(R.id.changename_edit);
        final TextView name = (TextView) getActivity().findViewById(R.id.usertext);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                name.setText(userInput.getText());
                                try {
                                    saveJson("text", String.valueOf(userInput.getText()));
                                    UpText("id", readInfo(readId()), "text", String.valueOf(userInput.getText()), "http://192.168.31.238/web/UpText.php");
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        })
                .setNegativeButton("取消",
                        (dialog, id) -> dialog.cancel());

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    public static String StringData() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "Sunday";
        } else if ("2".equals(mWay)) {
            mWay = "Monday";
        } else if ("3".equals(mWay)) {
            mWay = "Tuesday";
        } else if ("4".equals(mWay)) {
            mWay = "Wednesday";
        } else if ("5".equals(mWay)) {
            mWay = "Thursday";
        } else if ("6".equals(mWay)) {
            mWay = "Friday";
        } else if ("7".equals(mWay)) {
            mWay = "Saturday";
        }
        return mWay;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getBaiFenBi() {
        int[] num = new int[7];

        float sun1num = 3;
        float sun2num = 7;

        float mon1num = 4;
        float mon2num = 6;

        float tue1num = 45;
        float tue2num = 55;

        float wed1num = 2;
        float wed2num = 8;

        float thu1num = 23;
        float thu2num = 77;

        float fri1num = 0;
        float fri2num = 100;

        float sat1num = 55;
        float sat2num = 45;

        int sum = 0;

        File path = new File(getActivity().getFilesDir().getAbsolutePath() + "/Login.txt");
        if (ft.isFileExists(path.toString())) {
            num[0] = Integer.parseInt(Sunday);
            num[1] = Integer.parseInt(Moonday);
            num[2] = Integer.parseInt(Tuesday);
            num[3] = Integer.parseInt(Wednesday);
            num[4] = Integer.parseInt(Thursday);
            num[5] = Integer.parseInt(Friday);
            num[6] = Integer.parseInt(Saturday);
            int max = 0;
            for (int i = 0; i < 7; i++) {
                sum += num[i];
                if (max < num[i]) {
                    max = num[i];
                }
            }
            if (max == 0) max = 1;
            sun1num = 100 - num[0] * 100 / max;
            sun2num = num[0] * 100 / max;

            mon1num = 100 - num[1] * 100 / max;
            mon2num = num[1] * 100 / max;

            tue1num = 100 - num[2] * 100 / max;
            tue2num = num[2] * 100 / max;

            wed1num = 100 - num[3] * 100 / max;
            wed2num = num[3] * 100 / max;

            thu1num = 100 - num[4] * 100 / max;
            thu2num = num[4] * 100 / max;

            fri1num = 100 - num[5] * 100 / max;
            fri2num = num[5] * 100 / max;

            sat1num = 100 - num[6] * 100 / max;
            sat2num = num[6] * 100 / max;

            TextView maxnum = getActivity().findViewById(R.id.max_num);
            maxnum.setText(String.valueOf(max));

            int avg = sum / 7;
            Log.d("avg=" + avg, "sum==" + sum);
            LinearLayout avg1 = getActivity().findViewById(R.id.avg1);
            LinearLayout avg2 = getActivity().findViewById(R.id.avg2);
            avg1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, max));
            avg2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, avg));

        } else {

        }

        //Log.d("sun1=======" + sun1num + Sunday + num[0],"sun2=========="+ sun2num);
        //
        LinearLayout sun1 = getActivity().findViewById(R.id.sun1);
        sun1.setLayoutParams(new LinearLayout.LayoutParams(dip2px(getActivity(), 26), LinearLayout.LayoutParams.WRAP_CONTENT, sun1num));
        LinearLayout sun2 = getActivity().findViewById(R.id.sun2);
        sun2.setLayoutParams(new LinearLayout.LayoutParams(dip2px(getActivity(), 26), LinearLayout.LayoutParams.WRAP_CONTENT, sun2num));

        LinearLayout mon1 = getActivity().findViewById(R.id.mon1);
        mon1.setLayoutParams(new LinearLayout.LayoutParams(dip2px(getActivity(), 26), LinearLayout.LayoutParams.WRAP_CONTENT, mon1num));
        LinearLayout mon2 = getActivity().findViewById(R.id.mon2);
        mon2.setLayoutParams(new LinearLayout.LayoutParams(dip2px(getActivity(), 26), LinearLayout.LayoutParams.WRAP_CONTENT, mon2num));

        LinearLayout tue1 = getActivity().findViewById(R.id.tue1);
        tue1.setLayoutParams(new LinearLayout.LayoutParams(dip2px(getActivity(), 26), LinearLayout.LayoutParams.WRAP_CONTENT, tue1num));
        LinearLayout tue2 = getActivity().findViewById(R.id.tue2);
        tue2.setLayoutParams(new LinearLayout.LayoutParams(dip2px(getActivity(), 26), LinearLayout.LayoutParams.WRAP_CONTENT, tue2num));

        LinearLayout wed1 = getActivity().findViewById(R.id.wed1);
        wed1.setLayoutParams(new LinearLayout.LayoutParams(dip2px(getActivity(), 26), LinearLayout.LayoutParams.WRAP_CONTENT, wed1num));
        LinearLayout wed2 = getActivity().findViewById(R.id.wed2);
        wed2.setLayoutParams(new LinearLayout.LayoutParams(dip2px(getActivity(), 26), LinearLayout.LayoutParams.WRAP_CONTENT, wed2num));

        LinearLayout thu1 = getActivity().findViewById(R.id.thu1);
        thu1.setLayoutParams(new LinearLayout.LayoutParams(dip2px(getActivity(), 26), LinearLayout.LayoutParams.WRAP_CONTENT, thu1num));
        LinearLayout thu2 = getActivity().findViewById(R.id.thu2);
        thu2.setLayoutParams(new LinearLayout.LayoutParams(dip2px(getActivity(), 26), LinearLayout.LayoutParams.WRAP_CONTENT, thu2num));

        LinearLayout fri1 = getActivity().findViewById(R.id.fri1);
        fri1.setLayoutParams(new LinearLayout.LayoutParams(dip2px(getActivity(), 26), LinearLayout.LayoutParams.WRAP_CONTENT, fri1num));
        LinearLayout fri2 = getActivity().findViewById(R.id.fri2);
        fri2.setLayoutParams(new LinearLayout.LayoutParams(dip2px(getActivity(), 26), LinearLayout.LayoutParams.WRAP_CONTENT, fri2num));

        LinearLayout sat1 = getActivity().findViewById(R.id.sat1);
        sat1.setLayoutParams(new LinearLayout.LayoutParams(dip2px(getActivity(), 26), LinearLayout.LayoutParams.WRAP_CONTENT, sat1num));
        LinearLayout sat2 = getActivity().findViewById(R.id.sat2);
        sat2.setLayoutParams(new LinearLayout.LayoutParams(dip2px(getActivity(), 26), LinearLayout.LayoutParams.WRAP_CONTENT, sat2num));

        TextView atext = getActivity().findViewById(R.id.atext);
        TextView btext = getActivity().findViewById(R.id.btext);
        TextView ctext = getActivity().findViewById(R.id.ctext);
        TextView dtext = getActivity().findViewById(R.id.dtext);
        TextView etext = getActivity().findViewById(R.id.etext);
        TextView ftext = getActivity().findViewById(R.id.ftext);
        TextView gtext = getActivity().findViewById(R.id.gtext);

        StringData();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        gtext.setText(LocalDate.now().plusDays(-6).format(formatter).toString());
        atext.setText(LocalDate.now().plusDays(0).format(formatter).toString());
        atext.setTextColor(Color.parseColor("#F1BDB6"));
        btext.setText(LocalDate.now().plusDays(-1).format(formatter).toString());
        ctext.setText(LocalDate.now().plusDays(-2).format(formatter).toString());
        dtext.setText(LocalDate.now().plusDays(-3).format(formatter).toString());
        etext.setText(LocalDate.now().plusDays(-4).format(formatter).toString());
        ftext.setText(LocalDate.now().plusDays(-5).format(formatter).toString());


    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void readSj(String tempInfo) {
        try {
            JSONObject jsonObject = new JSONObject(tempInfo);
            userId = jsonObject.getString("id");
            username = jsonObject.getString("name");
            usertext = jsonObject.getString("text");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public String read(String file) {
        String result = "";
        try {
            FileInputStream fin = getActivity().openFileInput(file);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            result = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "error";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

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

    public String readInfo(String tempInfo) {
        try {
            JSONObject jsonArray = new JSONObject(tempInfo);
            String lv = jsonArray.getString("id");
            return lv;
        } catch (JSONException e) {
            Toast.makeText(getActivity(), "信息读取错误", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return "error";
    }

    public void UpText(String key, String val, String text, String valll, String url) throws IOException {
        new Thread(() -> {

            try {
                URL uu = new URL(url);
                Log.d("Internet类", "url=============" + uu);
                HttpURLConnection connection = (HttpURLConnection) uu.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setUseCaches(false);
                connection.connect();

                String body = key + "=" + val + "&" + text + "=" + valll;
                Log.d("key=========val====", body);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                writer.write(body);
                writer.close();

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
                } else {
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void saveJson(String id, String flag) {
        try {
            JSONObject jsonObject = null;
            JSONArray jsonArray = new JSONArray(todaySize);
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                jsonObject.put(id, flag);
                Log.d("jsonObject=============", String.valueOf(jsonObject));
            }
            String zxc = "[" + jsonObject + "]";
            todaySize = zxc;
            save(zxc);
            Log.d("=============", zxc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void save(String temp) {
        String FILENAME = "UserData";
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

    //---------------------------------------------------------------------------------------------------------------------------------
    //下拉刷新-----------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------

    public void UpData(String userid) throws IOException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.31.238:12345/userdates/1/" + userid);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();

                    String body = "userId=" + userid;

//
//                    Log.d("getuserdata====", username);
//                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
//                    writer.write(body);
//                    writer.close();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        String result = String.valueOf(inputStream);//将流转换为字符串。
                        //Log.d("kwwl","result============="+result);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        Log.d("pccp", response.toString());

                        saveData(response.toString());
//                        Looper.prepare();
//                        Toast.makeText(getActivity(), "同步成功", Toast.LENGTH_SHORT).show();
//                        Looper.loop();
                    } else {
                        Looper.prepare();
                        Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Looper.prepare();
                    //Toast.makeText(getActivity(), "同步失败  请反馈开发者", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        }).start();

    }

    public void saveData(String temp) {
        String FILENAME = "UserData";
        FileOutputStream fos = null;
        try {
            fos = getActivity().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(temp.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //---------------------------------------------------------------------------------------------------------------------------------
    //第二个统计图-----------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------
    // TODO: Rename method, update argument and hook method into UI event
    private LineChart chart;

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initCubic() throws JSONException {

        chart = getActivity().findViewById(R.id.chart1);

        chart.setViewPortOffsets(0, 0, 0, 0);
        chart.setBackgroundColor(Color.rgb(104, 241, 175));

        // no description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(false);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);
        chart.setMaxHighlightDistance(300);
        chart.setBackgroundColor(getResources().getColor(R.color.write_black));
        chart.setBorderWidth(1);
        XAxis x = chart.getXAxis();
        x.setEnabled(false);

        YAxis y = chart.getAxisLeft();
//        y.setEnabled(false);
        y.setLabelCount(4, true);
//        y.setTextColor(Color.parseColor("#919EFF"));
//        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
//        y.setDrawGridLines(false);
//        y.setAxisLineColor(Color.BLACK);
//        y.setDrawLabels(false); // no axis labels
        y.setDrawAxisLine(false); // no axis line
//        y.setDrawGridLines(false); // no grid lines
//        y.setDrawZeroLine(true); // draw a zero line
        chart.getAxisRight().setEnabled(false);

        chart.getLegend().setEnabled(false);

        chart.animateXY(2000, 2000);

        setData(30, 20);
        chart.invalidate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setData(int count, float range) throws JSONException {

        ArrayList<Entry> values = new ArrayList<>();

        if (userdata == null) {
            return;
        }

        JSONObject jsonArray = new JSONObject(userdata);
        for (int i = 0; i < count; i++) {
            values.add(new Entry(i, Float.parseFloat(jsonArray.getString(LocalDate.now().plusDays(-i).toString()))));
        }

        LineDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(Color.WHITE);
            set1.setHighLightColor(getResources().getColor(R.color.google_blue));
            set1.setColor(Color.parseColor("#6698cb"));//#FFB8B8
            set1.setFillColor(Color.parseColor("#7fccde"));
            set1.setFillAlpha(100);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // create a data object with the data sets
            LineData data = new LineData(set1);
//            data.setValueTypeface(tfLight);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            chart.setData(data);
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------------
    //第二个统计图结束-----------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------


}
