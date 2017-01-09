package loop.ActionListeners;

import loop.ObjectsInSimulation.Barrier;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import loop.Simulation.Simulation;

public class MMouseListener implements MouseListener {

    private List<Barrier> barriers;
    private Simulation s;

    public MMouseListener(Simulation s, List<Barrier> barriers) {
        s.addMouseListener(this);
        this.s = s;
        this.barriers = barriers;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getX() < 700 && e.getY() < 700) {
            barriers.add(new Barrier(e.getY(), e.getX(), s.getBarrierLen()));
            
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    } 

}
