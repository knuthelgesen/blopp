package no.plasmid.blopp.rest;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import no.plasmid.blopp.AbstractInMemoryTest;

public class AbstractRestControllerTest extends AbstractInMemoryTest {

	protected MockMvc mockMvc;
	protected ObjectMapper mapper;
	
	@Autowired
	protected WebApplicationContext webApplicationContext;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		this.mapper = new ObjectMapper();
	}

}
