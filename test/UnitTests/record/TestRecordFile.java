package UnitTests.record;

import mattdb.record.RecordFile;
import mattdb.record.Schema;
import mattdb.record.TableInfo;
import mattdb.server.MattDB;
import mattdb.tx.Transaction;

public class TestRecordFile {

    public void main() {
        MattDB.init("studentdb");
        Transaction tx = new Transaction();
        Schema sch = new Schema();
        sch.addIntField("A");
        TableInfo ti = new TableInfo("junk", sch);
        RecordFile rf = new RecordFile(ti, tx);
        for (int i = 0; i < 10000; i++) {
            rf.insert();
            int n = (int) Math.round(Math.random() * 200);
            rf.setInt("A", n);
        }
        int count = 0;
        rf.beforeFirst();
        while (rf.next()) {
            if (rf.getInt("A") < 100) {
                count++;
                rf.delete();
            }
        }
        System.out.println(count + " values under 100 were deleted");
        rf.close();
        tx.commit();
    }

}
