package mattdb.index.query;

import mattdb.index.Index;
import mattdb.metadata.IndexInfo;
import mattdb.query.Constant;
import mattdb.query.Plan;
import mattdb.query.Scan;
import mattdb.query.TableScan;
import mattdb.record.Schema;
import mattdb.tx.Transaction;

/**
 * The Plan class corresponding to the <i>indexselect</i>
 * relational algebra operator.
 *
 * @author Edward Sciore
 */
public class IndexSelectPlan implements Plan {
    private Plan p;
    private IndexInfo ii;
    private Constant val;

    /**
     * Creates a new indexselect node in the query tree
     * for the specified index and selection constant.
     *
     * @param p   the input table
     * @param ii  information about the index
     * @param val the selection constant
     * @param tx  the calling transaction
     */
    public IndexSelectPlan(Plan p, IndexInfo ii, Constant val, Transaction tx) {
        this.p = p;
        this.ii = ii;
        this.val = val;
    }

    /**
     * Creates a new indexselect scan for this query
     *
     * @see mattdb.query.Plan#open()
     */
    public Scan open() {
        // throws an exception if p is not a tableplan.
        TableScan ts = (TableScan) p.open();
        Index idx = ii.open();
        return new IndexSelectScan(idx, val, ts);
    }

    /**
     * Estimates the number of block accesses to compute the
     * index selection, which is the same as the
     * index traversal cost plus the number of matching data records.
     *
     * @see mattdb.query.Plan#blocksAccessed()
     */
    public int blocksAccessed() {
        return ii.blocksAccessed() + recordsOutput();
    }

    /**
     * Estimates the number of output records in the index selection,
     * which is the same as the number of search key values
     * for the index.
     *
     * @see mattdb.query.Plan#recordsOutput()
     */
    public int recordsOutput() {
        return ii.recordsOutput();
    }

    /**
     * Returns the distinct values as defined by the index.
     *
     * @see mattdb.query.Plan#distinctValues(java.lang.String)
     */
    public int distinctValues(String fldname) {
        return ii.distinctValues(fldname);
    }

    /**
     * Returns the schema of the data table.
     *
     * @see mattdb.query.Plan#schema()
     */
    public Schema schema() {
        return p.schema();
    }
}
