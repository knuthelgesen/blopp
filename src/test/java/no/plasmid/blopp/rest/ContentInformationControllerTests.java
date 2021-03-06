package no.plasmid.blopp.rest;

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
import no.plasmid.blopp.exception.ErrorJson;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BloppApplication.class)
@WebAppConfiguration
public class ContentInformationControllerTests extends AbstractRestControllerTest {

	@Test
	public void testGetContentInformation() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/blopp-api/rest/content-information")
				.param("url", "/")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		String resultString = result.getResponse().getContentAsString();
		Assert.assertNotNull(resultString);
		ContentInformationJson resultObject = mapper.readValue(resultString, ContentInformationJson.class);
		Assert.assertNotNull(resultObject);
		Assert.assertEquals("NavigationPage", resultObject.getContentType());
		Assert.assertEquals("Front page", resultObject.getName());
		Assert.assertNotNull(resultObject.getVertexId());
	}

	@Test
	public void testGetContentInformationNotExist() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/blopp-api/rest/content-information")
				.param("url", "/not-exist")).andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
		String resultString = result.getResponse().getContentAsString();
		ErrorJson resultObject = mapper.readValue(resultString, ErrorJson.class);
		Assert.assertNotNull(resultObject);
		Assert.assertEquals("Could not find content with URL /not-exist", resultObject.getMessage());
		Assert.assertEquals(404, resultObject.getStatusCode());
	}
	
	@Test
	public void testGetContentInformationMissingUrl() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/blopp-api/rest/content-information"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
		String resultString = result.getResponse().getContentAsString();
		ErrorJson resultObject = mapper.readValue(resultString, ErrorJson.class);
		Assert.assertNotNull(resultObject);
		Assert.assertEquals("Required String parameter 'url' is not present", resultObject.getMessage());
		Assert.assertEquals(400, resultObject.getStatusCode());
	}
	
}
