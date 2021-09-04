### **Future的使用**

Future代表异步执行的结果，也就是说异步执行完毕后，结果保存在Future里，
我们在使用线程池submit()时需要传入Callable接口,线程池的返回值为一个Future,而Future则保存了执行的结果
，可通过Future的get()方法取出结果,如果线程池使用的是execute(),则传入的是Runnable接口
无返回值。

```
    /**
     * submit返回值为Future,
     * @param num
     * @return
     */
    public static Future<?> futureTask(Integer num) {
        return executorService.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return getMemberStatusRpcApi(num);
            }
        });
    }
```

```
     /**
     * execute无返回值，因为参数是Runnable
     */
    public static void noResult(Integer num){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                getMemberStatusRpcApi(num);
            }
        });
    }
```

获取异步执行结果使用get(),isDone判断异步执行是否结束
```
   Future<?> future = futureTask(20);
   if (future.isDone()){
      System.out.println(future.get());
   }
```

cancel(),取消异步执行
```
  Future<?> future = futureTask(20);
  future.cancel(true);
```

get(long time , TimeUnit time),在指定时间内获取执行结果,如果超过时间还没获取到结果，则报超时异常
```
  Future<?> future = futureTask(20);
  future.get(3,TimeUnit.SECONDS);
```

isCancelled(),判断任务是否取消
```
  boolean cancelled = future.isCancelled();
```