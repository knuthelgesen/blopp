package no.plasmid.blopp.migration;

import no.plasmid.blopp.domain.VertexClass;
import no.plasmid.blopp.orientdb.OrientDBTransactionFactory;

public class Migration_20160524_1830_TestNavigationElement extends Migration {

	public Migration_20160524_1830_TestNavigationElement(OrientDBTransactionFactory transactionFactory) {
		super(transactionFactory);
	}

	@Override
	public String getDescription() {
		return "Add test navigation element";
	}

	@Override
	public void upgrade() {
		VertexClass testNavigationElementClass = VertexClass.findOrCreate("TestNavigationElement");
		testNavigationElementClass.setSuperclass(VertexClass.findOrCreate("NavigationElement"));
	}

}
