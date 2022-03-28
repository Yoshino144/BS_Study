package top.pcat.study.WrongQuestion.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apkfuns.logutils.LogUtils;

import java.util.List;

import top.pcat.study.Pojo.WrongChapter;
import top.pcat.study.R;
import top.pcat.study.Utils.DisplayUtil;
import top.pcat.study.WrongQuestion.Pojo.WrongProblem;
import top.pcat.study.WrongQuestion.WProblemActivity;

public class WPAdapter extends RecyclerView.Adapter<WPAdapter.ViewHolder> {

    private List<WrongProblem> mWChapterList;
    private Context context;
    private boolean ff;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView problemContent;
        TextView text_A;
        TextView text_B;
        TextView text_C;
        TextView text_D;
        LinearLayout AAA;
        LinearLayout BBB;
        LinearLayout CCC;
        LinearLayout DDD;
        LinearLayout wrong_item;
        ImageView image_A;
        ImageView image_B;
        ImageView image_C;
        ImageView image_D;
        LinearLayout jiexi;


        public ViewHolder(View view) {
            super(view);
            problemContent = view.findViewById(R.id.problemContent);
            text_A = view.findViewById(R.id.text_AA);
            text_B = view.findViewById(R.id.text_BB);
            text_C = view.findViewById(R.id.text_CC);
            text_D = view.findViewById(R.id.text_DD);
            AAA = view.findViewById(R.id.AAA);
            BBB = view.findViewById(R.id.BBB);
            CCC = view.findViewById(R.id.CCC);
            DDD = view.findViewById(R.id.DDD);
            wrong_item = view.findViewById(R.id.wrong_item);
            jiexi = view.findViewById(R.id.jiexi);
            image_A = view.findViewById(R.id.image_A);
            image_B = view.findViewById(R.id.image_B);
            image_C = view.findViewById(R.id.image_C);
            image_D = view.findViewById(R.id.image_D);
        }

    }

    public WPAdapter(List<WrongProblem> wChapterList,boolean ff) {
        mWChapterList = wChapterList;
        this.ff = ff;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_wp_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        context = view.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        WrongProblem wrongProblem = mWChapterList.get(position);

        if(ff){
            if (position == 0) {
                RecyclerView.LayoutParams layoutParams =
                        new RecyclerView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams = (RecyclerView.LayoutParams) holder.wrong_item.getLayoutParams();
                layoutParams.setMargins(DisplayUtil.dip2px(context, 7), DisplayUtil.dip2px(context, 70), DisplayUtil.dip2px(context, 7), 0);//left,top,right,bottom
                holder.wrong_item.setLayoutParams(layoutParams);
            }
        }

        holder.problemContent.setText(position+1+". "+wrongProblem.getProblemContent());
        holder.text_A.setText(wrongProblem.getA());
        LogUtils.d("选项"+wrongProblem.getA());
        holder.text_B.setText(wrongProblem.getB());
        holder.text_C.setText(wrongProblem.getC());
        holder.text_D.setText(wrongProblem.getD());

        holder.AAA.setOnClickListener(v->{
            checked("A","A".equals(wrongProblem.getProblemAnswer()),holder,wrongProblem.getProblemAnswer());
        });
        holder.BBB.setOnClickListener(v->{
            checked("B","B".equals(wrongProblem.getProblemAnswer()),holder,wrongProblem.getProblemAnswer());

        });
        holder.CCC.setOnClickListener(v->{
            checked("C","C".equals(wrongProblem.getProblemAnswer()),holder,wrongProblem.getProblemAnswer());

        });
        holder.DDD.setOnClickListener(v->{
            checked("D","D".equals(wrongProblem.getProblemAnswer()),holder,wrongProblem.getProblemAnswer());

        });

    }

    @Override
    public int getItemCount() {
        return mWChapterList.size();
    }

    public void checked(String xx,boolean trueFlag, ViewHolder holder,String answer){
        if(trueFlag){
            setTrue(holder,xx);
        }else{
            setFlase(holder,xx);
            setTrue(holder,answer);
        }
        holder.jiexi.setVisibility(View.VISIBLE);
    }

    public void setTrue(ViewHolder holder,String xx){

        switch (xx) {
            case "A" -> {
                holder.image_A.setImageResource(R.drawable.ttrue);
                holder.text_A.setTextColor(Color.parseColor("#1cabfa"));
            }
            case "B" -> {
                holder.image_B.setImageResource(R.drawable.ttrue);
                holder.text_B.setTextColor(Color.parseColor("#1cabfa"));
            }
            case "C" -> {
                holder.image_C.setImageResource(R.drawable.ttrue);
                holder.text_C.setTextColor(Color.parseColor("#1cabfa"));
            }
            case "D" -> {
                holder.image_D.setImageResource(R.drawable.ttrue);
                holder.text_D.setTextColor(Color.parseColor("#1cabfa"));
            }
        }
    }

    public void setFlase(ViewHolder holder,String xx){
        switch (xx) {
            case "A" -> {
                holder.image_A.setImageResource(R.drawable.wrong);
                holder.text_A.setTextColor(Color.parseColor("#fd6767"));
            }
            case "B" -> {
                holder.image_B.setImageResource(R.drawable.wrong);
                holder.text_B.setTextColor(Color.parseColor("#fd6767"));
            }
            case "C" -> {
                holder.image_C.setImageResource(R.drawable.wrong);
                holder.text_C.setTextColor(Color.parseColor("#fd6767"));
            }
            case "D" -> {
                holder.image_D.setImageResource(R.drawable.wrong);
                holder.text_D.setTextColor(Color.parseColor("#fd6767"));
            }
        }
    }
}