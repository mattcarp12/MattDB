package mattdb.materialize;

import mattdb.query.TableScan;
import mattdb.query.UpdateScan;
import mattdb.record.Schema;
import mattdb.record.TableInfo;
import mattdb.tx.Transaction;

/**
 * A class that creates temporary tables.
 * A temporary table is not registered in the catalog.
 * The class therefore has a method getTableInfo to return the
 * table's metadata.
 *
 * @author Edward Sciore
 */
public class TempTable {
    private static int nextTableNum = 0;
    private TableInfo ti;
    private Transaction tx;

    /**
     * Allocates a name for for a new temporary table
     * having the specified schema.
     *
     * @param sch the new table's schema
     * @param tx  the calling transaction
     */
    public TempTable(Schema sch, Transaction tx) {
        String tblname = nextTableName();
        ti = new TableInfo(tblname, sch);
        this.tx = tx;
    }

    private static synchronized String nextTableName() {
        nextTableNum++;
        return "temp" + nextTableNum;
    }

    /**
     * Opens a table scan for the temporary table.
     */
    public UpdateScan open() {
        return new TableScan(ti, tx);
    }

    /**
     * Return the table's metadata.
     *
     * @return the table's metadata
     */
    public TableInfo getTableInfo() {
        return ti;
    }
}