package com.auto.helper.one.automator.selector;

import android.accessibilityservice.AccessibilityService;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.auto.helper.one.automator.node.UiNode;
import com.auto.helper.one.common.keep.AutoKeep;

import java.util.ArrayList;
import java.util.List;


@AutoKeep
public class UiGlobalSelector extends Selector {
    private SearchAlgorithm mSearchAlgorithm = SearchAlgorithm.DFS;
    private final AccessibilityService mService;

    public UiGlobalSelector(AccessibilityService service) {
        this.mService = service;
    }

    public UiGlobalSelector(AccessibilityService service, String algorithm) {
        this.mService = service;
        setAlgorithm(algorithm);
    }

    private UiGlobalSelector setAlgorithm(String algorithm) {
        if (TextUtils.equals(algorithm, "BFS")) {
            mSearchAlgorithm = SearchAlgorithm.BFS;
            return this;
        }
        if (TextUtils.equals(algorithm, "DFS")) {
            mSearchAlgorithm = SearchAlgorithm.DFS;
            return this;
        }
        throw new IllegalArgumentException("not support setAlgorithm");
    }


    ///text，desc,id,packageName,className 匹配器
    public UiGlobalSelector id(String id) {
        addFilter(IDFilter.id(id));
        return this;
    }

    public UiGlobalSelector text(String text) {
        addFilter(TextFilter.textEquals(text));
        return this;
    }

    public UiGlobalSelector textContains(String text) {
        addFilter(TextFilter.textContains(text));
        return this;
    }

    public UiGlobalSelector textExclude(String exclude) {
        addFilter(TextFilter.textExclude(exclude));
        return this;
    }

    public UiGlobalSelector textExcludeAll(List<String> excludeList) {
        for (String exclude : excludeList) {
            addFilter(TextFilter.textExclude(exclude));
        }
        return this;
    }

    public UiGlobalSelector textMatches(String reg) {
        addFilter(TextFilter.textMatches(reg));
        return this;
    }


    public UiGlobalSelector desc(String desc) {
        addFilter(DescFilter.descEquals(desc));
        return this;
    }

    public UiGlobalSelector descContains(String desc) {
        addFilter(DescFilter.descContains(desc));
        return this;
    }

    public UiGlobalSelector descMatches(String reg) {
        addFilter(DescFilter.descMatches(reg));
        return this;
    }

    public UiGlobalSelector descExclude(String desc) {
        addFilter(DescFilter.descExclude(desc));
        return this;
    }

    public UiGlobalSelector packageName(String packageName) {
        addFilter(PackageNameFilter.packageName(packageName));
        return this;
    }

    public UiGlobalSelector className(String className) {
        addFilter(ClassNameFilter.className(className));
        return this;
    }

    public UiGlobalSelector className(Class<?> viewCls) {
        className(viewCls.getCanonicalName());
        return this;
    }

    //able匹配器
    public UiGlobalSelector isClickable(boolean value) {
        addFilter(AbleFilter.create(AbleFilter.isClickable, value));
        return this;
    }

    public UiGlobalSelector isLongClickable(boolean value) {
        addFilter(AbleFilter.create(AbleFilter.isLongClickable, value));
        return this;
    }

    public UiGlobalSelector isSelected(boolean value) {
        addFilter(AbleFilter.create(AbleFilter.isSelected, value));
        return this;
    }

    public UiGlobalSelector isEnabled(boolean value) {
        addFilter(AbleFilter.create(AbleFilter.isEnabled, value));
        return this;
    }

    public UiGlobalSelector isScrollable(boolean value) {
        addFilter(AbleFilter.create(AbleFilter.isScrollable, value));
        return this;
    }

    //自定义匹配器

    public UiGlobalSelector filter(Filter filter) {
        addFilter(filter);
        return this;
    }

    //int 匹配器

    public UiGlobalSelector index(int index) {
        addFilter(new IndexFilter(index));
        return this;
    }


    //bounds
    public UiGlobalSelector boundsContains(Rect rect) {
        addFilter(new BoundsFilter(rect));
        return this;
    }


    /////////////////    /////////////////    /////////////////    /////////////////


    public UiNode findOnce() {
        List<UiNode> resultList = findAndReturn(1);
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        }
        return null;
    }

    public boolean exists() {
        return findOnce() != null;
    }

    public List<UiNode> findAll() {
        return findAndReturn(Integer.MAX_VALUE);
    }

    private List<UiNode> getRootNode() {

        List<UiNode> windowRoots = new ArrayList<>();
        AccessibilityNodeInfo rootInActiveWindow = mService.getRootInActiveWindow();
        if (rootInActiveWindow != null) {
            windowRoots.add(new UiNode(rootInActiveWindow, mService, -1, null));
        }


        List<AccessibilityWindowInfo> windows = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            windows = mService.getWindows();
            if (windows != null) {
                for (AccessibilityWindowInfo window : windows) {
                    AccessibilityNodeInfo root = window.getRoot();
                    if (root == null) continue;
                    windowRoots.add(new UiNode(root, mService, -1, null));
                }
            }
        }
        return windowRoots;
    }

    public @Nullable
    UiNode findOne(float timeout) {
        long timeOutTimeMillis = (long) (timeout * 1000);

        UiNode uiNode;
        long startTime = System.currentTimeMillis();
        long costTime;
        while ((uiNode = findOnce()) == null) {
            costTime = (System.currentTimeMillis() - startTime);
            if (timeOutTimeMillis > 0 && costTime >= timeOutTimeMillis) {
                break;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return uiNode;
    }

    public @NonNull
    UiNode untilFindOne() {
        UiNode uiNode;
        while ((uiNode = findOnce()) == null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return uiNode;
    }

    public void waitFor() {
        untilFindOne();
    }


    private List<UiNode> findAndReturn(int limit) {
        List<UiNode> roots = getRootNode();
        List<UiNode> nodes = new ArrayList<>();
        for (UiNode root : roots) {
            List<UiNode> resultNodes = mSearchAlgorithm.search(root, this, limit);
            nodes.addAll(resultNodes);
        }
        return nodes;
    }
}
