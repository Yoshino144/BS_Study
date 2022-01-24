package top.pcat.study.View;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.blankj.utilcode.util.ScreenUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import top.pcat.study.R;
import top.pcat.study.Utils.PxToDp;

public class F2BangAdapter extends RecyclerView.Adapter<F2BangAdapter.ViewHolder>{

    private List<ItemF2Bang> mFruitList;
    private Handler handler;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CustomRoundAngleImageView fruitImage;
        String subject_id;
        TextView fruitName;
        View itemView;
        ViewGroup.LayoutParams layoutParams;
        LinearLayout kapian;
        RecyclerView recyclerView;
        public ViewHolder(View view) {
            super(view);
            itemView = view;
            fruitName = (TextView) view.findViewById(R.id.bang_name);
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_kechngbangdan_item);
            //kapian = view.findViewById(R.id.kapian1);
        }
    }

    public F2BangAdapter(List<ItemF2Bang> itemFragment2s,Handler cwjHandler) {
        mFruitList = itemFragment2s;
        handler = cwjHandler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_f2_bb, parent, false);
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
        ItemF2Bang fruit = mFruitList.get(position);
        holder.fruitName.setText(fruit.getName());


        List<ItemF2BangItem> itemFragment4 = new ArrayList<>();

        try {
            initBang_item(itemFragment4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

        };
//        LinearLayoutManager layoutManager = new
//                LinearLayoutManager(context){
//
//                    @Override
//                    public boolean canScrollVertically() {
//                        return false;
//                    }
//
//                };
        holder.recyclerView.setLayoutManager(layoutManager);
        F2BangItemAdapter adapter = new F2BangItemAdapter(itemFragment4);
        holder.recyclerView.setAdapter(adapter);

//        ViewGroup.LayoutParams layoutParams = holder.kapian.getLayoutParams();
//        layoutParams.width = (ScreenUtils.getScreenWidth() - PxToDp.dip2px(context, 60));
//
//
//
//        holder.kapian.setLayoutParams(layoutParams);

    }
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    public void re(){
        notifyDataSetChanged();
    }

    private void initBang_item(List<ItemF2BangItem> itemFragment3) throws JSONException {
        int [] arr=new int[]{1,4,2,5,3,6};
        try {
            for(int i =0 ; i < 6;i++){
                ItemF2BangItem apple = new ItemF2BangItem(arr[i],
                        "人数榜",3);
                itemFragment3.add(apple);
            }
        } catch(Exception exception){
            exception.printStackTrace();
        }



    }
}
