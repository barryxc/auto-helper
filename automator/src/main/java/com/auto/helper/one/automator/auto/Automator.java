package com.auto.helper.one.automator.auto;

import com.auto.helper.one.common.keep.AutoKeep;

@AutoKeep
public interface Automator {

    void launchPackage(String packageName);

    void launchApp(String appName);

    void openUrl(String url);

    void uninstall(String packageName);

    void viewFile(String filePath);

    boolean openPowerDialog();

    boolean performGlobalAction(int action);

    boolean openNotifications();

    boolean openSettings();

    boolean openRecent();

    boolean splitScreen();

    boolean takeScreenShot();

    boolean lockScreen();

    boolean toggleAccessibilityService();

    boolean back();

    boolean home();

    boolean scrollUp();

    boolean scrollDown();

    boolean scrollLeft();

    boolean scrollRight();

    boolean clickXY(int x, int y);

    boolean swipe(int x1, int y1, int x2, int y2, final int duration);

    boolean paste();

    void sleep(float seconds);
}
