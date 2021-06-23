package at.ac.fhcampuswien.newsanalyzer.downloader;

import at.ac.fhcampuswien.newsanalyzer.ctrl.Controller;
import at.ac.fhcampuswien.newsanalyzer.ctrl.NewsAPIException;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class ParallelDownloader extends Downloader implements ExecutorService, Future {

    public void run(){//new
    long start = System.nanoTime();
        int worker = Runtime.getRuntime().availableProcessors();//number of threads available returned
        ExecutorService pool = Executors.newFixedThreadPool(worker);//create pool that reuses a fixed number of threads

        List<Callable<String>> callables = new ArrayList<>();// List holds callable results

        for(int i = 0; i < Controller.urlList.size(); i++){//tasks dynamically created
            int index = i;
            String str = Controller.urlList.get(index);// needed for saveUrl2File(String)
            Callable<String> task = () -> saveUrl2File(str);// async function as lambda
            callables.add(task);// add future objects to array
        }
        try{
            List<Future<String>> allFutures = pool.invokeAll(callables);
            // invokeall=> executes the given list of callable tasks => returns list of future objects holding status and results when complete

            for (Future<String> f: allFutures){
                String result = f.get();
                System.out.println("Result: "+result);

            }
        }catch (InterruptedException | ExecutionException e){
            System.err.println(e.getMessage());
        }finally {
            pool.shutdown();
        }
        long end = System.nanoTime();
        System.out.println("Time: "+ (end-start)/1000000 + " milliseconds");
    }

    @Override
    public int process(List<String> urls) {
        return 0;
    }



    @Override
    public void shutdown() {

    }

    @NotNull
    @Override
    public List<Runnable> shutdownNow() {
        return null;
    }

    @Override
    public boolean isShutdown() {
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public boolean awaitTermination(long timeout, @NotNull TimeUnit unit) throws InterruptedException {
        return false;
    }

    @NotNull
    @Override
    public <T> Future<T> submit(@NotNull Callable<T> task) {
        return null;
    }

    @NotNull
    @Override
    public <T> Future<T> submit(@NotNull Runnable task, T result) {
        return null;
    }

    @NotNull
    @Override
    public Future<?> submit(@NotNull Runnable task) {
        return null;
    }

    @NotNull
    @Override
    public <T> List<Future<T>> invokeAll(@NotNull Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return null;
    }

    @NotNull
    @Override
    public <T> List<Future<T>> invokeAll(@NotNull Collection<? extends Callable<T>> tasks, long timeout, @NotNull TimeUnit unit) throws InterruptedException {
        return null;
    }

    @NotNull
    @Override
    public <T> T invokeAny(@NotNull Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public <T> T invokeAny(@NotNull Collection<? extends Callable<T>> tasks, long timeout, @NotNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    @Override
    public void execute(@NotNull Runnable command) {//change

    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public Object get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public Object get(long timeout, @NotNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }


}
