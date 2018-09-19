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

    @Test
    public void test() {
        int[] nums = new int[]{0, 1, 2, 0, 6};
        moveZeroes(nums);
    }
}
