package com.eyesee.algorithms.search;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The {@code ArraySearch} class represents .
 *
 * @author jessepi on 11/26/18
 */
public class ArraySearch {

    @Test
    public void testArraySearch() {
        int[][] array = {{1, 2, 8, 9}, {2, 4, 9, 12}, {4, 7, 10, 13}, {6, 8, 11, 15}};
        assertThat(search(array, 5)).isFalse();
        assertThat(search(array, 2)).isTrue();
        assertThat(search(array, 12)).isTrue();
        assertThat(search(array, 3)).isFalse();
    }

    public boolean search(int[][] array, int value) {
        if (null == array) {
            return false;
        }
        int xLength = array[0].length;
        int yLength = array.length;

        return search(array, xLength, yLength, 0, 0, value);
    }

    private boolean search(int[][] array, int xLength, int yLength,  int i , int j, int value) {
        if (i >= yLength || j >= xLength) {
            return false;
        }

        if (array[i][j] == value) {
            return true;
        }

        if (array[i][j] > value) {
            return false;
        }

        if (search(array, xLength, yLength, i, j + 1, value)
                || search(array, xLength, yLength, i + 1, j, value)) {
            return true;
        }

        return false;
    }
}
