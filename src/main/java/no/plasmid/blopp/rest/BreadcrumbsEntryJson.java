package no.plasmid.blopp.rest;

import no.plasmid.blopp.domain.domainobject.NavigationElement;

public class BreadcrumbsEntryJson {

	private String name;
	private String urlString;
	
	public BreadcrumbsEntryJson() {}

	public BreadcrumbsEntryJson(NavigationElement<?> navElement) {
		this.name = navElement.getName();
		this.urlString = navElement.getUrlString();
	}

	public String getName() { return name; }
	public String getUrlString() { return urlString; }

	public void setName(String name) { this.name = name; }
	public void setUrlString(String url) { this.urlString = url; }

}
