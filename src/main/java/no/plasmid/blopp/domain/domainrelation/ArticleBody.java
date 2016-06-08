package no.plasmid.blopp.domain.domainrelation;

import com.tinkerpop.blueprints.impls.orient.OrientEdge;

import no.plasmid.blopp.domain.EdgeClassName;
import no.plasmid.blopp.domain.domainobject.BlogEntry;
import no.plasmid.blopp.domain.domainobject.TextContent;

@EdgeClassName(value="ArticleBody")
public class ArticleBody extends DomainRelation<ArticleBody> {

	public ArticleBody(BlogEntry article, TextContent textContent) {
		super(article, textContent);
	}
  
  	public ArticleBody(OrientEdge oe) {
  		super(oe);
  	}

}
