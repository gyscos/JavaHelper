package com.helper.thread;

import java.util.LinkedList;
import java.util.List;

public class WorkerPool implements Runnable {
    LinkedList<Runnable> tasks;

    List<Thread>         workers;

    int                  n;

    boolean              waiting = true;

    /**
     * Creates a worker pool with the given number of threads
     * 
     * @param n
     */
    public WorkerPool(int n) {
        this.n = n;
    }

    public synchronized void addTask(Runnable task) {
        tasks.addLast(task);
        notifyAll();
    }

    public void createThreads() {
        for (int i = 0; i < n; i++)
            workers.add(new Thread(this));
    }

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

    public synchronized Runnable getTask() {
        if (tasks.isEmpty())
            return null;
        return tasks.removeFirst();
    }

    public void launch() {
        createThreads();
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
        for (Thread thread : workers)
            thread.start();
    }
}
