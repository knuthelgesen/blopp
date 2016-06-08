package no.plasmid.blopp.orientdb;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import no.plasmid.blopp.domain.EdgeClassName;
import no.plasmid.blopp.domain.VertexClassName;
import no.plasmid.blopp.domain.domainobject.DomainObject;
import no.plasmid.blopp.domain.domainrelation.DomainRelation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orientechnologies.orient.core.metadata.sequence.OSequence;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientBaseGraph;
import com.tinkerpop.blueprints.impls.orient.OrientEdge;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;

public abstract class AbstractOrientDBWrapper {

	private final static Logger LOG = LoggerFactory.getLogger(AbstractOrientDBWrapper.class);

	public static String getVertexClassNameFromClass(Class<?> theClass) {
		while (theClass != null) {
			VertexClassName annotation = theClass.getAnnotation(VertexClassName.class);
			if (null != annotation) return annotation.value();
			theClass = theClass.getSuperclass();
		}

		throw new IllegalArgumentException("Class lacks vertex class name");
	}
	
	public static String getEdgeClassNameFromClass(Class<?> theClass) {
		while (theClass != null) {
			EdgeClassName annotation = theClass.getAnnotation(EdgeClassName.class);
			if (null != annotation) return annotation.value();
			theClass = theClass.getSuperclass();
		}

		throw new IllegalArgumentException("Class lacks edge class name");
	}
	
	protected abstract OrientBaseGraph getGraph();
  
	public OrientVertex createVertex(@SuppressWarnings("rawtypes") Class<? extends DomainObject> vertexClass, String name) {
		OSequence vIdSequence = getGraph().getRawGraph().getMetadata().getSequenceLibrary().getSequence("vIdSeq");
		
		String vertexClassName = getVertexClassNameFromClass(vertexClass);
		LOG.debug("Creting vertex with java class " + vertexClass.getSimpleName() + " and vertex class " + vertexClassName);
		OrientVertex ov = getGraph().addVertex("class:" + vertexClassName, "vId", "V" + vIdSequence.next(), "name", name);

		ov.query().has("", "").edges();
		ov.query().has("", "").vertices();
    
		return ov;
	}
  
	public <T extends DomainObject<?>> Iterable<T> findVertexInstances(Class<T> vertexClass, String propertyName, String propertyValue) {
		String vertexClassName = getVertexClassNameFromClass(vertexClass);
		LOG.debug("Get vertices with java class " + vertexClass.getSimpleName() + " and vertex class " + vertexClassName + " and property " + propertyName + " equals " + propertyValue);
		return wrapIterable(vertexClass, getGraph().getVertices(vertexClassName + "." + propertyName, propertyValue));
	}
  
	@SuppressWarnings("rawtypes")
	public OrientEdge createEdge(Class<? extends DomainRelation> edgeClass, OrientVertex fromVertex, OrientVertex toVertex) {
		String edgeClassName = getEdgeClassNameFromClass(edgeClass);
		LOG.debug("Creting edge with java class " + edgeClass.getSimpleName() + " and edge class " + edgeClassName);
		OrientEdge oe = getGraph().addEdge(null, fromVertex, toVertex, edgeClassName);

		return oe;
	}
  
	protected <T extends DomainObject<?>> Iterable<T> wrapIterable(Class<T> theClass, final Iterable<Vertex> graphIterable) {
		Iterable<T> rc = new Iterable<T>() {
        
			@Override
			public Iterator<T> iterator() {
				final Iterator<Vertex> iterator = graphIterable.iterator();
            
				return new Iterator<T>() {

					@Override
					public boolean hasNext() {
						return iterator.hasNext();
					}

					@Override
					public T next() {
						try {
							return (T)theClass.getConstructor(OrientVertex.class).newInstance((OrientVertex)iterator.next());
						} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
							throw new RuntimeException("Error processing query result!", e);
						}
					}
				};
			}
		};
		
		return rc;
	}

}
