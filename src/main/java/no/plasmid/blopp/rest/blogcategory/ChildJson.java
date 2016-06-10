package no.plasmid.blopp.rest.blogcategory;

import no.plasmid.blopp.domain.domainobject.NavigationElement;
import no.plasmid.blopp.rest.NavigationElementJson;

public class ChildJson extends NavigationElementJson {

	private String contentType;
	
	public ChildJson() {
		super();
	}
	
	public ChildJson(NavigationElement<?> child) {
		super(child);
		
		this.contentType = child.getClass().getName();
	}

	public String getContentType() { return contentType; }

	public void setContentType(String contentType) { this.contentType = contentType; }

}
