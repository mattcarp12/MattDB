package mattdb.buffer.ReplacementManager;

import mattdb.buffer.Buffer;

public interface ReplacementManager {
    public Integer get();
    public void add(int idx);
    public void remove(int bufferIndex);
}
