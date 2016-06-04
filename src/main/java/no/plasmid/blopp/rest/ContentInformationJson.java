package no.plasmid.blopp.rest;

import no.plasmid.blopp.domain.DomainObject;

public class ContentInformationJson {

	private String vertexId;
	private String name;
	private String contentType;

	public ContentInformationJson() {}
	
	public ContentInformationJson(DomainObject<?> contentObject) {
		this.setVertexId(contentObject.getId());
		this.setName(contentObject.getName());
		this.setContentType(contentObject.getClass().getSimpleName());
	}

	public String getVertexId() { return vertexId; }
	public String getName() { return name; }
	public String getContentType() { return contentType; }

	public void setVertexId(String vertexId) { this.vertexId = vertexId; }
	public void setName(String name) { this.name = name; }
	public void setContentType(String contentType) { this.contentType = contentType; }
	
}
