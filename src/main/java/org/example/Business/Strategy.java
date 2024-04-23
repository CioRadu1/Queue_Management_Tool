package org.example.Business;

import org.example.Logical.Server;
import org.example.Logical.Task;

import java.util.List;

public interface Strategy {
    public void addTask(List<Server> servers, Task t);
}
