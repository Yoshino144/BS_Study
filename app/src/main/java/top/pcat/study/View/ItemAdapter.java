package top.pcat.study.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import top.pcat.study.Curriculum.Curriculum;
import top.pcat.study.R;
import com.suke.widget.SwitchButton;

import java.util.List;

public class ItemAdapter  extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<Item> mItemList;
    Curriculum context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        View itemView;
        SwitchButton switchButton;

        public ViewHolder(View view) {
            super(view);
            itemView = view;
            itemName = (TextView) view.findViewById(R.id.itemname);
            switchButton = view.findViewById(R.id.switch_button);
            //switchButton.toggle();     //switch state
        }

    }

    public ItemAdapter(Curriculum context, List<Item> itemList) {
        mItemList = itemList;
        this.context = context;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timu_item, parent, false);
        ViewHolder holder = new ViewHolder(view);


        //文字
        holder.itemView.setOnClickListener(v->{
            int position = holder.getAdapterPosition();
            Item item = mItemList.get(position);
            Toast.makeText(v.getContext(), "you clicked view " + item.getName(),
                    Toast.LENGTH_SHORT).show();

        });

        //按钮
        holder.switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                int position = holder.getAdapterPosition();
                Item item = mItemList.get(position);
                if(isChecked){
                    context.change(item.getName(), true);
                    Toast.makeText(view.getContext(),"选择了"+ item.getName(),Toast.LENGTH_SHORT).show();
                }
                else if(!isChecked) {
                    context.change(item.getName(), false);
                    Toast.makeText(view.getContext(), "取消了" + item.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item fruit = mItemList.get(position);
        holder.itemName.setText(fruit.getName());

        holder.switchButton.setChecked(fruit.getSet());
        holder.switchButton.isChecked();
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}