package com.eyesee.algorithms.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * The {@code QuickSort} class represents .
 *
 * @author jessepi on 11/24/18
 */
public class QuickSort {

    @Test
    public void testQuickSort() {
        int[] from = {1, 23, 3, 4, 8, 9, 7};
        quickSort(from, 0 , from.length - 1);
        Arrays.stream(from).forEach(System.out :: println);
    }

//    public void quickSort(int[] array) {
//        sort(array, 0, array.length - 1);
//    }

    public void quickSort(int[] array, int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return;
        }
        int pivot = partition(array, startIndex, endIndex, array[endIndex]);
        quickSort(array, startIndex, pivot - 1);
        quickSort(array, pivot + 1, endIndex);
    }

    private int partition(int[] array, int startIndex, int endIndex, int pivotValue) {
        int fromIndex = startIndex - 1;
        int toIndex = endIndex;
        while (true) {
            while (array[++fromIndex] < pivotValue);
            while (array[--toIndex] > pivotValue);
            if (fromIndex >= toIndex) {
                break;
            } else {
                swap(array, fromIndex, toIndex);
            }
        }
        swap(array, fromIndex, endIndex);
        return fromIndex;
    }

    private void swap(int[] array, int from, int to) {
        int temp = array[to];
        array[to] = array[from];
        array[from] = temp;
    }

}
