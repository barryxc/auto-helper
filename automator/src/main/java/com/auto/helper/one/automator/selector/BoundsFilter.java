package com.auto.helper.one.automator.selector;

import android.graphics.Rect;

import com.auto.helper.one.automator.node.UiNode;

public class BoundsFilter implements Filter {

    private static final KeyGetter<Rect> mRectGetter = new KeyGetter<Rect>() {
        @Override
        public Rect getKey(UiNode node) {
            return node.getBoundsInScreen();
        }
    };

    protected Rect mRect;

    public BoundsFilter(Rect rect) {
        this.mRect = rect;

    }

    @Override
    public boolean filter(UiNode uiNode) {
        return mRectGetter.getKey(uiNode).contains(mRect);
    }
}
