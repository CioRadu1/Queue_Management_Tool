package org.example.Business;

import org.example.Logical.Server;
import org.example.Logical.Task;

import java.util.Comparator;
import java.util.List;

public class ConcreteStrategyTime implements Strategy{
    @Override
    public void addTask(List<Server> servers, Task t){

        Server earliestServer = servers.getFirst();

        for (Server server : servers) {
            if (server.getWaitingPeriod() == 0){
                earliestServer = server;
                break;
            }
            else if (server.getWaitingPeriod() < earliestServer.getWaitingPeriod()) {
                earliestServer = server;
            }
        }

        if (earliestServer != null) {
            earliestServer.addTask(t);
        } else {
            System.out.println("No servers available to add task.");
        }
    }
}
