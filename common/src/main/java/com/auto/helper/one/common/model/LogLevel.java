package com.auto.helper.one.common.model;

import androidx.annotation.IntDef;

public interface LogLevel {
    int D = 1;
    int W = 2;
    int E = 3;

    @IntDef({LogLevel.D, LogLevel.W, LogLevel.E})
    @interface Restrict {
    }
}
