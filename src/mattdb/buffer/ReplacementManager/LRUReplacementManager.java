package mattdb.buffer.ReplacementManager;

import java.util.ArrayList;


public class LRUReplacementManager implements ReplacementManager {

    ArrayList<Integer> availableBuffers;

    public LRUReplacementManager() {
        this.availableBuffers = new ArrayList<Integer>();
    }

    @Override
    public void add(int idx) {
        availableBuffers.add(idx);
    }

    @Override
    public Integer get() {
        try {
            return availableBuffers.remove(0);
        } catch (IndexOutOfBoundsException e) {
            return -1;
        }
    }

    @Override
    public void remove(int bufferIndex) {
        availableBuffers.remove((Integer) bufferIndex);
    }
}
