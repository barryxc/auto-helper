package com.auto.helper.one.common.console;

import android.content.Context;
import android.util.Log;

import com.auto.helper.one.common.keep.AutoKeep;
import com.auto.helper.one.common.model.LogData;
import com.auto.helper.one.common.ui.view.ConsoleDialog;

@AutoKeep
public class Console {
    private static final String TAG = Console.class.getSimpleName();
    private static ConsoleDialog mConsole;

    public static boolean start() {
        if (!mConsole.isShowing()) {
            mConsole.show();
        }
        return true;
    }

    public static boolean stop() {
        if (mConsole.isShowing()) {
            mConsole.dismiss();
        }
        return true;
    }

    public synchronized static void init(Context context) {
        if (mConsole == null) {
            mConsole = new ConsoleDialog(context);
        }
    }

    public static void d(String tag, String errorMsg) {
        Log.d(TAG, tag + "==>" + errorMsg);
        if (mConsole != null) {
            mConsole.addData(LogData.d(tag, errorMsg));
            start();
        }
    }

    public static void w(String tag, String errorMsg) {
        Log.w(TAG, tag + "==>" + errorMsg);
        if (mConsole != null) {
            mConsole.addData(LogData.w(tag, errorMsg));
            start();
        }
    }

    public static void e(String tag, String errorMsg) {
        Log.e(TAG, tag + "==>" + errorMsg, null);
        if (mConsole != null) {
            mConsole.addData(LogData.e(tag, errorMsg, null));
            start();
        }

    }

    public static void e(String tag, String errorMsg, Throwable e) {
        Log.e(TAG, tag + "==>" + e.getMessage(), e);
        if (mConsole != null) {
            mConsole.addData(LogData.e(tag, errorMsg, e));
            start();
        }
    }
}
