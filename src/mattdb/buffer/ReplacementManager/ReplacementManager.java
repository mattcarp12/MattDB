package mattdb.buffer.ReplacementManager;

public interface ReplacementManager {
    Integer get();

    void add(int idx);

    void remove(int bufferIndex);
}
