package mattdb.buffer;

import mattdb.buffer.ReplacementManager.LRUReplacementManager;
import mattdb.buffer.ReplacementManager.ReplacementManager;
import mattdb.file.*;

import java.util.HashMap;

/**
 * Manages the pinning and unpinning of buffers to blocks.
 * @author Edward Sciore
 *
 */
class BasicBufferMgr {
    private Buffer[] bufferpool;
    private int numAvailable;
    HashMap<Block, Integer> blockMap;
    private ReplacementManager availableBuffers;

    /**
     * Creates a buffer manager having the specified number
     * of buffer slots.
     * This constructor depends on both the {@link FileMgr} and
     * {@link mattdb.log.LogMgr LogMgr} objects
     * that it gets from the class
     * {@link mattdb.server.MattDB}.
     * Those objects are created during system initialization.
     * Thus this constructor cannot be called until
     * {@link mattdb.server.MattDB#initFileAndLogMgr(String)} or
     * is called first.
     * @param numbuffs the number of buffer slots to allocate
     */
    BasicBufferMgr(int numbuffs) {
        bufferpool = new Buffer[numbuffs];
        numAvailable = numbuffs;
        availableBuffers = new LRUReplacementManager();
        blockMap = new HashMap<>();
        for (int i=0; i<numbuffs; i++) {
            bufferpool[i] = new Buffer();
            availableBuffers.add(i);
        }
    }

    /**
     * Flushes the dirty buffers modified by the specified transaction.
     * @param txnum the transaction's id number
     */
    synchronized void flushAll(int txnum) {
        for (Buffer buff : bufferpool)
            if (buff.isModifiedBy(txnum))
                buff.flush();
    }

    /**
     * Pins a buffer to the specified block.
     * If there is already a buffer assigned to that block
     * then that buffer is used;
     * otherwise, an unpinned buffer from the pool is chosen.
     * Returns a null value if there are no available buffers.
     * @param blk a reference to a disk block
     * @return the pinned buffer
     */
    synchronized Buffer pin(Block blk) {

      /*
      If the block already has an associated buffer,
      this will return a non-null value. In this case,
      pin the buffer and return it.
       */
        Buffer buff = findExistingBuffer(blk);

      /*
      if (buff == null) {
         buff = chooseUnpinnedBuffer();
         if (buff == null)
            return null;
         buff.assignToBlock(blk);
      }
      */

      /*
      If the block does not have an associated buffer,
      need to replace a buffer in the buffer pool.
      This is the job of the replacement manager.
       */
        if (buff == null) {
            int bufferIndex = chooseUnpinnedBuffer();
            if (bufferIndex == -1) return null; // there are no unpinned buffers
            buff = bufferpool[bufferIndex];
            blockMap.remove(buff.block()); // remove the block that is being replaced from the list of blocks in bufferpool
            buff.assignToBlock(blk);
            blockMap.put(blk, bufferIndex);
        }
        if (!buff.isPinned()){
            numAvailable--;
            int bufferIndex = blockMap.get(buff.block());
            availableBuffers.remove((Integer)bufferIndex);
        }

        buff.pin();
        return buff;
    }

    /**
     * Allocates a new block in the specified Tests.file, and
     * pins a buffer to it.
     * Returns null (without allocating the block) if
     * there are no available buffers.
     * @param filename the name of the Tests.file
     * @param fmtr a pageformatter object, used to format the new block
     * @return the pinned buffer
     */
    synchronized Buffer pinNew(String filename, PageFormatter fmtr) {

        int bufferIndex = chooseUnpinnedBuffer(); //removes buffer from unpinned list
        if (bufferIndex == -1) return null;
        Buffer buff = bufferpool[bufferIndex];
        blockMap.remove(buff.block());
        Block blk = buff.assignToNew(filename, fmtr);
        numAvailable--;
        buff.pin();
        blockMap.put(blk, bufferIndex); //adds buffer to pinned list
        return buff;
    }

    /**
     * Unpins the specified buffer.
     * @param buff the buffer to be unpinned
     */
    synchronized void unpin(Buffer buff) {
        buff.unpin();
        if (!buff.isPinned()) {
            numAvailable++;
            int bufferIndex = blockMap.get(buff.block());
            availableBuffers.add(bufferIndex);
        }

    }

    /**
     * Returns the number of available (i.e. unpinned) buffers.
     * @return the number of available buffers
     */
    int available() {
        return numAvailable;
    }

    private Buffer findExistingBuffer(Block blk) {

        if (blockMap.containsKey(blk)) {
            int index = blockMap.get(blk);
            return bufferpool[index];
        }
        return null;

    }

   /*
   private Tests.Buffer chooseUnpinnedBuffer() {
      for (Tests.Buffer buff : bufferpool)
         if (!buff.isPinned())
         return buff;
      return null;
   }
   */

    private int chooseUnpinnedBuffer() {
        return availableBuffers.get();
    }
}
