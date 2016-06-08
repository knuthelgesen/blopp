package no.plasmid.blopp.domain;

import org.junit.Assert;
import org.junit.Test;

import com.tinkerpop.blueprints.impls.orient.OrientVertex;

import no.plasmid.blopp.AbstractInMemoryTest;
import no.plasmid.blopp.domain.domainobject.DomainObject;

public class DomainObjectTests extends AbstractInMemoryTest {

	@Test
	public void testGetId() {
		TestObject testObject = new TestObject("name");
		Assert.assertNotNull(testObject.getId());
		Assert.assertNotEquals("", testObject.getId());
	}

	@Test
	public void testSetAndGetName() {
		TestObject testObject = new TestObject("name");
		testObject.setName("new name");
		Assert.assertEquals("new name", testObject.getName());
	}

	@Test
	public void testSetAndGetUrn() {
		TestObject testObject = new TestObject("name");
		testObject.setUrn("identifier");
		Assert.assertEquals("identifier", testObject.getUrn());
	}

	private class TestObject extends DomainObject<TestObject> {

		public TestObject(String name) {
			super(name);
		}
	
		public TestObject(OrientVertex ov) {
			super(ov);
		}

	}
	
}
