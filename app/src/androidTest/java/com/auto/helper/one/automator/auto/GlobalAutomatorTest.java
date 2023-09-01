package com.auto.helper.one.automator.auto;

import com.auto.helper.one.automator.service.AutoService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GlobalAutomatorTest {

    GlobalAutomator mGlobalAutomator;

    @Before
    public void setUp() throws Exception {
        while (AutoService.getService() == null) {

        }
        mGlobalAutomator = new GlobalAutomator(AutoService.getService());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void back() {
        mGlobalAutomator.back();
    }

    @Test
    public void clickXY() {
    }

    @Test
    public void paste() {
    }

    @Test
    public void swipe() {
    }

    @Test
    public void sleep() {
    }

    @Test
    public void launchApp() {
    }

    @Test
    public void home() {
    }

    @Test
    public void scrollUp() {
    }

    @Test
    public void scrollDown() {
    }

    @Test
    public void scrollLeft() {
    }

    @Test
    public void scrollRight() {
    }

    @Test
    public void performGlobalAction() {
    }

    @Test
    public void openPowerDialog() {
        mGlobalAutomator.openPowerDialog();
    }

    @Test
    public void openNotifications() {
        mGlobalAutomator.openNotifications();
    }

    @Test
    public void openSettings() {
        mGlobalAutomator.openSettings();
    }

    @Test
    public void openRecent() {
        mGlobalAutomator.openRecent();
    }

    @Test
    public void splitScreen() {
        mGlobalAutomator.splitScreen();
    }

    @Test
    public void takeScreenShot() {
        mGlobalAutomator.takeScreenShot();
    }

    @Test
    public void lockScreen() {
        mGlobalAutomator.lockScreen();
    }

    @Test
    public void openAccessibilityService() {
        mGlobalAutomator.toggleAccessibilityService();
    }

}