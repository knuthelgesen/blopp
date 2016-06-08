package no.plasmid.blopp.domain.domainobject;

import no.plasmid.blopp.domain.VertexClassName;
import no.plasmid.blopp.orientdb.OrientDBTransactionWrapper;

import com.tinkerpop.blueprints.impls.orient.OrientVertex;

@VertexClassName (value="NavigationPage")
public class NavigationPage extends NavigationElement<NavigationPage> {

	public NavigationPage(String name) {
		super(name);
	}

	public NavigationPage(OrientVertex ov) {
		super(ov);
	}
	
	public static NavigationPage getFrontPage() {
		return OrientDBTransactionWrapper.getInstance().getByUrn(NavigationPage.class, "front-page");
	}

}
