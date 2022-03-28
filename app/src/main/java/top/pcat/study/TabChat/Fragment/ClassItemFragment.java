package top.pcat.study.TabChat.Fragment;

import android.app.Person;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import top.pcat.study.Pojo.Clasp;
import top.pcat.study.Pojo.Msg;
import top.pcat.study.R;
import top.pcat.study.User.RegisterActivity;
import top.pcat.study.Utils.GetUser;

/**
 * A fragment representing a list of Items.
 */
public class ClassItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    public List<Clasp> ITEMS = new ArrayList<>();
    private RecyclerView recyclerView;
    private Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            init();
        }
    };
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ClassItemFragment() {
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
                .url(R.string.network_url+"/classes/"+ GetUser.getUserId(getContext()))
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
                ITEMS = gson.fromJson(rr, new TypeToken<List<Person>>(){}.getType());

                handler2.sendMessage(new Message());
            }
        });
    }

}