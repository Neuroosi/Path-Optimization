package loop.ObjectsInSimulation;

import java.util.ArrayDeque;
import java.util.Random;

public class Population {

    private int elitism;
    private int popSize;
    private int goalY;
    private int goalX;
    private int gen;
    private double mutation;
    private double avgFitness;
    private double bestFitness;
    private DNA bestInvidual;
    private DNA[] pop;
    private ArrayDeque<DNA> line;
    private Random r;

    public Population(int popSize, double mutation, int elitism, int goalY, int goalX) {
        this.popSize = popSize;
        this.elitism = elitism;
        this.mutation = mutation;
        this.goalY = goalY;
        this.goalX = goalX;
        this.gen = 1;
        this.line = new ArrayDeque<>();
        this.pop = new DNA[popSize];
        bestFitness = 0;
        this.r = new Random();
        setPopulation();
        bestInvidual = pop[0];
    }

    public void setPopulation() {
        int nro = 1;
        for (int i = 0; i < popSize; i++, nro++) {
            int lenLife = r.nextInt(150 - 1 + 1) + 1;
            pop[i] = new DNA(250, 250, lenLife, nro, 5);
            pop[i].setRandomData();
            line.push(pop[i]);
        }
    }

    public void updateGeneration() {
        int nro = 1;
        line.clear();
        DNA[] newGen = new DNA[popSize];
        addBestInvidual(popSize / elitism, newGen);
        for (int i = popSize / elitism; i < popSize; i++, nro++) {
            DNA parentA = chooseParent();
            DNA parentB = chooseParent();
            DNA child = new DNA(parentA, parentB, 250, 250, mutation, nro, 5);
            newGen[i] = child;
            line.push(child);
        }
        gen++;
        pop = newGen;
    }

    public DNA chooseParent() {
        while (true) {
            int invidual = r.nextInt(popSize);
            double factor = r.nextDouble();
            if (pop[invidual].getFitness() < factor) {
                return pop[invidual];
            }
        }
    }

    public void addBestInvidual(int reservedSlots, DNA[] newGen) {
        for (int i = 0; i < reservedSlots; i++) {
            newGen[i] = bestInvidual;
        }
    }

    public void setBestIndivual(DNA invidual) {
        bestInvidual = invidual;
    }

    public void setBestFitness(double x) {
        bestFitness = x;
    }

    public double bestFitness() {
        return this.bestFitness;
    }

    public double avgFitness() {
        return this.avgFitness;
    }

    public void setAvgFitness(double x) {
        avgFitness = x;
    }

    public void resetAvgFitness() {
        avgFitness = 0;
    }

    public int generationCounter() {
        return this.gen;
    }

    public int goalY() {
        return goalY;
    }

    public int goalX() {
        return goalX;
    }

    public int getSize() {
        return this.popSize;
    }

    public DNA[] getPop() {
        return pop;
    }

    public DNA getDNA() {
        if (!line.isEmpty()) {
            return line.pollLast();
        }
        return null;
    }
}
