package com.ybin.arithmetic.leetcode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @author : bing.yue001
 * @version : 1.0
 * @date : 2020-9-7 8:46
 * @description : 并查集,同一个集合的节点的顶节点是同一个,顶节点的父节点指向自己
 */
public class CheckAndSet<V> {

    public class Node<V>{

        private V value;

        public Node(V value) {
            this.value = value;
        }
    }

    public class UnionSet<V>{

        /**
         * 父节点MAP
         */
        private Map<Node<V>, Node<V>> parentMap = new HashMap<>();
        /**
         * 本身节点MAP
         */
        private Map<V, Node<V>> nodeMap = new HashMap<>();
        /**
         * 头节点对应的集合中的元素的数量
         */
        private Map<Node<V>, Integer> sizeMap = new HashMap<>();

        public UnionSet(List<V> values) {
           for (V value : values) {
               Node node = new Node<>(value);
               nodeMap.put(value, new Node<>(value));
               parentMap.put(node, node);
               sizeMap.put(node, 1);
           }
        }

        /**
         * 找到其父节点
         *
         * @param node
         * @return
         */
        public Node<V> findFather(Node<V> node) {
            Node<V> cur = node;
            Stack<Node<V>> stack = new Stack<>();
            while (cur != parentMap.get(node)) {
                stack.push(cur);
                cur = parentMap.get(node);
            }
            while (!stack.empty()) {
                parentMap.put(stack.pop(), cur);
            }
            return cur;
        }

        /**
         * 是否同属一个并查集
         *
         * @param a
         * @param b
         * @return
         */
        public boolean isSmaeSet(V a, V b) {
            if (!nodeMap.containsKey(a) || !nodeMap.containsKey(b)) {
                return false;
            }
            return findFather(nodeMap.get(a)) == findFather(nodeMap.get(b));
        }

        /**
         * 合并a,b的并查集和
         *
         * @param a
         * @param b
         */
        public void union(V a, V b) {
            if (!nodeMap.containsKey(a) || !nodeMap.containsKey(b)) {
                return;
            }
            Node<V> nodea = this.findFather(nodeMap.get(a));
            Node<V> nodeb = this.findFather(nodeMap.get(b));

            if (nodea != nodeb) {
                int aSize = sizeMap.get(nodeMap.get(a));
                int bSize = sizeMap.get(nodeMap.get(b));
                if (aSize > bSize) {
                    parentMap.put(nodeb, nodea);
                    sizeMap.put(nodea, aSize + bSize);
                    sizeMap.remove(nodeb);
                } else {
                    parentMap.put(nodea, nodeb);
                    sizeMap.put(nodeb, aSize + bSize);
                    sizeMap.remove(nodea);
                }
            }
        }
    }
}
