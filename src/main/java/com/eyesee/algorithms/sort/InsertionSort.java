package com.eyesee.algorithms.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * The {@code InsertionSort} class represents .
 *
 * @author jessepi on 12/13/18
 */
public class InsertionSort {

    @Test
    public void testInsertionSort() {
        int[] arr1 = {5, 2, 4, 6, 1, 3};
        insertionSort(arr1);
        Arrays.stream(arr1).forEach(System.out :: println);
    }

    public void insertionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i ++) {
            if (arr[i + 1] > arr[i]) {
                continue;
            }
            for (int j = i + 1; j > 0; j --) {
                if (arr[j] < arr[j - 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                }
            }
        }
    }


    public void binaryInsertionSort(int[] arr) {
        if (arr.length == 1) {
            return;
        }

        for (int i = 0; i < arr.length - 1; i ++) {
            int left = 0;
            int right = i + 1;
            int pivot = arr[right];

            while (left < right) {
                int mid = (left + right) >>> 1;
                if (arr[mid] > pivot)
                    right = mid;
                else
                    left = mid + 1;
            }

            int n = i + 1 - left;
            System.arraycopy(arr, left, arr, left + 1, n);
            arr[left] = pivot;
        }
    }
}
