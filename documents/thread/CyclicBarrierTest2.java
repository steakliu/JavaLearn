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
