package com.auto.helper.one.automator.thread;

import java.util.HashMap;


/**
 * 一个自动化case只能🈶一个线程
 **/
public class ThreadPool {

    private static final HashMap<String, AutoTaskThread> mCaseThreadHashMap = new HashMap<>();

    public synchronized static AutoTaskThread getThread(String name) {
        AutoTaskThread autoTaskThread = mCaseThreadHashMap.get(name);
        if (autoTaskThread == null) {
            AutoTaskThread caseThread = new AutoTaskThread(name);
            caseThread.start();
            mCaseThreadHashMap.put(name, caseThread);
            return caseThread;
        }
        return autoTaskThread;
    }

    public static void release() {
        for (AutoTaskThread autoTaskThread : mCaseThreadHashMap.values()) {
            autoTaskThread.quitSafely();
        }
        mCaseThreadHashMap.clear();
    }
}
