package com.auto.helper.one.automator.selector;

import com.auto.helper.one.automator.node.UiNode;

public interface KeyGetter<T> {

    T getKey(UiNode node);
}
