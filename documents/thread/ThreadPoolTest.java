package thread;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO
 *
 * @author 刘牌
 * @version 1.0
 * @date 2021/9/4 0004 19:54
 */
public class ThreadPoolTest {
    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    private static ThreadPoolExecutor executorServicel = new ThreadPoolExecutor(
            20,
            200,
            3,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1000000));
    static ReentrantLock reentrantLock = new ReentrantLock();
    //private static AtomicInteger count = new AtomicInteger(1000000);
    private static Integer count = 1000000;
    public void add(){
        //System.out.println(Thread.currentThread().getName());
        //count.decrementAndGet();

        reentrantLock.lock();
        try {
            count--;
        }finally {
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //信号量，此处用于控制并发的线程数
        final Semaphore semaphoreTest = new Semaphore(1000000);
        //闭锁，可实现计数器递减
        final CountDownLatch countDownLatch = new CountDownLatch(1000000);
        ThreadPoolTest poolTest = new ThreadPoolTest();
        for (int i = 0 ; i < 1000000 ; i++){
            executorServicel.execute(() -> {
                try {
                    semaphoreTest.acquire();
                    poolTest.add();
                    semaphoreTest.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println(count);
    }
}
