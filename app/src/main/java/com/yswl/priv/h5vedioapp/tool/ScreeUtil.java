package com.yswl.priv.h5vedioapp.tool;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by kangpAdministrator on 2017/7/25 0025.
 * Emial kangpeng@yunhetong.net
 */

public class ScreeUtil {
    public static boolean isScreenChange(Context context) {
        Configuration mConfiguration = context.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        if (ori == Configuration.ORIENTATION_LANDSCAPE) {
            return true; //横屏时
        } else if (ori == Configuration.ORIENTATION_PORTRAIT) {
            return false;
        }
        return false;
    }
}
