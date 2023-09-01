package com.auto.helper.one.tasks.script;

import android.graphics.Rect;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.auto.helper.one.automator.Script;
import com.auto.helper.one.automator.constanst.Package;
import com.auto.helper.one.automator.flow.Action;
import com.auto.helper.one.automator.flow.Flow;
import com.auto.helper.one.automator.node.UiNode;
import com.auto.helper.one.automator.selector.Filter;
import com.auto.helper.one.common.console.Console;
import com.auto.helper.one.common.util.RegexUtils;
import com.auto.helper.one.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.auto.helper.one.automator.constanst.Regex.TASK_FINISH;

/**
 * 淘宝的芭芭农场
 **/
public class TaoBaoFarmScript extends Script {

    @Override
    public String name() {
        return "芭芭农场";
    }

    @Override
    public boolean onExecute() {
        launchPackage(Package.APP_TAO_BAO);
        selector().packageName(Package.APP_TAO_BAO).desc("首页").untilFindOne().clickDeeply();
        selector().packageName(Package.APP_TAO_BAO).desc("芭芭农场").className(FrameLayout.class).untilFindOne().clickDeeply();
        selector().packageName(Package.APP_TAO_BAO).text("芭芭农场").className(WebView.class).waitFor();
        //取消各种对话框
        sleep(5);
        dismissDialogs();
        //点击松鼠
        clickSquirrel();
        //领取肥料
        getManure();
        //集肥料
        collectManure();
        //做所有任务
        doAllTasks();
        //关闭任务弹框
        closeTaskDialog();
        clickManure();//施肥
        return true;
    }

    private void doAllTasks() {
        List<String> tasks = new ArrayList<>();
        UiNode uiNode;
        do {
            uiNode = selector().packageName(Package.APP_TAO_BAO).text("去签到").findOne(1);
            if (uiNode != null) {
                uiNode.clickDeeply();
                incrementAndGet();
            }
            uiNode = selector().packageName(Package.APP_TAO_BAO).textMatches("农场百科问答\\(0/.*\\)").findOne(1);
            if (uiNode != null) {
                uiNode.clickDeeply();
                UiNode daNode = selector().textMatches("A\\..*").className(Button.class).findOne(5);
                if (daNode != null) {
                    daNode.clickDeeply();
                }
                UiNode gljNode = selector().packageName(Package.APP_TAO_BAO).textContains("领取鼓励奖").findOne(2);
                UiNode jlNode = selector().packageName(Package.APP_TAO_BAO).textContains("领取奖励").findOne(2);
                if (gljNode != null) {
                    gljNode.clickDeeply();
                }
                if (jlNode != null) {
                    jlNode.clickDeeply();
                }
                UiNode closeNode = selector().packageName(Package.APP_TAO_BAO).text("关闭").findOne(5);
                if (closeNode != null) {
                    closeNode.clickDeeply();
                }
                collectManure();
                incrementAndGet();
            }

            uiNode = selector().packageName(Package.APP_TAO_BAO).textMatches("搜一搜你心仪的宝贝\\(0/.*\\)").findOne(1);
            if (uiNode != null) {
                uiNode.clickDeeply();
                UiNode edNode = selector().packageName(Package.APP_TAO_BAO).className(EditText.class).findOne(2);
                UiNode ssNode = selector().packageName(Package.APP_TAO_BAO).text("搜索").findOne(2);
                if (edNode != null && ssNode != null) {
                    edNode.inputText("内裤");
                    ssNode.clickDeeply();
                    sleep(2);
                    scrollUp();
                    selector().packageName(Package.APP_TAO_BAO).textMatches(TASK_FINISH).textExclude("任务未完成").findOne(20);
                    backToBaba();
                    incrementAndGet();
                }
            }

            uiNode = selector().packageName(Package.APP_TAO_BAO).text("领400肥料礼包(0/1)").findOne(1);
            if (uiNode != null) {
                uiNode.clickDeeply();
                incrementAndGet();
            }

            uiNode = selector().packageName(Package.APP_TAO_BAO).text("领肥料小提示(0/1)").findOne(1);
            if (uiNode != null) {
                uiNode.clickDeeply();
                incrementAndGet();
            }

            uiNode = selector()
                    .packageName(Package.APP_TAO_BAO)
                    .textMatches(".*\\(.*/.*\\)")
                    .textExcludeAll(tasks)
                    .filter(new Filter() {
                        @Override
                        public boolean filter(UiNode uiNode) {
                            UiNode parent = uiNode.getParent();
                            if (parent != null && parent.getChild(1) != null) {
                                UiNode tipNode = parent.getChild(1).getChild(0);
                                return RegexUtils.regexMatch("浏览15秒.*", tipNode.getText());
                            }
                            return false;
                        }
                    }).filter(new Filter() {
                        @Override
                        public boolean filter(UiNode uiNode) {
                            return StringUtils.hasTask(uiNode.getText());
                        }
                    }).findOne(1);
            if (uiNode != null) {
                uiNode.clickDeeply();
                sleep(1);
                if (!uiNode.getText().contains("旗舰店") && !uiNode.getText().contains("直播间")) {
                    scrollUp();
                }
                selector().packageName(Package.APP_TAO_BAO)
                        .textExclude("任务未完成")
                        .textMatches(TASK_FINISH).findOne(20);
                backToBaba();
                incrementAndGet();
                tasks.add(uiNode.getText());
            }
        } while (uiNode != null);
    }

