package com.auto.helper.one.automator.selector;

import com.auto.helper.one.automator.node.UiNode;

public class AbleFilter {

    public static final KeyGetter<Boolean> isClickable = new KeyGetter<Boolean>() {
        @Override
        public Boolean getKey(UiNode node) {
            return node.isClickable();
        }
    };
    public static final KeyGetter<Boolean> isLongClickable = new KeyGetter<Boolean>() {
        @Override
        public Boolean getKey(UiNode node) {
            return node.isLongClickable();
        }
    };


    public static final KeyGetter<Boolean> isSelected = new KeyGetter<Boolean>() {
        @Override
        public Boolean getKey(UiNode node) {
            return node.isSelected();
        }
    };

    public static final KeyGetter<Boolean> isEnabled = new KeyGetter<Boolean>() {
        @Override
        public Boolean getKey(UiNode node) {
            return node.isEnabled();
        }
    };
    public static final KeyGetter<Boolean> isScrollable = new KeyGetter<Boolean>() {
        @Override
        public Boolean getKey(UiNode node) {
            return node.isScrollable();
        }
    };


    public static Filter create(KeyGetter<Boolean> keyGetter, boolean clickable) {
        return new Filter() {
            @Override
            public boolean filter(UiNode uiNode) {
                return keyGetter.getKey(uiNode) == clickable;
            }
        };
    }
}
