package org.example.Controller;

import org.example.Business.SelectionPolicy;
import org.example.Business.SimulationManager;
import org.example.View.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {

    View pv;
    SimulationManager manager;
    public Controller (View view, SimulationManager manager) {

        this.pv = view;
        this.manager = manager;
        pv.getStopButton().addActionListener(this);
        pv.getStartButton().addActionListener(this);

    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == pv.getStartButton()){
            if(     !manager.textVerify(pv.getSimulationIntervalTF().getText()) || !manager.textVerify(pv.getNumberOfQueuesTF().getText())
                    || !manager.textVerify(pv.getMinServiceTimeTF().getText()) || !manager.textVerify(pv.getNumberOfClientsTF().getText())
                    || !manager.textVerify(pv.getMaxServiceTimeTF().getText()) || !manager.textVerify(pv.getMinArrivalTimeTF().getText())
                    || !manager.textVerify(pv.getMaxArrivalTimeTF().getText()) || pv.getSimulationIntervalTF().getText().isEmpty()
                    || pv.getNumberOfQueuesTF().getText().isEmpty() || pv.getNumberOfClientsTF().getText().isEmpty()
                    || pv.getMinArrivalTimeTF().getText().isEmpty() || pv.getMaxArrivalTimeTF().getText().isEmpty()
                    || pv.getMinServiceTimeTF().getText().isEmpty() || pv.getMaxServiceTimeTF().getText().isEmpty()
                    || Integer.parseInt(pv.getSimulationIntervalTF().getText()) < Integer.parseInt(pv.getMaxArrivalTimeTF().getText())
                    || Integer.parseInt(pv.getSimulationIntervalTF().getText()) < Integer.parseInt(pv.getMinArrivalTimeTF().getText())
                    || Integer.parseInt(pv.getSimulationIntervalTF().getText()) < Integer.parseInt(pv.getMaxServiceTimeTF().getText())
                    || Integer.parseInt(pv.getSimulationIntervalTF().getText()) < Integer.parseInt(pv.getMinServiceTimeTF().getText())
            ){
                pv.displayInvalidInputMessage();
            }
            else{
                if(pv.getStrategySelect().getSelectedItem().toString().equals("Shortest Queue Strategy")) {
                    manager.setAllParameters(Integer.parseInt(pv.getSimulationIntervalTF().getText()), Integer.parseInt(pv.getMaxArrivalTimeTF().getText()),
                            Integer.parseInt(pv.getMinArrivalTimeTF().getText()), Integer.parseInt(pv.getMaxServiceTimeTF().getText()), Integer.parseInt(pv.getMinServiceTimeTF().getText()),
                            Integer.parseInt(pv.getNumberOfQueuesTF().getText()), Integer.parseInt(pv.getNumberOfClientsTF().getText()), SelectionPolicy.SHORTEST_QUEUE
                    );
                    manager.initialize();
                }
                else if(pv.getStrategySelect().getSelectedItem().toString().equals("Shortest Time Strategy")){
                    manager.setAllParameters(Integer.parseInt(pv.getSimulationIntervalTF().getText()), Integer.parseInt(pv.getMaxArrivalTimeTF().getText()),
                            Integer.parseInt(pv.getMinArrivalTimeTF().getText()), Integer.parseInt(pv.getMaxServiceTimeTF().getText()), Integer.parseInt(pv.getMinServiceTimeTF().getText()),
                            Integer.parseInt(pv.getNumberOfQueuesTF().getText()), Integer.parseInt(pv.getNumberOfClientsTF().getText()), SelectionPolicy.SHORTEST_TIME
                    );
                    manager.initialize();
                }
                manager.run();
                pv.displayFinishedMessage();
            }
        }
    }


}
