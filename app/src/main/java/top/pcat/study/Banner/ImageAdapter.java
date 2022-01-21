package top.pcat.study.Banner;

import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.youth.banner.adapter.BannerAdapter;

import java.util.List;


public class ImageAdapter extends BannerAdapter<DataBean, ImageAdapter.BannerViewHolder> {

    public ImageAdapter(List<DataBean> mDatas) {
        super(mDatas);
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        //RoundImageView imageView = new RoundImageView(parent.getContext());
        ImageView imageView = new ImageView(parent.getContext());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindView(BannerViewHolder holder, DataBean data, int position, int size) {
        holder.imageView.setImageResource(data.imageRes);
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull ImageView view) {
            super(view);
            this.imageView = view;
        }
    }
}