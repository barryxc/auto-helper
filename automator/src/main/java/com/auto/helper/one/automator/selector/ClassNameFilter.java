package com.auto.helper.one.automator.selector;

import com.auto.helper.one.automator.node.UiNode;

public class ClassNameFilter {

    private static final KeyGetter<CharSequence> mKeyGetter = new KeyGetter<CharSequence>() {
        @Override
        public CharSequence getKey(UiNode node) {
            if (node != null) {
                return node.getClassName();
            }
            return null;
        }
    };

    public static StringFilter.EqualsFilter className(String name) {
        return new StringFilter.EqualsFilter(mKeyGetter, name);
    }
}
