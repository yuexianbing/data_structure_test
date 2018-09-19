package com.ybin.arithmetic.search;

/**
 * @author yuebing
 * @version 1.0
 * @Date 2018/9/18
 * @category
 */
public class Search<T> {

    public int binarySerach(int[] t, int target) {
        if (t == null) {
            return -1;
        }

        for (int l = 0, r = t.length -1; l <= r; ) {
            int min = l + (r - l)/2;
            if (t[min] == target) {
                return min;
            } else if (t[min] > target) {
                r = min - 1;
            } else {
                l = min + 1;
            }
        }
        return -1;
    }
}
