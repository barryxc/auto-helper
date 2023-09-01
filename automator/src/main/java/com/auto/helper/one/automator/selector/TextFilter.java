package com.auto.helper.one.automator.selector;

import com.auto.helper.one.automator.node.UiNode;

public class TextFilter {

    private static final KeyGetter<CharSequence> mKeyGetter = new KeyGetter() {
        @Override
        public CharSequence getKey(UiNode node) {
            if (node != null) {
                return node.getText();
            }
            return null;
        }
    };


    public static StringFilter.EqualsFilter textEquals(String text) {
        return new StringFilter.EqualsFilter(mKeyGetter, text);
    }

    public static StringFilter.ContainsFilter textContains(String text) {
        return new StringFilter.ContainsFilter(mKeyGetter, text);
    }

    public static StringFilter.RegMatchFilter textMatches(String reg) {
        return new StringFilter.RegMatchFilter(mKeyGetter, reg);
    }

    public static StringFilter.ExcludeFilter textExclude(String reg) {
        return new StringFilter.ExcludeFilter(mKeyGetter, reg);
    }


}
