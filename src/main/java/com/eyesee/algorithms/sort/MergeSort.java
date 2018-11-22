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
        int[] to = new int[from.length];
        new MergeSort().merge(from, to, 0, 2, from.length - 1);

        Arrays.stream(to).forEach(System.out :: println);
    }

    @Test
    public void testMerge1() {
        int[] from = {1 , 2, 3, 4, 6, 97, 98, 53, 60};
        int[] to = new int[from.length];
        new MergeSort().merge(from, to, 0, 6, from.length - 1);

        Arrays.stream(to).forEach(System.out :: println);
    }

    /**
     *
     *
     * @param from
     * @param to
     * @param l the begin position for from and to
     * @param m the mergeFrom1 end position
     * @param n the merge from2 end position
     */
    public void merge(int[] from, int[] to, final int l, final int m, final int n) {
        int toPosition = l;
        int firstPosition = l;
        int firstEndPosition = m;
        int secondPosition = m + 1;
        int secondEndPosition = n;

        while (toPosition <= n) {
            if (firstPosition <= firstEndPosition && secondPosition <= secondEndPosition) {
                if (from[firstPosition] > (from[secondPosition])) {
                    to[toPosition ++] = from[secondPosition ++];
                } else {
                    to[toPosition ++] = from[firstPosition ++];
                }
            } else if (firstPosition <= firstEndPosition) {
                to[toPosition ++] = from[firstPosition ++];
            } else if (secondPosition <= secondEndPosition) {
                to[toPosition ++] = from[secondPosition];
            }
        }
    }
}
