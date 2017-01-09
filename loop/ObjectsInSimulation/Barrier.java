package loop.ObjectsInSimulation;

public class Barrier {

    private int y;
    private int x;
    private int sideLen;

    public Barrier(int y, int x, int sideLen) {
        this.y = y;
        this.x = x;
        this.sideLen = sideLen;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int barrierY() {
        return this.y;
    }

    public int barrierX() {
        return this.x;
    }

    public int getBarrierSideLen() {
        return this.sideLen;
    }
}
