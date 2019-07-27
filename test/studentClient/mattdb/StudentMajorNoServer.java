package studentClient.mattdb;

import mattdb.query.Plan;
import mattdb.query.Scan;
import mattdb.server.MattDB;
import mattdb.tx.Transaction;

/* This is a version of the StudentMajor program that
 * accesses the MattDB classes directly (instead of
 * connecting to it as a JDBC client).  You can run it
 * without having the server also run.
 *
 * These kind of programs are useful for debugging
 * your changes to the MattDB source code.
 */


public class StudentMajorNoServer {
    public static void main(String[] args) {
        try {
            // analogous to the driver
            MattDB.init("studentdb");

            // analogous to the connection
            Transaction tx = new Transaction();

            // analogous to the statement
            String qry = "select SName, DName "
                    + "from DEPT, STUDENT "
                    + "where MajorId = DId";
            Plan p = MattDB.planner().createQueryPlan(qry, tx);

            // analogous to the result set
            Scan s = p.open();

            System.out.println("Name\tMajor");
            while (s.next()) {
                String sname = s.getString("sname"); //MattDB stores field names
                String dname = s.getString("dname"); //in lower case
                System.out.println(sname + "\t" + dname);
            }
            s.close();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
