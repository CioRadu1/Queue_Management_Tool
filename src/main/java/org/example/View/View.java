package org.example.View;

import org.example.Logical.Server;
import org.example.Logical.Task;

import javax.print.event.PrintJobAttributeListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.concurrent.BlockingQueue;


public class View extends JFrame{


    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JPanel inputPanel;
    private JPanel maxAndMinPanel;
    private JPanel dropPanel;

    private JButton startButton;
    private JButton stopButton;
    private JComboBox<String> strategySelect;
    private JFrame allFrame;
    private JFrame invalidInputFrame;
    private JFrame finishedFrame;
    private JFrame queueFrame;

    private JTextField numberOfClientsTF;
    private JTextField numberOfQueuesTF;
    private JTextField simulationIntervalTF;
    private JTextField minArrivalTimeTF;
    private JTextField maxArrivalTimeTF;
    private JTextField minServiceTimeTF;
    private JTextField maxServiceTimeTF;

    private JLabel nrOfClientsLable;
    private JLabel nrOfQueuesLable;
    private JLabel simulationIntervalLable;
    private JLabel minArrivalTimeLable;
    private JLabel maxArrivalTimeLable;
    private JLabel maxServiceTimeLable;
    private JLabel minServiceTimeLable;
    private JLabel intervalLabel;
    private JLabel intervalLabel2;
    private JLabel intervalLabel3;
    private JLabel intervalLabel4;



