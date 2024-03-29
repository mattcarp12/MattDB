package mattdb.planner;

import mattdb.parse.*;
import mattdb.query.*;
import mattdb.server.MattDB;
import mattdb.tx.Transaction;

import java.util.Iterator;

/**
 * The basic planner for SQL update statements.
 *
 * @author sciore
 */
public class BasicUpdatePlanner implements UpdatePlanner {

   public int executeDelete(DeleteData data, Transaction tx) {
      Plan p = new TablePlan(data.tableName(), tx);
      p = new SelectPlan(p, data.pred());
      UpdateScan us = (UpdateScan) p.open();
      int count = 0;
      while (us.next()) {
         us.delete();
         count++;
      }
      us.close();
      return count;
   }

   public int executeModify(ModifyData data, Transaction tx) {
      Plan p = new TablePlan(data.tableName(), tx);
      p = new SelectPlan(p, data.pred());
      UpdateScan us = (UpdateScan) p.open();
      int count = 0;
      while (us.next()) {
         Constant val = data.newValue().evaluate(us);
         us.setVal(data.targetField(), val);
         count++;
      }
      us.close();
      return count;
   }

   public int executeInsert(InsertData data, Transaction tx) {
      Plan p = new TablePlan(data.tableName(), tx);
      UpdateScan us = (UpdateScan) p.open();
      us.insert();
      Iterator<Constant> iter = data.vals().iterator();
      for (String fldname : data.fields()) {
         Constant val = iter.next();
         us.setVal(fldname, val);
      }
      us.close();
      return 1;
   }

   public int executeCreateTable(CreateTableData data, Transaction tx) {
      MattDB.mdMgr().createTable(data.tableName(), data.newSchema(), tx);
      return 0;
   }

   public int executeCreateView(CreateViewData data, Transaction tx) {
      MattDB.mdMgr().createView(data.viewName(), data.viewDef(), tx);
      return 0;
   }

   public int executeCreateIndex(CreateIndexData data, Transaction tx) {
      MattDB.mdMgr().createIndex(data.indexName(), data.tableName(), data.fieldName(), tx);
      return 0;
   }
}
