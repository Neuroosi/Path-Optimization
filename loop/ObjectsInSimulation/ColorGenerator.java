package loop.ObjectsInSimulation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ColorGenerator {

    private Map<Integer, LinkedList<Integer>> keys;

    public ColorGenerator() {
        keys = new HashMap<>();

    }

    public boolean checkColor(int i, int j, int k, int index) {
        int sum = i + j + k;
        int hash = sum % index;
        if (keys.containsKey(hash)) {
            for (int color : keys.get(hash)) {
                if (sum == color) {
                    return false;
                }
            }
            keys.get(hash).add(sum);
        } else {
            keys.put(hash, new LinkedList<>());
            keys.get(hash).add(sum);
        }
        return true;
    }

    public void reset() {
        keys.clear();
    }
}
