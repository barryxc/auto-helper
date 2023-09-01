package com.auto.helper.one.common.app;

import android.content.Context;

public class Screen {
    private final int mScreenHeight;
    private final int mScreenWidth;

    public Screen(Context context) {
        mScreenHeight = context.getResources().getDisplayMetrics().heightPixels;
        mScreenWidth = context.getResources().getDisplayMetrics().widthPixels;
    }

    public int getScreenHeight() {
        return mScreenHeight;
    }

    public int getScreenWidth() {
        return mScreenWidth;
    }


    public int scaleX(int x) {
        return x;
    }

    public int scaleY(int x) {
        return x;
    }
}
