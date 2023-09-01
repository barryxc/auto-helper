package com.auto.wireless.accessibilitydemo.util;

import com.auto.helper.one.common.util.RegexUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RegexUtilsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findGroup() throws InterruptedException {
//        boolean hasTask = StringUtils.hasTask("jordan官方店(1/5)");


        boolean match = RegexUtils.regexMatch("(逛|.*浏览|.*观看|.*直播|.*旗舰店).*\\(.*/.*\\)", "观看抽C罗签名秋衣(0/10)");

        Assert.assertTrue(match);
    }
}