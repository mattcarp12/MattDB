package UnitTests.file;

import java.io.File;

public class TestFile3 {

    public static void main(String[] args) {
        String homedir = System.getProperty("user.home");
        String dbname = "studentdb";
        File dbDirectory;
        if (dbname.lastIndexOf('\\') == -1) {
            dbDirectory = new File(homedir, dbname);
        } else {
            dbDirectory = new File(homedir + "\\" + dbname.substring(0, dbname.lastIndexOf('\\')),
                    dbname.substring(dbname.lastIndexOf('\\') + 1));
        }

        dbDirectory.mkdirs();

    }
}
