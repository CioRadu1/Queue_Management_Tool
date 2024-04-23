package org.example.Logical;

public class Task {
    private int taskId;
    private int arrivalTime;
    private int serviceTime;

    public Task(int taskId, int arrivalTime, int serviceTime) {
        this.taskId = taskId;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getTaskId() {
        return taskId;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }
    @Override
    public String toString() {
        return "(id: " + taskId + ", arrivalTime: " + arrivalTime + ", serviceTime: " + serviceTime + ")";
    }
}

