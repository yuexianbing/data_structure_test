package com.ybin.arithmetic.leetcode;

import com.ybin.link.SinglyLinked;
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

    public int findKthLargest(int[] nums, int k) {
        return findK(nums, nums.length-k, 0, nums.length-1);
    }

    private int findK(int[] nums, int k, int i, int j) {
        if(i>=j) return nums[i];
        int m = partition(nums, i, j);
        if(m==k) return nums[m];
        else if(m<k) {
            return findK(nums, k, m+1, j);
        } else {
            return findK(nums, k, i, m-1);
        }
    }

    private int partition(int[] nums, int i, int j) {
        int x = nums[i];
        int m = i;
        int n = i+1;

        while(n<=j){
            if(nums[n]<x) {
                swap(nums, ++m, n);
            }
            ++n;
        }
        swap(nums,i, m);
        return m;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public void reverst(SinglyLinked singlyLinked) {

    }

    @Test
    public void test() {
        int[] nums = new int[]{3,2,3,1,2,4,5,5,6};
        findKthLargest(nums, 4);
    }
}
