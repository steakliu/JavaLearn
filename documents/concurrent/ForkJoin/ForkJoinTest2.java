package concurrent.ForkJoin;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class ForkJoinTest2 {

    private static final Integer N = 20000000;

    static {

    }

    private volatile static long res = 0;

    private final static Integer CPU = Runtime.getRuntime().availableProcessors();

    final static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(CPU * 2,
            CPU * 20,
            4,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(100));

    static long solution(long[] array) {
        for (long l : array) {
            res += l;
        }
        return res;
    }

    static Random random = new Random(0);

    static long random() {
        return random.nextInt(10);
    }

    public static void main(String[] args) {
        //long[] array = new long[N];
        long[] array = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
//        for (int i = 0 ; i < array.length ; i++){
//            array[i] = random();
//        }
        long start = System.currentTimeMillis();
        Long solution = solution(array);
        System.out.println("cost time : " + (System.currentTimeMillis() - start));
        System.out.println("solution result  " + solution);

    }
}
