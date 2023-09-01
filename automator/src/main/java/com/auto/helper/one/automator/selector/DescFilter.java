package com.auto.helper.one.automator.selector;

import com.auto.helper.one.automator.node.UiNode;

public class DescFilter {

    private static final KeyGetter<CharSequence> mKeyGetter = new KeyGetter() {
        @Override
        public CharSequence getKey(UiNode node) {
            if (node != null) {
                return node.getContentDescription();
            }
            return null;
        }
    };

    public static StringFilter.EqualsFilter descEquals(String text) {
        return new StringFilter.EqualsFilter(mKeyGetter, text);
    }

    public static StringFilter.ContainsFilter descContains(String text) {
        return new StringFilter.ContainsFilter(mKeyGetter, text);
    }

    public static StringFilter.RegMatchFilter descMatches(String reg) {
        return new StringFilter.RegMatchFilter(mKeyGetter, reg);
    }


    public static StringFilter.ExcludeFilter descExclude(String reg) {
        return new StringFilter.ExcludeFilter(mKeyGetter, reg);
    }
}
