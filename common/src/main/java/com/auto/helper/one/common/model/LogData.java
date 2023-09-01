package com.auto.helper.one.common.model;

import java.io.Serializable;

public class LogData implements Serializable {
    private String tag;
    private String message;
    private Throwable throwable;
    private @LogLevel.Restrict
    int level;


    private LogData(@LogLevel.Restrict int level, String tag, String message, Throwable throwable) {
        this.tag = tag;
        this.message = message;
        this.throwable = throwable;
        this.level = level;
    }

    public static LogData d(String tag, String msg) {
        return new LogData(LogLevel.D, tag, msg, null);
    }

    public static LogData w(String tag, String msg) {
        return new LogData(LogLevel.W, tag, msg, null);
    }

    public static LogData e(String tag, String msg, Throwable e) {
        return new LogData(LogLevel.E, tag, msg, e);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
