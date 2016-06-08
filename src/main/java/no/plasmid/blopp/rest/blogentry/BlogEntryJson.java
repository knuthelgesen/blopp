package no.plasmid.blopp.rest.blogentry;

import no.plasmid.blopp.domain.domainobject.BlogEntry;

public class BlogEntryJson {

	private String id;
	private String name;
	private String urlString;
	private String urn;
	
	private String leadText;
	private String bodyText;
	
	public BlogEntryJson() {}
	
	public BlogEntryJson(BlogEntry blogEntry) {
		this.id = blogEntry.getId();
		this.name = blogEntry.getName();
		this.urlString = blogEntry.getUrlString();
		this.urn = blogEntry.getUrn();
		
		this.leadText = blogEntry.getLeadTextContent().getContent();
		this.bodyText = blogEntry.getBodyTextContent().getContent();
	}

	public String getId() { return id; }
	public String getName() { return name; }
	public String getUrlString() { return urlString; }
	public String getUrn() { return urn; }
	public String getLeadText() { return leadText; }
	public String getBodyText() { return bodyText; }

	public void setId(String id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setUrlString(String urlString) { this.urlString = urlString; }
	public void setUrn(String urn) { this.urn = urn; }
	public void setLeadText(String leadText) { this.leadText = leadText; }
	public void setBodyText(String bodyText) { this.bodyText = bodyText; }

}
