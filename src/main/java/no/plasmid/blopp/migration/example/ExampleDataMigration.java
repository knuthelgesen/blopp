package no.plasmid.blopp.migration.example;

import no.plasmid.blopp.migration.Migration;
import no.plasmid.blopp.orientdb.OrientDBTransactionFactory;

public abstract class ExampleDataMigration extends Migration {

	public ExampleDataMigration(OrientDBTransactionFactory transactionFactory) {
		super(transactionFactory);
	}
}
