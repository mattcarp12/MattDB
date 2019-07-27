package UnitTests.buffer;

import mattdb.buffer.PageFormatter;
import mattdb.file.Page;

import static mattdb.file.Page.BLOCK_SIZE;
import static mattdb.file.Page.STR_SIZE;

public class ABCStringFormatter implements PageFormatter {

    ABCStringFormatter() {
    }

    /**
     * Initializes a page, whose contents will be
     * written to a new disk block.
     * This method is called only during the method
     * Tests.Buffer.assignToNew()
     *
     * @param p a buffer page
     */
    @Override
    public void format(Page p) {
        int recsize = STR_SIZE(3);
        for (int i = 0; i + recsize <= BLOCK_SIZE; i += recsize)
            p.setString(i, "abc");

    }
}
