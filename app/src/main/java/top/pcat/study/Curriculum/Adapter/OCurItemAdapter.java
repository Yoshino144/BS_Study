package top.pcat.study.Curriculum.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import top.pcat.study.Pojo.Subject;
import top.pcat.study.R;
import top.pcat.study.View.CustomRoundAngleImageView;

public class OCurItemAdapter extends RecyclerView.Adapter<OCurItemAdapter.ViewHolder> {
    private final List<Subject> mFruitList;
    Context context;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CustomRoundAngleImageView image;
        TextView name;
        TextView zz;
        TextView size;
        TextView time;

        public ViewHolder (View view)
        {
            super(view);
            image = view.findViewById(R.id.cur_image);
            name = view.findViewById(R.id.cur_name);
            zz = view.findViewById(R.id.cur_zz);
            size = view.findViewById(R.id.cur_size);
            time=view.findViewById(R.id.cur_time);
        }

    }

    public OCurItemAdapter(List <Subject> fruitList){
        mFruitList = fruitList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cur_items,parent,false);
        ViewHolder holder = new ViewHolder(view);

        this.context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Subject fruit = mFruitList.get(position);
        //image = view.findViewById(R.id.cur_image);
        holder.name.setText(fruit.getSubjectName());
        holder.zz.setText("创始人:"+fruit.getSubjectFounder().substring(1,6));
        holder.size.setText("题数："+String.valueOf(fruit.getSubjectSize()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");  // 设置日期格式
        String strTime = simpleDateFormat.format(fruit.getSubjectTime());
        holder.time.setText(strTime);
//        holder.image.setImageResource(fruit.getImageId());
//        holder.name.setText(fruit.getName());
//        holder.nameTwo.setText(fruit.getNameTwo());
//        holder.text.setText(fruit.getText());
    }

    @Override
    public int getItemCount(){
        return mFruitList.size();
    }

    public int dip2px(float dpValue) {
        final float scale = this.context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
