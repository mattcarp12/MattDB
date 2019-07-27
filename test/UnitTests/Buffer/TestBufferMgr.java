package UnitTests.Buffer;

import mattdb.buffer.BufferMgr;
import mattdb.buffer.PageFormatter;
import mattdb.server.MattDB;


public class TestBufferMgr {

    static BufferMgr bm;
    static PageFormatter pf;


    public static void TestFindUnpinnedBuffer() {
        bm.pinNew("junk", pf);
    }

    public static void main(String[] args) {
        MattDB.init("studentdb");
        bm = MattDB.bufferMgr();
        pf = new ABCStringFormatter();
        TestFindUnpinnedBuffer();
    }

}
