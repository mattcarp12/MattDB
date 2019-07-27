package UnitTests.File;

import mattdb.file.Block;
import mattdb.file.FileMgr;
import mattdb.file.Page;
import mattdb.server.MattDB;

public class TestFile {

    public static void main(String[] args) {
        MattDB.init("studentdb");
        FileMgr fm = MattDB.fileMgr();
        int filesize = fm.size("junk"); //gives number of blocks
        Block blk = new Block("junk", filesize);

        Page p1 = new Page();
        p1.setInt(69, 420);
        p1.write(blk);
        p1.read(blk);
        int n = p1.getInt(69);
        p1.setInt(69, n + 1);
        p1.write(blk);

        Page p2 = new Page();
        p2.setString(20, "hello");
        blk = p2.append("junk");
        Page p3 = new Page();
        p3.read(blk);
        String s = p3.getString(20);
        System.out.println("Block " + blk.number() + " contains " + s);
    }

}
