package mattdb.query;

/**
 * The interface that denotes values stored in the database.
 *
 * @author Edward Sciore
 */
public interface Constant extends Comparable<Constant> {

    /**
     * Returns the Java object corresponding to this constant.
     *
     * @return the Java value of the constant
     */
    Object asJavaVal();
}
