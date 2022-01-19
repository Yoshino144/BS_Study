package top.pcat.study.WrongQuestion.provider;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import top.pcat.study.R;
import top.pcat.study.WrongQuestion.WrongOne;
import top.pcat.study.WrongQuestion.WrongQuestion;
import top.pcat.study.WrongQuestion.node.SecondNode;

import org.jetbrains.annotations.NotNull;

public class SecondProvider extends BaseNodeProvider {
    WrongQuestion wrongQuestion;

    public SecondProvider(WrongQuestion context){
        wrongQuestion=context;
    }

    @Override
    public int getItemViewType() {
        return 2;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_node_second;
    }

    @Override
    public void convert(@NotNull BaseViewHolder helper, @NotNull BaseNode data) {
        SecondNode entity = (SecondNode) data;
        helper.setText(R.id.title, entity.getTitle());

        if (entity.isExpanded()) {
            helper.setImageResource(R.id.iv, R.mipmap.arrow_b);
        } else {
            helper.setImageResource(R.id.iv, R.mipmap.arrow_r);
        }
    }

    @Override
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        SecondNode entity = (SecondNode) data;
        if (entity.isExpanded()) {
            getAdapter().collapse(position);
        } else {
            getAdapter().expandAndCollapseOther(position);
        }

        int aa =getAdapter().findParentNode(entity);
        //Log.d(entity.getTitle().charAt(8)+"ThirdProvider=====",(position)+entity.getTitle());

        Intent intent01 = new Intent();
        intent01.setClass(wrongQuestion, WrongOne.class);
        if(aa == 0){
            intent01.putExtra("kemu_name", "Cpp"+entity.getTitle().charAt(1));
            intent01.putExtra("ti", entity.getTitle() );
        }
//        //getActivity().finish();
        wrongQuestion.startActivity(intent01);
        //getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        //wrongQuestion.finish();
    }
}
