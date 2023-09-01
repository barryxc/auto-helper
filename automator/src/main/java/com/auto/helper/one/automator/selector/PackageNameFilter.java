package com.auto.helper.one.automator.selector;

import com.auto.helper.one.automator.node.UiNode;

public class PackageNameFilter {

    private static final KeyGetter<CharSequence> mKeyGetter = new KeyGetter() {
        @Override
        public CharSequence getKey(UiNode node) {
            if (node != null) {
                return node.getPackageName();
            }
            return "";
        }
    };

    public static StringFilter.EqualsFilter packageName(String packageName) {
        return new StringFilter.EqualsFilter(mKeyGetter, packageName);
    }
}
