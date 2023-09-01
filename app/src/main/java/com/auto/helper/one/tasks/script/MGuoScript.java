package com.auto.helper.one.tasks.script;

import android.view.View;
import android.webkit.WebView;

import com.auto.helper.one.automator.Script;
import com.auto.helper.one.automator.constanst.Package;
import com.auto.helper.one.automator.flow.Action;
import com.auto.helper.one.automator.flow.Flow;
import com.auto.helper.one.automator.node.UiNode;
import com.auto.helper.one.automator.selector.DescFilter;
import com.auto.helper.one.automator.selector.Filter;
import com.auto.helper.one.automator.selector.TextFilter;
import com.auto.helper.one.common.console.Console;
import com.auto.helper.one.common.util.RegexUtils;
import com.auto.helper.one.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.auto.helper.one.automator.constanst.Regex.TASK_FINISH;

public class MGuoScript extends Script {


    @Override
    public String name() {
        return "喵果任务";
    }

    @Override
    public boolean onExecute() {
        launchPackage(Package.APP_TAO_BAO);
        selector().packageName(Package.APP_TAO_BAO).desc("首页").untilFindOne().clickDeeply();
        selector().packageName(Package.APP_TAO_BAO).desc("双11喵果总动员").untilFindOne().clickDeeply();
        selector().packageName(Package.APP_TAO_BAO).text("天猫双11喵果总动员").className(WebView.class).waitFor();
        Console.d(getTag(), "进入天猫双11喵果总动员页面");
        closeDialog();
        clickTasks();
        doTasks();
        return true;
    }

    private void clickTasks() {
        do {
            selector().packageName(Package.APP_TAO_BAO).text("去赚能量").untilFindOne().clickDeeply();
        } while (selector().packageName(Package.APP_TAO_BAO).id("fissionOverlayPortal").findOne(1) == null);
    }

    private void closeDialog() {
        Flow.ifThat(selector().packageName(Package.APP_TAO_BAO).text("天猫双11喵果总动员").className(WebView.class).exists())
                .then(new Action() {
                          @Override
                          public void action() {
                              clickXY(537, 1720);
                              sleep(1);
                          }
                      }
                );
    }

    private void doTasks() {
        List<String> mExcludeTask = new ArrayList<>();
        //处理所有任务
        UiNode uiNode;
        do {
            uiNode = selector().packageName(Package.APP_TAO_BAO).text("立即领取").findOne(1);
            if (uiNode != null) {
                uiNode.clickDeeply();
                incrementAndGet();
                sleep(1);
            }
            uiNode = selector().packageName(Package.APP_TAO_BAO).text("签到得喵果(0/1)").findOne(1);
            if (uiNode != null) {
                uiNode.clickDeeply();
                incrementAndGet();
                sleep(1);
            }
            uiNode = selector().packageName(Package.APP_TAO_BAO).text("去签到领现金逛逛(0/1)").findOne(1);
            if (uiNode != null) {
                uiNode.clickDeeply();
                incrementAndGet();
                sleep(25);
                backToMg();
            }

            //浏览观看任务
            uiNode = selector().packageName(Package.APP_TAO_BAO)
                    .textMatches(".*\\(.*/.*\\)")
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
                    }).textExcludeAll(mExcludeTask)
                    .filter(new Filter() {
                        @Override
                        public boolean filter(UiNode uiNode) {
                            return StringUtils.hasTask(uiNode.getText());
                        }
                    }).findOne(1);
            if (uiNode != null) {
                uiNode.clickDeeply();
                incrementAndGet();
                sleep(2);
                if (!uiNode.getText().contains("旗舰店") && !uiNode.getText().contains("直播间")) {
                    scrollUp();
                }
                selector().packageName(Package.APP_TAO_BAO)
                        .filter(new Filter() {
                            @Override
                            public boolean filter(UiNode uiNode) {
                                Filter filter = TextFilter.textMatches(TASK_FINISH);
                                Filter descFilter = DescFilter.descMatches(TASK_FINISH);
                                return filter.filter(uiNode) || descFilter.filter(uiNode);
                            }
                        })
                        .textExclude("任务未完成")
                        .descExclude("任务未完成")
                        .className(View.class)
                        .findOne(20);
                backToMg();
                mExcludeTask.add(uiNode.getText());//避免页面没自动刷新，一直循环
            }
        } while (uiNode != null);
    }

    private void zfbFarm() {
    }


    private void backToMg() {
        while (!isMgPage()) {
            backAndWait();
        }
    }

    private boolean isMgPage() {
        return selector().packageName(Package.APP_TAO_BAO).text("天猫双11喵果总动员").className(WebView.class).exists();
    }
}
