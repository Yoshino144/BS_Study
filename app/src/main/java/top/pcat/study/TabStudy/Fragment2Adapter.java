package top.pcat.study.TabStudy;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.akexorcist.roundcornerprogressbar.TextRoundCornerProgressBar;

import top.pcat.study.R;
import top.pcat.study.View.CustomRoundAngleImageView;
import top.pcat.study.View.ItemFragment2;

import java.util.List;
import java.util.Random;

public class Fragment2Adapter extends RecyclerView.Adapter<Fragment2Adapter.ViewHolder>{

    private List<ItemFragment2> mFruitList;
    private Handler handler;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CustomRoundAngleImageView fruitImage;
        String subject_id;
        TextView fruitName;
        View itemView;
        ViewGroup.LayoutParams layoutParams;
        LinearLayout kapian;
        TextView f2itembut;
        TextRoundCornerProgressBar pg;
        public ViewHolder(View view) {
            super(view);
            itemView = view;
            pg = view.findViewById(R.id.pg);
            f2itembut = view.findViewById(R.id.f2itembut);
            fruitImage = (CustomRoundAngleImageView) view.findViewById(R.id.fruit_image);
            fruitName = (TextView) view.findViewById(R.id.fruit_name);
            kapian = view.findViewById(R.id.kapian_kuan);
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
        int  i = suiji();
        holder.f2itembut.setText("完成 "+i + "%");
        holder.pg.setSecondaryProgress(i);
        holder.pg.setProgress(i-15);
        context = parent.getContext();

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

//        ViewGroup.LayoutParams layoutParams = holder.kapian.getLayoutParams();
//        layoutParams.width = (ScreenUtils.getScreenWidth() - PxToDp.dip2px(context, 70));



//        holder.kapian.setLayoutParams(layoutParams);

    }
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    public void re(){
        notifyDataSetChanged();
    }

    public int suiji(){
        Random rand=new Random();
        //int i=(int)(Math.random()*100);       //  生成0-100的随机数
        int j=rand.nextInt(100);              // 这里是一个方法的重载，参数的内容是指定范围
        //System.out.println("i:"+i+"\nj:"+j);
        return j;
    }
}
