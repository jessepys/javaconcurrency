package com.eyesee.algorithms.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The {@code MedianOfTwoSortedArrays4} class represents .
 *
 * @author jessepi on 11/30/18
 */
public class MedianOfTwoSortedArrays4 {
    @Test
    public void testFindMedianSortedArrays() {
        int[] nums1 = {1, 3};
        int[] nums2 = {2};
        assertThat(findMedianSortedArrays(nums1, nums2)).isEqualTo(2.0);

        int[] nums11 = {1, 2};
        int[] nums22 = {3, 4};
        assertThat(findMedianSortedArrays(nums11, nums22)).isEqualTo(2.5);
    }

    /**
     *
     * 1, 4, 6, 8, 20
     * 7, 9, 80
     * @param nums1
     * @param nums2
     * @return
     */

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m1 = nums1.length;
        int m2 = nums2.length;

        int k1 = 0;
        int k2 = 0;
        int[] arr = new int[m1 + m2];

        int i = 0;
        while(k1 < m1 && k2 < m2) {
            if (nums1[k1] < nums2[k2]) {
                arr[i] = nums1[k1];
                k1 ++;
            } else {
                arr[i] = nums2[k2];
                k2 ++;
            }
            i ++;
        }

        if (k1 >= m1) {
            while(k2 < m2) {
                arr[i] = nums2[k2];
                k2 ++;
                i ++;
            }
        }

        if (k2 >= m2) {
            while(k1 < m1) {
                arr[i] = nums1[k1];
                k1 ++;
                i ++;
            }
        }

        int midMod = (m1 + m2) % 2;
        int mid = (m1 + m2) /2;
        if (midMod == 0) {
            return (arr[mid] + arr[mid - 1]) / 2.0;
        } else {
            return arr[mid];
        }
    }
}
