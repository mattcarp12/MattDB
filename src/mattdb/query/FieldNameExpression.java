package mattdb.query;

import mattdb.record.Schema;

/**
 * An expression consisting entirely of a single field.
 * @author Edward Sciore
 *
 */
public class FieldNameExpression implements Expression {
   private String fldname;
   
   /**
    * Creates a new expression by wrapping a field.
    * @param fldname the name of the wrapped field
    */
   public FieldNameExpression(String fldname) {
      this.fldname = fldname;
   }
   
   /**
    * Returns false.
    * @see mattdb.query.Expression#isConstant()
    */
   public boolean isConstant() {
      return false;
   }
   
   /**
    * Returns true.
    * @see mattdb.query.Expression#isFieldName()
    */
   public boolean isFieldName() {
      return true;
   }
   
   /**
    * This method should never be called.
    * Throws a ClassCastException.
    * @see mattdb.query.Expression#asConstant()
    */
   public Constant asConstant() {
      throw new ClassCastException();
   }
   
   /**
    * Unwraps the field name and returns it.
    * @see mattdb.query.Expression#asFieldName()
    */
   public String asFieldName() {
      return fldname;
   }
   
   /**
    * Evaluates the field by getting its value in the scan.
    * @see mattdb.query.Expression#evaluate(mattdb.query.Scan)
    */
   public Constant evaluate(Scan s) {
      return s.getVal(fldname);
   }
   
   /** 
    * Returns true if the field is in the specified schema.
    * @see mattdb.query.Expression#appliesTo(mattdb.record.Schema)
    */
   public boolean appliesTo(Schema sch) {
      return sch.hasField(fldname);
   }
   
   public String toString() {
      return fldname;
   }
}
