package thread.threadpool;

import java.util.concurrent.*;

public class ScheduleThreadPool {
    /**
     * 直接new一个ScheduledExecutorService
     */
    private static final ScheduledExecutorService schedule = Executors.newScheduledThreadPool(20);

    /**
     * 手动创建一个
     */
    private static final ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(20,
            new MyThreadFactory("business-thread"), new MyThreadRejectExecutionHandler());

    private static String scheduleSayHello(){
        System.out.println("hello");
        return "hello";
    }

    /**
     *
     * 拒绝策略：当线程池的任务缓存队列已满并且线程池中的线程数目达到maximumPoolSize时，如果还有任务到来就会采取任务拒绝策略，通常有以下四种策略：
     * ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
     * ThreadPoolExecutor.DiscardPolicy：丢弃任务，但是不抛出异常。
     * ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新提交被拒绝的任务
     * ThreadPoolExecutor.CallerRunsPolicy：由调用线程（提交任务的线程）处理该任务
     * @param args
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         *
         * initialDelay初始延迟，也就是第一次启动的时候需要延迟多久才执行，之后会按照period周期执行
         *
         */
        //schedule.scheduleAtFixedRate(ScheduleThreadPool::scheduleSayHello,0,3, TimeUnit.SECONDS);

        /**
         *
         * Runnable command ， 无返回值
         * delay：延迟多长时间执行，执行过一次后就不再执行
         *
         */
        //schedule.schedule(ScheduleThreadPool::scheduleSayHello,3,TimeUnit.SECONDS);

        /**
         *
         * 有返回值的定时线程池，
         * delay：延迟多长时间执行，执行过一次后就不再执行
         * Callable<V> callable有返回值，返回值为ScheduledFuture<?>,scheduledFuture.get()
         *
         */
        //ScheduledFuture<?> scheduledFuture = schedule.schedule(ScheduleThreadPool::scheduleSayHello, 3, TimeUnit.SECONDS);


        /**
         * initialDelay初始延迟
         * delay : 执行周期
         *
         */
        //schedule.scheduleWithFixedDelay(ScheduleThreadPool::scheduleSayHello,0,3,TimeUnit.SECONDS);

    }
}
