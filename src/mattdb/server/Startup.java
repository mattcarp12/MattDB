package mattdb.server;


import mattdb.remote.RemoteDriver;
import mattdb.remote.RemoteDriverImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Startup {
    public static void main(String[] args) throws Exception {
        // configure and initialize the database
        MattDB.init(args[0]);

        // create a registry specific for the server on the default port
        Registry reg = LocateRegistry.createRegistry(1099);

        // and post the server entry in it
        RemoteDriver d = new RemoteDriverImpl();
        reg.rebind("mattdb", d);

        System.out.println("database server ready");
    }
}
