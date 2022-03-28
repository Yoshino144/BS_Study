package top.pcat.study.Dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import top.pcat.study.R;
import top.pcat.study.Exercises.ExercisesActivity;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {
    private List<Fruit> mFruitList;
    private ExercisesActivity ex;
    private  addClickListener listener;
    GoodsDialogFragment gd;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View fruitView;
        ImageView fruitImage;
        TextView fruitName;
        LinearLayout fruitColor;
        public ViewHolder(View view) {
            super(view);
            fruitView = view;
            fruitName = (TextView) view.findViewById(R.id.fruit_name);
            fruitColor = view.findViewById(R.id.item_color);
        }
    }
    public FruitAdapter(List<Fruit> fruitList,addClickListener listener) {
        this.listener = listener;
        mFruitList = fruitList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.
                fruit_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.fruitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
//                Toast.makeText(v.getContext(), "you clicked view " + Integer.parseInt(fruit.getName()),
//                        Toast.LENGTH_SHORT).show();
                listener.addClick(position);

               // LogUtils.d("点击的=======","============="+position);
               // ex.setCurrentView(Integer.parseInt(fruit.getName()));
                //aa(Integer.parseInt(fruit.getName()));
            }
        });
//        holder.fruitImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                Fruit fruit = mFruitList.get(position);
//                Toast.makeText(v.getContext(), "you clicked image " + fruit.getName(),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit = mFruitList.get(position);
//        holder.fruitImage.setImageResource(fruit.getImageId());
        holder.fruitName.setText(fruit.getName());
        if(fruit.getFlag().matches("wrong")){
            holder.fruitColor.setBackground(holder.fruitView.getResources().getDrawable(R.drawable.dialog_radius_wrong));
        }else if(fruit.getFlag().matches("true")){
            holder.fruitColor.setBackground(holder.fruitView.getResources().getDrawable(R.drawable.dialog_radius));
        }else{
            holder.fruitColor.setBackground(holder.fruitView.getResources().getDrawable(R.drawable.dialog_radius_no));
        }

    }
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    public void setCusClickListener(addClickListener cusClickListener) {
        this.listener = cusClickListener;
    }


}