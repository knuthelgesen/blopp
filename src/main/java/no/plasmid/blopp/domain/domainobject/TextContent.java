package no.plasmid.blopp.domain.domainobject;

import com.orientechnologies.orient.core.record.impl.ORecordBytes;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;

import no.plasmid.blopp.domain.VertexClassName;

@VertexClassName(value="TextContent")
public class TextContent extends DomainObject<TextContent> {

	public TextContent(OrientVertex ov) {
		super(ov);
	}

	public TextContent(String name) {
		super(name);
		setProperty("content", new ORecordBytes("".getBytes()));
	}
	
	public String getContent() {
		ORecordBytes contentRecord = getProperty("content");
		return new String(contentRecord.toStream());
	}
	
	public TextContent setContent(String content) {
		ORecordBytes contentRecord = getProperty("content");
		contentRecord.fromStream(content.getBytes());
		contentRecord.setDirty().save();
		return this;
	}

}
