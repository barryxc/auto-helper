package com.auto.helper.one.automator;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.auto.helper.one.common.console.Console;
import com.auto.helper.one.common.keep.AutoKeep;

@AutoKeep
public abstract class Script extends BaseAutoTask {
    private volatile boolean mStarted;

    public Script() {
    }

    @Override
    public boolean start() {
        if (!super.start()) {
            return false;
        }
        if (mStarted) {
            Console.e(getTag(), String.format("%s 已经启动", name()));
            return false;
        }
        synchronized (this) {
            mStarted = true;
            dispatchState();
        }
        mThreadHandler.post(this::executeTask);
        return true;
    }

    @Override
    public boolean stop() {
        if (mContext != null) {
            Toast.makeText(mContext, "脚本执行中", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @SuppressLint("DefaultLocale")
    private void executeTask() {
        try {
            set(0);
            boolean result = onExecute();
            Console.d(getTag(), String.format("已完成 %d 任务，脚本执行%s", get(), result ? "成功" : "失败"));
        } catch (Throwable e) {
            Console.e(getTag(), String.format("脚本执行异常,%s", e.getMessage()), e);
        }
        synchronized (this) {
            mStarted = false;
            dispatchState();
        }
    }

    @Override
    public void onServiceEvent() {
        // do nothing
    }

    @Override
    public String[] getPackages() {
        return null;
    }

    @Override
    public String getTag() {
        return name();
    }

    public abstract boolean onExecute();

    public abstract String name();


    @Override
    public boolean isStart() {
        return mStarted;
    }
}
