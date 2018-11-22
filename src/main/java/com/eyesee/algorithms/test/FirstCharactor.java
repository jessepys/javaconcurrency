package com.eyesee.algorithms.test;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The {@code FirstCharactor} class represents .
 *
 * @author jessepi on 11/21/18
 */
public class FirstCharactor {

    @Test
    public void testGetFirstCharactor() {
        String test = "absdda";

        System.out.println((int)'A');
        char result = getFirstCharactor(test);
        assertThat(result).isEqualTo('b');
    }


    @Test
    public void testGetFirstCharactorChinese() {
        String test = "absdda中国繁体";

        System.out.println((int)'A');
        char result = getFirstCharactor(test);
        assertThat(result).isEqualTo('b');
    }


    public char getFirstCharactor(String value) {
        int[] table = new int[256];
        if (value == null || value.length() == 0) {
            return 0;
        }

        for (int i = 0; i < value.length(); i ++) {
//            table[value.charAt(i)] = table[value.charAt(i)] + 1;
            System.out.println(value.charAt(i));
        }

        for (int i = 0; i < value.length(); i ++) {
            if (table[value.charAt(i)] == 1) {
                return value.charAt(i);
            }
        }
        return 0;
    }
}
