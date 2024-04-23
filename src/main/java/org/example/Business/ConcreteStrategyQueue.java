package org.example.Business;

import org.example.Logical.Server;
import org.example.Logical.Task;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy{
    @Override
    public void addTask(List<Server> servers, Task t){
        Server shortestQueueServer = null;
        int minQueueSize = Integer.MAX_VALUE;

        for (Server server : servers) {
            int queueSize = server.getWaitingPeriod();
            if (queueSize < minQueueSize) {
                minQueueSize = queueSize;
                shortestQueueServer = server;
            }
        }

        if (shortestQueueServer != null) {
            shortestQueueServer.addTask(t);
        } else {
            System.out.println("No servers available to add task.");
        }
    }
}
