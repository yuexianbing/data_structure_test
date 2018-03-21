package queue;

import com.ybin.queue.MyConcurrentLinkedQueue;
import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yuebing
 * @version 1.0 2017/9/4
 * @Description
 */
public class ConcurrentLinkedQueueTest {

    @Test
    public void testLinkedQueue() throws NoSuchFieldException, IllegalAccessException {
        final MyConcurrentLinkedQueue queue = new MyConcurrentLinkedQueue();
        ExecutorService executors = Executors.newFixedThreadPool(10);
        final AtomicInteger data = new AtomicInteger(0);
        final CyclicBarrier barrier = new CyclicBarrier(10, new Runnable() {
            @Override
            public void run() {
                System.out.println("线程池初始化完成，开始执行入队操作!!!");
            }
        });
        final CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0 ; i < 10 ; i++) {
            executors.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0 ; i <100; i++) {
                        queue.add(data.incrementAndGet());
                        System.out.println("线程="+Thread.currentThread().getName() + " 尾节点数据值"+queue.getData());
                    }
                    latch.countDown();
                }
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        Thread t1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//
//        Thread t2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 10; i < 200; i++) {
//                    queue.add(i);
//                    System.out.println("线程2==" + queue.getData());
//                }
//            }
//        });
//        t1.start();
//        t2.start();
    }

    @Test
    public void testPoll() {
        ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.add(5);
        queue.add(6);

        for (;;) {
            queue.poll();
        }
    }
}
