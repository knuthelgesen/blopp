package no.plasmid.blopp.migration.example;

import no.plasmid.blopp.domain.BlogEntry;
import no.plasmid.blopp.domain.Category;
import no.plasmid.blopp.domain.NavigationPage;
import no.plasmid.blopp.domain.ParentChild;
import no.plasmid.blopp.orientdb.OrientDBTransactionFactory;
import no.plasmid.blopp.orientdb.OrientDBTransactionWrapper;

public class Migration_20160602_2120_Blog_entries extends ExampleDataMigration {

	public Migration_20160602_2120_Blog_entries(OrientDBTransactionFactory transactionFactory) {
		super(transactionFactory);
	}

	@Override
	public String getDescription() {
		return "Create some blog entries";
	}

	@Override
	public void upgrade() {
		OrientDBTransactionWrapper transaction = transactionFactory.getTransaction();

		BlogEntry testEntry = new BlogEntry("Testing blopp");
		Category projectsCategory = NavigationPage.getFrontPage().findChild("projects");
		new ParentChild(projectsCategory, testEntry).setUrlFragment("testing-blopp");
		
		transaction.finish();
		
	}

}
