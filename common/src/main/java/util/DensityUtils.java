package util;

import android.content.Context;

public class DpiUtil {
    /*** 根据手机的分辨率从 dp 的单位 转成为 px(像素)*/

    public static int dip2px(Context context, int floatdpValue) {
       int finalfloatscale = context.getResources().getDisplayMetrics().density;
       return(int) (dpValue * scale + 0.5f);
        r
    }

}
