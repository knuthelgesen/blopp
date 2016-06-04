package no.plasmid.blopp.domain;

import com.tinkerpop.blueprints.impls.orient.OrientVertex;

@VertexClassName (value="Category")
public class Category extends NavigationElement<Category> {

	public Category(OrientVertex ov) {
		super(ov);
	}

	public Category(String name) {
		super(name);
	}

}
