package com.eyesee.algorithms.leetcode;

import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The {@code ArraysSolution} class represents .
 *
 * @author jessepi on 10/7/18
 */
public class ArraysSolution {
    //[1, 3, 5]
    // [0,0,1,1,1,2,2,3,3,4]

    @Test
    public void testRemoveDuplicate() {
        ArraysSolution arraysSolution = new ArraysSolution();
        int[] tests = {1, 3, 5};

        int result = arraysSolution.removeDuplicates(tests);

        System.out.println(result);
        Arrays.stream(tests).forEach(System.out::println);
    }

    @Test
    public void testMaxProfit() {
        int[] tests = {7,1,5,3,6,4};
        ArraysSolution arraysSolution = new ArraysSolution();
        int result = arraysSolution.maxProfit(tests);
        int expected = 7;
        assertThat(result).isEqualTo(expected);
    }


    @Test
    public void testRotate() {
        int[] tests = {1, 2, 3, 4, 5, 6};
        int k = 2;  //{5, 6, 7, 1, 2, 3, 4};
        ArraysSolution arraysSolution = new ArraysSolution();
        arraysSolution.rotate(tests, k);

        int[] expected = {5, 6, 1, 2, 3, 4};
        assertThat(tests).contains(expected);
        Arrays.stream(tests).forEach(System.out :: println);
    }

    @Test
    public void testContainsDuplicate() {
        int[] tests = {1,2,3,4};

        ArraysSolution arraysSolution = new ArraysSolution();
        boolean result = arraysSolution.containsDuplicate(tests);
        assertThat(result).isFalse();

        int[] tests1 = {1,2,3,1};
        result = arraysSolution.containsDuplicate(tests1);
        assertThat(result).isTrue();
    }

    public boolean containsDuplicate(int[] nums) {
        Set<Integer> sets = new HashSet<>(nums.length);
        for (int i = 0; i < nums.length; i ++) {
            if (sets.contains(nums[i])) {
                return true;
            } else {
                sets.add(nums[i]);
            }
        }

        return false;
    }

    @Test
    public void testSingleNumber() {
        int[] nums = {2,2,1};
        int result = singleNumber(nums);
        assertThat(result).isEqualTo(1);
    }


    public int singleNumber(int[] nums) {
        Set<Integer> sets = new HashSet<>();

        for (int i = 0; i < nums.length; i ++) {
            if (sets.contains(nums[i])) {
                sets.remove(nums[i]);
            } else {
                sets.add(nums[i]);
            }
        }

        return sets.iterator().next();
    }

    @Test
    public void testIntersect() {
        int[] nums1 = {1,2,2,1};
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        int[] nums2 = {2,2};
        int[] result = intersect(nums2, nums1);
        System.out.println(result);
//        assertThat(result).contains(2,2);
        for (int num : result) {
            System.out.println(num);
            hashMap.put(num, num);
        }
        System.out.println(hashMap.keySet());
    }

//    f(2) = 2
//    f(3) = 3
//    n = o, f(n) = f(n - 1) + f (n - 2) - 1
//    n = j, f(n) = = f(n-1)  + f(n-2)

    @Test
    public void testClimbStairs() {
        int result = climbStairs(5);
        assertThat(result).isEqualTo(8);

        assertThat(climbStairs(4)).isEqualTo(5);
        assertThat(climbStairs(6)).isEqualTo(13);
    }

    public int climbStairs(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;
        if (n == 3) return 3;

        int i = 4;
        int result = 0;
        int evenNumber = 2;
        int oddNumber = 3;

        while ( i <= n) {
            if (i % 2 == 0) {
                result  = oddNumber + evenNumber;
                evenNumber = result;
            } else {
                result  = oddNumber + evenNumber;
                oddNumber = result;
            }
            i ++;
        }

        return result;
    }


    public int[] intersect(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> map = new HashMap<>();
        ArrayList<Integer> result = new ArrayList<>();
        for(int i = 0; i < nums1.length; i++)
        {
            map.put(nums1[i], map.getOrDefault(nums1[i], 0) + 1);
        }

        for(int i = 0; i < nums2.length; i++)
        {
            if(map.containsKey(nums2[i]) && map.get(nums2[i]) > 0)
            {
                result.add(nums2[i]);
                map.put(nums2[i], map.get(nums2[i])-1);
            }
        }

        int[] r = new int[result.size()];
        for(int i = 0; i < result.size(); i++)
        {
            r[i] = result.get(i);
        }

        return r;
    }

    private boolean quickSort(int[] nums, int from , int end) {
        if (from <= end) {
            return false;
        }

        int pivot = nums[end];
        int index = partition(nums, from, end, pivot);
        if (index == -1) {
            return true;
        }
        quickSort(nums, from, index - 1);
        quickSort(nums, index + 1, end);
        return false;
    }

    private int partition(int[] nums, int from, int end, int pivot) {
        int low = from - 1;
        int high = end;

        while (true) {
            while (nums[++low] < pivot);
            while (nums[--high] > pivot);

            if (low >= high) {
                break;
            } else {
                swap(nums, low, high);
            }
        }
        swap(nums, low, end);
        return low;
    }



    public void rotate(int[] nums, int k) {
        int rotateSize = k % nums.length;
        rotate(nums, 0, nums.length - 1);
        rotate(nums, 0, rotateSize - 1);
        rotate(nums, rotateSize, nums.length - 1);
    }

    private void rotate(int[] nums, int from, int end) {
        while (from < end) {
            swap(nums, from, end);
            from ++;
            end --;
        }
    }



    public int maxProfit(int[] prices) {
        int total = 0;
        for (int i=0; i< prices.length-1; i++) {
            if (prices[i+1] > prices[i]) {
                total += prices[i+1]-prices[i];
            }
        }

        return total;
    }

    public int removeDuplicates(int[] nums) {
        if (nums.length < 2) {
            return 1;
        }
        int index = 1;
        int currentPosition = 0;
        while(index <= nums.length - 1) {
            if (nums[index] == nums[currentPosition]) {
                index ++;
            } else if ((nums[index] > nums[currentPosition]) && (index - currentPosition > 1)) {
                swap(nums,index, currentPosition + 1);
                currentPosition ++;
                index ++;
            } else {
                index ++;
                currentPosition ++;
            }
        }

        return currentPosition + 1;
    }

    private void swap(int[] nums, int from , int to) {
        int temp = nums[from];
        nums[from] = nums[to];
        nums[to] = temp;
    }
}
