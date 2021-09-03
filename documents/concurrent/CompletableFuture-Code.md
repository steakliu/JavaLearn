package com.tongtech.thridapi;

import java.util.concurrent.*;

public class CompletableFutureTest {

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
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return 3;
		});
	}

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

	public static Future<?> futureTask(Integer num){
		ExecutorService executorService = Executors.newCachedThreadPool();
		return executorService.submit(() -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return num + 1;
		});
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
//		long startTime = System.currentTimeMillis();
//		CompletableFuture<Integer> solution = solution();
//		System.out.println(solution.get());
//		System.out.println("cost time   "+(System.currentTimeMillis() - startTime));

		Future<?> future = futureTask(2);
		System.out.println(future.get());
	}
}
