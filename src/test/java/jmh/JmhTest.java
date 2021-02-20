//package jmh;
//
//import com.ybin.btree.AvlTree;
//import com.ybin.btree.BtreeLinked;
//import lombok.extern.slf4j.Slf4j;
//import org.openjdk.jmh.annotations.*;
//
//import java.util.Date;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * @author yuebing
// * @version 1.0
// * @Date 2018/6/15
// * @category JMH测试各数据结构性能
// */
//@State(Scope.Benchmark)
//@BenchmarkMode({Mode.AverageTime})
//@Warmup(iterations = 5)
//@Measurement(iterations = 5)
//@Threads(5)
//@Fork(1)
//@Slf4j
//@OutputTimeUnit(TimeUnit.NANOSECONDS)
//public class JmhTest {
//
//    @Setup(Level.Trial)
//    public void start() {
//        log.info("benchmark start run : time = {}", System.currentTimeMillis());
//    }
//
//    @TearDown(Level.Trial)
//    public void end() {
//        log.info("benchmark end run : time = {}", System.currentTimeMillis());
//    }
//
//    @Benchmark
//    public void btreeAdd() {
//        BtreeLinked linked = new BtreeLinked(0);
//        for (int i = 0 ; i < 10000; i++) {
//            linked.add(i);
//        }
//    }
//
//
//    @Benchmark
//    public void avlTreeAdd() {
//        AvlTree avlTree = new AvlTree();
//        for (int i = 0 ; i < 10000; i++) {
//            avlTree.add(i);
//        }
//    }
//}
