package com.auto.helper.one.automator.node;

import android.accessibilityservice.AccessibilityService;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.NonNull;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import com.auto.helper.one.automator.auto.GlobalAutomator;
import com.auto.helper.one.common.console.Console;

public class UiNode {

    private static final String TAG = UiNode.class.getSimpleName();
    private final AccessibilityNodeInfoCompat mNodeInfoCompat;
    private final AccessibilityService mService;
    private final GlobalAutomator mGlobalAutomator;
    private final UiNode mParent;
    private int mIndexInParent = -1;//在父节点的的索引位置

    public UiNode(AccessibilityNodeInfo nodeInfo, AccessibilityService service, int indexInParent, UiNode parent) {
        mNodeInfoCompat = AccessibilityNodeInfoCompat.wrap(nodeInfo);
        mService = service;
        mGlobalAutomator = new GlobalAutomator(mService);
        mIndexInParent = indexInParent;
        mParent = parent;
    }


    public @NonNull
    String getText() {
        return mNodeInfoCompat.getText() != null ? mNodeInfoCompat.getText().toString() : "";
    }

    public CharSequence getContentDescription() {
        return mNodeInfoCompat.getContentDescription();
    }

    public CharSequence getViewIdResourceName() {
        return mNodeInfoCompat.getViewIdResourceName();
    }

    public CharSequence getClassName() {
        return mNodeInfoCompat.getClassName();
    }

    /**
     * 基于控件点击
     **/
    public boolean click() {
        if (isClickable()) {
            return mNodeInfoCompat.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
        return false;
    }

    public boolean clickDeeply() {
        Console.d(TAG, "点击" + (TextUtils.isEmpty(getText()) ? getContentDescription() : getText()));
        return doClickUntil(this);
    }

    private boolean doClickUntil(UiNode uiNode) {
        if (uiNode == null)
            return false;
        if (uiNode.isClickable()) {
            return uiNode.click();
        }

        UiNode parent = uiNode.getParent();
        if (parent == null) {
            return false;
        }
        if (parent.isClickable()) {
            return parent.click();
        }

        return doClickUntil(parent.getParent());
    }

    /**
     * 基于坐标点击
     **/
    public boolean clickXY(int x, int y) {
        return mGlobalAutomator.clickXY(x, y);
    }

    public boolean clickCenter() {
        return clickXY(this.getBoundsInScreen().centerX(), this.getBoundsInScreen().centerY());
    }

    public boolean isVisibleToUser() {
        return mNodeInfoCompat.isVisibleToUser();
    }

    public Rect getBoundsInScreen() {
        Rect rect = new Rect();
        mNodeInfoCompat.getBoundsInScreen(rect);
        return rect;
    }

    public int getChildCount() {
        return mNodeInfoCompat.getChildCount();
    }

    public UiNode getChild(int index) {
        AccessibilityNodeInfoCompat child = mNodeInfoCompat.getChild(index);
        if (child != null) {
            return new UiNode(child.unwrap(), mService, index, this);
        }
        return null;
    }

    public UiNode nextSibling(int next) {
        AccessibilityNodeInfoCompat parent = mNodeInfoCompat.getParent();
        int siblingIndex = mIndexInParent + next;
        if (parent != null && siblingIndex >= 0 && parent.getChildCount() > siblingIndex) {
            AccessibilityNodeInfoCompat sibling = parent.getChild(siblingIndex);
            if (sibling != null) {
                return new UiNode(sibling.unwrap(), mService, siblingIndex, this.getParent());
            }
        }
        return null;
    }


    public UiNode getParent() {
        AccessibilityNodeInfoCompat parent = mNodeInfoCompat.getParent();
        if (parent != null) {
            return new UiNode(parent.unwrap(), mService, -1, mParent);
        }
        return null;
    }

    public int getIndexInParent() {
        return mIndexInParent;
    }

    public boolean isView(Class<?> viewCls) {
        return TextUtils.equals(mNodeInfoCompat.getClassName(), viewCls.getCanonicalName());
    }

    public CharSequence getPackageName() {
        return mNodeInfoCompat.getPackageName();
    }

    public void inputText(String input) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Bundle arguments = new Bundle();
            arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, input);
            mNodeInfoCompat.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
        }
    }

    public void recycle() {
        mNodeInfoCompat.recycle();
    }

    public boolean isClickable() {
        return mNodeInfoCompat.isClickable();
    }

    public boolean isLongClickable() {
        return mNodeInfoCompat.isLongClickable();
    }

    public boolean isEnabled() {
        return mNodeInfoCompat.isEnabled();
    }

    public boolean isScrollable() {
        return mNodeInfoCompat.isScrollable();
    }

    public boolean isSelected() {
        return mNodeInfoCompat.isScrollable();
    }

    public boolean equals(UiNode uiNode) {
        return mNodeInfoCompat.equals(uiNode.mNodeInfoCompat);
    }

    public boolean dismiss() {
        if (mNodeInfoCompat.isDismissable()) {
            return mNodeInfoCompat.performAction(AccessibilityNodeInfoCompat.ACTION_DISMISS);
        }
        return false;
    }
}
