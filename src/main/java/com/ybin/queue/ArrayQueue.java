package com.ybin.queue;

/**
 * @author yuebing
 * @version 1.0 2017/9/4
 * @Description 数组实现循环队列
 */
public class ArrayQueue<T> {

    private T[] item;
    /**
     * 队列的大小
     */
    private int size;
    /**
     * 队列头所在数组下标
     */
    private int head;
    /**
     * 队列尾所在数组下标
     */
    private int tail;

    public T[] getItem() {
        return item;
    }

    public int getSize() {
        return size;
    }

    public int getHead() {
        return head;
    }

    public int getTail() {
        return tail;
    }

    public ArrayQueue() {
        this.size = 11;
        newArray(this.size);
    }

    public ArrayQueue(int size) {
        this.size = size+1;
        newArray(this.size);
    }

    private void newArray(int size) {
        this.item = (T[]) new Object[size];
        this.head = 0;
        this.tail = 0;
    }

    /**
     * 入对， ++tail % size判断队列是否满，满之后，尾指针如果在数组的最后一位则尾指针归0；
     * 且每次队列将满时，尾指针的位置都为null
     * @param item
     * @return
     */
    public boolean add(T item) {
        int newTail = (tail+1) % size;
        if (newTail == head) {
            throw new IndexOutOfBoundsException("queue is full");
        }
        this.item[tail] = item;
        this.tail = newTail;
        return true;
    }

    /**
     * 出对
     * @return
     */
    public T pop() {
        if (head == tail) {
            throw new IndexOutOfBoundsException("queue is empty");
        }
        T t = this.item[this.head];
        remove(this.head);
        this.head = (++this.head) % size;
        return t;
    }

    private T remove(int index) {
        if (index < 0 ) {
            throw new IndexOutOfBoundsException("index is must > 0");
        }
        if (index > size) {
            throw new IndexOutOfBoundsException("数组下标越界");
        }
        T t = item[index];
        item[index] = null;
        return t;
    }
}
