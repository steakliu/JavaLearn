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
        System.out.println("all child thread is over");
        executorService.shutdown();
    }
}
