package no.plasmid.blopp.domain.domainrelation;

import com.tinkerpop.blueprints.impls.orient.OrientEdge;

import no.plasmid.blopp.domain.EdgeClassName;
import no.plasmid.blopp.domain.domainobject.BlogEntry;
import no.plasmid.blopp.domain.domainobject.TextContent;

@EdgeClassName(value="ArticleLead")
public class ArticleLead extends DomainRelation<ArticleLead> {

	public ArticleLead(BlogEntry article, TextContent textContent) {
		super(article, textContent);
	}
  
  	public ArticleLead(OrientEdge oe) {
  		super(oe);
  	}

}
