package UnitTests.record;

import mattdb.file.Block;
import mattdb.record.RecordPage;
import mattdb.record.Schema;
import mattdb.record.TableInfo;
import mattdb.server.MattDB;
import mattdb.tx.Transaction;

public class TestRecordPage {

    public static void main() {
        MattDB.init("studentdb");

        Schema sch = new Schema();
        sch.addIntField("cid");
        sch.addStringField("title", 20);
        sch.addIntField("deptid");
        TableInfo ti = new TableInfo("course", sch);

        Transaction tx = new Transaction();
        String filename = ti.fileName();
        Block blk = new Block(filename, 337);
        RecordPage rp = new RecordPage(blk, ti, tx);

        //Part 1
        while (rp.next()) {
            int dept = rp.getInt("deptid");
            if (dept == 30)
                rp.delete();
            else if (rp.getString("title").equals("VB programming"))
                rp.setString("title", "Java programming");
        }

        // Part 2
        boolean ok = rp.insert();
        if (ok) {
            rp.setInt("cid", 82);
            rp.setString("title", "OO Design");
            rp.setInt("deptid", 20);
        }
        rp.close();
        tx.commit();

    }


}
