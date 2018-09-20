package com.ybin.arithmetic.leetcode;

import org.junit.Test;

/**
 * @author yuebing
 * @version 1.0
 * @Date 2018/9/19
 * @category
 */
public class Solution {

    /**
     * leetcode #283
     *
     * @param nums
     */
    public void moveZeroes(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0 ) {
                if (j != i)
                    nums[j++] = nums[i];
                else
                    j++;
            }
        }
        for (int i = j; i < nums.length ; i++) {
            nums[i] = 0;
        }
    }

    /**
     * leetcode 27
     *
     * @param nums
     * @param val
     * @return
     */
    public int removeElement(int[] nums, int val) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("nums is empty!");
        }
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (val != nums[i]) {
                if (j != i) {
                    nums[j++] = nums[i];
                } else {
                    j++;
                }
            }
        }
        return j;
    }

    /**
     * lettcode 75  三路快排解决数组3种重复元素的排序 O(n)
     *
     * @param nums
     */
    public void sortColors(int[] nums) {
        int zero = -1;
        int two = nums.length;
        for (int i = 0; i < two;) {
            if (nums[i] == 1 ) {
                i++;
            } else if (nums[i] == 2) {
                swap(nums, i, --two);
            } else {
                assert nums[i] == 0;
                swap(nums, i++, ++zero);
            }
        }
    }

    public void swap(int[] nums, int a, int b) {
        int c = nums[a];
        nums[a] = nums[b];
        nums[b] = c;
    }

    @Test
    public void test() {
        int[] nums = new int[]{2,0,2,1,1,0};
        sortColors(nums);
    }
}
