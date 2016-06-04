package no.plasmid.blopp.migration;

import no.plasmid.blopp.domain.Category;
import no.plasmid.blopp.domain.NavigationPage;
import no.plasmid.blopp.domain.ParentChild;
import no.plasmid.blopp.domain.VertexClass;
import no.plasmid.blopp.orientdb.OrientDBTransactionFactory;
import no.plasmid.blopp.orientdb.OrientDBTransactionWrapper;

public class Migration_20160529_2259_Category extends Migration {

	public Migration_20160529_2259_Category(
			OrientDBTransactionFactory transactionFactory) {
		super(transactionFactory);
	}

	@Override
	public String getDescription() {
		return "Create type for categories.";
	}

	@Override
	public void upgrade() {
		VertexClass.findOrCreate("Category").setSuperclass(VertexClass.findOrCreate("NavigationElement"));
		
		OrientDBTransactionWrapper transaction = transactionFactory.getTransaction();
		Category cookingCategory = new Category("Cooking");
		new ParentChild(NavigationPage.getFrontPage(), cookingCategory).setUrlFragment("cooking");
		Category programmingCategory = new Category("Programming");
		new ParentChild(NavigationPage.getFrontPage(), programmingCategory).setUrlFragment("programming");
		Category projectsCategory = new Category("Projects");
		new ParentChild(NavigationPage.getFrontPage(), projectsCategory).setUrlFragment("projects");
		transaction.finish();
	}

}
