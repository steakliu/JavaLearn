package concurrent.Future;


import java.util.concurrent.*;

public class FutureTest {

    final static  ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 返回值为Future,
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

    /**
     * 模拟rpc接口
     * @param num
     * @return
     */
    public static Boolean getMemberStatusRpcApi(Integer num){
        return num > 10;
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        Future<?> future = futureTask(20);
        boolean cancelled = future.isCancelled();
        //System.out.println(cancelled);
        if (future.isDone()){
            System.out.println(future.get());
        }

    }
}
