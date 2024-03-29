package mattdb.tx;

import mattdb.buffer.Buffer;
import mattdb.buffer.BufferMgr;
import mattdb.buffer.PageFormatter;
import mattdb.file.Block;
import mattdb.server.MattDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the transaction's currently-pinned buffers.
 *
 * @author Edward Sciore
 */
class BufferList {
   private Map<Block, Buffer> buffers = new HashMap<Block, Buffer>();
   private List<Block> pins = new ArrayList<Block>();
   private BufferMgr bufferMgr = MattDB.bufferMgr();

   /**
    * Returns the buffer pinned to the specified block.
    * The method returns null if the transaction has not
    * pinned the block.
    *
    * @param blk a reference to the disk block
    * @return the buffer pinned to that block
    */
   Buffer getBuffer(Block blk) {
      return buffers.get(blk);
   }

   /**
    * Pins the block and keeps track of the buffer internally.
    *
    * @param blk a reference to the disk block
    */
   void pin(Block blk) {
      Buffer buff = bufferMgr.pin(blk);
      buffers.put(blk, buff);
      pins.add(blk);
   }

   /**
    * Appends a new block to the specified file
    * and pins it.
    *
    * @param filename the name of the file
    * @param fmtr     the formatter used to initialize the new page
    * @return a reference to the newly-created block
    */
   Block pinNew(String filename, PageFormatter fmtr) {
      Buffer buff = bufferMgr.pinNew(filename, fmtr);
      Block blk = buff.block();
      buffers.put(blk, buff);
      pins.add(blk);
      return blk;
   }

   /**
    * Unpins the specified block.
    *
    * @param blk a reference to the disk block
    */
   void unpin(Block blk) {
      Buffer buff = buffers.get(blk);
      bufferMgr.unpin(buff);
      pins.remove(blk);
      if (!pins.contains(blk))
         buffers.remove(blk);
   }

   /**
    * Unpins any buffers still pinned by this transaction.
    */
   void unpinAll() {
      for (Block blk : pins) {
         Buffer buff = buffers.get(blk);
         bufferMgr.unpin(buff);
      }
      buffers.clear();
      pins.clear();
   }
}