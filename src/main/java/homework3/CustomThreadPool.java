package homework3;

import java.util.LinkedList;

public class CustomThreadPool {
    private final LinkedList<Runnable> tasks;
    private final Worker[] workers;
    private final Object monitor;
    private volatile boolean isShutdown;

    public CustomThreadPool(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Количество рабочих потоков должно быть больше 0");
        }
        tasks = new LinkedList<>();
        workers = new Worker[capacity];
        for (int i = 0; i < capacity; i++) {
            Worker worker = new Worker();
            workers[i] = worker;
            new Thread(worker, "worker_from_custom_pool: " + i).start();
        }
        monitor = new Object();
        isShutdown = false;
    }

    public void execute(Runnable task) {
        synchronized (monitor) {
            if (isShutdown) {
                throw new IllegalStateException("Был вызван shutdown, задачи больше не принимаются");
            }
            tasks.addFirst(task);
            monitor.notify();
        }
    }

    public void shutdown() {
        synchronized (monitor) {
            isShutdown = true;
            monitor.notifyAll();
        }
    }

    public boolean awaitTermination() throws InterruptedException {
        for (Worker worker : workers) {
            worker.thread.join();
        }
        return true;
    }

    private class Worker implements Runnable {
        private Thread thread;

        @Override
        public void run() {
            Runnable task;
            thread = Thread.currentThread();
            while (!tasks.isEmpty() || !isShutdown) {
                synchronized (monitor) {
                    if (tasks.isEmpty() && !isShutdown) {
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                            thread.interrupt();
                        }
                    }
                    task = tasks.pollLast();
                }
                if (task != null) {
                    task.run();
                }
            }
        }
    }
}
