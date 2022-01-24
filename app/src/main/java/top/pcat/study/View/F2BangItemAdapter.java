package top.pcat.study.View;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ScreenUtils;

import java.util.List;

import top.pcat.study.R;
import top.pcat.study.Utils.PxToDp;

public class F2BangItemAdapter extends RecyclerView.Adapter<F2BangItemAdapter.ViewHolder>{

    private List<ItemF2BangItem> mFruitList;
    private Handler handler;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CustomRoundAngleImageView fruitImage;
        TextView bangid;
        TextView fruitName;
        View itemView;
        ViewGroup.LayoutParams layoutParams;
        LinearLayout bang_kapian;
        public ViewHolder(View view) {
            super(view);
            itemView = view;
            fruitName = (TextView) view.findViewById(R.id.fruit_name);
            bangid =  (TextView) view.findViewById(R.id.bang_idd);
            bang_kapian = view.findViewById(R.id.bang_kapian);
        }
    }

    public F2BangItemAdapter(List<ItemF2BangItem> itemFragment2s) {
        mFruitList = itemFragment2s;
        //handler = cwjHandler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_f2_bang, parent, false);
        ViewHolder holder = new ViewHolder(view);


        context = parent.getContext();

//        holder.itemView.setOnClickListener(v->{
//            int position = holder.getAdapterPosition();
//            ItemF2Bang fruit = mFruitList.get(position);
////            Toast.makeText(v.getContext(), "you clicked view " + fruit.getName(),
////                    Toast.LENGTH_SHORT).show();
//            Bundle bundle = new Bundle();
//            bundle.putString("Subject_name",fruit.getName());
//            Message message = Message.obtain(handler,0);
//            message.setData(bundle);
//            handler.sendMessage(message);
//        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemF2BangItem fruit = mFruitList.get(position);
        holder.fruitName.setText(fruit.getName());
        holder.bangid.setText(String.valueOf(fruit.getPos()));

        ViewGroup.LayoutParams layoutParams = holder.bang_kapian.getLayoutParams();
        layoutParams.width = ((ScreenUtils.getScreenWidth()/2) - PxToDp.dip2px(context, 10));
        holder.bang_kapian.setLayoutParams(layoutParams);

    }
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    public void re(){
        notifyDataSetChanged();
    }
}
