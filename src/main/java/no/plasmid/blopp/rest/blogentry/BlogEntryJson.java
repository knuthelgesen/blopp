package no.plasmid.blopp.rest.blogentry;

import no.plasmid.blopp.domain.BlogEntry;

public class BlogEntryJson {

	private String id;
	private String name;
	private String urlString;
	private String urn;
	
	public BlogEntryJson() {}
	
	public BlogEntryJson(BlogEntry blogEntry) {
		this.id = blogEntry.getId();
		this.name = blogEntry.getName();
		this.urlString = blogEntry.getUrlString();
		this.urn = blogEntry.getUrn();
	}

	public String getId() { return id; }
	public String getName() { return name; }
	public String getUrlString() { return urlString; }
	public String getUrn() { return urn; }

	public void setId(String id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setUrlString(String urlString) { this.urlString = urlString; }
	public void setUrn(String urn) { this.urn = urn; }

}
