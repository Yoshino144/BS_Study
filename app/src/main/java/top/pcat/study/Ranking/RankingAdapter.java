package top.pcat.study.Ranking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import top.pcat.study.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RankingAdapter  extends RecyclerView.Adapter<RankingAdapter.ViewHolder>{

    private List<RankingList> mFruitList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fruitImage;
        TextView fruitName;
        TextView id_num;
        ViewHolder(View view) {
            super(view);
            fruitImage = view.findViewById(R.id.fruit_image);
            fruitName = (TextView) view.findViewById(R.id.fruit_name);
            id_num = view.findViewById(R.id.id_num);
        }
    }
    public RankingAdapter(List<RankingList> fruitList) {
        mFruitList = fruitList;
    }
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ranking_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RankingList rankingList = mFruitList.get(position);
        holder.fruitImage.setImageResource(rankingList.getImageId());

        holder.fruitName.setText(rankingList.getName());
        holder.id_num.setText(String.valueOf(position+1));
    }
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
}
