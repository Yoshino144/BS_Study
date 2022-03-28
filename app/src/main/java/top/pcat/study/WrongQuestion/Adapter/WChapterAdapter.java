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

import top.pcat.study.Pojo.WrongChapter;
import top.pcat.study.R;
import top.pcat.study.Utils.DisplayUtil;
import top.pcat.study.WrongQuestion.WProblemActivity;

public class WChapterAdapter extends RecyclerView.Adapter<WChapterAdapter.ViewHolder> {

    private List<WrongChapter> mWChapterList;
    private Context context;
    private boolean ff;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView subjectName;
        TextView problemSize;
        LinearLayout wChapterItem;

        public ViewHolder(View view) {
            super(view);
            subjectName = (TextView) view.findViewById(R.id.subjectName);
            problemSize = (TextView) view.findViewById(R.id.problemSize);
            wChapterItem = view.findViewById(R.id.wrong_item);
        }

    }

    public WChapterAdapter(List<WrongChapter> wChapterList,boolean ff) {
        mWChapterList = wChapterList;
        ff = ff;
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

        WrongChapter wChapter = mWChapterList.get(position);
        holder.subjectName.setText(wChapter.getChapterName());
        holder.problemSize.setText(String.valueOf(wChapter.getProblemSize()));


            if (position == 0) {
                RecyclerView.LayoutParams layoutParams =
                        new RecyclerView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams = (RecyclerView.LayoutParams) holder.wChapterItem.getLayoutParams();
                layoutParams.setMargins(DisplayUtil.dip2px(context,7), DisplayUtil.dip2px(context,70), DisplayUtil.dip2px(context,7), 0);//left,top,right,bottom
                holder.wChapterItem.setLayoutParams(layoutParams);
            }


        holder.subjectName.setText(wChapter.getChapterName());
        holder.problemSize.setText(String.valueOf(wChapter.getProblemSize()));

        holder.wChapterItem.setOnClickListener(v->{
            Intent intent = new Intent();
            intent.setClass(context, WProblemActivity.class);
            intent.putExtra("chapterId",wChapter.getChapterId());
            intent.putExtra("subjectId",wChapter.getSubjectId());
            intent.putExtra("chapterName",wChapter.getChapterName());


            context.startActivity(intent) ;
        });
    }

    @Override
    public int getItemCount() {
        return mWChapterList.size();
    }
}