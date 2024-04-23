package org.example.Business;

import org.example.Logical.Server;
import org.example.Logical.Task;
import org.example.View.View;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.BlockingQueue;

public class SimulationManager implements Runnable {
    private int timeLimit;
    private int maxArrivalTime;
    private int minArrivalTime;
    private int maxProcessingTime;
    private int minProcessingTime;
    private int numberOfServers;
    private int numberOfClients;
    private SelectionPolicy selectionPolicy;
    private Scheduler scheduler;
    private List<Task> generatedTasks = new ArrayList<>();
    private double totalServiceTime = 0.0;
    private int maxQueuePeak = 0;
    private int peakHour = 0;

    public SimulationManager() {}

    public void setAllParameters(int timeLimit, int maxArrivalTime, int minArrivalTime, int maxProcessingTime, int minProcessingTime, int numberOfServers, int numberOfClients, SelectionPolicy selectionPolicy) {
        this.timeLimit = timeLimit;
        this.maxArrivalTime = maxArrivalTime;
        this.minArrivalTime = minArrivalTime;
        this.maxProcessingTime = maxProcessingTime;
        this.minProcessingTime = minProcessingTime;
        this.numberOfServers = numberOfServers;
        this.numberOfClients = numberOfClients;
        this.selectionPolicy = selectionPolicy;
    }

    public void initialize() {
        scheduler = new Scheduler(numberOfServers, numberOfClients);
        for (Server server : scheduler.getServers()) {
            new Thread(server).start();
        }
        scheduler.changeStrategy(selectionPolicy);
        generateNRRandomTasks();
    }

    public boolean textVerify (String textVeryfy){
        boolean test = true;
        for(char temp : textVeryfy.toCharArray()){
            if(!(temp >= 48 && temp <= 57)){
                test = false;
                break;
            }
        }
        return test;
    }
    private void generateNRRandomTasks() {
        Random random = new Random();
        Task[] tasksArray = new Task[numberOfClients];
        for (int i = 0; i < numberOfClients; i++) {
            int arrivalTime = random.nextInt(maxArrivalTime - minArrivalTime + 1) + minArrivalTime;
            int serviceTime = random.nextInt(maxProcessingTime - minProcessingTime + 1) + minProcessingTime;
            Task task = new Task(i + 1, arrivalTime, serviceTime);
            tasksArray[i] = task;
        }
        Arrays.sort(tasksArray, Comparator.comparingInt(Task::getArrivalTime));
        generatedTasks.addAll(Arrays.asList(tasksArray));
    }

    @Override
    public void run() {
        int currentTime = 0;
        int taskIndex = 0;
        try (FileWriter writer = new FileWriter("SimulationResult.txt")) {
            while (currentTime <= timeLimit && !generatedTasks.isEmpty()) {

                for (Server server : scheduler.getServers()) {
                    int maxQueue = server.getTasks().length;
                    for (Task task : server.getTasks()) {
                        if (maxQueue > maxQueuePeak) {
                            maxQueuePeak = maxQueue;
                            peakHour = currentTime;
                        }
                        if(task.getServiceTime() > 0) {
                            totalServiceTime += task.getServiceTime();
                        }
                    }
                }
                while (taskIndex < generatedTasks.size() && generatedTasks.get(taskIndex).getArrivalTime() <= currentTime) {
                    Task task = generatedTasks.get(taskIndex);
                    scheduler.dispatchTask(task);
                    taskIndex++;
                }
                for (int i = generatedTasks.size() - 1; i >= 0; i--) {
                    Task task = generatedTasks.get(i);
                    if (task.getArrivalTime() <= currentTime) {
                        scheduler.dispatchTask(task);
                        if (task.getServiceTime() <= 0) {
                            generatedTasks.remove(i);
                        }
                    }
                }
                writer.write("Time " + currentTime + "\n");
                writer.write("Waiting clients: " + generatedTasks + "\n");

                Set<Task> printedTasks = new HashSet<>();

                for (int i = 0; i < numberOfServers; i++) {
                    Server server = scheduler.getServers().get(i);
                    Task[] tasks = server.getTasks();
                    writer.write("Queue " + (i + 1) + ": ");
                    for (Task task : tasks) {
                        if (!printedTasks.contains(task)) {
                            writer.write(task.toString() + ", ");
                            printedTasks.add(task);
                        }
                    }
                    writer.write("\n");
                }
                writer.write("\n");
                currentTime++;
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            double avgWaitingTime = (double) Server.getTotalWaitingTime() / numberOfClients;
            double avgServiceTime = totalServiceTime / Server.getTasksCompleted();

            writer.write("Average Waiting Time: " + avgWaitingTime + "\n");
            writer.write("Average Service Time: " + avgServiceTime + "\n");
            writer.write("Peak Hour: " + peakHour + "\n");
        }
         catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public int getMaxArrivalTime() {
        return maxArrivalTime;
    }

    public int getMinArrivalTime() {
        return minArrivalTime;
    }

    public int getMaxProcessingTime() {
        return maxProcessingTime;
    }

    public int getMinProcessingTime() {
        return minProcessingTime;
    }

    public int getNumberOfServers() {
        return numberOfServers;
    }

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public SelectionPolicy getSelectionPolicy() {
        return selectionPolicy;
    }


    public Scheduler getScheduler() {
        return scheduler;
    }

    public List<Task> getGeneratedTasks() {
        return generatedTasks;
    }

    public double getTotalServiceTime() {
        return totalServiceTime;
    }

    public int getMaxQueuePeak() {
        return maxQueuePeak;
    }

    public int getPeakHour() {
        return peakHour;
    }
}
