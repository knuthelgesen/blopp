package no.plasmid.blopp.domain.domainobject;

import no.plasmid.blopp.domain.DomainUtils;
import no.plasmid.blopp.domain.VertexClassName;
import no.plasmid.blopp.domain.domainrelation.DomainRelation;
import no.plasmid.blopp.orientdb.OrientDBTransactionWrapper;

import java.util.ArrayList;
import java.util.List;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.VertexQuery;
import com.tinkerpop.blueprints.impls.orient.OrientEdge;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;

@VertexClassName (value="V")
public abstract class DomainObject<T extends DomainObject<T>> {
	
  private OrientVertex ov;

  public DomainObject(String name) {
    ov = OrientDBTransactionWrapper.getInstance().createVertex(this.getClass(), name);
  }
  
  public DomainObject(OrientVertex ov) {
  	this.ov = ov;
  }
  
  public OrientVertex getOrientVertex() {
  	return ov;
  }
  
  public String getId() {
    return getProperty("vId");
  }

  public String getName() {
    return getProperty("name");
  }
  
  @SuppressWarnings("unchecked")
  public <R extends DomainObject<?>> R setName(String name) {
    setProperty("name", name);
    return (R)this;
  }

  public String getUrn() {
    return getProperty("urn");
  }
  
  @SuppressWarnings("unchecked")
  public <R extends DomainObject<?>> R setUrn(String urn) {
    setProperty("urn", urn);
    return (R)this;
  }
  
  protected <R> R getProperty(String propertyName) {
    return ov.getProperty(propertyName);
  }

  protected <R> void setProperty(String propertyName, R value) {
    ov.setProperty(propertyName, value);
  }

  @SuppressWarnings("unchecked")
  protected <R extends DomainObject<?>> R getRelatedDomainObject(Class<? extends DomainRelation<?>> relationType, Direction direction, EdgeProperty... edgeProperties) {
	  R rc = null;
	  VertexQuery query = ov.query().labels(DomainUtils.resolveEdgeClassName(relationType)).direction(direction);
	  for (EdgeProperty edgeProperty : edgeProperties) {
		  query.has(edgeProperty.propertyName, edgeProperty.propertyValue);
	  }
	  for (Vertex vertex : query.vertices()) {
		  rc = DomainUtils.createDomainObjectInstance((OrientVertex) vertex);
	  }
	  return rc;
  }
  
  @SuppressWarnings("unchecked")
  protected <R extends DomainObject<?>> List<R> getRelatedDomainObjects(Class<? extends DomainRelation<?>> relationType, Direction direction, EdgeProperty... edgeProperties) {
	  List<R> rc = new ArrayList<R>();
	  VertexQuery query = ov.query().labels(DomainUtils.resolveEdgeClassName(relationType)).direction(direction);
	  for (EdgeProperty edgeProperty : edgeProperties) {
		  query.has(edgeProperty.propertyName, edgeProperty.propertyValue);
	  }
	  for (Vertex vertex : query.vertices()) {
		  rc.add(DomainUtils.createDomainObjectInstance((OrientVertex) vertex));
	  }
	  return rc;
  }
  
  @SuppressWarnings("unchecked")
  protected <R extends DomainRelation<?>> R getRelation(Class<? extends DomainRelation<?>> relationType, Direction direction, EdgeProperty... edgeProperties) {
	  R rc = null;
	  VertexQuery query = ov.query().labels(DomainUtils.resolveEdgeClassName(relationType)).direction(direction);
	  for (EdgeProperty edgeProperty : edgeProperties) {
		  query.has(edgeProperty.propertyName, edgeProperty.propertyValue);
	  }
	  for (Edge edge : query.edges()) {
		  rc = DomainUtils.createDomainRelationInstance((OrientEdge) edge);
	  }
	  
	  return rc;
  }  
  
  protected class EdgeProperty {
	  private String propertyName;
	  private Object propertyValue;
	
	  protected EdgeProperty(String propertyName, Object propertyValue) {
		  this.propertyName = propertyName;
		  this.propertyValue = propertyValue;
	  }
  }
  
}
