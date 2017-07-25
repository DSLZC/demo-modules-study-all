package com.dslcode.core.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by dongsilin on 2017/7/21.
 */
public class ThreadPoolUtil {

    private static ExecutorService executorService = null;
    private static final Lock lock = new ReentrantLock();
    private static final int THREAD_SIZE = 5;

    /**
     * 初始化线程池
     * @param threadSize 池容量大小
     */
    public static final void initPool(int threadSize){
        if (null == executorService){
            lock.lock();
            try {
                executorService = Executors.newFixedThreadPool(threadSize);
            }finally {
                lock.unlock();
            }
        }
    }

    /**
     * 关闭线程池
     */
    public static final void closePool(){
        if (null != executorService) executorService.shutdown();
    }

    /**
     * 执行Runnable任务
     * @param task
     */
    public static final void run(Runnable task){
        initPool(THREAD_SIZE);
        executorService.submit(task);
    }

    /**
     * 执行Callable任务
     * @param task
     * @param <T> 返回值类型
     * @return Future实例，通过调用Future.get()获得线程返回值
     */
    public static final <T> Future<T> runCallable(Callable<T> task){
        initPool(THREAD_SIZE);
        return executorService.submit(task);
    }

//    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        run(() -> {
//            int sum = 0;
//            for(int i = 1; i < 10; i++) {
//                sum += i;
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                }
//            }
//            System.out.println(sum);
//        });
//        Future<Integer> future =  run(() -> {
//            int sum = 0;
//            for(int i = 1; i < 10; i++) {
//                sum += i;
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                }
//            }
//            System.out.println("f =  "+ sum);
//            return sum;
//        });
//        System.out.println("ff = " + future.get());
//
//        Thread.sleep(2000);
//        closePool();
//    }
}
