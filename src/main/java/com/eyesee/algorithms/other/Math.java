package com.eyesee.algorithms.other;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The {@code Math} class represents .
 *
 * @author jessepi on 9/28/18
 */
public class Math {

    @Test
    public void testPower() {
        double expected = 9;
        double result = power(3, 2);
        assertThat(result).isEqualTo(expected);
        result = power(3, 1);
        assertThat(result).isEqualTo(3);
        assertThat(power(3, 3)).isEqualTo(27);
        assertThat(power(3, 0)).isEqualTo(1);
        assertThat(power(3, -3)).isEqualTo(1.0/27);
    }

    public static double power(double base, int exponent) {
        double result = 0;
        if (exponent == 0) {
            return 1;
        } else if (exponent < 0) {
            result = 1.0 / base;
            exponent = exponent * -1;
            while (exponent > 1) {
                result = result * ( 1.0 / base);
                exponent --;
            }
        } else if (exponent > 0) {
            result = base;
            while (exponent > 1) {
                result = result * base;
                exponent --;
            }
        }
        return result;
    }
}
