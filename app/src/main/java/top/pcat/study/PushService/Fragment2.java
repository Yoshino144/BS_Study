package top.pcat.study.PushService;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import top.pcat.study.Utils.FileTool;
import top.pcat.study.R;
import top.pcat.study.Ranking.ClassAdapter;
import top.pcat.study.Ranking.ClassList;
import com.apkfuns.logutils.LogUtils;

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
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Fragment2 extends Fragment {
    private List<ClassList> classList = new ArrayList<>();
    private RecyclerView recyclerView2;
    private ClassAdapter adapter;
    private int new_size = 0;
    private FileTool ft;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view= inflater.inflate(R.layout.layout2, container, false);
        TextView btn = view.findViewById(R.id.fragment2_btn);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "请登录", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);recyclerView2 = (RecyclerView) getActivity().findViewById(R.id.rank_recycler_view2);

        File path = new File(getActivity().getFilesDir().getAbsolutePath()+"/Login.txt");
        if(ft.isFileExists(path.toString())){
            initClass();
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView2.setLayoutManager(layoutManager);
            LogUtils.d("!!!!!!!!!!!!!!!!!!!!!!!!!已加载list");
            adapter = new ClassAdapter(classList);
            recyclerView2.setNestedScrollingEnabled(false);
            recyclerView2.setAdapter(adapter);
        }else{
            recyclerView2.setVisibility(View.GONE);
        }

    }

    private void initClass() {

        ClassList apple2 = new ClassList(0,"新朋友", null,new_size);
        classList.add(apple2);
        ClassList apple = new ClassList(1,"管理员", null,0);
        classList.add(apple);


    }

    private void saveNewFriend(String fromUsername,String reason){
        File flagpath = new File(getActivity().getFilesDir().getAbsolutePath()+"/NewFriend.json");
        if(ft.isFileExists(flagpath.toString())){
            String json=read("NewFriend.json");
            json=editJson(json,fromUsername,reason);
            save(json);
        }
        else{
            String json = getFromAssets("Flag.json");
            json=editJson(json,fromUsername,reason);
            save(json);
        }
    }

    public String editJson(String json,String id,String flag){
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fromUsername",id);
            jsonObject.put("reason",flag);
            jsonArray.put(jsonObject);
            return jsonArray.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public String read(String file) {
        String result = "";
        try {
            FileInputStream fin = getActivity().openFileInput(file);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            result = EncodingUtils.getString(buffer,"UTF-8");
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

    public void save(String temp) {
        String FILENAME = "NewFriend.json";
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

    public String getFromAssets(String fileName){
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            while((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}