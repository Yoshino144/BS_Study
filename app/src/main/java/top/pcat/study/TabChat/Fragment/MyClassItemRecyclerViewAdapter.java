package top.pcat.study.TabChat.Fragment;

import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;

import es.dmoral.toasty.Toasty;
import io.rong.imkit.utils.RouteUtils;
import io.rong.imlib.model.Conversation;
import top.pcat.study.Pojo.Clasp;
import top.pcat.study.databinding.FragmentClassItemBinding;


import java.util.List;


public class MyClassItemRecyclerViewAdapter extends RecyclerView.Adapter<MyClassItemRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private final List<Clasp> mValues;

    public MyClassItemRecyclerViewAdapter(List<Clasp> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(FragmentClassItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.className.setText(mValues.get(position).getClassName());
        holder.mContentView.setText(mValues.get(position).getClassId());
        holder.linearLayout.setOnClickListener(v -> {
            LogUtils.d(mValues.get(position).getClassName());
            Log.d("hb", mValues.get(position).getClassName());
            RouteUtils.routeToConversationActivity(context, Conversation.ConversationType.GROUP, mValues.get(position).getClassId());
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
            linearLayout = binding.ci;
        }
        
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}