    protected void beautifyButton(JButton button, Color color)
    {
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(100 ,30));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(button.getFont().deriveFont(Font.ITALIC,12));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(145, 99, 31));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(86, 58, 27, 255));
            }
        });

    }

    protected void beautifyLabel(JLabel label){

        label.setPreferredSize(new Dimension(100,20));
        label.setBackground(new Color(107, 69, 0));
        label.setForeground(Color .WHITE);
        label.setFont(new Font("Calibre", Font.ITALIC, 14));
        label.setOpaque(true);

    }

    protected void beautifyLabelMaxAndMin(JLabel label){

        label.setPreferredSize(new Dimension(100,20));
        label.setBackground(new Color(107, 69, 0));
        label.setForeground(Color .WHITE);
        label.setFont(new Font("Calibre", Font.ITALIC, 10));
        label.setOpaque(true);

    }

    protected void beautifyTextFields(JTextField textField){

        textField.setEditable(true);
        textField.setCaretColor(Color.WHITE);
        textField.setPreferredSize(new Dimension(200, 25));
        textField.setBackground(new Color(79, 57, 20));
        textField.setBorder(new LineBorder(new Color(58, 39, 11, 255)));
        textField.setForeground(Color.WHITE);

    }

    protected void beautifyTextFieldsMaxAndMin(JTextField textField){

        textField.setEditable(true);
        textField.setCaretColor(Color.WHITE);
        textField.setPreferredSize(new Dimension(70, 25));
        textField.setBackground(new Color(79, 57, 20));
        textField.setBorder(new LineBorder(new Color(58, 39, 11, 255)));
        textField.setForeground(Color.WHITE);

    }

    public void displayFinishedMessage() {
        JOptionPane.showMessageDialog(finishedFrame,
                "Simulation has finished. Check SimulationResult.txt",
                "Simulation Finished",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void displayInvalidInputMessage() {
        JOptionPane.showMessageDialog(invalidInputFrame,
                "INVALID INPUT!",
                "Error!",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public View (){

        allFrame = new JFrame("Queue Simulation Tool");
        finishedFrame = new JFrame("Finished!");
        invalidInputFrame = new JFrame("Invalid Input!");
        ImageIcon img = new ImageIcon("C:\\Users\\fdrff\\IdeaProjects\\PT2024_30222_Ciobanu_Radu-Rares_Assignment_2\\Queue.png");

        mainPanel = new JPanel();
        inputPanel = new JPanel();
        maxAndMinPanel = new JPanel();
        buttonPanel = new JPanel();
        dropPanel = new JPanel();

        strategySelect = new JComboBox<String>();
        strategySelect.setBackground(new Color(79, 57, 20));
        strategySelect.setPreferredSize(new Dimension(100,25));
        strategySelect.setEditable(false);
        strategySelect.setForeground(Color.WHITE);
        strategySelect.setBorder(new LineBorder(new Color(58, 39, 11, 255)));
        strategySelect.addItem("Shortest Queue Strategy");
        strategySelect.addItem("Shortest Time Strategy");

        startButton = new JButton("Start");
        stopButton = new JButton("Stop");

        startButton.setMaximumSize(new Dimension(70,40));
        stopButton.setMaximumSize(new Dimension(70, 40));

        beautifyButton(startButton,new Color(79, 57, 20));
        beautifyButton(stopButton,new Color(79, 57, 20));

        numberOfClientsTF = new JTextField();
        numberOfQueuesTF = new JTextField();
        simulationIntervalTF = new JTextField();
        minArrivalTimeTF = new JTextField();
        maxArrivalTimeTF = new JTextField();
        minServiceTimeTF = new JTextField();
        maxServiceTimeTF = new JTextField();

        beautifyTextFields(numberOfClientsTF);
        beautifyTextFields(numberOfQueuesTF);
        beautifyTextFields(simulationIntervalTF);
        beautifyTextFieldsMaxAndMin(minArrivalTimeTF);
        beautifyTextFieldsMaxAndMin(maxArrivalTimeTF);
        beautifyTextFieldsMaxAndMin(minServiceTimeTF);
        beautifyTextFieldsMaxAndMin(maxServiceTimeTF);

        nrOfClientsLable = new JLabel("Number of clients:");
        nrOfQueuesLable = new JLabel("Number of queues:");
        simulationIntervalLable = new JLabel("Maximum Simulation Time:");
        minServiceTimeLable = new JLabel("Minimum Service Time:");
        maxServiceTimeLable = new JLabel("Maximum Service Time:");
        minArrivalTimeLable = new JLabel("Minimum Arrival Time:");
        maxArrivalTimeLable = new JLabel("Maximum Arrival Time:");

        beautifyLabel(nrOfClientsLable);
        beautifyLabel(nrOfQueuesLable);
        beautifyLabel(simulationIntervalLable);
        beautifyLabelMaxAndMin(minServiceTimeLable);
        beautifyLabelMaxAndMin(maxServiceTimeLable);
        beautifyLabelMaxAndMin(minArrivalTimeLable);
        beautifyLabelMaxAndMin(maxArrivalTimeLable);

        inputPanel.setLayout(new GridLayout(6,1));
        inputPanel.setBorder(new EmptyBorder(0,100,0,100));
        inputPanel.add(nrOfClientsLable);
        inputPanel.add(numberOfClientsTF);
        inputPanel.add(nrOfQueuesLable);
        inputPanel.add(numberOfQueuesTF);
        inputPanel.add(simulationIntervalLable);
        inputPanel.add(simulationIntervalTF);
        inputPanel.setBackground(new Color(107, 69, 0));

        maxAndMinPanel.setLayout(new GridLayout(4,2,5,0));
        maxAndMinPanel.setBorder(new EmptyBorder(20,50,20,50));
        maxAndMinPanel.add(minArrivalTimeLable);
        maxAndMinPanel.add(maxArrivalTimeLable);
        maxAndMinPanel.add(minArrivalTimeTF);
        maxAndMinPanel.add(maxArrivalTimeTF);
        maxAndMinPanel.add(minServiceTimeLable);
        maxAndMinPanel.add(maxServiceTimeLable);
        maxAndMinPanel.add(minServiceTimeTF);
        maxAndMinPanel.add(maxServiceTimeTF);
        maxAndMinPanel.setBackground(new Color(107, 69, 0));

        dropPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        dropPanel.setBorder(new EmptyBorder(0,0,20,0));
        dropPanel.add(strategySelect);
        dropPanel.setBackground(new Color(107, 69, 0));

        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(new EmptyBorder(20,0,20,0));
        buttonPanel.add(startButton);
        buttonPanel.setBackground(new Color(107, 69, 0));

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.add(inputPanel);
        mainPanel.add(maxAndMinPanel);
        mainPanel.add(strategySelect);
        mainPanel.add(buttonPanel);
        mainPanel.setBackground(new Color(107, 69, 0));

        allFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        allFrame.setIconImage(img.getImage());
        Point posOfFrame = new Point(550,300);
        allFrame.add(mainPanel);
        allFrame.pack();
        allFrame.setLocation(posOfFrame);
        allFrame.setResizable(false);
        allFrame.setVisible(true);

        finishedFrame.setBackground(new Color(107, 69, 0));
        finishedFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
        finishedFrame.setSize(300, 100);

        invalidInputFrame.setBackground(new Color(107, 69, 0));
        invalidInputFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
        invalidInputFrame.setSize(300, 100);

    }


    public JButton getStartButton() {
        return startButton;
    }

    public JButton getStopButton() {
        return stopButton;
    }

    public JFrame getAllFrame() {
        return allFrame;
    }

    public JFrame getInvalidInputFrame() {
        return invalidInputFrame;
    }

    public JFrame getQueueFrame() {
        return queueFrame;
    }

    public JTextField getNumberOfClientsTF() {
        return numberOfClientsTF;
    }

    public JTextField getNumberOfQueuesTF() {
        return numberOfQueuesTF;
    }

    public JTextField getSimulationIntervalTF() {
        return simulationIntervalTF;
    }

    public JTextField getMinArrivalTimeTF() {
        return minArrivalTimeTF;
    }

    public JTextField getMaxArrivalTimeTF() {
        return maxArrivalTimeTF;
    }

    public JTextField getMinServiceTimeTF() {
        return minServiceTimeTF;
    }

    public JTextField getMaxServiceTimeTF() {
        return maxServiceTimeTF;
    }

    public JComboBox<String> getStrategySelect() {
        return strategySelect;
    }

    public JFrame getFinishedFrame() {
        return finishedFrame;
    }
}
