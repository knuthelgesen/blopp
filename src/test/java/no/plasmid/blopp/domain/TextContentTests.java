package no.plasmid.blopp.domain;

import org.junit.Assert;
import org.junit.Test;

import no.plasmid.blopp.AbstractInMemoryTest;
import no.plasmid.blopp.domain.domainobject.TextContent;

public class TextContentTests extends AbstractInMemoryTest {

	@Test
	public void testSetGetContent() {
		TextContent textContent = new TextContent("Content");
		textContent.setContent("Here comes the content!");
		String readBack = textContent.getContent();
		Assert.assertEquals("Here comes the content!", readBack);
	}

}
