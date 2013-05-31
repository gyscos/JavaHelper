package com.helper.thread;

import java.util.LinkedList;
import java.util.List;

/**
 * Generic worker pool implementation.
 * 
 * @author gyscos
 * 
 */
public class WorkerPool implements Runnable {
    LinkedList<Runnable> tasks;

    List<Thread>         workers;

    int                  n;

    boolean              waiting = true;

    /**
     * Creates a worker pool with a fixed number of threads.
     * 
     * @param n
     *            Number of threads used. Should be > 0.
     */
    public WorkerPool(int n) {
        assert (n > 0);

        this.n = n;
    }

    public synchronized void addTask(Runnable task) {
        tasks.addLast(task);
        notifyAll();
    }

    /**
     * Fill the pool with fresh workers.
     */
    synchronized void createThreads() {
        workers.clear();
        for (int i = 0; i < n; i++)
            workers.add(new Thread(this));
    }

    /**
     * Wait for everyone to come home, and close the shop.
     */
    public void end() {
        // All tasks should have been added by now.
        waiting = false;

        for (Thread thread : workers)
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    synchronized Runnable getTask() {
        if (tasks.isEmpty())
            return null;
        return tasks.removeFirst();
    }

    /**
     * Start working !
     * This will return when all tasks are completed.
     */
    public void launch() {
        start();
        end();
    }

    @Override
    public void run() {
        while (true) {
            Runnable task = getTask();
            if (task == null)
                if (waiting)
                    synchronized (this) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                else
                    break;

            task.run();
        }
    }

    public void start() {
        if (workers.isEmpty())
            createThreads();

        for (Thread thread : workers)
            thread.start();
    }
}
