package link;

import com.ybin.arithmetic.leetcode.Linked;
import com.ybin.arithmetic.leetcode.training.TwentyNovember;
import com.ybin.link.CircularLink;
import com.ybin.link.DoubleLink;
import com.ybin.link.SinglyLinked;
import com.ybin.queue.ArrayQueue;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * @author yuebing
 * @version 1.0 2017/9/4
 */
public class LinkTest {
    @Test
    public void testSinglyLink() {
        SinglyLinked singlyLinked = new SinglyLinked();
        singlyLinked.add(1);
        singlyLinked.add(2);
        singlyLinked.add(3);
        singlyLinked.add(4);
        singlyLinked.add(5);
        singlyLinked.add(6);
////        singlyLinked.forEachNode(singlyLinked);
//
////        singlyLinked.delNode("节点3");
////        singlyLinked.forEachNode(singlyLinked);
//
////        singlyLinked.insertNode("节点2", "新增节点5");
//        singlyLinked.forEachNode(singlyLinked);
////        singlyLinked.findNode("节点2");
//
//        System.out.println("singlyLinked 反转后 " );
//        singlyLinked.reverse();
//        singlyLinked.reverseBetween(2, 4);
//        singlyLinked.forEachNode(singlyLinked);
//        new Linked().segmentation(singlyLinked.getHead(), 3);
//        new TwentyNovember().reorderList(singlyLinked.getHead());
    }

    @Test
    public void testCircularLink() {
        CircularLink circularLink = new CircularLink();
        circularLink.add("节点1");
        circularLink.add("节点2");
        circularLink.add("节点3");
        circularLink.add("节点4");
        circularLink.showAllNode(circularLink);
        System.out.println("删除节点");
        //circularLink.dealNode("节点3");
        circularLink.showAllNode(circularLink);
        System.out.println("插入节点");
        circularLink.insertNode("节点4", "节点5");
        circularLink.showAllNode(circularLink);
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode reverseBetween(ListNode head, int m, int n) {
        assert head != null;
        if (m == n) {
            return head;
        }
        if (m > n ) {
            throw new IllegalArgumentException("m must is more n");
        }
        int i = 1;
        ListNode cur = head;
        ListNode  pre = null;
        ListNode newNode = null;
        ListNode oldTail = null;
        while (cur != null) {
            ListNode next = cur.next;
            if (i >= m && i <= n) {
                cur.next = pre;
                pre = cur;
                if (i == n) {
                    oldTail = next;
                }
            } else if (m > 0 && i == m -1){
                newNode = cur;
                newNode.next = null;
            }
            cur = next;
            if (i == n) {
                break;
            }
            i++;
        }
        for (; ;) {
            if (newNode == null) {
                newNode = pre;
            } else if (newNode.next == null) {
                newNode.next = pre;
            }
            if (pre.next == null) {
                pre.next = oldTail;
                break;
            }else {
                pre = pre.next;
            }
        }
        head = newNode;
        return head;
    }

    @Test
    public void testDoubleLink() {
        List<Long> lists = Arrays.asList(new Long[]{1L, 2L, 3L, 5L});
        ListNode node = new ListNode(3);
        node.next = new ListNode(5);
//        node.next.next = new ListNode(3);
//        node.next.next.next = new ListNode(4);
//        node.next.next.next.next = new ListNode(5);
        reverseBetween(node, 1, 2);
    }

}
