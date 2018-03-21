package com.ybin.queue;

import sun.misc.Unsafe;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * @author yuebing
 * @version 1.0 2017/9/4
 * @Description
 */
public class MyConcurrentLinkedQueue<T> implements Serializable {

    private static final long serialVersionUID = 7956179482617663503L;
    public static Unsafe UNSAFE;

    private static class Node<T>{
        private volatile T item;
        private volatile Node next;

        Node(T item) {
            UNSAFE.putObject(this, itemOffset, item);
        }
        /**
         * item偏移量
         */
        private static final long itemOffset;
        /**
         * next偏移量
         */
        private static final long nextOffset;
        static {
            try {
                Class nodeClass= Node.class;
                itemOffset = UNSAFE.objectFieldOffset(nodeClass.getDeclaredField("item"));
                nextOffset = UNSAFE.objectFieldOffset(nodeClass.getDeclaredField("next"));
            } catch (Exception e) {
                throw new Error(e);
            }
        }

        public boolean casNext(Node expect, Node result) {
            return UNSAFE.compareAndSwapObject(this, nextOffset, expect, result);
        }

        public boolean casItem(T exception, T result) {
            return UNSAFE.compareAndSwapObject(this, itemOffset, exception, result);
        }

        public void lazySet(Node h) {
            UNSAFE.putOrderedObject(this, nextOffset, h);
        }
    }

    static {
        Class classs = MyConcurrentLinkedQueue.class;
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            UNSAFE = (Unsafe) field.get(null);
            headOffset = UNSAFE.objectFieldOffset(classs.getDeclaredField("head"));
            tailOffset = UNSAFE.objectFieldOffset(classs.getDeclaredField("tail"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    private volatile Node head;

    private volatile Node tail;

    private static final long headOffset;

    private static final long tailOffset;

    public MyConcurrentLinkedQueue() {
        head = tail = new Node(null);
    }

    /**
     * 添加节点
     * @param e
     * @return
     */
    public boolean add(T e) {
        checkNull(e);
        Node node = new Node(e);
        for (Node t = tail, p = t; ;) {
            Node q = p.next;
            if (q == null) {
                if (p.casNext(null, node)) {
                    if (p != t) {
                        //允许tail滞后新节点至少两个节点（单线程）的距离才更新tail，
                        // 增多volatile读操作，减少cas操作。就算没更新成功也没关系。
                        casTail(t, node);
                    }
                    return true;
                }
            } else if (p == q) {
                //哨兵节点，尾节点的next指向尾节点自身。
                //在并发的情况下将head赋值给p的过程中，如果其他线程更新
                // 了尾节点，则将新的尾节点赋值给p，避免从head遍历；
                p = t != (t = tail) ? t: head;
            } else {
                p = (p != t && t != (t = tail)) ? t : q;
            }
        }
    }

    public T poll() {
        restart:
        for (; ; ) {
            for (Node h = head, p = h, q; ;) {
                T e = (T) p.item;
                if (e != null && p.casItem(e, null)) {
                    if (p != h) {
                        casHead(h, ((q = p.next) != null? q: p));
                    }
                    return e;
                } else if ((q = p.next) == null) {
                    updateHead(h, p);
                } else {
                    p = q;
                }
            }
        }
    }

    private void checkNull(T e) {
        if (e == null) {
            throw new NullPointerException();
        }
    }

    private void updateHead(Node h, Node p) {
        if (h != p && casHead(h, p)) {
            h.lazySet(h);
        }
    }

    private boolean casHead(Node except, Node result) {
        return UNSAFE.compareAndSwapObject(this, headOffset, except, result);
    }

    private boolean casTail(Node expect, Node result) {
        return UNSAFE.compareAndSwapObject(this, tailOffset,  expect, result);
    }

    public Node getHead() {
        return head;
    }

    public Node getTail() {
        return tail;
    }

    public Object getData() {
        return tail.item;
    }
}
