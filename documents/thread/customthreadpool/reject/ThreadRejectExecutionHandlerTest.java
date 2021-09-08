package thread.customthreadpool.reject;

import thread.customthreadpool.threadFactory.MyThreadFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池拒绝策略
 *
 *
 *
 * 当
 */
public class ThreadRejectExecutionHandlerTest {

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
     *
     */
    private static final BlockingQueue<Runnable> WORK_QUEUE = new ArrayBlockingQueue<>(12);
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
     *
     *      * 拒绝策略：当线程池的任务缓存队列已满并且线程池中的线程数目达到maximumPoolSize时，如果还有任务到来就会采取任务拒绝策略，通常有以下四种策略：
     *      * ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
     *      * ThreadPoolExecutor.DiscardPolicy：丢弃任务，但是不抛出异常。
     *      * ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新提交被拒绝的任务
     *      * ThreadPoolExecutor.CallerRunsPolicy：由调用线程（提交任务的线程）处理该任务
     *      可以自定义拒绝策略，直接继承RejectedExecutionHandler
     *      使用内置的 new ThreadPoolExecutor.AbortPolicy()
     */
    private static final ThreadPoolExecutor pool =
            new ThreadPoolExecutor(
                    CORE_POOL_SIZE,
                    MAXIMUM_POOL_SIZE,
                    KEEP_ALIVE_TIME,
                    UNIT,
                    WORK_QUEUE,
                    THREAD_FACTORY,
                    new ThreadPoolExecutor.AbortPolicy()  );

    private static int count = 0;

    private synchronized static void add(){
        System.out.println(Thread.currentThread().getName());
        count++;
    }
    public static void main(String[] args) {
        for (int i = 0 ; i < 100000 ; i++){
            pool.submit(ThreadRejectExecutionHandlerTest::add);
        }
        pool.shutdown();
        System.out.println(count);
    }
}
