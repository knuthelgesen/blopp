package no.plasmid.blopp.rest.blogcategory;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import no.plasmid.blopp.domain.domainobject.Category;
import no.plasmid.blopp.orientdb.OrientDBTransactionWrapper;

@Controller
@RequestMapping("/blopp-api/rest")
public class CategoryController {

	private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(CategoryController.class);

	@RequestMapping (value="categories/{id}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<CategoryJson> get(@PathVariable String id) {
		LOG.debug("Get blog entry with ID: " + id);

		Category category = OrientDBTransactionWrapper.getInstance().getById(Category.class, id);
		
		return new ResponseEntity<CategoryJson>(new CategoryJson(category), HttpStatus.OK);
	}

}
