package com.ybin.btree;

/**
 * @author yuebing
 * @version 1.0
 * @Date 2018/5/11
 * @category AVL树操作
 */
public class AvlTree<E extends Comparable> {

    /**
     * 根节点
     */
    private Node root;
    /**
     * AVL树高度
     */
    private int height;

    private static final class Node<E> {
        /**
         * 元素值
         */
        E element;
        /**
         * 左节点
         */
        Node left;
        /**
         * 右节点
         */
        Node right;
        /**
         * 节点高度
         */
        int nodeHeight;

        public Node(E element) {
            this(element, null, null);
        }

        public Node(E element, Node left, Node right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }
    }

    public void add(E e) {
        if (e != null)
            this.root = insert(e, root);
    }

    private Node insert (E e, Node node) {
        if (node == null) {
            return new Node(e);
        }
        int compare = e.compareTo(node.element);
        if (compare < 0) {
            node.left = insert(e, node.left);
        }else if (compare > 0) {
            node.right = insert(e, node.right);
        } else {
            return node;
        }
        return balance(node);
    }

    public void remove(E e) {
        if (e != null) {
            this.root = this.remove(e, root);
        }
    }

    private Node remove(E e, Node node) {
        if (node == null) {
            return node;
        }
        int compare = e.compareTo(node.element);
        if (compare < 0) {
            node.left = remove(e, node.left);
        } else if (compare > 0) {
            node.right = remove(e, node.right);
        } else if (node.left != null && node.right != null) {
            //找出该节点右节点的最小节点,替换被删除的节点
            Node min = node.right;
            while (min.left != null) {
                min = min.left;
            }
            node.element = min.element;
            node.right = remove((E) node.element, node.right);
        } else {
            node = node.left == null ? node.right : node.left;
        }
        return this.balance(node);
    }

    /**
     * 添加节点后,平衡AVL树
     *
     * @param node
     * @return
     */
    private Node balance(Node node) {
        if (node == null) {
            return node;
        }
        if (height(node.left) - height(node.right) > 1) {
            if (height(node.left.left) >= height(node.left.right)) {
                node = this.rightRotate(node);
            } else {
                node = this.leftAndRightRotate(node);
            }
        } else if (height(node.right) - height(node.left) > 1) {
            if (height(node.right.right) >= height(node.right.left)) {
                node = this.leftRotate(node);
            } else {
                node = this.rightAndLeftRotate(node);
            }

        }
        node.nodeHeight = Math.max(height(node.left), height(node.right)) + 1;
        return node;
    }

    /**
     * 先左旋再右旋
     * 1.先将该节点的左孩子节点左旋;
     * 2.再将旋转后的节点,右旋;
     * 3.重新计算旋转后各节点的高度;
     * 4.旋转效果:
     *       3      |      3    |     2
     *      /       |     /     |    / \
     *     1        ->   2      ->  1  3
     *      \       |   /       |
     *       2      |  1        |
     * @param node
     * @return
     */
    private Node leftAndRightRotate(Node node) {
        node.left = this.leftRotate(node.left);
        return rightRotate(node);
    }

    /**
     * 先右旋再左旋
     * 1.先将该节点的右孩子节点右旋;
     * 2.先将上一步旋转完成的节点,左旋;
     * 3.再重新计算旋转后各节点的高度;
     * 4.旋转效果:
     *     1      |    1      |     2
     *      \     |     \     |    / \
     *       3    ->    2     ->  1  3
     *      /     |      \    |
     *     2      |       3   |
     * @param node
     * @return
     */
    private Node rightAndLeftRotate(Node node) {
        node.right = this.rightRotate(node.right);
        return leftRotate(node);
    }

    /**
     * 左旋
     * 1.以传入的节点的右节点为支点,将传入的节点旋转到该支点节点的左节点上;
     * 2.重新计算旋转后的节点的高度;
     * 3.旋转效果:
     *      1       |        2
     *       \      |       / \
     *        2     ->     1  3
     *         \    |
     *          3   |
     * @param oldNode
     * @return
     */
    private Node leftRotate(Node oldNode) {
        Node  newNode = oldNode.right;
        oldNode.right = newNode.left;
        newNode.left = oldNode;
        oldNode.nodeHeight = Math.max(height(oldNode.left), height(oldNode.right)) + 1;
        newNode.nodeHeight = Math.max(height(newNode.right), oldNode.nodeHeight) + 1;
        return newNode;
    }

    /**
     * 右旋
     * 1.以传入的节点的左节点为支点,将传入的节点旋转到该支点节点的右节点上;
     * 2.重新计算旋转后的节点的高度;
     * 3.旋转效果:
     *          3    |       2
     *         /     |      / \
     *        2      ->    1   3
     *       /       |
     *      1        |
     * @param oldNode
     * @return
     */
    private Node rightRotate(Node oldNode) {
        Node newNode = oldNode.left;
        oldNode.left = newNode.right;
        newNode.right = oldNode;
        oldNode.nodeHeight = Math.max(height(oldNode.left), height(oldNode.right)) + 1;
        newNode.nodeHeight = Math.max(height(newNode.left), oldNode.nodeHeight) + 1;
        return newNode;
    }

    /**
     * 获取节点高度
     * 1.节点为空,则高度为-1;
     * 2.否则返回节点高度;
     *
     * @param node
     * @return
     */
    private int height(Node node) {
        return node == null ? -1 : node.nodeHeight;
    }
}
