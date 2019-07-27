package mattdb.query;

/**
 * The interface will be implemented by each query scan.
 * There is a Scan class for each relational
 * algebra operator.
 *
 * @author Edward Sciore
 */
public interface Scan {

    /**
     * Positions the scan before its first record.
     */
    void beforeFirst();

    /**
     * Moves the scan to the next record.
     *
     * @return false if there is no next record
     */
    boolean next();

    /**
     * Closes the scan and its subscans, if any.
     */
    void close();

    /**
     * Returns the value of the specified field in the current record.
     * The value is expressed as a Constant.
     *
     * @param fldname the name of the field
     * @return the value of that field, expressed as a Constant.
     */
    Constant getVal(String fldname);

    /**
     * Returns the value of the specified integer field
     * in the current record.
     *
     * @param fldname the name of the field
     * @return the field's integer value in the current record
     */
    int getInt(String fldname);

    /**
     * Returns the value of the specified string field
     * in the current record.
     *
     * @param fldname the name of the field
     * @return the field's string value in the current record
     */
    String getString(String fldname);

    /**
     * Returns true if the scan has the specified field.
     *
     * @param fldname the name of the field
     * @return true if the scan has that field
     */
    boolean hasField(String fldname);
}
