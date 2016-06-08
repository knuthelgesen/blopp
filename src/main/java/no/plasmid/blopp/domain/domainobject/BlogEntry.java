package no.plasmid.blopp.domain.domainobject;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;

import no.plasmid.blopp.domain.VertexClassName;
import no.plasmid.blopp.domain.domainrelation.ArticleBody;
import no.plasmid.blopp.domain.domainrelation.ArticleLead;

@VertexClassName (value="BlogEntry")
public class BlogEntry extends NavigationElement<BlogEntry> {

	public BlogEntry(OrientVertex ov) {
		super(ov);
	}

	public BlogEntry(String name) {
		super(name);
		
		new ArticleLead(this, new TextContent(name + " lead"));
		new ArticleBody(this, new TextContent(name + " body"));
	}
	
	@SuppressWarnings("unchecked")
	public TextContent getLeadTextContent() {
		return getRelatedDomainObject(ArticleLead.class, Direction.OUT);
	}

	@SuppressWarnings("unchecked")
	public TextContent getBodyTextContent() {
		return getRelatedDomainObject(ArticleBody.class, Direction.OUT);
	}

}
