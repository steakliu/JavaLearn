package thread.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    private static Integer count = 1000;

    private static int i = 0;

    private synchronized static void dec(){
        i++;
        count--;
    }

    public static void main(String[] args) {
        for (int i = 0 ; i < 1000 ; i++){
            executorService.submit(ThreadPool::dec);
        }
        executorService.shutdown();
        System.out.println(count);
        System.out.println(i);
    }
}
