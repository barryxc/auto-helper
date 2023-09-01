package com.auto.helper.one.automator;

import com.auto.helper.one.common.keep.AutoKeep;

@AutoKeep
public interface AutoTask {

    String name();

    boolean start();

    boolean stop();

    boolean isStart();

    void registerListener(StateListener stateListener);
}
