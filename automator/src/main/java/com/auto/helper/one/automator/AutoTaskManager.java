package com.auto.helper.one.automator;

import com.auto.helper.one.common.keep.AutoKeep;

import java.util.ArrayList;
import java.util.List;

@AutoKeep
public class AutoTaskManager {

    private static final List<BaseAutoTask> M_BASE_AUTO_CASES = new ArrayList<>();

    public static List<? extends AutoTask> getAllCases() {
        return M_BASE_AUTO_CASES;
    }

    public static void register(BaseAutoTask baseAutoCase) {
        M_BASE_AUTO_CASES.add(baseAutoCase);
    }

    public static void unRegister(BaseAutoTask baseAutoCase) {
        M_BASE_AUTO_CASES.remove(baseAutoCase);
    }
}
