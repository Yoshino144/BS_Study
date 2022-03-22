package top.pcat.study.WrongQuestion.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import top.pcat.study.R;
import top.pcat.study.Utils.DisplayUtil;
import top.pcat.study.WrongQuestion.WChapterActivity;
import top.pcat.study.WrongQuestion.Pojo.Wrong;

public class WrongAdapter extends RecyclerView.Adapter<WrongAdapter.ViewHolder> {

    private List<Wrong> mWrongList;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView subjectName;
        TextView problemSize;
        LinearLayout wrongItem;
        LinearLayout linearLayout;

        public ViewHolder(View view) {
            super(view);
            subjectName = (TextView) view.findViewById(R.id.subjectName);
            problemSize = (TextView) view.findViewById(R.id.problemSize);
            wrongItem = view.findViewById(R.id.wrong_item);
        }

    }

    public WrongAdapter(List<Wrong> wrongList) {
        mWrongList = wrongList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_wrong_question_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        context = view.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Wrong wrong = mWrongList.get(position);
        if (position == 0) {
            RecyclerView.LayoutParams layoutParams =
                    new RecyclerView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams = (RecyclerView.LayoutParams) holder.wrongItem.getLayoutParams();
            layoutParams.setMargins(DisplayUtil.dip2px(context,7), DisplayUtil.dip2px(context,70), DisplayUtil.dip2px(context,7), 0);//left,top,right,bottom
            holder.wrongItem.setLayoutParams(layoutParams);
        }
        holder.subjectName.setText(wrong.getSubjectName());
        holder.problemSize.setText(String.valueOf(wrong.getProblemSize()));

        holder.wrongItem.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(context, WChapterActivity.class);
            intent.putExtra("subjectId", wrong.getSubjectId());
            intent.putExtra("subjectName", wrong.getSubjectName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mWrongList.size();
    }
}