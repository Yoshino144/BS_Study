package top.pcat.study.WrongQuestion.provider;

import android.util.Log;
import android.view.View;

import com.apkfuns.logutils.LogUtils;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import top.pcat.study.R;
import top.pcat.study.WrongQuestion.node.ThirdNode;

import org.jetbrains.annotations.NotNull;

public class ThirdProvider extends BaseNodeProvider {

    @Override
    public int getItemViewType() {
        return 3;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_node_third;
    }

    @Override
    public void convert(@NotNull BaseViewHolder helper, @NotNull BaseNode data) {
        ThirdNode entity = (ThirdNode) data;
        helper.setText(R.id.title, entity.getTitle());
    }

    @Override
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        ThirdNode entity = (ThirdNode) data;
        int aa =getAdapter().findParentNode(entity);
        LogUtils.d(aa+"ThirdProvider=====",(position)+entity.getTitle());

    }
}
