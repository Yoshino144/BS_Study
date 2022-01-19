package top.pcat.study.Ranking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import top.pcat.study.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class NewAdapter extends RecyclerView.Adapter<NewAdapter.ViewHolder>{

    private List<NewList> mFruitList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fruitImage;
        TextView fruitName;
        TextView id_num;
        RelativeLayout item;
        LinearLayout new_fr;
        TextView new_size;
        LinearLayout tongyi;
        ViewHolder(View view) {
            super(view);
            fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
            fruitName = (TextView) view.findViewById(R.id.fruit_name);
            id_num = view.findViewById(R.id.id_num);
            item = view.findViewById(R.id.item);
            new_fr = view.findViewById(R.id.new_fr);
            new_size = view.findViewById(R.id.new_size);
            tongyi = view.findViewById(R.id.tongyi);
        }
    }
    public NewAdapter(List<NewList> fruitList) {
        mFruitList = fruitList;
    }
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.newf_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewList newList = mFruitList.get(position);
        holder.fruitImage.setImageResource(newList.getImageId());
        holder.fruitName.setText(newList.getName());
        holder.id_num.setText(String.valueOf(position+1));
        holder.new_size.setText(String.valueOf(newList.getReason()));
        holder.tongyi.setOnClickListener(v->{

        });
    }
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
}
