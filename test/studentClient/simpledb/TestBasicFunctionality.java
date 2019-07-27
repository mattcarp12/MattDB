package studentClient.simpledb;

import java.io.File;
import java.io.IOException;

import mattdb.server.MattDB;
import mattdb.server.Startup;

public class TestBasicFunctionality {

    public static void deleteDbDir() {
        String homedir = System.getProperty("user.home");
        File dbDirectory = new File(homedir, "TestDB");
        if (dbDirectory.exists()) {
            for(String file : dbDirectory.list()) {
                new File(dbDirectory, file).delete();
            }
            dbDirectory.delete();
        }
    }

    public static void main(String[] args) {
        deleteDbDir();
        String[] dbName = {"TestDB"};
        try {
            Startup.main(dbName);
        } catch(Exception e) {
            e.printStackTrace();
        }
        CreateStudentDB.main(new String[]{});
        StudentMajor.main(new String[]{});
        FindMajors.main(new String[]{"math"});
        ChangeMajor.main(new String[]{});

    }


}
