package com.ybin.arithmetic.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuebing
 * @version 1.0
 * @Date 2018/10/16
 * @category 回溯算法
 */
public class BacktrackingAlgorithm {

    private int[][] step = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    private Map<String, Boolean> isFind = new HashMap<>();

    /**
     * 递归回溯,单词查找
     *
     * @param board
     * @param word
     * @return 是否查找到word
     */
    public boolean solution(char[][] board, String word) {
        if (board == null || board.length == 0 || word == null) {
            return false;
        }

        for (int i = 0; i < board.length ; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int index = 0;
                if (searchWord(board, i, j, word, index)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean searchWord(char[][] board, int x, int y, String word, int index) {
        if (index == word.length() - 1) {
            return board[x][y] == word.charAt(index);
        }
        if (board[x][y] == word.charAt(index)) {
            String position = "" + x + y;
            isFind.put(position, true);
            for (int i = 0; i < step.length; i++) {
                int newX = x + step[i][0];
                int newY = y + step[i][1];
                if (newX >= 0 && newX <= board.length - 1 && newY >= 0 && newY <= board[newX].length) {
                    if ((isFind.get(""+ newX + newY) == null || !isFind.get(""+ newX + newY)) &&
                            searchWord(board, newX, newY, word, index + 1)) {
                        return true;
                    }
                }
            }
            isFind.put(position, false);
        }
        return false;
    }

    public static void main(String[] args) {
        char[][] board = new char[][]{{'A','C','G','K','O'}, {'B','C','O','D'},{'A','B','F'}};
        String word = "BABCO";
        BacktrackingAlgorithm algorithm = new BacktrackingAlgorithm();
        System.out.printf("isFind: " + algorithm.solution(board, word));
    }

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
}
