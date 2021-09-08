## 定时线程池  
使用定时线程池，我们可以做一些定时任务，我们可以使用内置的定时线程池，也可以通过ScheduledThreadPoolExecutor来手动创建，
ScheduledExecutorService继承自ExecutorService,通过Executors.newScheduledThreadPool(core)便可以创建，
ScheduledThreadPoolExecutor是ThreadPoolExecutor的子类，继承ScheduledExecutorService，
![img.png](img.png)

#### 创建一个定时线程池  
如果使用内置的线程池，那么核心线程数由我们设置，其他的则不能由我们设置,最大线程数为Integer.MAX_VALUE，这样不合理
,
super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS, new DelayedWorkQueue(), threadFactory, handler);
```
    /**
     * 直接new一个内置的ScheduledExecutorService   
     */
    private static final ScheduledExecutorService schedule = Executors.newScheduledThreadPool(20);
    
     /**
     * 手动创建一个
     */
    private static final ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(20,
            new MyThreadFactory("business-thread"), new MyThreadRejectExecutionHandler());
```

#### 使用  
```
       /**
        * initialDelay初始延迟，也就是第一次启动的时候需要延迟多久才执行，之后会按照period周期执行
        * 此例初始延迟为0（项目启动马上执行），之后按照3秒的周期定时执行
        */
        schedule.scheduleAtFixedRate(ScheduleThreadPool::scheduleSayHello,0,3, TimeUnit.SECONDS);
```


```
        /**
         *
         * delay：延迟多长时间执行，执行过一次后就不再执行
         * 此例在项目启动时延迟三秒再执行，之后就不再执行
         *
         */
        schedule.schedule(ScheduleThreadPool::scheduleSayHello,3,TimeUnit.SECONDS);

```

```
        /**
         *
         * 有返回值的定时线程池，
         * delay：延迟多长时间执行，执行过一次后就不再执行
         * Callable<V> callable有返回值，返回值为ScheduledFuture<?>,scheduledFuture.get()
         * 此例在项目启动时延迟三秒再执行，之后就不再执行
         */
        ScheduledFuture<?> scheduledFuture = schedule.schedule(ScheduleThreadPool::scheduleSayHello, 3, TimeUnit.SECONDS);
```

```
        /**
         * initialDelay初始延迟
         * delay : 执行周期
         * 
         */
        schedule.scheduleWithFixedDelay(ScheduleThreadPool::scheduleSayHello,0,3,TimeUnit.SECONDS);
```
