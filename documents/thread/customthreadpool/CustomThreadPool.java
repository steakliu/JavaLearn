package thread.customthreadpool;

import thread.customthreadpool.reject.MyThreadRejectExecutionHandler;
import thread.customthreadpool.threadFactory.MyThreadFactory;

import java.util.concurrent.*;

/**
 * 手动创建线程池
 */
public class CustomThreadPool {

    /**
     * 核心线程数
     */
    private static final int CORE_POOL_SIZE = 20;
    /**
     * 最大线程数
     */
    private static final int MAXIMUM_POOL_SIZE = 100;
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
    private static final BlockingQueue<Runnable> WORK_QUEUE = new PriorityBlockingQueue<>();
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
    private static final ThreadPoolExecutor pool =
            new ThreadPoolExecutor(
                    CORE_POOL_SIZE,
                    MAXIMUM_POOL_SIZE,
                    KEEP_ALIVE_TIME,
                    UNIT,
                    WORK_QUEUE,
                    THREAD_FACTORY,
                    THREAD_REJECT_EXECUTION_HANDLER);
    public static void main(String[] args) {

    }
}
