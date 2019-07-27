package mattdb.materialize;

import mattdb.query.Constant;
import mattdb.query.Scan;

/**
 * The <i>max</i> aggregation function.
 *
 * @author Edward Sciore
 */
public class MaxFn implements AggregationFn {
    private String fldname;
    private Constant val;

    /**
     * Creates a max aggregation function for the specified field.
     *
     * @param fldname the name of the aggregated field
     */
    public MaxFn(String fldname) {
        this.fldname = fldname;
    }

    /**
     * Starts a new maximum to be the
     * field value in the current record.
     *
     * @see mattdb.materialize.AggregationFn#processFirst(mattdb.query.Scan)
     */
    public void processFirst(Scan s) {
        val = s.getVal(fldname);
    }

    /**
     * Replaces the current maximum by the field value
     * in the current record, if it is higher.
     *
     * @see mattdb.materialize.AggregationFn#processNext(mattdb.query.Scan)
     */
    public void processNext(Scan s) {
        Constant newval = s.getVal(fldname);
        if (newval.compareTo(val) > 0)
            val = newval;
    }

    /**
     * Returns the field's name, prepended by "maxof".
     *
     * @see mattdb.materialize.AggregationFn#fieldName()
     */
    public String fieldName() {
        return "maxof" + fldname;
    }

    /**
     * Returns the current maximum.
     *
     * @see mattdb.materialize.AggregationFn#value()
     */
    public Constant value() {
        return val;
    }
}
