package top.pcat.study.View;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.blankj.utilcode.util.ScreenUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import top.pcat.study.Pojo.Subject;
import top.pcat.study.R;
import top.pcat.study.Utils.PxToDp;

public class F2BangAdapter extends RecyclerView.Adapter<F2BangAdapter.ViewHolder> {

    private List<Subject> mFruitList;
    private Handler handler;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView subject_id;
        TextView fruitName;
        View itemView;
        TextView textView;

        public ViewHolder(View view) {
            super(view);
            itemView = view;
            fruitName = (TextView) view.findViewById(R.id.fruit_name);
            textView = view.findViewById(R.id.zzname);
            subject_id = view.findViewById(R.id.bang_idd);
        }
    }

    public F2BangAdapter(List<Subject> itemFragment2s, Handler cwjHandler) {
        mFruitList = itemFragment2s;
        handler = cwjHandler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_f2_bang, parent, false);
        ViewHolder holder = new ViewHolder(view);


        context = parent.getContext();

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Subject fruit = mFruitList.get(position);
        holder.fruitName.setText(fruit.getSubjectName());
        holder.textView.setText(fruit.getFounderName());
        holder.subject_id.setText(String.valueOf(position+1));


    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

}
