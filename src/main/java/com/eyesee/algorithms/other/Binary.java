package com.eyesee.algorithms.other;
import org.junit.Test;

import java.util.Stack;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The {@code Binary} class represents .
 *
 * @author jessepi on 9/14/18
 */
public class Binary {

    private static char[] array = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static String numStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    final static int N = array.length;

    final static char[] digits = {
            'A' , 'B' ,
            'C' , 'D' , 'E' , 'F' , 'G' , 'H' ,
            'I' , 'J' , 'K' , 'L' , 'M' , 'N' ,
            'O' , 'P' , 'Q' , 'R' , 'S' , 'T' ,
            'U' , 'V' , 'W' , 'X' , 'Y' , 'Z'
    };

    static String decode(long value) {
        long rest = value;
        Stack<Character> stack = new Stack<>();
        StringBuilder result = new StringBuilder(0);
        while (rest != 0) {
            stack.add(array[new Long((rest % N)).intValue()]);
            rest = rest / N;
        }
        while(!stack.isEmpty()) {
            result.append(stack.pop());
        }
        return result.length() == 0 ? String.valueOf(array[0]) : result.toString();
    }

    public String toString1(int i , int radix) {
        char[] buf = new char[33];
        int pos = 32;

        while (i >= radix) {
            buf[pos --] = digits[i%radix];
            i = i / radix;
        }
        buf[pos] = digits[i];
        return new String(buf, pos, 33 - pos);
    }

    static long encode(String value) {
        char ch[] = value.toCharArray();
        int len = ch.length;
        long result = 0;
        if (N == 10) {
            return Long.parseLong(value);
        }
        long base = 1;
        for (int i = len - 1; i >= 0; i--) {
            int index = numStr.indexOf(ch[i]);
            result += index * base;
            base *= N;
        }
        return result;

    }

    public static String toString(int i, int radix) {
        char buf[] = new char[33];
        int charPos = 32;

        while (i >= radix) {
            buf[charPos--] = digits[i % radix];
            i = i / radix;
        }
        buf[charPos] = digits[i];
        return new String(buf, charPos, (33 - charPos));
    }

    @Test
    public void testNumberToCharactor() {
//        System.out.println(        Integer.toString(25, 26));


//        String result = decode(0);
//        Assertions.assertThat(result).isEqualTo("A");

//        result = decode(52);
//        Assertions.assertThat(result).isEqualTo("BA");

        int n = 18;
        int count = 0;
        while (n > 0) {
            n = (n - 1) & n;
            count ++;
        }
//        10010
        System.out.println(18 & 15);
        System.out.println(count);

//        for (int i = 0; i < 26 * 26 + 1; i ++ ) {
//            System.out.println(toString1(i, 26).equals(decode(i)));
//        }
    }

    public static String numberToCharactor(int number) {
        char[] CHARACTORS = {'A', 'B', 'C', 'D', 'E', 'F', 'G' , 'H', 'I' , 'J', 'K', 'L', 'M', 'N', 'O',
                'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y' ,'Z'};
        //A 0   B 1
        //AA 26  AZ 51
        //1 * 26 + 0  1 * 26 + 25
        //BA 52  BZ 777
        //2 * 26 + 0   2 * 26 + 25
        //CA 79  CZ 104
        //3 * 26 + 1     3 * 26 + 26
        //DA     DZ
        //26 * 4 + 1     4 * 26 + 26
        //ZZ 26 * 26 + 26
        //AAA 26 * 26 * 26 + 1 * 26 + 1
        //AAB 26 * 26 * 26 + 1 * 26 + 2
        String result = "";
        int value = number;
        while (value > 0) {
            int mod = value & 26;
            result = CHARACTORS[mod] + result;
            value = value - 26;
        }





//        int value = number / 26;
//        String result = "";
//        while(value > 0) {
//            int mod = number / 26;
//            mod = (mod == 0) ? 26 : mod;
//            result = result + CHARACTORS[mod];
//        }

        return result;
    }

    @Test
    public void testNumberOf1() {
        int result = numberOf1(2);
        assertThat(result).isEqualTo(1);
        System.out.println(Integer.toHexString(31).toUpperCase());
        System.out.println(Integer.numberOfLeadingZeros(2));

        System.out.println(Integer.toString(1, 26).toUpperCase());
    }

    public static int numberOf1(int n) {
        int count = 0;
        while (n > 0) {
            if ((n & 1) == 1) {
                count ++;
            }
            n = n >>> 1;
        }
        return count;
    }
}
