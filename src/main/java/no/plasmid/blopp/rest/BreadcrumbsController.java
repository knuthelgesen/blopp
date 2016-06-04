package no.plasmid.blopp.rest;

import java.util.ArrayList;
import java.util.List;

import no.plasmid.blopp.domain.NavigationElement;
import no.plasmid.blopp.domain.NavigationPage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/blopp-api/rest")
public class BreadcrumbsController {

  private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(BreadcrumbsController.class);

	@RequestMapping (value="breadcrumbs", method=RequestMethod.GET)
  @ResponseBody
	public ResponseEntity<List<BreadcrumbsEntryJson>> get(@RequestParam(value = "url") String url) {
		LOG.debug("Get breadcrumbs for URL: " + url);
		List<BreadcrumbsEntryJson> rc = new ArrayList<BreadcrumbsEntryJson>();

		NavigationElement<?> navElement = NavigationPage.getFrontPage();
		rc.add(new BreadcrumbsEntryJson(navElement));
		String[] urlParts = url.split("/");
		for (String urlPart : urlParts) {
			if (StringUtils.isEmpty(urlPart)) {
				continue;
			}
			
			navElement = navElement.findChild(urlPart);
			if (null == navElement) {
				break;
			}
			
			rc.add(new BreadcrumbsEntryJson(navElement));
		}
		
		return new ResponseEntity<List<BreadcrumbsEntryJson>>(rc, HttpStatus.OK);
	}
	
}
