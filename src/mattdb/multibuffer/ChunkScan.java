package mattdb.multibuffer;

import static java.sql.Types.INTEGER;
import mattdb.tx.Transaction;
import mattdb.record.*;
import mattdb.file.Block;
import mattdb.query.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The class for the <i>chunk</i> operator.
 * @author Edward Sciore
 */
public class ChunkScan implements Scan {
   private List<RecordPage> pages;
   private int startbnum, endbnum, current;
   private Schema sch;
   private RecordPage rp;
   
   /**
    * Creates a chunk consisting of the specified pages. 
    * @param ti the metadata for the chunked table
    * @param startbnum the starting block number
    * @param endbnum  the ending block number
    * @param tx the current transaction
    */ 
   public ChunkScan(TableInfo ti, int startbnum, int endbnum, Transaction tx) {
      pages = new ArrayList<RecordPage>();
      this.startbnum = startbnum;
      this.endbnum   = endbnum;
      this.sch = ti.schema();
      String filename = ti.fileName();
      for (int i=startbnum; i<=endbnum; i++) {
         Block blk = new Block(filename, i);
         pages.add(new RecordPage(blk, ti, tx));
      }
      beforeFirst();
   }
   
   
   /**
    * @see mattdb.query.Scan#beforeFirst()
    */
   public void beforeFirst() {
      moveToBlock(startbnum);
   }
   
   /**
    * Moves to the next record in the current block of the chunk.
    * If there are no more records, then make
    * the next block be current.
    * If there are no more blocks in the chunk, return false.
    * @see mattdb.query.Scan#next()
    */
   public boolean next() {
      while (true) {
         if (rp.next())
            return true;
         if (current == endbnum)
            return false;
         moveToBlock(current+1);
      }
   }
   
   /**
    * @see mattdb.query.Scan#close()
    */
   public void close() {
      for (RecordPage r : pages)
         r.close();
   }
   
   /**
    * @see mattdb.query.Scan#getVal(java.lang.String)
    */
   public Constant getVal(String fldname) {
      if (sch.type(fldname) == INTEGER)
         return new IntConstant(rp.getInt(fldname));
      else
         return new StringConstant(rp.getString(fldname));
   }
   
   /**
    * @see mattdb.query.Scan#getInt(java.lang.String)
    */
   public int getInt(String fldname) {
      return rp.getInt(fldname);
   }
   
   /**
    * @see mattdb.query.Scan#getString(java.lang.String)
    */
   public String getString(String fldname) {
      return rp.getString(fldname);
   }
   
   /**
    * @see mattdb.query.Scan#hasField(java.lang.String)
    */
   public boolean hasField(String fldname) {
      return sch.hasField(fldname);
   }
   
   private void moveToBlock(int blknum) {
      current = blknum;
      rp = pages.get(current - startbnum);
      rp.moveToId(-1);
   }
}