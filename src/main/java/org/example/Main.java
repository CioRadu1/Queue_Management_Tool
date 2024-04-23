package org.example;

import org.example.Business.SimulationManager;
import org.example.Controller.Controller;
import org.example.View.View;

public class Main {
    public static void main(String[] args) {
        View av = new View();
        SimulationManager gen = new SimulationManager();
        Controller controller = new Controller(av, gen);
        Thread thread = new Thread(gen);
        thread.start();
    }
}