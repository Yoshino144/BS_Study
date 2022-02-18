package top.pcat.study.Curriculum.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.apkfuns.logutils.LogUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import es.dmoral.toasty.Toasty;
import top.pcat.study.Pojo.Subject;
import top.pcat.study.R;
import top.pcat.study.View.CustomRoundAngleImageView;

public class OCurItemAdapter extends RecyclerView.Adapter<OCurItemAdapter.ViewHolder> {
    private final List<Subject> mFruitList;
    Context context;
    private ButtonInterface buttonInterface;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CustomRoundAngleImageView image;
        TextView name;
        TextView zz;
        TextView size;
        TextView time;
        CheckBox switchButton;

        public ViewHolder (View view)
        {
            super(view);
            image = view.findViewById(R.id.cur_image);
            name = view.findViewById(R.id.cur_name);
            zz = view.findViewById(R.id.cur_zz);
            size = view.findViewById(R.id.cur_size);
            time=view.findViewById(R.id.cur_time);
            switchButton=view.findViewById(R.id.switch_button);
        }

    }

    /**
     *按钮点击事件需要的方法
     */
    public void buttonSetOnclick(ButtonInterface buttonInterface){
        this.buttonInterface=buttonInterface;
    }

    /**
     * 按钮点击事件对应的接口
     */
    public interface ButtonInterface{
        public void onclick( boolean isChecked,long position) throws IOException;
    }

    public OCurItemAdapter(List <Subject> fruitList){
        mFruitList = fruitList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cur_items,parent,false);
        ViewHolder holder = new ViewHolder(view);

        this.context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Subject fruit = mFruitList.get(position);
        //image = view.findViewById(R.id.cur_image);
        holder.name.setText(fruit.getSubjectName());
        holder.zz.setText("作者:"+fruit.getFounderName());
        holder.size.setText("题数："+String.valueOf(fruit.getSubjectSize()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");  // 设置日期格式
        String strTime = simpleDateFormat.format(fruit.getSubjectTime());
        holder.time.setText(strTime);

        if(fruit.isChooseFlag()){
            //holder.switchButtonx.setChecked(true);
            holder.switchButton.setChecked(true);
            holder.switchButton.setText("已选");
        }else{

            holder.switchButton.setChecked(false);
            holder.switchButton.setText("去体验");
        }

        holder.switchButton.setOnCheckedChangeListener((view, isChecked) -> {

            if(buttonInterface!=null) {
//                  接口实例化后的而对象，调用重写后的方法
                try {
                    buttonInterface.onclick(isChecked,fruit.getSubjectId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            if (isChecked){
                LogUtils.d("选择了"+fruit.getSubjectName());
                holder.switchButton.setText("已选");
                Toasty.success(this.context,"选择了"+fruit.getSubjectName()).show();
            }else{
                LogUtils.d("取消了"+fruit.getSubjectName());
                holder.switchButton.setText("去体验");
                Toasty.info(this.context,"取消了"+fruit.getSubjectName()).show();
            }
        });
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
