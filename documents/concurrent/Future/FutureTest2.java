package concurrent.Future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureTest2 {

    final static ExecutorService executorService = Executors.newCachedThreadPool();

    private static void test1(){
        int s = 1/0;
        System.out.println("test 1");
    }

    private static void test2(){
        System.out.println("test 2");
    }

    private static void handleExecute(){
        executorService.execute(() -> {
            test1();
            test2();
        });
    }

    private static Future<?> handleSubmit(){
        return executorService.submit(() -> {
            test1();
            test2();
        });
    }

    public static void main(String[] args) {
        Future<?> future = handleSubmit();
        Object o = null;
        try {
             o = future.get();
        }catch (Exception e){
            e.printStackTrace();
        }
        
        System.out.println(o);
    }
}
