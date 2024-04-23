package org.example.Logical;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private static int tasksCompleted = 0;
    private static int totalWaitingTime = 0;

    public Server(int maxTasksPerServer){
        tasks = new LinkedBlockingQueue<>(maxTasksPerServer);
        waitingPeriod = new AtomicInteger(0);
    }
    public void addTask(Task newTask){
        try {
            tasks.put(newTask);
            waitingPeriod.addAndGet(newTask.getServiceTime());
            totalWaitingTime += waitingPeriod.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (true) {
            if (!tasks.isEmpty()) {
                Task task = tasks.peek();
                while (task != null && task.getServiceTime() > 0) {
                    try {
                        Thread.sleep(550);
                        if(task.getServiceTime() > 0){
                            task.setServiceTime(task.getServiceTime() - 1);
                        }
                        waitingPeriod.getAndDecrement();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                tasks.poll();
                tasksCompleted++;
            }
        }
    }


    public Task[] getTasks() {
        return tasks.toArray(new Task[0]);
    }

    public static int getTasksCompleted() {
        return tasksCompleted;
    }

    public static int getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public int getWaitingPeriod() {
        return waitingPeriod.get();
    }
}
