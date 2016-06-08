package no.plasmid.blopp.domain.domainobject;

import com.tinkerpop.blueprints.impls.orient.OrientVertex;

import no.plasmid.blopp.domain.VertexClassName;

@VertexClassName (value="Category")
public class Category extends NavigationElement<Category> {

	public Category(OrientVertex ov) {
		super(ov);
	}

	public Category(String name) {
		super(name);
	}

}
