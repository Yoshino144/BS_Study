package top.pcat.study.WrongQuestion;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import top.pcat.study.WrongQuestion.node.FirstNode;
import top.pcat.study.WrongQuestion.node.SecondNode;
import top.pcat.study.WrongQuestion.node.ThirdNode;
import top.pcat.study.WrongQuestion.provider.FirstProvider;
import top.pcat.study.WrongQuestion.provider.SecondProvider;
import top.pcat.study.WrongQuestion.provider.ThirdProvider;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NodeTreeAdapter extends BaseNodeAdapter {
    private WrongQuestion wrongQuestion;

    public NodeTreeAdapter(WrongQuestion context) {
        super();
        wrongQuestion = context;
        addNodeProvider(new FirstProvider());
        addNodeProvider(new SecondProvider(wrongQuestion));
        addNodeProvider(new ThirdProvider());
    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> data, int position) {
        BaseNode node = data.get(position);
        if (node instanceof FirstNode) {
            return 1;
        } else if (node instanceof SecondNode) {
            return 2;
        } else if (node instanceof ThirdNode) {
            return 3;
        }
        return -1;
    }

    public static final int EXPAND_COLLAPSE_PAYLOAD = 110;
}
