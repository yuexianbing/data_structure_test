package com.ybin.arithmetic.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author : bing.yue001
 * @version : 1.0
 * @date : 2020-7-28 13:02
 * @description : 单调栈
 */
public class MonotonousStack {

    /**
     * 最大矩形问题：
     * 因为求一个矩形的最大面积即是求当前的高*当前矩形能往左往右扩的最远距离，当满足小于当前矩形值时不能再扩，所以构建一个单调递增栈
     * 当放入元素，使栈要弹出时，计算当前弹出元素的面积，宽度=当前放入元素下标= 当前弹出元素位置-1-弹出元素栈下的元素
     *
     * @param arr
     * @return
     */
    public int area(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        Stack<List<Integer>> stack = new Stack<>();
        int area= 0;
        for (int i = 0; i< arr.length; i++) {

            while (!stack.empty() && arr[stack.peek().get(stack.peek().size() - 1)] > arr[i]) {
                List<Integer> value = stack.pop();
                int height = arr[value.get(value.size() - 1)];
                int next = stack.empty() ? 0 : stack.peek().get(stack.peek().size() - 1);
                int wide = i - 1 - next;
                area = Math.max(wide * height, area);
            }


            if (stack.empty() || arr[i] != arr[stack.peek().get(0)]) {
                List<Integer> con = new ArrayList<>();
                con.add(i);
                stack.push(con);
            } else {
                if (arr[i] == arr[stack.peek().get(0)]) {
                    stack.peek().add(i);
                }

            }
        }
        // 剩下的栈中全是单调递增的,求剩余的能绘出的最大矩形
        if (!stack.empty()) {
            List<Integer> index = stack.pop();
            int end = index.get(index.size() - 1);
            area = Math.max((end - stack.peek().get(stack.peek().size() - 1)) * arr[end], area);
            while (!stack.empty()) {
                index = stack.pop();
                area = Math.max(arr[index.get(0)] * (end - index.get(0) + 1), area);
            }
        }
        return area;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{2, 4, 5, 5};
        new MonotonousStack().area(arr);
    }
}
