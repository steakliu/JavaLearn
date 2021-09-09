package thread.customthreadpool.threadFactory;

import thread.customthreadpool.reject.MyThreadRejectExecutionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadFactoryTest {

    /**
     * 核心线程数
     */
    private static final int CORE_POOL_SIZE = 5;
    /**
     * 最大线程数
     */
    private static final int MAXIMUM_POOL_SIZE = 30;
    /**
     * 等待时间
     */
    private static final long KEEP_ALIVE_TIME = 3;
    /**
     * 单位
     */
    private static final TimeUnit UNIT = TimeUnit.SECONDS;
    /**
     * 任务队列
     */
    private static final BlockingQueue<Runnable> WORK_QUEUE = new ArrayBlockingQueue<>(100);
    /**
     * 线程工厂
     */
    private static final MyThreadFactory THREAD_FACTORY = new MyThreadFactory("business");
    /**
     * 拒绝策略
     */
    private static final MyThreadRejectExecutionHandler THREAD_REJECT_EXECUTION_HANDLER = new MyThreadRejectExecutionHandler();
    /**
     * 手动创建线程池
     */
    private static final ThreadPoolExecutor service =
            new ThreadPoolExecutor(
                    CORE_POOL_SIZE,
                    MAXIMUM_POOL_SIZE,
                    KEEP_ALIVE_TIME,
                    UNIT,
                    WORK_QUEUE,
                    THREAD_FACTORY,
                    new ThreadPoolExecutor.AbortPolicy());

    /**
     * 创建线程池
     */
    public static final ThreadPoolExecutor pool =
            new ThreadPoolExecutor(
                    20,
                    100,
                    5,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(100),
                    new MyThreadFactory("business")
            );

    public static void main(String[] args) {
        pool.execute(() -> {
            Integer integer = getOrderInfo();
            System.out.println(integer);
        });
        pool.shutdown();
    }

    private static Integer getOrderInfo(){
        try {
            List<Integer> list = new ArrayList<>();
            for (int i = 0 ; i < 10 ; i++){
                list.add(i);
            }
            return list.get(11);
        }catch (Exception e){
            throw new IndexOutOfBoundsException("数组越界");
        }
    }
}
