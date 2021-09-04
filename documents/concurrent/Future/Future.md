## **Future的使用**

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

### **execute()和submit区别**

1.execute无返回值，这样就无法知道任务是否执行成功
2.execute抛出异常后无法处理，不能捕捉异常，而submit可以捕获异常;

### **Future不足之处**
虽然Future在执行多个操作时的确做到了异步，但是Future.get()取出异步执行结果的时候缺是阻塞的，
只有当前一个操作get完以后才能到下一个操作get
![输入图片说明](https://images.gitee.com/uploads/images/2021/0904/151701_14cc1d4c_4775150.png "get.png")
