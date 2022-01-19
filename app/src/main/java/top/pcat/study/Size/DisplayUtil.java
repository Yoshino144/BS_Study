package top.pcat.study.Size;

import android.content.Context;
import android.util.TypedValue;


public class DisplayUtil {
    public static int dipToPix(Context context, int dip) {
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
        return size;
    }
}
