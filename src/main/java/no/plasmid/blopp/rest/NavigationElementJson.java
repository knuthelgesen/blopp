package no.plasmid.blopp.rest;

import no.plasmid.blopp.domain.domainobject.NavigationElement;

public class NavigationElementJson {
	
	private String id;
	private String name;
	private String urlString;
	private String urn;

	public NavigationElementJson() { }

	public NavigationElementJson(NavigationElement<?> navElement) {
		this.id = navElement.getId();
		this.name = navElement.getName();
		this.urlString = navElement.getUrlString();
		this.urn = navElement.getUrn();
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
