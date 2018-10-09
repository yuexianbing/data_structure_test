package com.ybin.arithmetic.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuebing
 * @version 1.0
 * @Date 2018/10/9
 * @category
 */
public class SolutionTwo {

    /**
     * 回溯法解决组合问题
     *
     * @param arr
     * @return
     */
    public List<List<Integer>> recall(int[] arr) {
        if (arr == null ){
            return null;
        }
        List<Integer> res = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        if (arr.length == 1) {
            res.add(arr[arr.length - 1]);
            result.add(res);
            return result;
        }
        result = recall(arr, 0, res, result);
        res = new ArrayList<>();
        res.add(arr[arr.length - 1]);
        result.add(res);
        return result;
    }

    private List<List<Integer>> recall(int[] arr, int index, List<Integer> res, List<List<Integer>> result) {
        if (index == arr.length) {
            result.add(res);
            return result;
        }
        for (int i = index; i < arr.length; i++) {
            if (!(res.size() == 0 && i == arr.length - 1)) {
                res.add(arr[i]);
                recall(arr, i + 1, res, result);
                res = new ArrayList<>();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        SolutionTwo solutionTwo = new SolutionTwo();
        solutionTwo.recall(new int[]{1,2,3});
    }
}
