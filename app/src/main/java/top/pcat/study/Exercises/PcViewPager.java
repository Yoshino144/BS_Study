package top.pcat.study.Exercises;


import android.content.Context;
import android.util.AttributeSet;

import androidx.viewpager.widget.ViewPager;


public class PcViewPager extends ViewPager {

    private boolean isScrollable = false;

    public boolean isScrollable() {
        return isScrollable;
    }

    public void setScrollable(boolean isScrollable) {
        this.isScrollable = isScrollable;
    }

    public PcViewPager(Context context) {
        super(context);

    }

    public PcViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent arg0) {
//        //滑动事件
//        if (!isScrollable)
//            return false;
//        return super.onTouchEvent(arg0);
//
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent arg0) {
//        //点击事件
//        if (!isScrollable)
//            return false;
//        return super.onInterceptTouchEvent(arg0);
//    }

}
