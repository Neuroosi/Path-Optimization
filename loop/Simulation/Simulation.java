package loop.Simulation;

import loop.ObjectsInSimulation.ColorGenerator;
import loop.ActionListeners.MMouseListener;
import loop.ActionListeners.ButtonActionListener;
import loop.ObjectsInSimulation.Barrier;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import loop.ObjectsInSimulation.DNA;
import loop.ObjectsInSimulation.Population;

public class Simulation extends JPanel implements ActionListener {

    private Population p;
    private ColorGenerator c;
    private Timer t;
    private Random r;
    private DNA invidual;
    private JLabel value;
    private int i;
    private int barrierSize;
    private double avgFitness;
    private boolean labelClear;
    private List<DNA> inviduals;
    private List<Barrier> barriers;

    public Simulation(Population p) {
        labelClear = false;
        setLayout(null);
        barrierSize = 0;
        createButtons();
        barriers = new ArrayList<>();
        inviduals = new ArrayList<>();
        this.p = p;
        this.c = new ColorGenerator();
        this.r = new Random();
        MMouseListener m = new MMouseListener(this, barriers);
        invidual = p.getDNA();
        getColor(invidual);
        i = 0;
        avgFitness = 0;
        t = new Timer(25, this);
        t.start();
    }

    public void increaseBarrier() {
        barrierSize++;
    }

    public void decreaseBarrier() {
        barrierSize--;
    }

    public void labelC() {
        labelClear = true;
    }

    public void createButtons() {
        JLabel text = new JLabel("[+/-] Barrier length:");
        text.setBounds(0, 710, 110, 15);
        text.setBorder(null);
        text.setVisible(true);
        this.add(text);
        JButton increase = new JButton("+");
        increase.setBounds(110, 710, 15, 15);
        increase.setBorder(null);
        increase.setVisible(true);
        this.add(increase);
        JButton decrease = new JButton("-");
        decrease.setBounds(140, 710, 15, 15);
        decrease.setBorder(null);
        decrease.setVisible(true);
        this.add(decrease);
        updateJLabelValue();
        ButtonActionListener listen = new ButtonActionListener(this, increase, decrease);
    }

    public void updateJLabelValue() {
        if (labelClear) {
            value.setText("");
        }
        value = new JLabel(Integer.toString(barrierSize));
        value.setBounds(125, 710, 15, 15);
        value.setBorder(null);
        value.setVisible(true);
        this.add(value);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("Generation: " + p.generationCounter(), 710, 10);
        g.drawString("Avg fitness: " + p.avgFitness(), 710, 20);
        g.drawString("Best invidual: " + p.bestFitness(), 710, 30);
        g.drawString("Invidual: " + invidual.getNum(), 710, 40);
        g.fillRect(700, 0, 5, 700);
        g.fillRect(0, 700, 705, 5);
        g.drawRect(p.goalX(), p.goalY(), 10, 10);
        drawPreviousInviduals(g);
        if (!barriers.isEmpty()) {
            drawBarriers(g);
        }
        g.setColor(invidual.getColor());
        g.drawRect(invidual.locationX(), invidual.locationY(), invidual.getSideLen(), invidual.getSideLen());
        g.fillRect(invidual.locationX(), invidual.locationY(), invidual.getSideLen(), invidual.getSideLen());
        t.start();
    }

    public void drawPreviousInviduals(Graphics g) {
        for (int j = 0; j < inviduals.size(); j++) {
            g.setColor(inviduals.get(j).getColor());
            g.fillRect(inviduals.get(j).locationX(), inviduals.get(j).locationY(), 5, 5);
        }
    }

    public void drawBarriers(Graphics g) {
        for (int j = 0; j < barriers.size(); j++) {
            g.setColor(Color.red);
            g.fillRect(barriers.get(j).barrierX(), barriers.get(j).barrierY(), barriers.get(j).getBarrierSideLen(), barriers.get(j).getBarrierSideLen());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        char data[] = invidual.getData();
        int y = invidual.locationY();
        int x = invidual.locationX();
        if (i < invidual.dataLen()) {
            switch (data[i]) {
                case 'N':
                    y -= 5;
                    break;
                case 'S':
                    y += 5;
                    break;
                case 'E':
                    x += 5;
                    break;
                case 'W':
                    x -= 5;
                    break;
            }
            if (!checkBarrier(y, x, y + invidual.getSideLen(), x + invidual.getSideLen())) {
                i++;
                invidual.updateLocation(y, x);
                repaint();
            } else {
                update();
            }
        } else {
            update();
        }
    }

    public void update() {
        inviduals.add(invidual);
        double fit = 1 / ((double) Math.abs(invidual.locationY() - p.goalY()) + Math.abs(invidual.locationX() - p.goalX()));
        avgFitness += fit;
        p.getPop()[invidual.getNum() - 1].setFit(fit);
        if (fit > p.bestFitness()) {
            p.setBestFitness(fit);
            p.setBestIndivual(invidual);
        }
        invidual = p.getDNA();
        if (invidual == null) {
            p.setAvgFitness(avgFitness / p.getSize());
            avgFitness = 0;
            p.updateGeneration();
            invidual = p.getDNA();
            inviduals.clear();
        }
        i = 0;
        getColor(invidual);
        repaint();
    }

    public void getColor(DNA invidual) {
        while (true) {
            int i = r.nextInt(256);
            int j = r.nextInt(256);
            int k = r.nextInt(256);
            if (c.checkColor(i, j, k, invidual.getNum())) {
                invidual.setColor(i, j, k);
                break;
            }
        }
    }

    public boolean checkBarrier(int yLpoint, int xLpoint, int yRpoint, int xRpoint) {
        for (int j = 0; j < barriers.size(); j++) {
            int sLen = barriers.get(j).getBarrierSideLen();
            //upper left point
            int y1 = barriers.get(j).barrierY();
            int x1 = barriers.get(j).barrierX();
            //lower right point
            int y2 = barriers.get(j).barrierY() + sLen;
            int x2 = barriers.get(j).barrierX() + sLen;
            //check if parameters are inside of barrier.
            if (((yLpoint >= y1 && yLpoint <= y2) && (xLpoint >= x1 && xLpoint <= x2)) || ((yRpoint >= y1 && yRpoint <= y2) && (xRpoint >= x1 && x2 <= xRpoint))) {
                return true;
            }
        }
        return false;
    }

    public int getBarrierLen() {
        return this.barrierSize;
    }
}
