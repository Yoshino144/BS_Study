package top.pcat.study.Ranking;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import top.pcat.study.PushService.NewFriendActivity;
import top.pcat.study.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder>{

    private List<ClassList> mFruitList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fruitImage;
        TextView fruitName;
        TextView id_num;
        RelativeLayout item;
        LinearLayout new_fr;
        TextView new_size;
        ViewHolder(View view) {
            super(view);
            fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
            fruitName = (TextView) view.findViewById(R.id.fruit_name);
            id_num = view.findViewById(R.id.id_num);
            item = view.findViewById(R.id.item);
            new_fr = view.findViewById(R.id.new_fr);
            new_size = view.findViewById(R.id.new_size);
        }
    }
    public ClassAdapter(List<ClassList> fruitList) {
        mFruitList = fruitList;
    }
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.class_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ClassList classList = mFruitList.get(position);
        if(classList.getName().equals("新朋友")){
            holder.fruitImage.setImageResource(R.drawable.new_friend);
        }else if(classList.getName().equals("管理员")){
            holder.fruitImage.setImageResource(R.drawable.admin);
        }else{
            holder.fruitImage.setImageBitmap(classList.getImageId());
        }
        holder.fruitName.setText(classList.getName());
        holder.id_num.setText(String.valueOf(position+1));
        if(classList.getName().equals("新朋友") && classList.getSize()!=0){
            holder.new_fr.setVisibility(View.VISIBLE);
            holder.new_size.setText(String.valueOf(classList.getSize()));
        }
        holder.item.setOnClickListener(v->{
            if(classList.getName().equals("新朋友") && classList.getSize()!=0){
                Intent intent01 = new Intent();
                intent01.setClass(v.getContext(), NewFriendActivity.class);
                v.getContext().startActivity(intent01);
            }
            else if(classList.getName().equals("新朋友") && classList.getSize()==0){
                Toast.makeText(v.getContext(),"暂无请求",Toast.LENGTH_SHORT).show();
            }else{

            }
        });
    }
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
}
