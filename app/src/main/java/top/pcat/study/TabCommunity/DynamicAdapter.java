package top.pcat.study.TabCommunity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import top.pcat.study.R;

public class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.ViewHolder> {
    private final List<Dynamic> mFruitList;
    Context context;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        TextView nameTwo;
        TextView text;
        LinearLayout layout;

        public ViewHolder (View view)
        {
            super(view);
            image = view.findViewById(R.id.dynamic_headImg);
            name = view.findViewById(R.id.dynamic_name);
            nameTwo = view.findViewById(R.id.dynamic_nametwo);
            text = view.findViewById(R.id.dynamic_text);
            layout=view.findViewById(R.id.dongtai);
        }

    }

    public  DynamicAdapter (List <Dynamic> fruitList){
        mFruitList = fruitList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dynamic,parent,false);
        ViewHolder holder = new ViewHolder(view);

        this.context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
//        BarUtils.getStatusBarHeight();
//        if(position==0){
//            LinearLayout.LayoutParams lp = new LinearLayout
//                    .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            lp.setMargins(dip2px(10), dip2px(10), dip2px(10), dip2px(12));
//            holder.layout.setLayoutParams(lp);
//        }

        Dynamic fruit = mFruitList.get(position);
        holder.image.setImageResource(fruit.getImageId());
        holder.name.setText(fruit.getName());
        holder.nameTwo.setText(fruit.getNameTwo());
        holder.text.setText(fruit.getText());
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
