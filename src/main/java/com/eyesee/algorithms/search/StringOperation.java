package com.eyesee.algorithms.search;

import com.eyesee.algorithms.annotation.Solution;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The {@code StringOperation} class represents .
 *
 * @author jessepi on 11/26/18
 */
public class StringOperation {

    @Test
    public void testReplaceEmpty() {
        String value = "this is a test";
        String expected = "this%20is%20a%20test";

        assertThat(replaceEmpty(value)).isEqualTo(expected);
        assertThat(replaceEmpty2(value)).isEqualTo(expected);
    }

    @Solution(recommend = true)
    public String replaceEmpty2(String value) {
        char[] values = value.toCharArray();
        int emptyCount = 0;
        for (int i = 0; i < values.length; i ++) {
            if (values[i] == ' ') {
                emptyCount ++;
            }
        }
        char[] results = new char[values.length + 2 * emptyCount];
        int resultIndex = results.length - 1;
        for (int j = values.length - 1; j >= 0; j --) {
            if (values[j] == ' ') {
                results[resultIndex] = '0';
                results[resultIndex - 1] = '2';
                results[resultIndex - 2] = '%';
                resultIndex = resultIndex - 3;
            } else {
                results[resultIndex] = values[j];
                resultIndex --;
            }
        }

        return new String(results);
    }

    @Solution(easy = true)
    public String replaceEmpty(String value) {
        if (null == value) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < value.length(); i ++) {
            char temp = value.charAt(i);
            if (temp == ' ') {
                stringBuilder.append("%20");
            } else {
                stringBuilder.append(temp);
            }
        }
        return stringBuilder.toString();
    }
}
