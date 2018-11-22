package com.eyesee.algorithms.search;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class QuickSearch {

    @Test
    public void testQuickSearch() {
        int[] arrays = {3, 5, 7, 9, 15, 23,  25, 89};
        int index = QuickSearch.search(arrays, 7);
        assertTrue(index == 2);
        index = search(arrays, 23);
        assertTrue(index == 5);

        int[] data = {2};
        int[] data1 = {2, 3};
        int[] data2 = {2, 3, 4};
        index = search(data, 3);
        assertTrue(index == -1);
        index = search(data1, 3);
        assertTrue(index == 1);
        index = search(data2, 3);
        assertTrue(index == 1);
        index = search(data2, 4);
        assertTrue(index == 2);
    }

    public static int search(int[] arrays, int value) {
        int length = arrays.length;
        return search(arrays, 0 , length - 1, value);
    }

    private static int search(int[] arrays, int from, int to, int value) {
//        if (from == to) {
//            return arrays[to] == value ? to : -1;
//        }
//        int mid = from + (to - from) / 2;
//        if (arrays[mid] > value) {
//            return search(arrays, from, mid - 1, value);
//        } else if (arrays[mid] < value) {
//            return search(arrays, mid + 1, to, value);
//        } else {
//            return mid;
//        }
        int low = from;
        int high = to;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            if (arrays[mid] > value) {
                high = mid - 1;
            } else if (arrays[mid] < value) {
                low = mid + 1;
            } else {
                return mid;
            }
        }

        return -1;
    }
}
