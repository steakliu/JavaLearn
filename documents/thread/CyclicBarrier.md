## CyclicBarrier
CyclicBarrier和CountDownLatch很像，下面我们用它来实现CountDownLatch计数器功能，
代码如下，我们创建了一个CyclicBarrier，它的构造函数为parties和Runnable接口，parties表示计数器，Runnable表示parties
为0时执行的任务，我们再main函数中两个线程任务中执行后都进行了cyclicBarrier.await()操作，每进行一次，计数器parties值-1，两次
后parties为0，此时出发Runnable任务，
```
package thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO
 *
 * @author 刘牌
 * @version 1.0
 * @date 2021/9/4 0004 23:28
 */
public class CyclicBarrierTest2 {
    static ExecutorService executorService = Executors.newCachedThreadPool();
    static CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
        @Override
        public void run() {
            System.out.println("task1 and task2 over , It's my turn");
        }
    });

    public static void main(String[] args) {
        executorService.submit(() -> {
            System.out.println("do task1");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        executorService.submit(() -> {
            System.out.println("do task2");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
    }
}
```

再举一个CountDownLatch无法实现的功能，执行有三个任务，每个任务都有三个阶段，需要每个任务的阶段同时执行，再到下一个阶段，阶段一执行后
到阶段2，阶段2完成后再到阶段3，code如下。
```
package thread;

import java.util.concurrent.*;

/**
 * TODO
 *  来自《并发编程之美》例子
 * @author 刘牌
 * @version 1.0
 * @date 2021/9/4 0004 22:09
 */
public class CyclicBarrierTest {
    static ExecutorService executorService = Executors.newCachedThreadPool();
    static CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
    public static void main(String[] args) {
        executorService.submit(() -> {
            System.out.println("task1");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("task2");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("task3");
        });

        executorService.submit(() -> {
            System.out.println("task1");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("task2");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("task3");
        });

        executorService.submit(() -> {
            System.out.println("task1");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("task2");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("task3");
        });
    }
}

```