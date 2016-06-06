package no.plasmid.blopp.rest;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.type.TypeReference;

import no.plasmid.blopp.BloppApplication;
import no.plasmid.blopp.exception.ErrorJson;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BloppApplication.class)
@WebAppConfiguration
public class BreadcrumbsControllerTests extends AbstractRestControllerTest {

	@Test
	public void testGetBreadcrumbs() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/blopp-api/rest/breadcrumbs")
				.param("url", "/search")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		String resultString = result.getResponse().getContentAsString();
		Assert.assertNotNull(resultString);
		List<BreadcrumbsEntryJson> resultObjects = mapper.readValue(resultString, new TypeReference<List<BreadcrumbsEntryJson>>(){});
		Assert.assertNotNull(resultObjects);
		Assert.assertEquals(2, resultObjects.size());

		BreadcrumbsEntryJson firstEntry = resultObjects.get(0);
		Assert.assertEquals("Front page", firstEntry.getName());
		Assert.assertEquals("/", firstEntry.getUrlString());
		BreadcrumbsEntryJson secondEntry = resultObjects.get(1);
		Assert.assertEquals("Search", secondEntry.getName());
		Assert.assertEquals("/search/", secondEntry.getUrlString());
	}

	@Test
	public void testGetBreadcrumbsNotExist() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/blopp-api/rest/breadcrumbs")
				.param("url", "/not-exist")).andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
		String resultString = result.getResponse().getContentAsString();
		ErrorJson resultObject = mapper.readValue(resultString, ErrorJson.class);
		Assert.assertNotNull(resultObject);
		Assert.assertEquals("Could not find content with URL /not-exist", resultObject.getMessage());
		Assert.assertEquals(404, resultObject.getStatusCode());
	}
	
	@Test
	public void testGetBreadcrumbsMissingUrl() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/blopp-api/rest/content-information"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
		String resultString = result.getResponse().getContentAsString();
		ErrorJson resultObject = mapper.readValue(resultString, ErrorJson.class);
		Assert.assertNotNull(resultObject);
		Assert.assertEquals("Required String parameter 'url' is not present", resultObject.getMessage());
		Assert.assertEquals(400, resultObject.getStatusCode());
	}
	
}
