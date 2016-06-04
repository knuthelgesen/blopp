package no.plasmid.blopp;

import org.junit.After;
import org.junit.Before;

import no.plasmid.blopp.migration.MigrationRunner;
import no.plasmid.blopp.orientdb.OrientDBTransactionWrapper;
import no.plasmid.blopp.orientdb.OrientDBTransactionlessWrapper;

public abstract class AbstractInMemoryTest {

  protected InMemoryOrientDBTransactionFactory transactionFactory;
  protected OrientDBTransactionWrapper transaction;
  protected OrientDBTransactionlessWrapper transactionless;

  @Before
  public void before() {
    //Run migration on in memory graph DB
  	transactionFactory = new InMemoryOrientDBTransactionFactory();
  	transactionFactory.connect();
    MigrationRunner migrationRunner = new MigrationRunner();
    migrationRunner.upgrade(transactionFactory, false);
      
    transaction = new InMemoryOrientDBTransactionFactory().getTransaction();
    transactionless = new InMemoryOrientDBTransactionFactory().getTransactionLess();
  }
  
  @After
  public void after() {
     transaction.finish();
     transactionFactory.disconnect();
  }
  
}
