package queue;

import com.ybin.queue.ArrayQueue;
import javafx.beans.binding.ObjectExpression;
import org.junit.Test;

/**
 * @author yuebing
 * @version 1.0 2017/9/4
 * @Description
 */
public class QueueTest {

    @Test
    public void testArrayQueue() {
        ArrayQueue<Integer> queue = new ArrayQueue<>(3);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.pop();
        queue.pop();
        queue.pop();
        queue.pop();
    }

}
