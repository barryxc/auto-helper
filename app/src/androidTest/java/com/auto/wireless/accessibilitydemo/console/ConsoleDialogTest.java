package com.auto.wireless.accessibilitydemo.console;

import android.os.Handler;
import android.os.Looper;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.auto.helper.one.common.console.Console;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

@RunWith(AndroidJUnit4.class)
public class ConsoleDialogTest {
    private static final String TAG = ConsoleDialogTest.class.getSimpleName();
    private final Handler mHandler = new android.os.Handler(Looper.getMainLooper());

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void start() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    Console.d("TAG", "测试" + i);
                }
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}