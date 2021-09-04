## Semaphore
Semaphore即信号量机制，它和CountDownLatch,CyclicBarrier类似，都是计数器的思想，
构造函数permits表示计数器，在每个线程任务执行完毕时我们调用了semaphore.release()，那么permits的值将会+1
因为我们执行了连个线程任务，所以此时permits为2，semaphore.acquire(2)处则满足条件，主线程将往下执行，如果改为
semaphore.acquire(3),那么主线程将会一直阻塞，因为计数器为2.
```
package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * TODO
 *信号量机制
 * @author 刘牌
 * @version 1.0
 * @date 2021/9/4 0004 21:36
 */
public class SemaphoreTest {

    static Semaphore semaphore = new Semaphore(0);
    static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) throws InterruptedException {
        executorService.submit(() -> {
            System.out.println(Thread.currentThread().getName()+"  is over");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            semaphore.release();
        });

        executorService.submit(() -> {
            System.out.println(Thread.currentThread().getName()+"  is over");
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            semaphore.release();
        });
        semaphore.acquire(2);
        System.out.println("all child thread is over,main thread is start");
        executorService.shutdown();
    }
}

```
