package no.plasmid.blopp.migration;

import com.orientechnologies.orient.core.metadata.schema.OType;

import no.plasmid.blopp.domain.EdgeClass;
import no.plasmid.blopp.domain.VertexClass;
import no.plasmid.blopp.domain.domainobject.NavigationPage;
import no.plasmid.blopp.domain.domainrelation.ParentChild;
import no.plasmid.blopp.orientdb.OrientDBTransactionFactory;
import no.plasmid.blopp.orientdb.OrientDBTransactionWrapper;

public class Migration_20160522_1135_NavigationElement extends Migration {

	public Migration_20160522_1135_NavigationElement(OrientDBTransactionFactory transactionFactory) {
		super(transactionFactory);
	}

	@Override
	public String getDescription() {
		return "Create abstract type for all navigation elements, and type for navigation page.";
	}

	@Override
	public void upgrade() {
		VertexClass navigationElementClass = VertexClass.findOrCreate("NavigationElement");
		navigationElementClass.setAbstract(true);
		navigationElementClass.setSuperclass(VertexClass.findOrCreate("DomainObject"));
		
		EdgeClass parentChildEdgeClass = EdgeClass.findOrCreate("ParentChild");
		parentChildEdgeClass.addProperty("urlFragment", OType.STRING, true, false);
		parentChildEdgeClass.setFromVertexClass(navigationElementClass);
		parentChildEdgeClass.setToVertexClass(navigationElementClass);
		
		VertexClass navigationPage = VertexClass.findOrCreate("NavigationPage");
		navigationPage.setSuperclass(navigationElementClass);

		OrientDBTransactionWrapper transaction = transactionFactory.getTransaction();
		NavigationPage frontPage = new NavigationPage("Front page").setUrn("front-page");
		NavigationPage searchPage = new NavigationPage("Search");
		new ParentChild(frontPage, searchPage, "search");
		transaction.finish();
	}

}
