package no.plasmid.blopp.orientdb;

import java.util.Stack;

import no.plasmid.blopp.domain.DomainObject;
import no.plasmid.blopp.exception.VertexNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tinkerpop.blueprints.impls.orient.OrientBaseGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

public class OrientDBTransactionWrapper extends AbstractOrientDBWrapper {

	private final static Logger LOG = LoggerFactory.getLogger(OrientDBTransactionWrapper.class);
	
	protected static ThreadLocal<Stack<OrientDBTransactionWrapper>> threadBoundInstances = new ThreadLocal<Stack<OrientDBTransactionWrapper>>();

	private OrientGraph graph;

	public OrientDBTransactionWrapper(OrientGraph graph) {
		pushInstance(this);
		this.graph = graph;
	}

	public static OrientDBTransactionWrapper getInstance() {
		Stack<OrientDBTransactionWrapper> transactions = threadBoundInstances.get();
		if (null == transactions) {
			transactions = new Stack<OrientDBTransactionWrapper>();
			threadBoundInstances.set(transactions);
		}
		if (transactions.isEmpty()) {
			return null;
		}
		return transactions.peek();
	}

	protected static void pushInstance(OrientDBTransactionWrapper transaction) {
		Stack<OrientDBTransactionWrapper> transactions = threadBoundInstances.get();
		if (null == transactions) {
			transactions = new Stack<OrientDBTransactionWrapper>();
			threadBoundInstances.set(transactions);
		}
		transactions.push(transaction);
	}
    
	protected static void closeInstance() {
		threadBoundInstances.get().pop();
	}

	public void finish() {
		closeInstance();
		graph.commit();
		graph.shutdown();
	}

	public void rollback() {
	  closeInstance();
	  graph.rollback();
	  graph.shutdown();
	}

	public void commit() {
	    graph.commit();
	}

	@Override
	protected OrientBaseGraph getGraph() {
		return graph;
	}

	public <T extends DomainObject<?>> T getByUrn(Class<T> vertexClass, String urn) {
		for (T vertex : findVertexInstances(vertexClass, "urn", urn)) {
			return vertex;
		}
		throw new VertexNotFoundException("Could not find vertex with URN " + urn);
	}

	public <T extends DomainObject<?>> T getById(Class<T> vertexClass, String vertexId) {
		for (T vertex : findVertexInstances(vertexClass, "vId", vertexId)) {
			return vertex;
		}
		throw new VertexNotFoundException("Could not find vertex with ID " + vertexId);
	}

}
