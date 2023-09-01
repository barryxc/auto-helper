package com.auto.helper.one.automator.selector;

import com.auto.helper.one.automator.node.UiNode;

public class IDFilter {

    private static final KeyGetter<CharSequence> mKeyGetter = new KeyGetter() {
        @Override
        public CharSequence getKey(UiNode node) {
            if (node != null) {
                return node.getViewIdResourceName();
            }
            return null;
        }
    };

    public static StringFilter.EqualsFilter id(String id) {
        return new StringFilter.EqualsFilter(mKeyGetter, id);
    }
}
