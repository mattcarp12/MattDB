package mattdb.remote;

import mattdb.tx.Transaction;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * The RMI server-side implementation of RemoteConnection.
 *
 * @author Edward Sciore
 */
@SuppressWarnings("serial")
class RemoteConnectionImpl extends UnicastRemoteObject implements RemoteConnection {
   private Transaction tx;

   /**
    * Creates a remote connection
    * and begins a new transaction for it.
    *
    * @throws RemoteException
    */
   RemoteConnectionImpl() throws RemoteException {
      tx = new Transaction();
   }

   /**
    * Creates a new RemoteStatement for this connection.
    *
    * @see mattdb.remote.RemoteConnection#createStatement()
    */
   public RemoteStatement createStatement() throws RemoteException {
      return new RemoteStatementImpl(this);
   }

   /**
    * Closes the connection.
    * The current transaction is committed.
    *
    * @see mattdb.remote.RemoteConnection#close()
    */
   public void close() throws RemoteException {
      tx.commit();
   }

// The following methods are used by the server-side classes.

   /**
    * Returns the transaction currently associated with
    * this connection.
    *
    * @return the transaction associated with this connection
    */
   Transaction getTransaction() {
      return tx;
   }

   /**
    * Commits the current transaction,
    * and begins a new one.
    */
   void commit() {
      tx.commit();
      tx = new Transaction();
   }

   /**
    * Rolls back the current transaction,
    * and begins a new one.
    */
   void rollback() {
      tx.rollback();
      tx = new Transaction();
   }
}

