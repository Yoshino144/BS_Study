package top.pcat.study.TabChat.Fragment;

import static io.rong.imlib.NativeClient.getApplicationContext;

import android.app.Activity;
import android.app.Person;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import top.pcat.study.Pojo.Clasp;
import top.pcat.study.Pojo.Msg;
import top.pcat.study.R;
import top.pcat.study.TabChat.DialogActivity;
import top.pcat.study.User.RegisterActivity;
import top.pcat.study.Utils.GetUser;

/**
 * A fragment representing a list of Items.
 */
public class ClassItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private ActivityResultLauncher<Intent> intentActivityResultLauncher;
    private int mColumnCount = 1;
    public List<Clasp> ITEMS = new ArrayList<>();
    private RecyclerView recyclerView;
    private Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            init();
        }
    };

    private Handler handler3 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                PutFwq2();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

//    public static Handler mMsgHander =new Handler(){
//        public void handleMessage(android.os.Message msg) {
//            switch (msg.what){
//                case 1:
//                    //新建班级
//                    Bundle b =msg.getData();
//                    try {
//                        createClass(b.getString("type"));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                case 2:
//                    //加入班级
//
//                    break;
//            }
//        }
//    };
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ClassItemFragment() {
    }

    public void createClassBut(){

        Log.d("dsd","调用其那套framer");
        Toast.makeText(getActivity(), "新建班级", Toast.LENGTH_SHORT).show();

//        Intent intent = new Intent(getActivity(),DialogActivity.class);
//        //在Intent对象当中添加一个键值对
//        intent.putExtra("type",1);
//        startActivityForResult(intent,3);
//        startActivity(intent);

        Intent intent = new Intent(getActivity(), DialogActivity.class);
        intent.putExtra("type",1);
        intentActivityResultLauncher.launch(intent);
    }

    public void joinClassBut(){

        Log.d("dsd","调用其那套framer");
        Toast.makeText(getActivity(), "加入班级", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getActivity(), DialogActivity.class);
        intent.putExtra("type",2);
        intentActivityResultLauncher.launch(intent);
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ClassItemFragment newInstance(int columnCount) {
        ClassItemFragment fragment = new ClassItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                //此处是跳转的result回调方法
                LogUtils.d("进入回调函数");
                if (result.getData() != null && result.getResultCode() == Activity.RESULT_OK) {
                    LogUtils.d("判断通过");
                    int type = result.getData().getIntExtra("type",0);
                    String value = result.getData().getStringExtra("value");
                    LogUtils.d("type="+type+",value="+value);
                    if (type==1){
                        LogUtils.d("创建课程");
                        try {
                            createClass(value);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else if(type == 2){
                        LogUtils.d("加入课程");
                        try {
                            joinClass(Integer.parseInt(value));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "123", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_item_list, container, false);
        try {
            PutFwq2();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
        }
        return view;
    }

    public void init(){
        recyclerView.setAdapter(new MyClassItemRecyclerViewAdapter(ITEMS));
    }


    public void PutFwq2() throws IOException {
        Request request = new Request.Builder()
                .url(getResources().getString(R.string.network_url)+"/classes/"+ GetUser.getUserId(getContext()))
                .get()
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toasty.error(getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String rr = response.body().string();
                Gson gson = new Gson();
                ITEMS.clear();
                ITEMS = gson.fromJson(rr, new TypeToken<List<Clasp>>(){}.getType());

                handler2.sendMessage(new Message());
            }
        });
    }

    public void createClass(String className) throws IOException {
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formBodyBuilder.add("className", className);
        LogUtils.d(getResources().getString(R.string.network_url)+"/classes/"+ GetUser.getUserId(getContext()));
        Request request = new Request.Builder()
                .url(getResources().getString(R.string.network_url)+"/classes/"+ GetUser.getUserId(getContext()))
                .post(formBodyBuilder.build())
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toasty.error(getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String rr = response.body().string();
                LogUtils.d("创建课程结果"+rr);
                handler3.sendMessage(new Message());
                Looper.prepare();
                Toasty.success(getContext(), "创建成功", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });
    }

    public void joinClass(int classId) throws IOException {
        LogUtils.d(getResources().getString(R.string.network_url)+"/classes/"+ GetUser.getUserId(getContext()) + "/"+classId);
        Request request = new Request.Builder()
                .url(getResources().getString(R.string.network_url)+"/classes/"+ GetUser.getUserId(getContext()) + "/"+classId)
                .put(new RequestBody() {
                    @Nullable
                    @Override
                    public MediaType contentType() {
                        return null;
                    }

                    @Override
                    public void writeTo(@NonNull BufferedSink bufferedSink) throws IOException {

                    }
                })
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toasty.error(getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String rr = response.body().string();
                Gson gson = new Gson();
                ITEMS = gson.fromJson(rr, new TypeToken<List<Clasp>>(){}.getType());

                handler2.sendMessage(new Message());
                Looper.prepare();
                Toasty.success(getContext(), "加入成功", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });
    }

}