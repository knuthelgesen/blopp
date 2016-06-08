package no.plasmid.blopp.domain;

import com.tinkerpop.blueprints.impls.orient.OrientVertex;

import no.plasmid.blopp.domain.domainobject.NavigationElement;

@VertexClassName(value="TestNavigationElement")
public class TestNavigationElement extends NavigationElement<TestNavigationElement> {

	public TestNavigationElement(String name) {
		super(name);
	}
	
	public TestNavigationElement(OrientVertex ov) {
		super(ov);
	}

}
