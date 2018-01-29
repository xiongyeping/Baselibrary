package com.experience.baselibrary;

import android.content.Context;

/**
 * Created by Administrator on 2016/12/9 0009.
 */
public class ScreenUtil {
    /**
     * 获得通知栏，也就是状态栏的高度
     * @return
     */
    public static int getStatusHeight(Context context) {
        int resid = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resid > 0) {
            return context.getResources().getDimensionPixelSize(resid);
        }
        return -1;
    }
}