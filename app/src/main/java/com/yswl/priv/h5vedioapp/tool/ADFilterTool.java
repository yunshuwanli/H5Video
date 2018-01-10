package com.yswl.priv.h5vedioapp.tool;

import android.content.Context;
import android.content.res.Resources;

import com.yswl.priv.h5vedioapp.R;

public class ADFilterTool {
    public static boolean hasAd(Context context, String url) {
        Resources res = context.getResources();
        String[] adUrls = res.getStringArray(R.array.adBlockUrl);
        for (String adUrl : adUrls) {
            if (url.contains(adUrl)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasAd2(Context context, String url) {
        return url.contains(".gif");
    }

}