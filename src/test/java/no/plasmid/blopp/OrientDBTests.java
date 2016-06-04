package no.plasmid.blopp;

import org.junit.Assert;
import org.junit.Test;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

public class OrientDBTests extends AbstractInMemoryTest {

//	@Test
	public void hei() {
		OrientGraph graph = transactionFactory.getGraphFactory().getTx();
		
		Vertex v1 = graph.addVertex("class:V");
		v1.setProperty("name", "Knut");
		
		Vertex v2 = graph.addVertex("class:V");
		v2.setProperty("name", "Wei");
		
		Vertex v3 = graph.addVertex("class:V");
		v3.setProperty("name", "Vidar");
		
		Edge e1 = graph.addEdge(null, v1, v2, "E");
		e1.setProperty("relationship", "married");

		Edge e2 = graph.addEdge(null, v1, v3, "E");
		e2.setProperty("relationship", "friends");

		Edge e3 = graph.addEdge(null, v2, v3, "E");
		e3.setProperty("relationship", "friends");

		for (Vertex vertex : v1.query().has("relationship", "married").vertices()) {
			Assert.assertEquals("Wei", vertex.getProperty("name"));
		}
		for (Vertex vertex : v2.query().has("relationship", "married").vertices()) {
			Assert.assertEquals("Knut", vertex.getProperty("name"));
		}
		
		for (Vertex vertex : v1.query().has("relationship", "friends").vertices()) {
			Assert.assertEquals("Vidar", vertex.getProperty("name"));
		}
		
		for (Edge edge : v1.query().direction(Direction.OUT).edges()) {
//			System.out.println(edge.getVertex(Direction.IN).getProperty("name"));
		}
		
		for (Edge edge : v1.query().edges()) {
			Assert.assertEquals("E", edge.getLabel());
		}
		
	}
	
}
