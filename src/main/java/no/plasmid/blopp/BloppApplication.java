package no.plasmid.blopp;

import java.sql.SQLException;

import no.plasmid.blopp.migration.MigrationRunner;
import no.plasmid.blopp.orientdb.OrientDBServerManager;
import no.plasmid.blopp.orientdb.OrientDBTransactionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BloppApplication {

	private final static Logger LOG = LoggerFactory.getLogger(BloppApplication.class);

	public static void main(String[] args) throws SQLException {
		//Initialize DB factory
		OrientDBTransactionFactory transactionFactory = new OrientDBTransactionFactory();
		transactionFactory.connect();

		Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
          try {
          	transactionFactory.disconnect();
          	OrientDBServerManager.shutdownOrientDBServer();
          } catch (Exception e) {
              LOG.error(e.getMessage(), e);
          }
      }
    });
		
		//Start database server
		OrientDBServerManager.startOrientDBServer("Blopp node", "test");

		//Run migrations
		new MigrationRunner().upgrade(transactionFactory, true);

		SpringApplication.run(BloppApplication.class, args);
	}

}
