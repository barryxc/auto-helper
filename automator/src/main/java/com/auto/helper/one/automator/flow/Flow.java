package com.auto.helper.one.automator.flow;

import com.auto.helper.one.common.keep.AutoKeep;

import java.util.ArrayList;
import java.util.List;

@AutoKeep
public class Flow {
    private final List<Condition> mConditions = new ArrayList<>();

    public Flow() {
    }

    public static Flow ifThat(boolean condition) {
        Flow flow = new Flow();
        flow.mConditions.add(new Condition() {
            @Override
            public boolean predict() {
                return condition;
            }
        });
        return flow;
    }

    public static Flow ifThat(Condition condition) {
        Flow flow = new Flow();
        flow.mConditions.add(condition);
        return flow;
    }

    public Flow and(boolean condition) {
        mConditions.add(new Condition() {
            @Override
            public boolean predict() {
                return condition;
            }
        });
        return this;
    }

    public void then(Action action) {
        for (Condition condition : mConditions) {
            if (!condition.predict()) {
                return;
            }
        }
        action.action();
    }
}
