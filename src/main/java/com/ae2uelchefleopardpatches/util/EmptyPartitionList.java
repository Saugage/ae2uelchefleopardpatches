package com.ae2uelchefleopardpatches.util;

import appeng.api.storage.data.IAEStack;
import appeng.util.prioritylist.DefaultPriorityList;

public final class EmptyPartitionList {

    private static final DefaultPriorityList<?> INSTANCE = new DefaultPriorityList<>();

    private EmptyPartitionList() {}

    @SuppressWarnings("unchecked")
    public static <T extends IAEStack<T>> DefaultPriorityList<T> get() {
        return (DefaultPriorityList<T>) INSTANCE;
    }
}
