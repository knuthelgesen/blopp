package no.plasmid.blopp.rest.blogentry;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import no.plasmid.blopp.domain.BlogEntry;
import no.plasmid.blopp.orientdb.OrientDBTransactionWrapper;

@Controller
@RequestMapping("/blopp-api/rest")
public class BlogEntryController {

	private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(BlogEntryController.class);

	@RequestMapping (value="blog-entries/{id}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<BlogEntryJson> get(@PathVariable String id) {
		LOG.debug("Get blog entry with ID: " + id);

		BlogEntry blogEntry = OrientDBTransactionWrapper.getInstance().getById(BlogEntry.class, id);
		
		return new ResponseEntity<BlogEntryJson>(new BlogEntryJson(blogEntry), HttpStatus.OK);
	}
	
}
