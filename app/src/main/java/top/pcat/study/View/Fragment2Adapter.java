package top.pcat.study.View;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import top.pcat.study.R;

import java.util.List;

public class Fragment2Adapter extends RecyclerView.Adapter<Fragment2Adapter.ViewHolder>{

    private List<ItemFragment2> mFruitList;
    private Handler handler;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fruitImage;
        String subject_id;
        TextView fruitName;
        View itemView;
        public ViewHolder(View view) {
            super(view);
            itemView = view;
            fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
            fruitName = (TextView) view.findViewById(R.id.fruit_name);
        }
    }

    public Fragment2Adapter(List<ItemFragment2> itemFragment2s, android.os.Handler cwjHandler) {
        mFruitList = itemFragment2s;
        handler = cwjHandler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragment2, parent, false);
        ViewHolder holder = new ViewHolder(view);

        holder.itemView.setOnClickListener(v->{
            int position = holder.getAdapterPosition();
            ItemFragment2 fruit = mFruitList.get(position);
//            Toast.makeText(v.getContext(), "you clicked view " + fruit.getName(),
//                    Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putString("Subject_id",fruit.getSubject_id());
            bundle.putString("Subject_name",fruit.getName());
            Message message = Message.obtain(handler,0);
            message.setData(bundle);
            handler.sendMessage(message);
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemFragment2 fruit = mFruitList.get(position);
        holder.fruitImage.setImageResource(fruit.getImageId());
        holder.fruitName.setText(fruit.getName());
    }
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    public void re(){
        notifyDataSetChanged();
    }
}
