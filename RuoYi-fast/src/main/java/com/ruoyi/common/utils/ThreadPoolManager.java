package com.ruoyi.common.utils;
 
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
 
/**
 * Created by malei on 2018/9/30.
 */
 
public class ThreadPoolManager<T> {
 
    /**
     * 根据cpu的数量动态的配置核心线程数和最大线程数
     */
    private static final int CPU_COUNT         = Runtime.getRuntime().availableProcessors();
//    private static final int CPU_COUNT         = 2;
    /**
     * 核心线程数 = CPU核心数 + 1
     */
    private static final int CORE_POOL_SIZE    = CPU_COUNT + 1;
//    private static final int CORE_POOL_SIZE    = CPU_COUNT;
    /**
     * 线程池最大线程数 = CPU核心数 * 2 + 1
     */
//    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    /**
     * 非核心线程闲置时超时1s
     */
    private static final int KEEP_ALIVE        = 1;
//    /**
//     *  线程池的对象
//     */
//    private ThreadPoolExecutor executor;
 
    /**
     * 要确保该类只有一个实例对象，避免产生过多对象消费资源，所以采用单例模式
     */
//    private ThreadPoolManager() {
//    }
// 
//    private static ThreadPoolManager sInstance;
// 
//    public ThreadPoolManager<Object> getsInstance() {
//        if (sInstance == null) {
//            sInstance = ;
//        }
//        return new ThreadPoolManager<Object>();
//    }
 
    /**
     * 开启一个无返回结果的线程
     * @param r
     */
    public static ThreadPoolExecutor getExecute(int poolSize) {
//        if (executor == null) {
            /**
             * corePoolSize:核心线程数
             * maximumPoolSize：线程池所容纳最大线程数(workQueue队列满了之后才开启)
             * keepAliveTime：非核心线程闲置时间超时时长
             * unit：keepAliveTime的单位
             * workQueue：等待队列，存储还未执行的任务
             * threadFactory：线程创建的工厂
             * handler：异常处理机制
             *
             */
    	int size = poolSize == 0 ? CORE_POOL_SIZE : poolSize;
    	ThreadPoolExecutor executor = new ThreadPoolExecutor(size, MAXIMUM_POOL_SIZE,
                    KEEP_ALIVE, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000),
                    Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
//        }
//         把一个任务丢到了线程池中
//        executor.execute(r);
		return executor;
    }
// 
//    /**
//     * 开启一个有返回结果的线程
//     * @param r
//     * @return
//     */
//    public Future<T> submit(Callable<T> r) {
//        if (executor == null) {
//            /**
//             * corePoolSize:核心线程数
//             * maximumPoolSize：线程池所容纳最大线程数(workQueue队列满了之后才开启)
//             * keepAliveTime：非核心线程闲置时间超时时长
//             * unit：keepAliveTime的单位
//             * workQueue：等待队列，存储还未执行的任务
//             * threadFactory：线程创建的工厂
//             * handler：异常处理机制
//             *
//             */
//            executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
//                    KEEP_ALIVE, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20),
//                    Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
//        }
//        // 把一个任务丢到了线程池中
//        return executor.submit(r);
//    }
 
//    /**
//     * 把任务移除等待队列
//     * @param r
//     */
//    public void cancel(Runnable r) {
//        if (r != null) {
//            executor.getQueue().remove(r);
//        }
//    }
 
}
