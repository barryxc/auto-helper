package com.auto.wireless.accessibilitydemo.service;

import com.auto.helper.one.tasks.script.TaoBaoFarmScript;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class BaseAutomatorTaskTest {
    private final TaoBaoFarmScript mBABAFarmAutoCase = new TaoBaoFarmScript();

    @Before
    public void setUp() throws Exception {
        mBABAFarmAutoCase.start();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void swipe() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}