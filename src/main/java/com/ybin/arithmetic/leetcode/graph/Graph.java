package com.ybin.arithmetic.leetcode.graph;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author : bing.yue001
 * @version : 1.0
 * @date : 2020-9-17 13:33
 * @description :
 */
@Getter
@Setter
public class Graph<T> {

    private Map<T, GraphNode> graphNodeMap;

    private List<Edge> edges;

    @Getter
    @Setter
    public static class GraphNode<T> {

        /**
         * 节点值
         */
        private T value;
        /**
         * 节点的边
         */
        private List<Edge> edges;
        /**
         * 入方向的边
         */
        private List<Edge> in;
        /**
         * 出方向的边
         */
        private List<Edge> to;
    }

    @Getter
    @Setter
    public static class Edge {

        /**
         * 权重值
         */
        private int value;
        /**
         *
         */
        private GraphNode from;
        /**
         *
         */
        private GraphNode to;
    }
}
