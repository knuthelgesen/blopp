package no.plasmid.blopp.migration;

import no.plasmid.blopp.orientdb.OrientDBTransactionFactory;

public abstract class Migration {

	protected OrientDBTransactionFactory transactionFactory;
	
	public Migration(OrientDBTransactionFactory transactionFactory) {
		this.transactionFactory = transactionFactory;
	}
	
	public abstract String getDescription();
	
	public abstract void upgrade();

}