    private void backToBaba() {
        while (!isBaBaPage()) {
            backAndWait();
        }
    }

    /**
     * 各种弹框
     **/
    private void dismissDialogs() {
        //领取肥料弹框
        backToBaba();
        if (selector().packageName(Package.APP_TAO_BAO)
                .text("明日7点可领").exists() || selector().packageName(Package.APP_TAO_BAO)
                .text("今日7点可领").exists()) {
            UiNode closeNode = selector().packageName(Package.APP_TAO_BAO)
                    .text("关闭")
                    .findOne(3);
            if (closeNode != null) {
                closeNode.clickDeeply();
            }
        }

        //施肥大礼包
        if (selector().packageName(Package.APP_TAO_BAO).textMatches(".*施肥大礼包").exists()) {
            UiNode lqNode = selector().packageName(Package.APP_TAO_BAO).text("马上去领").findOne(3);
            if (lqNode != null) {
                lqNode.clickDeeply();
            }
            UiNode closeNode = selector().packageName(Package.APP_TAO_BAO).text("关闭").findOne(3);
            if (closeNode != null) {
                closeNode.clickDeeply();
            }
        }

        //亲密度弹框
        if (selector().packageName(Package.APP_TAO_BAO).textMatches(".*亲密奖励").exists()) {
            UiNode lqNode = selector().packageName(Package.APP_TAO_BAO).text("领取奖励").findOne(3);
            if (lqNode != null) {
                lqNode.clickDeeply();
            }

            UiNode gbNode = selector().packageName(Package.APP_TAO_BAO).text("关闭").findOne(3);
            if (gbNode != null) {
                gbNode.clickDeeply();
            }
        }

        //欢迎回来弹框
        if (selector().packageName(Package.APP_TAO_BAO).text("继续努力").exists() || selector().text("最近你的队友都有努力种树哦").exists()) {
            UiNode nlNode = selector().packageName(Package.APP_TAO_BAO).text("继续努力").findOne(3);
            if (nlNode != null) {
                nlNode.clickDeeply();
            }
        }

        //施肥全返弹框
        if (selector().packageName(Package.APP_TAO_BAO).text("今日施肥全返").exists()) {
            UiNode closeNode = selector().packageName(Package.APP_TAO_BAO).text("关闭").findOne(3);
            if (closeNode != null) {
                closeNode.clickDeeply();
            }
        }

        //取消相册弹框
        if (selector().packageName(Package.APP_TAO_BAO).text("保存到相册").exists()) {
            UiNode cancelNode = selector().packageName(Package.APP_TAO_BAO).text("取消").findOne(3);
            if (cancelNode != null) {
                cancelNode.clickDeeply();
            }
        }
    }


    /**
     * 点击松鼠
     **/
    private void clickSquirrel() {
        clickXY(213, 1512);
        Console.d(getTag(), "点击松鼠🐿");
        sleep(1);
    }

    /**
     * 领取肥料
     **/
    private void getManure() {
        Flow.ifThat(isBaBaPage()).then(new Action() {
            @Override
            public void action() {
                clickXY(886, 1517);
                Console.d(getTag(), "领取肥料");
                sleep(1);
            }
        });
    }

    /**
     * 收集肥料
     **/
    private void collectManure() {
        Flow.ifThat(isBaBaPage())
                .then(() -> {
                    selector().text("图片")
                            .className("android.widget.Image")
                            .isEnabled(true)
                            .isClickable(true)
                            .index(2).untilFindOne().clickDeeply();
                    selector().packageName(Package.APP_TAO_BAO).id("taskBottomSheet").waitFor();
                });
    }

    private void closeTaskDialog() {
        //关闭任务弹框
        Flow.ifThat(selector().packageName(Package.APP_TAO_BAO).id("taskBottomSheet").findOne(1) != null)
                .then(new Action() {
                          @Override
                          public void action() {
                              UiNode gbNode = selector().packageName(Package.APP_TAO_BAO)
                                      .className(Button.class)
                                      .boundsContains(new Rect(929, 313, 1047, 431))
                                      .index(1)
                                      .text("关闭").findOne(1);
                              if (gbNode != null) {
                                  gbNode.clickDeeply();
                                  sleep(1);
                              }
                          }
                      }
                );
    }

    /**
     * 点击施肥
     **/
    private void clickManure() {
        Flow.ifThat(isBaBaPage())
                .then(new Action() {
                          @Override
                          public void action() {
                              do {
                                  dismissDialogs();
                                  clickXY(520, 2025);
                                  Console.d(getTag(), "施肥");
                                  sleep(1);
                              } while (selector().packageName(Package.APP_TAO_BAO)
                                      .id("taskBottomSheet")
                                      .findOne(1) == null);
                          }
                      }
                );
    }

    private boolean isBaBaPage() {
        return selector().packageName(Package.APP_TAO_BAO).text("芭芭农场").className(WebView.class).exists();
    }
}
