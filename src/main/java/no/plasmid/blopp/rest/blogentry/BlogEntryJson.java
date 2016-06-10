package no.plasmid.blopp.rest.blogentry;

import no.plasmid.blopp.domain.domainobject.BlogEntry;
import no.plasmid.blopp.rest.NavigationElementJson;

public class BlogEntryJson extends NavigationElementJson {

	private String leadText;
	private String bodyText;
	
	public BlogEntryJson() {
		super();
	}
	
	public BlogEntryJson(BlogEntry blogEntry) {
		super(blogEntry);

		this.leadText = blogEntry.getLeadTextContent().getContent();
		this.bodyText = blogEntry.getBodyTextContent().getContent();
	}

	public String getLeadText() { return leadText; }
	public String getBodyText() { return bodyText; }

	public void setLeadText(String leadText) { this.leadText = leadText; }
	public void setBodyText(String bodyText) { this.bodyText = bodyText; }

}
