package concurrent.ForkJoin;

import java.util.concurrent.*;

public class ForkJoinTest3 {

    private final static Integer CPU = Runtime.getRuntime().availableProcessors();

    final static ExecutorService execute = Executors.newFixedThreadPool(20);

    private volatile static Integer i = 0;

    private void add(){
        synchronized(ForkJoinTest3.class){
            i++;
        }

    }

    public static void main(String[] args) {
        ForkJoinTest3 forkJoinTest3 = new ForkJoinTest3();
        for (int i = 0 ; i < 10 ; i++){
            Runnable runnable = new Runnable(){
                @Override
                public void run() {
                    forkJoinTest3.add();
                }
            };
            execute.execute(runnable);
        }
        execute.shutdown();
        System.out.println(i);
    }
}
