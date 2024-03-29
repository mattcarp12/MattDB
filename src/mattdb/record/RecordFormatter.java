package mattdb.record;

import mattdb.buffer.PageFormatter;
import mattdb.file.Page;

import static java.sql.Types.INTEGER;
import static mattdb.file.Page.BLOCK_SIZE;
import static mattdb.file.Page.INT_SIZE;
import static mattdb.record.RecordPage.EMPTY;

/**
 * An object that can format a page to look like a block of
 * empty records.
 *
 * @author Edward Sciore
 */
class RecordFormatter implements PageFormatter {
    private TableInfo ti;

    /**
     * Creates a formatter for a new page of a table.
     *
     * @param ti the table's metadata
     */
    public RecordFormatter(TableInfo ti) {
        this.ti = ti;
    }

    /**
     * Formats the page by allocating as many record slots
     * as possible, given the record length.
     * Each record slot is assigned a flag of EMPTY.
     * Each integer field is given a value of 0, and
     * each string field is given a value of "".
     *
     * @see mattdb.buffer.PageFormatter#format(mattdb.file.Page)
     */
    public void format(Page page) {
        int recsize = ti.recordLength() + INT_SIZE;
        for (int pos = 0; pos + recsize <= BLOCK_SIZE; pos += recsize) {
            page.setInt(pos, EMPTY);
            makeDefaultRecord(page, pos);
        }
    }

    private void makeDefaultRecord(Page page, int pos) {
        for (String fldname : ti.schema().fields()) {
            int offset = ti.offset(fldname);
            if (ti.schema().type(fldname) == INTEGER)
                page.setInt(pos + INT_SIZE + offset, 0);
            else
                page.setString(pos + INT_SIZE + offset, "");
        }
    }
}
