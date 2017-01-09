package loop.Simulation;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import loop.ObjectsInSimulation.Population;

public class CreateGui implements Runnable{

    private JFrame frame;
    private Population p;

    public CreateGui(Population pop) {
        this.p = pop;

    }

    @Override
    public void run() {
        frame = new JFrame();
        frame.setPreferredSize(new Dimension(1000, 800));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponent(new Simulation(p));
        frame.pack();
        frame.setVisible(true);
    }

    public void addComponent(Container c) {
        frame.add(c);
    }
    
    public void addButtons(Container c){
        
    }
}
