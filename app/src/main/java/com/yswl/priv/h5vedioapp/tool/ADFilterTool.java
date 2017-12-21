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

    public static boolean hasHomeUrl(Context context, String url) {
        Resources resources = context.getResources();
        String[] homeUrls = resources.getStringArray(R.array.adBlockUrl);
        for (String homeUrl : homeUrls) {
            if (url.contains(homeUrl)) {
                return true;
            }
        }
        return false;
    }
}