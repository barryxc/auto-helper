package com.auto.helper.one.automator;

import com.auto.helper.one.automator.service.AutoService;
import com.auto.helper.one.common.keep.AutoKeep;

@AutoKeep
public abstract class Service extends BaseAutoTask {
    private volatile boolean mStarted = false;

    @Override
    public void onServiceBind(AutoService autoService) {
        super.onServiceBind(autoService);
        if (needStart()) {
            start();//服务自动启动
        }
    }

    public boolean needStart() {
        return true;
    }

    @Override
    public boolean start() {
        if (!super.start()) {
            return false;
        }
        if (mStarted) {
            return true;
        }
        synchronized (this) {
            mStarted = true;
            dispatchState();
        }
        return true;
    }

    @Override
    public boolean isStart() {
        return mStarted;
    }

    @Override
    public boolean stop() {
        if (!mStarted) {
            return true;
        }
        synchronized (this) {
            mStarted = false;
            dispatchState();
        }
        return true;
    }

    public void onServiceEvent() {
        onCall();
    }

    public String[] getPackages() {
        return packages();
    }

    public abstract boolean onCall();

    public abstract String name();

    public abstract String[] packages();

    @Override
    public String getTag() {
        return name();
    }
}
