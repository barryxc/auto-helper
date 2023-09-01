package com.auto.helper.one.modules;

import com.auto.helper.one.automator.constanst.Package;
import com.auto.helper.one.common.app.AppModule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AppModuleTest {
    AppModule mApp;

    @Before
    public void setUp() throws Exception {
        mApp = AppModule.getApp();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void launchApp() {
        mApp.launchPackage(Package.APP_TAO_BAO);
    }

    @Test
    public void killApp() {
        boolean b = mApp.killApp(Package.APP_TAO_BAO);
        Assert.assertTrue(b);
    }
}