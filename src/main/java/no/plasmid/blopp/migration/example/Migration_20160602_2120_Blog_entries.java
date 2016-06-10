package no.plasmid.blopp.migration.example;

import no.plasmid.blopp.domain.domainobject.BlogEntry;
import no.plasmid.blopp.domain.domainobject.Category;
import no.plasmid.blopp.domain.domainobject.NavigationPage;
import no.plasmid.blopp.domain.domainobject.TextContent;
import no.plasmid.blopp.domain.domainrelation.ParentChild;
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

		Category projectsCategory = NavigationPage.getFrontPage().findChild("projects");
		Category bloppCategory = new Category("Blopp");
		new ParentChild(projectsCategory, bloppCategory, "blopp");
		Category anotherCategory = new Category("Another");
		new ParentChild(projectsCategory, anotherCategory, "another");
		
		BlogEntry testEntry = new BlogEntry("Testing blopp");
		new ParentChild(bloppCategory, testEntry, "testing-blopp");
		
		TextContent leadText = testEntry.getLeadTextContent();
		String lead1 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent dapibus tincidunt massa, vel varius lorem suscipit a. "
				+ "Cras et est tempus, faucibus orci non, tincidunt eros. Pellentesque in elit congue, vulputate mi euismod, elementum tortor. "
				+ "Nulla tortor magna, ullamcorper sit amet luctus eu, suscipit ut ligula. Nullam sed elit enim. "
				+ "Pellentesque rutrum lacus ligula, sit amet ullamcorper leo dignissim et. Vestibulum nec magna eu dolor pellentesque interdum. "
				+ "Praesent pharetra libero lobortis lacinia accumsan. Fusce pretium urna elit, non ornare sapien cursus sit amet. "
				+ "Praesent auctor at sapien eget molestie. Aenean consectetur aliquet venenatis. Phasellus non mi ac felis porttitor imperdiet.";
		leadText.setContent(lead1.substring(0, 400));
		
		TextContent bodyText = testEntry.getBodyTextContent();
		bodyText.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent dapibus tincidunt massa, vel varius lorem suscipit a. "
				+ "Cras et est tempus, faucibus orci non, tincidunt eros. Pellentesque in elit congue, vulputate mi euismod, elementum tortor. "
				+ "Nulla tortor magna, ullamcorper sit amet luctus eu, suscipit ut ligula. Nullam sed elit enim. "
				+ "Pellentesque rutrum lacus ligula, sit amet ullamcorper leo dignissim et. Vestibulum nec magna eu dolor pellentesque interdum. "
				+ "Praesent pharetra libero lobortis lacinia accumsan. Fusce pretium urna elit, non ornare sapien cursus sit amet. "
				+ "Praesent auctor at sapien eget molestie. Aenean consectetur aliquet venenatis. Phasellus non mi ac felis porttitor imperdiet.");
		
		BlogEntry testEntry2 = new BlogEntry("Another article");
		new ParentChild(bloppCategory, testEntry2, "testing-blopp");
		
		TextContent leadText2 = testEntry2.getLeadTextContent();
		String lead2 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent dapibus tincidunt massa, vel varius lorem suscipit a. "
				+ "Cras et est tempus, faucibus orci non, tincidunt eros. Pellentesque in elit congue, vulputate mi euismod, elementum tortor. "
				+ "Nulla tortor magna, ullamcorper sit amet luctus eu, suscipit ut ligula. Nullam sed elit enim. "
				+ "Pellentesque rutrum lacus ligula, sit amet ullamcorper leo dignissim et. Vestibulum nec magna eu dolor pellentesque interdum. "
				+ "Praesent pharetra libero lobortis lacinia accumsan. Fusce pretium urna elit, non ornare sapien cursus sit amet. "
				+ "Praesent auctor at sapien eget molestie. Aenean consectetur aliquet venenatis. Phasellus non mi ac felis porttitor imperdiet.";
		leadText2.setContent(lead2.substring(0, 400));
				
		TextContent bodyText2 = testEntry2.getBodyTextContent();
		bodyText2.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent dapibus tincidunt massa, vel varius lorem suscipit a. "
				+ "Cras et est tempus, faucibus orci non, tincidunt eros. Pellentesque in elit congue, vulputate mi euismod, elementum tortor. "
				+ "Nulla tortor magna, ullamcorper sit amet luctus eu, suscipit ut ligula. Nullam sed elit enim. "
				+ "Pellentesque rutrum lacus ligula, sit amet ullamcorper leo dignissim et. Vestibulum nec magna eu dolor pellentesque interdum. "
				+ "Praesent pharetra libero lobortis lacinia accumsan. Fusce pretium urna elit, non ornare sapien cursus sit amet. "
				+ "Praesent auctor at sapien eget molestie. Aenean consectetur aliquet venenatis. Phasellus non mi ac felis porttitor imperdiet.");
		transaction.finish();
	}

}
