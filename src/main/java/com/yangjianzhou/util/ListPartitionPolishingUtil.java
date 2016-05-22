package com.yangjianzhou.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by yangjianzhou on 16-4-11.
 */
public class ListPartitionPolishingUtil {


    public static List<List<String>> splitStringList(final List<String> originalList) {
        return splitAndFulfillToTargetSize(originalList, getRange(originalList.size()));
    }

    public static List<List<Integer>> splitIntegerList(final List<Integer> originalList) {
        return splitAndFulfillToTargetSize(originalList, getRange(originalList.size()));
    }

    public static List<List<Long>> splitLongList(final List<Long> originalList) {
        return splitAndFulfillToTargetSize(originalList, getRange(originalList.size()));
    }

    public static List<List<String>> splitStringList(final List<String> originalList, final int splitSize) {
        return splitAndFulfillToTargetSize(originalList, splitSize);
    }

    public static List<List<Integer>> splitIntegerList(final List<Integer> originalList, final int splitSize) {
        return splitAndFulfillToTargetSize(originalList, splitSize);
    }

    public static List<List<Long>> splitLongList(final List<Long> originalList, final int splitSize) {
        return splitAndFulfillToTargetSize(originalList, splitSize);
    }

    /**
     * 对所给的列表按照给定的分区大小进行分区
     * @param originalList
     * @param splitSize
     * @param <T>
     * @return
     */
    private static <T> List<List<T>> splitAndFulfillToTargetSize(final List<T> originalList, final int splitSize) {
        if (CollectionUtils.isEmpty(originalList)) {
            return Collections.emptyList();
        }

        int targetSplitSize = splitSize;
        if (targetSplitSize <= 0) {
            targetSplitSize = originalList.size();
        }

        // 分区
        List<List<T>> splitList = Lists.partition(Lists.<T> newLinkedList(Sets.newHashSet(originalList.iterator())),
                targetSplitSize);
        List<T> lastSubList = splitList.get(splitList.size() - 1);

        // 补齐
        int delta = targetSplitSize - lastSubList.size();
        if (delta > 0) {
            lastSubList.addAll(Collections.nCopies(delta, lastSubList.get(lastSubList.size() - 1)));
        }
        return splitList;
    }

    public static <T> List<T> fillToTargetSize(final List<T> originalList, final int splitSize) {
        int delta = splitSize - originalList.size();
        if (delta > 0) {
            originalList.addAll(Collections.nCopies(delta, originalList.get(originalList.size() - 1)));
        }
        return originalList;
    }

    @SuppressWarnings("unchecked")
    public static List<Set<KeyValueList>> partitionAndCombine(Map<String, List> paramsMap) {
        List<Set<KeyValueList>> paramsList = new LinkedList<Set<KeyValueList>>();

        for (Map.Entry<String, List> entry : paramsMap.entrySet()) {
            Set<KeyValueList> keyValueLists = new HashSet<KeyValueList>();
            List<List> splitedList = splitAndFulfillToTargetSize(entry.getValue(), getRange(entry.getValue().size()));
            for (List list : splitedList) {
                keyValueLists.add(new KeyValueList(entry.getKey(), list));
            }

            paramsList.add(keyValueLists);
        }

        return paramsList;
    }

    public static class KeyValueList {
        private String key;
        private List value;

        public KeyValueList(String key, List value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List getValue() {
            return value;
        }

        public void setValue(List value) {
            this.value = value;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("KeyValueList{");
            sb.append("key='").append(key).append('\'');
            sb.append(", value=").append(value);
            sb.append('}');
            return sb.toString();
        }
    }

    private static int getMaxRange() {
        return OptionRange.RANGE_500.getRange();
    }

    /**
     * <pre>
     * case 1 id in(1,2,3,4,5)  ==> 20
     * case 2 id in(1,2,3,4,5,6...31) and status in (1,2,3,4,5,6,7) ==>50
     * case 3 id in(1,2,3,4,5....600) and status in (1,2,3,4,5,6,7) ==>500
     * case 4 id in(1,2,3,4,5....1001) ==>500
     * </pre>
     */
    private static int getRange(int paramsListSize) {
        if (paramsListSize == 0) {
            return 0;
        }

        for (OptionRange range : OptionRange.values()) {
            if (range.in(paramsListSize)) {
                return range.getRange();
            }
        }

        if (getMaxRange() < paramsListSize) {
            return getMaxRange();
        }

        return paramsListSize;
    }

    private enum OptionRange {
        RANGE_20(0, 20), RANGE_30(21, 30), RANGE_100(31, 100), RANGE_300(101, 300), RANGE_500(301, 500);

        private int rangeStart;
        private int rangeEnd;

        public int getRange() {
            return this.rangeEnd;
        }

        public boolean in(int collectionSize) {
            return collectionSize >= rangeStart && collectionSize <= rangeEnd;
        }

        OptionRange(int rangeStart, int rangeEnd) {
            this.rangeStart = rangeStart;
            this.rangeEnd = rangeEnd;
        }
    }

}
