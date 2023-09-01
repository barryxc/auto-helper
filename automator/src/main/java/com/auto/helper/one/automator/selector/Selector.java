package com.auto.helper.one.automator.selector;

import com.auto.helper.one.automator.node.UiNode;

import java.util.LinkedList;

public abstract class Selector implements Select<UiNode> {
    protected final LinkedList<Filter> mFilters = new LinkedList<>();

    public Selector() {
    }

    @Override
    public boolean select(UiNode node) {
        for (Filter filter : mFilters) {
            if (!filter.filter(node)) {
                return false;
            }
        }
        return true;
    }

    protected void addFilter(Filter filter) {
        mFilters.add(filter);
    }
}
