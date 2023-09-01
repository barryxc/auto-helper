package com.auto.helper.one.tasks.script;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.auto.helper.one.automator.Script;
import com.auto.helper.one.automator.constanst.Regex;
import com.auto.helper.one.automator.node.UiNode;
import com.auto.helper.one.automator.selector.Filter;
import com.auto.helper.one.common.util.RegexUtils;
import com.auto.helper.one.common.util.StringUtils;


public class ZFBFarmScript extends Script {

    String appPackage = "com.eg.android.AlipayGphone";

    @Override
    public boolean onExecute() {
        launchApp("支付宝");
        selector().id("com.alipay.android.phone.openplatform:id/app_text").text("芭芭农场").className(TextView.class).untilFindOne().clickDeeply();
        selector().index(3).text("芭芭农场,种果树得水果，助农增收").waitFor();
        sleep(5);
        clickTasks();
        sleep(5);
        doTasks();
        return true;
    }

    private void clickTasks() {

        UiNode taskNode;
        do {
            taskNode = selector().packageName(appPackage).index(1).text("任务列表").findOne(3);
            if (taskNode != null) {
                taskNode.clickDeeply();
                selector().packageName(appPackage).index(0).text("做任务集肥料").waitFor();
            }
        } while (taskNode == null);
    }


    public void doTasks() {
        UiNode uiNode = null;
        do {
            uiNode = selector().text("蚂蚁庄园小鸡肥料")
                    .filter(new Filter() {
                        @Override
                        public boolean filter(UiNode uiNode) {
                            UiNode sibling = uiNode.nextSibling(2);
                            if (sibling != null) {
                                UiNode child = sibling.getChild(0);
                                return child != null && child.isView(Button.class);
                            }

                            return false;
                        }
                    })
                    .findOne(1);
            if (uiNode != null) {
                uiNode.nextSibling(2).getChild(0).click();
            }

            uiNode = selector().textMatches(".*\\(.*/.*\\)")
                    .filter(new Filter() {
                        @Override
                        public boolean filter(UiNode uiNode) {
                            return StringUtils.hasTask(uiNode.getText());
                        }
                    })
                    .filter(new Filter() {
                        @Override
                        public boolean filter(UiNode uiNode) {
                            UiNode sibling = uiNode.nextSibling(1);
                            UiNode sibling2 = uiNode.nextSibling(2);

                            if (sibling != null && sibling2 != null) {
                                UiNode child = sibling2.getChild(0);
                                if (child != null) {
                                    return RegexUtils.regexMatch("浏览.*秒得.*", sibling.getText()) && child.isView(Button.class)
                                            && child.isClickable();
                                }
                            }
                            return false;
                        }
                    }).findOne(1);
            if (uiNode != null) {
                uiNode.nextSibling(2).getChild(0).click();
                sleep(3);
                scrollUp();
                selector().textMatches(Regex.TASK_FINISH).findOne(20);
                backToFarm();
                incrementAndGet();
            }

        } while (uiNode != null);
    }

    private void backToFarm() {
        while (!isFarmPage()) {
            back();
            sleep(1);
        }
    }

    private boolean isFarmPage() {
        return selector().index(3).text("芭芭农场,种果树得水果，助农增收").exists();
    }

    @Override
    public String name() {
        return "支付宝芭芭农场";
    }

}
