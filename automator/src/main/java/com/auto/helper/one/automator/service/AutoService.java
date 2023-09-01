package com.auto.helper.one.automator.service;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import com.auto.helper.one.automator.AutoTask;
import com.auto.helper.one.automator.AutoTaskManager;
import com.auto.helper.one.automator.BaseAutoTask;

import java.util.Arrays;
import java.util.List;

public class AutoService extends AccessibilityService {
    @SuppressLint("StaticFieldLeak")
    private static AccessibilityService mService;
    private List<? extends AutoTask> mAutoTasks;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        mService = this;
        mAutoTasks = AutoTaskManager.getAllCases();
        Toast.makeText(this, "自动化服务已连接", Toast.LENGTH_SHORT).show();
        onServiceBind();
    }


    public static AccessibilityService getService() {
        return mService;
    }

    private void onServiceBind() {
        for (AutoTask autoTask : mAutoTasks) {
            if (autoTask instanceof BaseAutoTask) {
                ((BaseAutoTask) autoTask).onServiceBind(this);
            }
        }
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        for (AutoTask task : mAutoTasks) {
            if (task instanceof BaseAutoTask) {
                CharSequence packageName = accessibilityEvent.getPackageName();
                if (packageName == null) {
                    continue;
                }
                String[] processes = ((BaseAutoTask) task).getPackages();
                if (processes == null || processes.length == 0) {
                    ((BaseAutoTask) task).onServiceEvent();
                    continue;
                }
                List<String> packages = Arrays.asList(processes);
                if (packages.contains(packageName.toString())) {
                    ((BaseAutoTask) task).onServiceEvent();
                }
            }
        }
    }

    @Override
    public void onInterrupt() {
        Toast.makeText(this, "自动化服务已断开", Toast.LENGTH_SHORT).show();
        onServiceInterrupt();
    }

    private void onServiceInterrupt() {
        for (AutoTask autoTask : mAutoTasks) {
            if (autoTask instanceof BaseAutoTask) {
                ((BaseAutoTask) autoTask).onServiceInterrupt();
            }
        }
    }
}


