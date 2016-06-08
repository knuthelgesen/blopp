package no.plasmid.blopp.rest.blogentry;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import no.plasmid.blopp.BloppApplication;
import no.plasmid.blopp.domain.domainobject.BlogEntry;
import no.plasmid.blopp.domain.domainobject.NavigationPage;
import no.plasmid.blopp.domain.domainrelation.ParentChild;
import no.plasmid.blopp.exception.ErrorJson;
import no.plasmid.blopp.rest.AbstractRestControllerTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BloppApplication.class)
@WebAppConfiguration
public class BlogEntryControllerTests extends AbstractRestControllerTest {

	@Test
	public void testGetBlogEntry() throws Exception {
		BlogEntry testEntry = new BlogEntry("Test entry").setUrn("urn:test-entry");
		testEntry.getLeadTextContent().setContent("Lead text content");
		testEntry.getBodyTextContent().setContent("Body text content");
		new ParentChild(NavigationPage.getFrontPage(), testEntry, "test-entry");
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/blopp-api/rest/blog-entries/" + testEntry.getId())
				.param("url", "/")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		String resultString = result.getResponse().getContentAsString();
		Assert.assertNotNull(resultString);
		BlogEntryJson resultObject = mapper.readValue(resultString, BlogEntryJson.class);
		Assert.assertNotNull(resultObject);
		Assert.assertEquals("Test entry", resultObject.getName());
		Assert.assertEquals("urn:test-entry", resultObject.getUrn());
		Assert.assertEquals(testEntry.getId(), resultObject.getId());
		Assert.assertEquals("/test-entry/", resultObject.getUrlString());
		Assert.assertEquals("Lead text content", resultObject.getLeadText());
		Assert.assertEquals("Body text content", resultObject.getBodyText());
	}

	@Test
	public void testGetBlogEntryNotExist() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/blopp-api/rest/blog-entries/non-existent"))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
		String resultString = result.getResponse().getContentAsString();
		ErrorJson resultObject = mapper.readValue(resultString, ErrorJson.class);
		Assert.assertNotNull(resultObject);
		Assert.assertEquals("Could not find vertex with ID non-existent", resultObject.getMessage());
		Assert.assertEquals(404, resultObject.getStatusCode());
	}
	
}
