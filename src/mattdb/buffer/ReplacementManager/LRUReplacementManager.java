package mattdb.buffer.ReplacementManager;

import java.util.ArrayList;


public class LRUReplacementManager implements ReplacementManager {

    ArrayList<Integer> list;

    public LRUReplacementManager() {
        this.list = new ArrayList<Integer>();
    }

    @Override
    public void add(int idx) {
        list.add((Integer)idx);
    }

    @Override
    public Integer get() {
        try {
            return list.remove(0);
        } catch(IndexOutOfBoundsException e) {
            return -1;
        }
    }

    @Override
    public void remove(int bufferIndex) {
        list.remove((Integer)bufferIndex);
    }
}
