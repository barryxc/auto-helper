package com.auto.helper.one.automator;

import android.app.Application;

import com.auto.helper.one.common.app.AppModule;
import com.auto.helper.one.common.console.Console;
import com.auto.helper.one.common.keep.AutoKeep;

@AutoKeep
public class AutoServiceHelper {
    public static final String SERVICE = "com.auto.helper.one/com.auto.helper.one.automator.service.AutoService";

    public synchronized static void init(Application application) {
        AppModule.getApp().init(application);
        Console.init(application);
    }

    public static boolean isServiceEnable() {
        return AppModule.getApp().isAccessibilityServiceEnabled(SERVICE);
    }
}
