package no.plasmid.blopp.domain;

import org.junit.Assert;
import org.junit.Test;

import no.plasmid.blopp.AbstractInMemoryTest;
import no.plasmid.blopp.domain.domainobject.BlogEntry;
import no.plasmid.blopp.domain.domainobject.TextContent;

public class BlogEntryTests extends AbstractInMemoryTest {

	@Test
	public void testCanSetGetLeadText() {
		BlogEntry blogEntry = new BlogEntry("Test entry");
		TextContent leadTextContent = blogEntry.getLeadTextContent();
		Assert.assertNotNull(leadTextContent);
		leadTextContent.setContent("Here is the lead text");
		Assert.assertEquals("Here is the lead text", blogEntry.getLeadTextContent().getContent());
	}
	
}
