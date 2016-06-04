package no.plasmid.blopp.domain;

import com.tinkerpop.blueprints.impls.orient.OrientVertex;

@VertexClassName (value="BlogEntry")
public class BlogEntry extends NavigationElement<BlogEntry> {

	public BlogEntry(OrientVertex ov) {
		super(ov);
	}

	public BlogEntry(String name) {
		super(name);
	}

}
