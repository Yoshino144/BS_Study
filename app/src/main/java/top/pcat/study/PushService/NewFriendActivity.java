package top.pcat.study.PushService;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import top.pcat.study.R;
import top.pcat.study.Ranking.NewAdapter;
import top.pcat.study.Ranking.NewList;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewFriendActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private List<NewList> newList = new ArrayList<>();
    private NewAdapter newAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);

        initClass();

        recyclerView = (RecyclerView) findViewById(R.id.recyc);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        newAdapter = new NewAdapter(newList);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(newAdapter);
    }

    private void initClass() {
        String json=read("NewFriend.json");
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = jsonArray.length()-1; i >0; i--) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String fromUsername = jsonObject.getString("fromUsername");
                String reason = jsonObject.getString("reason");
                NewList apple2 = new NewList(i,fromUsername, R.drawable.new_friend,reason);
                newList.add(apple2);
            }
        } catch (JSONException e) {
            Toast.makeText(this,"信息读取错误",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
//        for(int i = 0; i <1;i++){
//            NewList apple2 = new NewList(i,"新朋友", R.drawable.new_friend,0);
//            newList.add(apple2);
//            NewList apple = new NewList(i,"管理员", R.drawable.admin,0);
//            newList.add(apple);
//        }
    }

    public String read(String file) {
        String result = "";
        try {
            FileInputStream fin = openFileInput(file);
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
}
