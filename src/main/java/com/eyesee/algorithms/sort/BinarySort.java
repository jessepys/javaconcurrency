package com.eyesee.algorithms.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * The {@code BinarySort} class represents .
 *
 * @author jessepi on 12/3/18
 */
public class BinarySort {
    @Test
    public void testBinarySort() {
        int[] arrays = {1, 2, 3, 4, 9, 7};
        binarySort(arrays, 0, arrays.length - 1, 4);
        Arrays.stream(arrays).forEach(System.out::println);
    }

    public int binarySearch(int[] a, int key) {
        int low = 0;
        int high = a.length -1;

        while (low <= high) {
            int mid = (low + high) >> 1;

            if (key > a[mid]) {
                low = mid + 1;
            } else if (key == a[mid]) {
                return mid;
            } else {
                high = mid -1;
            }
        }

        return -1;
    }


    public void binarySort(int[] a, int lo, int hi, int start) {
        assert lo <= start && start <= hi;
        if (start == lo)
            start++;
        for (; start < hi; start++) {
            int pivot = a[start];

            // Set left (and right) to the index where a[start] (pivot) belongs
            int left = lo;
            int right = start;
            assert left <= right;
            /*
             * Invariants:
             *   pivot >= all in [lo, left).
             *   pivot <  all in [right, start).
             */
            while (left < right) {
                int mid = (left + right) >>> 1;
                if (pivot < a[mid])
                    right = mid;
                else
                    left = mid + 1;
            }
            assert left == right;

            /*
             * The invariants still hold: pivot >= all in [lo, left) and
             * pivot < all in [left, start), so pivot belongs at left.  Note
             * that if there are elements equal to pivot, left points to the
             * first slot after them -- that's why this sort is stable.
             * Slide elements over to make room for pivot.
             */
            int n = start - left;  // The number of elements to move
            // Switch is just an optimization for arraycopy in default case
            switch (n) {
                case 2:
                    a[left + 2] = a[left + 1];
                case 1:
                    a[left + 1] = a[left];
                    break;
                default:
                    System.arraycopy(a, left, a, left + 1, n);
            }
            a[left] = pivot;
        }
    }
}
