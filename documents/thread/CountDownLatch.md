## CountDownLatch
在开发过程中我们常常遇到需要对多个任务进行汇总，比如报表，或者大屏显示，需要将所有接口的数据都
获取到后再进行汇总，如果使用同步的方式，那么会比较耗时，体验不好，所以我们使用多线程，但是使用多线程
只能异步的执行，有些接口响应比较快，有些比较慢，而返回结果之间又有依赖，这样就无法汇总了，
所以我们引入了CountDownLatch，它能让所有子线程全部执行完毕后主线程才会往下执行，如果子线程没有执行完毕
，那么主线程将无法继续向下执行。

例子：我们需要对三个接口的返回结果进行求和。
```
    模拟三个接口

    public static Integer getOne(){
        return 1;
    }
    public static Integer getTwo(){
        return 2;
    }
    public static Integer getThree(){
        return 3;
    }
```

 我们创建一个线程池和CountDownLatch，CountDownLatch构造函数参数我们传3,表示计数器为3
```
    static ExecutorService executorService = Executors.newCachedThreadPool();
    /**
     * CountDownLatch(3) , 构造函数参数为3，
     */
    static volatile CountDownLatch countDownLatch = new CountDownLatch(3);
```


main函数，我们将三个任务加入线程池中，并且调用了countDownLatch.countDown()，调用此方法后计数器减1，因为一开始我们设置的
计数器为3，而三个线程执行后，每个-1，此时计算器变为0，这时候主线程的await才会返回，主线程才会向下执行，如果我们将计算器设置为
10，三个线程-3，此时计算器为7，那么await将会一直阻塞，主线程则无法向下执行，所以一定要让计算器为0后才会向下执行，
```
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
            //计数器-1
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
        //阻塞，等到计数器为0蔡往下执行
        countDownLatch.await();
        System.out.println("count  "+countDownLatch.getCount());
        System.out.println("child thread over , main thread start");
        Integer value1 = futureOne.get();
        Integer value2 = futureTwo.get();
        Integer value3 = futureThree.get();
        int total = value1 + value2 + value3;
        System.out.println("total  "+total);
    }
```