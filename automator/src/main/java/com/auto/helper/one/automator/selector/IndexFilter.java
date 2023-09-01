package com.auto.helper.one.automator.selector;

import com.auto.helper.one.automator.node.UiNode;

public class IndexFilter extends IntFilter {

    private final static KeyGetter<Integer> mIndexGetter = new KeyGetter<Integer>() {
        @Override
        public Integer getKey(UiNode node) {
            return node.getIndexInParent();
        }
    };

    public IndexFilter(int intValue) {
        super(mIndexGetter, intValue);
    }
}
