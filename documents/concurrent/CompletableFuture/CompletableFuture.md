    

##  **CompletableFuture**
1:当我们系统中某个接口需要调用多个接口，如果我们使用同步的方式去调用，那么只有等一个接口调用完毕后再到下一个接口继续调用，如果这些任务不耗时，其实是完全能接受的，但是如果调用的这些接口耗时，用同步的方式去调用其实是不明智的做法。
如图，我们系统需要用RPC远程去调用A,B,C,D四个系统的接口，每个接口耗时100ms,那么总共就是400ms
 ![输入图片说明](https://images.gitee.com/uploads/images/2021/0903/125639_27b63c27_4775150.png "同步调用.png")


我们能不能想其它的方法来缩短时间呢？当然可以，譬如使用消息队列，在这里我们先不讨论中间件，而是直接使用java类库，Java8引入了CompletableFuture,使用它可以进行完美的异步操作，我们看下使用它后的示意图。
可以看出，使用CompletableFuture是异步调用的，等到大家都调用结束后，再对结果进行汇总
![输入图片说明](https://images.gitee.com/uploads/images/2021/0903/130615_d307f663_4775150.png "异步调用.png")

code
```
private static CompletableFuture<Integer> test1() throws InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });
    }

    private static CompletableFuture<Integer> test2() throws InterruptedException {

        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 2;
        });
    }

    private static CompletableFuture<Integer> test3() throws InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 3;
        });
    }
    
    /**
     *如下代码，我们要计算三个接口的返回值进行汇总，每个接口耗时两秒，同步执行
     *需要消耗6秒，使用Completable后只需要2秒， anyOff是将多个执行完毕的
     *CompletableFuture进行计算
     *
     */
    private static CompletableFuture<Integer> solution() throws InterruptedException {
        CompletableFuture<Integer> test1 = test1();
        CompletableFuture<Integer> test2 = test2();
        CompletableFuture<Integer> test3 = test3();
        return CompletableFuture
                .anyOf(test1,test2,test3)
                .thenApply(v -> {
                    Integer i = 0;
                    try {
                        Integer integer = test1.get();
                        Integer integer1 = test2.get();
                        Integer integer2 = test3.get();
                        i = integer + integer1 + integer2;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    return i;
                });
    }
```

