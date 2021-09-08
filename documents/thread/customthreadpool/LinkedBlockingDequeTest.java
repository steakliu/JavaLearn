package thread.customthreadpool;

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeTest {

    private static final LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();

    public static void main(String[] args) throws InterruptedException {
        deque.put(1);
        deque.put(2);
        deque.put(3);
        deque.put(4);
        deque.put(5);
        System.out.println(deque);
        System.out.println("deque size  "+deque.size());
        deque.take();
        deque.take();
        deque.take();
        deque.take();
        deque.take();
        System.out.println(deque);
        System.out.println("deque size  "+deque.size());
    }
}
