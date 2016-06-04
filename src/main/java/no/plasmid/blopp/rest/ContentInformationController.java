package no.plasmid.blopp.rest;

import no.plasmid.blopp.domain.NavigationElement;
import no.plasmid.blopp.domain.NavigationPage;

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
public class ContentInformationController {

  private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(ContentInformationController.class);

  @RequestMapping(method = RequestMethod.GET, value = "/content-information")
  @ResponseBody
  public ResponseEntity<ContentInformationJson> getContentInformation(@RequestParam(value = "url") String url) {
  	LOG.debug("Get content information for URL: " + url);
		NavigationElement<?> found = NavigationElement.findDecendent(NavigationPage.getFrontPage(), url);
  	
  	return new ResponseEntity<ContentInformationJson>(new ContentInformationJson(found), HttpStatus.OK);
  }
  	
}
