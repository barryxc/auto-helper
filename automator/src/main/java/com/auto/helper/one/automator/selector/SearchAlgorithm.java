package com.auto.helper.one.automator.selector;

import com.auto.helper.one.automator.node.UiNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public abstract class SearchAlgorithm {

    public abstract List<UiNode> search(UiNode root, Selector selector, int limit);

    public static final SearchAlgorithm BFS = new SearchAlgorithm() {
        @Override
        public List<UiNode> search(UiNode root, Selector selector, int limit) {
            List<UiNode> result = new ArrayList<>();
            return result;
        }
    };

    public static final SearchAlgorithm DFS = new SearchAlgorithm() {
        @Override
        public List<UiNode> search(UiNode root, Selector selector, int limit) {
            List<UiNode> result = new ArrayList<>();
            Stack<UiNode> stack = new Stack<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                UiNode parent = stack.pop();
                int childCount = parent.getChildCount();

                for (int i = 0; i < childCount; i++) {
                    UiNode child = parent.getChild(i);
                    if (child == null) continue;
                    stack.push(child);
                }

                if (selector.select(parent)) {
                    result.add(parent);
                    if (result.size() >= limit) {
                        break;
                    }
                }
            }
            return result;
        }
    };
}
