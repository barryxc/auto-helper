package com.auto.helper.one.automator.selector;

import com.auto.helper.one.automator.node.UiNode;

public abstract class IntFilter implements Filter {

    protected final int mIntValue;
    protected final KeyGetter<Integer> mIntegerKeyGetter;

    public IntFilter(KeyGetter<Integer> integerKeyGetter, int intValue) {
        mIntegerKeyGetter = integerKeyGetter;
        this.mIntValue = intValue;
    }


    @Override
    public boolean filter(UiNode uiNode) {
        return mIntegerKeyGetter.getKey(uiNode) == mIntValue;
    }
}
