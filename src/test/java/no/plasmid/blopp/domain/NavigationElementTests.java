package no.plasmid.blopp.domain;

import org.junit.Assert;
import org.junit.Test;

import no.plasmid.blopp.AbstractInMemoryTest;

public class NavigationElementTests extends AbstractInMemoryTest {

	@Test
	public void testCreateNavigationTree() {
		TestNavigationElement parent = new TestNavigationElement("Parent");
		TestNavigationElement child1 = new TestNavigationElement("Child 1");
		TestNavigationElement child2 = new TestNavigationElement("Child 2");
		
		new ParentChild(parent, child1).setUrlFragment("child1");
		new ParentChild(parent, child2).setUrlFragment("child2");
		
		TestNavigationElement found1 = parent.findChild("child1");
		Assert.assertNotNull(found1);
		Assert.assertEquals("Child 1", found1.getName());
		TestNavigationElement found2 = parent.findChild("child2");
		Assert.assertNotNull(found2);
		Assert.assertEquals("Child 2", found2.getName());
	}
	
	@Test
	public void testGetParent() {
		TestNavigationElement parent = new TestNavigationElement("Parent");
		TestNavigationElement child1 = new TestNavigationElement("Child 1");
		
		new ParentChild(parent, child1).setUrlFragment("child1");
		
		TestNavigationElement found = child1.getParent();
		Assert.assertNotNull(found);
		Assert.assertEquals("Parent", found.getName());
	}

	@Test
	public void testFindDecendent() {
		TestNavigationElement parent = new TestNavigationElement("Parent");
		TestNavigationElement child1 = new TestNavigationElement("Child 1");
		TestNavigationElement child2 = new TestNavigationElement("Child 2");
		
		new ParentChild(parent, child1).setUrlFragment("child1");
		new ParentChild(child1, child2).setUrlFragment("child2");
		
		TestNavigationElement found = NavigationElement.findDecendent(parent, "/child1/child2");
		Assert.assertNotNull(found);
		Assert.assertEquals("Child 2", found.getName());
	}
	
	@Test
	public void testGetUrlString() {
		TestNavigationElement parent = new TestNavigationElement("Parent");
		TestNavigationElement child1 = new TestNavigationElement("Child 1");
		TestNavigationElement child2 = new TestNavigationElement("Child 2");
		
		new ParentChild(parent, child1).setUrlFragment("child1");
		new ParentChild(child1, child2).setUrlFragment("child2");
		
		String urlString = child2.getUrlString();
		Assert.assertNotNull(urlString);
		Assert.assertEquals("/child1/child2/", urlString);
	}
	
}
