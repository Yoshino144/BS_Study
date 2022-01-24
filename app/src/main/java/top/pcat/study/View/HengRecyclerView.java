package top.pcat.study.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class HengRecyclerView  extends RecyclerView {

    private int startX;
    private int startY;
    private PagerSnapHelper pagerSnapHelper;

    public HengRecyclerView(@NonNull Context context) {
        super(context);
    }

    public HengRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HengRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private static final float minXlength = 20;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int) ev.getX();
                int endY = (int) ev.getY();
                int disX = Math.abs(endX - startX);
                int disY = Math.abs(endY - startY);
                // 水平方向滑动
                if (disX > disY) {
                    // 左滑
                    if ((endX - startX) > 0 ) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setPagerSnapHelper(PagerSnapHelper pagerSnapHelper) {
        this.pagerSnapHelper = pagerSnapHelper;
    }

    public int getCurrentPage() {
        int position = 0;
        View view = pagerSnapHelper.findSnapView(getLayoutManager());
        if (view != null) {
            position = getLayoutManager().getPosition(view);
        }
        return position;
    }

}
