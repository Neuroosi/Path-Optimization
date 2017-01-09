package loop.ActionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import loop.Simulation.Simulation;

public class ButtonActionListener implements ActionListener {

    private JButton increase;
    private JButton decrease;
    private Simulation s;

    public ButtonActionListener(Simulation s, JButton increase, JButton decrease) {
        this.increase = increase;
        this.decrease = decrease;
        increase.addActionListener(this);
        decrease.addActionListener(this);
        this.s = s;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == increase) {
            s.increaseBarrier();
        } else if (e.getSource() == decrease) {
            s.decreaseBarrier();
        }
        s.labelC();
        s.updateJLabelValue();
    }

}
