package loop;

import loop.ObjectsInSimulation.Population;
import loop.Simulation.CreateGui;

public class Loop {

    public static void main(String[] args) {
        Population p = new Population(10, 0.01, 10, 400, 400);
        CreateGui g = new CreateGui(p);
        g.run();
    }
}
