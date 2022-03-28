package top.pcat.study.TabChat.Fragment;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import top.pcat.study.Pojo.Clasp;
import top.pcat.study.databinding.FragmentClassItemBinding;


import java.util.List;


public class MyClassItemRecyclerViewAdapter extends RecyclerView.Adapter<MyClassItemRecyclerViewAdapter.ViewHolder> {

    private final List<Clasp> mValues;

    public MyClassItemRecyclerViewAdapter(List<Clasp> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentClassItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.className.setText(mValues.get(position).getClassName());
        holder.mContentView.setText(mValues.get(position).getClassId());
        holder.linearLayout.setOnClickListener(v->{

        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView className;
        public final TextView mContentView;
        public Clasp mItem;
        public LinearLayout linearLayout;

        public ViewHolder(FragmentClassItemBinding binding) {
            super(binding.getRoot());
            className = binding.className;
            mContentView = binding.content;
            linearLayout=binding.ci;
        }



        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}