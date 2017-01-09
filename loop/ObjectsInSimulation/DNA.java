package loop.ObjectsInSimulation;

import java.awt.Color;
import java.util.Random;

public class DNA {

    private int y;
    private int x;
    private int len;
    private int dataIndex;
    private int line;
    private int sideLen;
    private double fit;
    private char[] data;
    private char[] dir = new char[4];
    private Random r;
    private Color c;

    public DNA(int y, int x, int len, int line, int sideLen) {
        this.y = y;
        this.x = x;
        this.len = len;
        this.line = line;
        this.sideLen = sideLen;
        c = null;
        this.data = new char[len];
        dir[0] = 'N';
        dir[1] = 'S';
        dir[2] = 'E';
        dir[3] = 'W';
        dataIndex = 0;
        r = new Random();
    }

    public DNA(DNA parentA, DNA parentB, int y, int x, double mutation, int line, int sideLen) {
        r = new Random();
        this.y = y;
        this.line = line;
        this.sideLen = sideLen;
        this.x = x;
        dataIndex = 0;
        c = null;
        if (parentA.dataLen() == parentB.dataLen()) {
            this.len = (parentA.dataLen() + parentB.dataLen() + 1) / 2;
        } else {
            this.len = (parentA.dataLen() + parentB.dataLen()) / 2;
        }
        this.data = new char[len];
        DNA dominant;
        DNA poorGene;
        if (parentA.fit > parentB.fit) {
            dominant = parentA;
            poorGene = parentB;
        } else {
            dominant = parentB;
            poorGene = parentA;
        }
        for (int i = 0; i < len; i++) {
            if (i <= len / 2) {
                data[i] = dominant.getChromosome();
            } else {
                data[i] = poorGene.getChromosome();
            }
            double mutationCoefficient = r.nextDouble();
            if (mutationCoefficient <= mutation) {
                data[i] = dominant.getChromosome();
            }
        }
    }

    public void setRandomData() {
        for (int i = 0; i < len; i++) {
            int index = r.nextInt(4);
            data[i] = dir[index];
        }
    }

    public char[] getData() {
        return data;
    }

    public char getChromosome() {
        return data[r.nextInt(len)];
    }

    public int locationY() {
        return y;
    }

    public int locationX() {
        return x;
    }

    public void updateLocation(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public int dataLen() {
        return len;
    }
    
    public int getSideLen(){
        return this.sideLen;
    }

    public void setFit(double fit) {
        this.fit = fit;
    }

    public double getFitness() {
        return fit;
    }

    public int getNum() {
        return line;
    }

    public void setColor(int i, int j, int k) {
        this.c = new Color(i, j, k);
    }

    public Color getColor() {
        return c;
    }
}
