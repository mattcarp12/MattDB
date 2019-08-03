package UnitTests.metadata;

import mattdb.metadata.MetadataMgr;
import mattdb.record.RecordFile;
import mattdb.record.Schema;
import mattdb.record.TableInfo;
import mattdb.server.MattDB;
import mattdb.tx.Transaction;

public class TableMgrTest {

    public static void main(String[] args) {
        MattDB.init("mattdb\\testdb");
        MetadataMgr mdMgr = MattDB.mdMgr();
// Part 1: Create the DEPT table
        Transaction tx1 = new Transaction();
        Schema sch = new Schema();
        sch.addIntField("did");
        sch.addStringField("dname", 8);
        mdMgr.createTable("dept", sch, tx1);
        tx1.commit();

// Part 2: Print the name of each department
        Transaction tx2 = new Transaction();
        TableInfo ti = mdMgr.getTableInfo("dept", tx2);
        RecordFile rf = new RecordFile(ti, tx2);
        while (rf.next())
            System.out.println(rf.getString("dname"));
        rf.close();
        tx2.commit();

// Part 3: Attempt to Create duplicate DEPT table
        tx1 = new Transaction();
        sch = new Schema();
        sch.addIntField("did");
        sch.addStringField("dname", 8);
        mdMgr.createTable("dept", sch, tx1);
        tx1.commit();

// Part 4: Drop DEPT table
        tx1 = new Transaction();
        mdMgr.dropTable("dept", tx1);
        tx1.commit();
    }
}
