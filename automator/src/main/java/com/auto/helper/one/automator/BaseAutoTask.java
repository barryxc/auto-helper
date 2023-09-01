package com.auto.helper.one.automator;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.auto.helper.one.automator.auto.Automator;
import com.auto.helper.one.automator.auto.GlobalAutomator;
import com.auto.helper.one.automator.selector.UiGlobalSelector;
import com.auto.helper.one.automator.service.AutoService;
import com.auto.helper.one.automator.thread.AutoTaskThread;
import com.auto.helper.one.automator.thread.ThreadPool;
import com.auto.helper.one.common.app.AppModule;
import com.auto.helper.one.common.keep.AutoKeep;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@AutoKeep
public abstract class BaseAutoTask extends AtomicInteger implements AutoTask, Automator {
    public AccessibilityService mAutoService;
    protected Handler mThreadHandler;
    protected Context mContext;
    protected List<StateListener> mStateListeners = new ArrayList<>();
    private Handler mMainHandler;
    private GlobalAutomator mGlobalAutomator;

    public void onServiceBind(AutoService autoService) {
        synchronized (this) {
            mContext = autoService.getApplicationContext();
            AutoTaskThread mAutoTaskThread = ThreadPool.getThread(name());
            mAutoService = autoService;
            mThreadHandler = new Handler(mAutoTaskThread.getLooper());
            mMainHandler = new Handler(Looper.getMainLooper());
            mGlobalAutomator = new GlobalAutomator(autoService);
        }
    }

    @Override
    public boolean start() {
        if (!isConnected()) {
            AppModule.getApp().openServiceActivity();
            return false;
        }
        return true;
    }

    protected synchronized boolean isConnected() {
        return mContext != null && mAutoService != null && mMainHandler != null;
    }

    public UiGlobalSelector selector() {
        return new UiGlobalSelector(mAutoService);
    }

    public UiGlobalSelector selector(String algorithm) {
        return new UiGlobalSelector(mAutoService, algorithm);
    }

    public void onServiceInterrupt() {
        synchronized (this) {
            mContext = null;
        }
    }

    public abstract void onServiceEvent();

    public abstract String getTag();

    @Override
    public void registerListener(StateListener stateListener) {
        mStateListeners.add(stateListener);
    }

    public void removeListener(StateListener stateListener) {
        mStateListeners.remove(stateListener);
    }

    protected void dispatchState() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            doDispatch();
        } else {
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {
                    doDispatch();
                }
            });
        }
    }

    private void doDispatch() {
        mMainHandler.post(() -> {
            for (StateListener stateListener : mStateListeners) {
                stateListener.onStateChanged();
            }
        });
    }


    //自动化操作
    ////////////////////////////////////////////////////////////////////////

    @Override
    public void launchPackage(String packageName) {
        mGlobalAutomator.launchPackage(packageName);
    }

    @Override
    public void launchApp(String appName) {
        mGlobalAutomator.launchApp(appName);
    }

    @Override
    public boolean back() {
        return mGlobalAutomator.back();
    }

    public boolean backAndWait() {
        boolean result = back();
        sleep(2);
        return result;
    }

    @Override
    public boolean home() {
        return mGlobalAutomator.home();
    }

    @Override
    public boolean scrollUp() {
        return mGlobalAutomator.scrollUp();
    }

    @Override
    public boolean clickXY(int x, int y) {
        return mGlobalAutomator.clickXY(x, y);
    }

    @Override
    public boolean swipe(int x1, int y1, int x2, int y2, int duration) {
        return mGlobalAutomator.swipe(x1, y1, x2, y2, duration);
    }

    @Override
    public void sleep(float seconds) {
        mGlobalAutomator.sleep(seconds);
    }

    @Override
    public boolean scrollDown() {
        return mGlobalAutomator.scrollDown();
    }

    @Override
    public boolean scrollLeft() {
        return mGlobalAutomator.scrollLeft();
    }

    @Override
    public boolean scrollRight() {
        return mGlobalAutomator.scrollRight();
    }

    @Override
    public boolean paste() {
        return mGlobalAutomator.paste();
    }

    @Override
    public boolean openPowerDialog() {
        return mGlobalAutomator.openPowerDialog();
    }

    @Override
    public boolean performGlobalAction(int action) {
        return mGlobalAutomator.performGlobalAction(action);
    }

    @Override
    public boolean openNotifications() {
        return mGlobalAutomator.openNotifications();
    }

    @Override
    public boolean openSettings() {
        return mGlobalAutomator.openSettings();
    }

    @Override
    public boolean openRecent() {
        return mGlobalAutomator.openRecent();
    }

    @Override
    public boolean splitScreen() {
        return mGlobalAutomator.splitScreen();
    }

    @Override
    public boolean takeScreenShot() {
        return mGlobalAutomator.takeScreenShot();
    }

    @Override
    public boolean lockScreen() {
        return mGlobalAutomator.lockScreen();
    }

    @Override
    public boolean toggleAccessibilityService() {
        return mGlobalAutomator.toggleAccessibilityService();
    }

    @Override
    public void openUrl(String url) {
        mGlobalAutomator.openUrl(url);
    }

    @Override
    public void uninstall(String packageName) {
        mGlobalAutomator.uninstall(packageName);
    }

    @Override
    public void viewFile(String filePath) {
        mGlobalAutomator.viewFile(filePath);
    }

    public abstract String[] getPackages();


    ///
}
