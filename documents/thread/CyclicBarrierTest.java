package thread;

import java.util.concurrent.*;

/**
 * TODO
 *
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
