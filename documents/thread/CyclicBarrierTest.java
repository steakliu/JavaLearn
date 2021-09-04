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
 * @date 2021/9/4 0004 22:09
 */
public class CyclicBarrierTest {
    static ExecutorService executorService = Executors.newCachedThreadPool();

    static CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
        @Override
        public void run() {
            System.out.println("merge result");
        }
    });

    public static void main(String[] args) {
        executorService.submit(() -> {
            System.out.println("task1  over");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        executorService.submit(() -> {
            System.out.println("task2 over");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
    }
}
