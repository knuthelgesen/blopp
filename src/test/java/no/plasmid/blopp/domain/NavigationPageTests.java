package no.plasmid.blopp.domain;

import org.junit.Assert;
import org.junit.Test;

import no.plasmid.blopp.AbstractInMemoryTest;
import no.plasmid.blopp.domain.domainobject.NavigationPage;

public class NavigationPageTests extends AbstractInMemoryTest {

	@Test
	public void testCanGetFrontPage() {
		NavigationPage frontPage = NavigationPage.getFrontPage();
		Assert.assertNotNull(frontPage);
		Assert.assertEquals("Front page", frontPage.getName());
		Assert.assertEquals("front-page", frontPage.getUrn());
	}
	
	@Test
	public void testCanGetSearchPage() {
		NavigationPage searchPage = NavigationPage.getFrontPage().findChild("search");
		Assert.assertNotNull(searchPage);
		Assert.assertEquals("Search", searchPage.getName());
		Assert.assertNull(searchPage.getUrn());
	}
	
}
