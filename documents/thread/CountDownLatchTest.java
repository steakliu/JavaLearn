package thread;

import java.util.concurrent.*;

/**
 * TODO
 *
 * @author 刘牌
 * @version 1.0
 * @date 2021/9/4 0004 21:54
 */
public class CountDownLatchTest {

    static ExecutorService executorService = Executors.newCachedThreadPool();
    /**
     * CountDownLatch(3) , 构造函数参数为3，
     */
    static volatile CountDownLatch countDownLatch = new CountDownLatch(3);

    public static Integer getOne(){
        return 1;
    }
    public static Integer getTwo(){
        return 2;
    }
    public static Integer getThree(){
        return 3;
    }
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Future<Integer> futureOne = executorService.submit(() -> {
            System.out.println(Thread.currentThread().getName()+"  is over");
            Thread.sleep(2000);
            countDownLatch.countDown();
            return getOne();
        });
        Future<Integer> futureTwo = executorService.submit(() -> {
            System.out.println(Thread.currentThread().getName()+"  is over");
            Thread.sleep(2000);
            countDownLatch.countDown();
            return getTwo();
        });
        Future<Integer> futureThree = executorService.submit(() -> {
            System.out.println(Thread.currentThread().getName()+"  is over");
            Thread.sleep(2000);
            countDownLatch.countDown();
            System.out.println("count3 "+countDownLatch.getCount());
            return getThree();
        });
        countDownLatch.await();
        System.out.println("count  "+countDownLatch.getCount());
        System.out.println("child thread over , main thread start");
        Integer value1 = futureOne.get();
        Integer value2 = futureTwo.get();
        Integer value3 = futureThree.get();
        int total = value1 + value2 + value3;
        System.out.println("total  "+total);
    }
}
