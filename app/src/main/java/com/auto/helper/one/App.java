package com.auto.helper.one;

import com.auto.helper.one.automator.AutoServiceHelper;
import com.auto.helper.one.automator.AutoTaskManager;
import com.auto.helper.one.common.console.Console;
import com.auto.helper.one.tasks.script.MGuoScript;
import com.auto.helper.one.tasks.script.TaoBaoFarmScript;
import com.auto.helper.one.tasks.script.ZFBFarmScript;
import com.auto.helper.one.tasks.service.PackageService;

public class App extends android.app.Application {

    public static App mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        initAppModule();
        startConsole();
        registerTasks();
    }

    private void initAppModule() {
        AutoServiceHelper.init(this);
    }

    private void startConsole() {
        Console.start();
    }

    private void registerTasks() {
        AutoTaskManager.register(new PackageService());
        AutoTaskManager.register(new TaoBaoFarmScript());
        AutoTaskManager.register(new ZFBFarmScript());

    }
}
