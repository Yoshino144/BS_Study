package top.pcat.study.Size;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import top.pcat.study.R;

import java.util.List;


public class ChAdapter extends ArrayAdapter<Chap> {
    private int resourceId;
    public ChAdapter(Context context, int textViewResourceId,
                     List<Chap> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Chap chap = getItem(position);
        @SuppressLint("ViewHolder") View view = LayoutInflater.from(getContext()).inflate(resourceId, parent,
                false);
        ImageView fruitImage = (ImageView) view.findViewById(R.id.chap_image);
        TextView fruitName = (TextView) view.findViewById(R.id.chap_name);
        LinearLayout fen = view.findViewById(R.id.fen);
        if(position==0) fen.setVisibility(View.GONE);
        if(position > 1) fruitImage.setVisibility(View.GONE);
        fruitImage.setImageResource(chap.getImageId());
        fruitName.setText(chap.getName());
        return view;
    }
}
