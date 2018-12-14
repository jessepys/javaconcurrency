package com.eyesee.algorithms.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * The {@code MergeSort} class represents .
 *
 * @author jessepi on 9/15/18
 */
public class MergeSort {

    @Test
    public void testMerge() {
        int[] from = {8,9,10,1,2,3,4,6};
        new MergeSort().merge(from, 0, 2, from.length - 1);

        Arrays.stream(from).forEach(System.out :: println);
    }

    @Test
    public void testMerge1() {
        int[] from = {1 , 2, 3, 4, 6, 97, 98, 53, 60};
        new MergeSort().merge(from, 0, 6, from.length - 1);

        Arrays.stream(from).forEach(System.out :: println);
    }

    @Test
    public void testMergeSort() {
        int[] from = {1, 23, 3, 4, 8, 9, 7};
        mergeSort(from, 0, from.length - 1);
        Arrays.stream(from).forEach(System.out :: println);
    }


    public void mergeSort(int[] from, int start, int end) {
        if (start >= end) {
            return;
        }

        int mid = (end + start) / 2;
        mergeSort(from, start, mid);
        mergeSort(from, mid + 1, end);
        merge(from, start, mid, end);
    }

    /**
     *
     *
     * @param from
     * @param low the begin position for from and to
     * @param mid the mergeFrom1 end position
     * @param high the merge from2 end position
     */
    public void merge(int[] from, final int low, final int mid, final int high) {
        int firstPosition = low;
        int firstEndPosition = mid;
        int secondPosition = mid + 1;
        int secondEndPosition = high;

        int length = high - low + 1;
        int[] to = new int[high - low + 1];
        int i = 0;

        while (i <= length - 1) {
            if (firstPosition <= firstEndPosition && secondPosition <= secondEndPosition) {
                if (from[firstPosition] > (from[secondPosition])) {
                    to[i ++] = from[secondPosition ++];
                } else {
                    to[i ++] = from[firstPosition ++];
                }
            } else if (firstPosition <= firstEndPosition) {
                to[i ++] = from[firstPosition ++];
            } else if (secondPosition <= secondEndPosition) {
                to[i ++] = from[secondPosition];
            }
        }
        System.arraycopy(to, 0, from, low, length);
    }
}
