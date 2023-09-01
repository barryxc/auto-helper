package com.auto.helper.one.automator.auto;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.os.Build;

import com.auto.helper.one.common.app.AppModule;
import com.auto.helper.one.common.app.Screen;
import com.auto.helper.one.common.console.Console;
import com.auto.helper.one.common.keep.AutoKeep;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

@AutoKeep
public class GlobalAutomator implements Automator {
    private static final String TAG = GlobalAutomator.class.getSimpleName();
    private final AccessibilityService mService;
    private final Screen mScreen;
    private final AppModule mApp;

    public GlobalAutomator(AccessibilityService autoService) {
        mService = autoService;
        mScreen = new Screen(mService);
        mApp = AppModule.getApp();
    }

    private boolean checkParams() {
        return mService != null;
    }

    @Override
    public boolean clickXY(int x, int y) {
        if (!checkParams()) {
            return false;
        }
        if (x < 0 || y < 0) {
            return false;
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Console.w(TAG, "点击屏幕坐标>>" + x + ":" + y);
            GestureDescription.Builder builder = null;
            builder = new GestureDescription.Builder();
            Path p = new Path();
            p.moveTo(x, y);
            builder.addStroke(new GestureDescription.StrokeDescription(p, 0L, 50L));
            GestureDescription gesture = builder.build();

            AtomicBoolean result = new AtomicBoolean(false);
            CountDownLatch countDownLatch = new CountDownLatch(1);
            mService.dispatchGesture(gesture, new AccessibilityService.GestureResultCallback() {
                @Override
                public void onCompleted(GestureDescription gestureDescription) {
                    super.onCompleted(gestureDescription);
                    result.set(true);
                    countDownLatch.countDown();
                }

                @Override
                public void onCancelled(GestureDescription gestureDescription) {
                    super.onCancelled(gestureDescription);
                    result.set(false);
                    countDownLatch.countDown();
                }
            }, null);

            try {
                countDownLatch.await();
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return result.get();
        }
        return false;
    }


    @Override
    public boolean paste() {
        return false;
    }

    @Override
    public boolean swipe(int x1, int y1, int x2, int y2, final int duration) {
        if (!checkParams()) {
            return false;
        }
        if (x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Console.d(TAG, "滑动屏幕");
            GestureDescription.Builder builder = new GestureDescription.Builder();
            Path p = new Path();
            p.moveTo(x1, y1);
            p.lineTo(x2, y2);

            builder.addStroke(new GestureDescription.StrokeDescription(p, 0L, duration));
            GestureDescription gesture = builder.build();

            AtomicBoolean result = new AtomicBoolean(false);
            CountDownLatch countDownLatch = new CountDownLatch(1);
            mService.dispatchGesture(gesture, new AccessibilityService.GestureResultCallback() {
                @Override
                public void onCompleted(GestureDescription gestureDescription) {
                    super.onCompleted(gestureDescription);
                    result.set(true);
                    countDownLatch.countDown();
                    Console.d(TAG, "滑动屏幕完成");
                }

                @Override
                public void onCancelled(GestureDescription gestureDescription) {
                    super.onCancelled(gestureDescription);
                    Console.d(TAG, "取消滑动屏幕");
                    result.set(false);
                    countDownLatch.countDown();
                }
            }, null);
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return result.get();
        }
        return false;
    }

    @Override
    public void sleep(float seconds) {
        try {
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void launchPackage(String packageName) {
        mApp.launchPackage(packageName);
    }

    @Override
    public void launchApp(String appName) {
        mApp.launchApp(appName);
    }

    @Override
    public void openUrl(String url) {
        mApp.openUrl(url);
    }

    @Override
    public void uninstall(String packageName) {
        mApp.uninstall(packageName);
    }


    @Override
    public boolean back() {
        boolean result = checkParams() && performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
        Console.d(TAG, String.format("按返回键%s", result ? "成功" : "失败"));
        return result;
    }

    @Override
    public boolean home() {
        boolean result = checkParams() && performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
        Console.d(TAG, String.format("按home键%s", result ? "成功" : "失败"));
        return result;
    }


    @Override
    public boolean scrollUp() {
        return swipe(100, mScreen.getScreenHeight() / 2, 100, 0, 1000);
    }

    @Override
    public boolean scrollDown() {
        return false;
    }

    @Override
    public boolean scrollLeft() {
        return false;
    }

    @Override
    public boolean scrollRight() {
        return false;
    }

    @Override
    public boolean performGlobalAction(int action) {
        return mService.performGlobalAction(action);
    }

    @Override
    public boolean openPowerDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return performGlobalAction(AccessibilityService.GLOBAL_ACTION_POWER_DIALOG);
        }
        return false;
    }


    @Override
    public boolean openNotifications() {
        return performGlobalAction(AccessibilityService.GLOBAL_ACTION_NOTIFICATIONS);
    }

    @Override
    public boolean openSettings() {
        return performGlobalAction(AccessibilityService.GLOBAL_ACTION_QUICK_SETTINGS);
    }

    @Override
    public boolean openRecent() {
        return performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS);
    }

    @Override
    public boolean splitScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return performGlobalAction(AccessibilityService.GLOBAL_ACTION_TOGGLE_SPLIT_SCREEN);
        }
        return false;
    }

    @Override
    public boolean takeScreenShot() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return performGlobalAction(AccessibilityService.GLOBAL_ACTION_TAKE_SCREENSHOT);
        }
        return false;
    }

    @Override
    public boolean lockScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return performGlobalAction(AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN);
        }
        return false;
    }

    @Override
    public boolean toggleAccessibilityService() {
        return performGlobalAction(AccessibilityService.GLOBAL_ACTION_ACCESSIBILITY_BUTTON);
    }


    @Override
    public void viewFile(String path) {
        mApp.viewFile(path);
    }

}
