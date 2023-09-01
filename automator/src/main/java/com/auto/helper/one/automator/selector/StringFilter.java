package com.auto.helper.one.automator.selector;

import android.text.TextUtils;

import com.auto.helper.one.automator.node.UiNode;

import java.util.regex.Pattern;

public abstract class StringFilter implements Filter {

    protected final KeyGetter<CharSequence> mKeyGetter;
    protected final String mText;

    public StringFilter(KeyGetter<CharSequence> keyGetter, String text) {
        mKeyGetter = keyGetter;
        mText = text;
    }

    static class EqualsFilter extends StringFilter {
        public EqualsFilter(KeyGetter<CharSequence> keyGetter, String text) {
            super(keyGetter, text);
        }

        @Override
        public boolean filter(UiNode uiNode) {
            CharSequence key = mKeyGetter.getKey(uiNode);
            return TextUtils.equals(key, mText);
        }
    }

    static class ContainsFilter extends StringFilter {

        public ContainsFilter(KeyGetter<CharSequence> keyGetter, String text) {
            super(keyGetter, text);
        }

        @Override
        public boolean filter(UiNode uiNode) {
            CharSequence key = mKeyGetter.getKey(uiNode);
            if (key == null) {
                return false;
            }
            return key.toString().contains(mText);
        }
    }

    static class RegMatchFilter extends StringFilter {

        public RegMatchFilter(KeyGetter<CharSequence> keyGetter, String reg) {
            super(keyGetter, reg);
        }

        @Override
        public boolean filter(UiNode uiNode) {
            CharSequence key = mKeyGetter.getKey(uiNode);
            if (key == null) {
                return false;
            }
            return Pattern.compile(mText).matcher(key).matches();
        }
    }

    static class ExcludeFilter extends StringFilter {

        public ExcludeFilter(KeyGetter<CharSequence> keyGetter, String text) {
            super(keyGetter, text);
        }

        @Override
        public boolean filter(UiNode uiNode) {
            CharSequence key = mKeyGetter.getKey(uiNode);
            if (key == null) {
                return false;
            }
            return !key.toString().contains(mText);
        }
    }
}
