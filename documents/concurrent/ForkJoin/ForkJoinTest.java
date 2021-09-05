package concurrent.ForkJoin;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {
    private static final Integer N = 200000000;

    public static void main(String[] args) {
        long[] array = new long[N];
        for (int i = 0 ; i < array.length ; i++){
            array[i] = random();
        }
        long start = System.currentTimeMillis();
        ForkJoinTaskSum forkJoinTask = new ForkJoinTaskSum(0, array.length, array);
        ForkJoinPool pool = new ForkJoinPool();
        Long res = pool.invoke(forkJoinTask);
        System.out.println("cost time : "+(System.currentTimeMillis() - start));
        System.out.println(res);
    }
    static Random random = new Random(0);

    static long random(){
        return random.nextInt(10);
    }

    static class ForkJoinTaskSum extends RecursiveTask<Long> {

        private final Integer start;
        private final Integer end;
        private final long[] array;

        public ForkJoinTaskSum(Integer start, Integer end,long[] array) {
            this.start = start;
            this.end = end;
            this.array = array;
        }

        @Override
        protected Long compute() {
            if (end - start < N){
                long sum = 0;
                for (int i = start ; i < end ; i++){
                    sum+=this.array[i];
                }
                return sum;
            }
            int middle = (end + start) / 2;
            ForkJoinTaskSum forkJoinTask1 = new ForkJoinTaskSum(start, middle, this.array);
            ForkJoinTaskSum forkJoinTask2 = new ForkJoinTaskSum(middle, end, this.array);
            invokeAll(forkJoinTask1,forkJoinTask2);
            Long result1 = forkJoinTask1.join();
            Long result2 = forkJoinTask2.join();
            return result1 + result2;
        }
    }
}
