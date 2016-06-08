package no.plasmid.blopp.domain.domainrelation;

import no.plasmid.blopp.domain.DomainUtils;
import no.plasmid.blopp.domain.EdgeClassName;
import no.plasmid.blopp.domain.domainobject.DomainObject;
import no.plasmid.blopp.orientdb.OrientDBTransactionWrapper;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.impls.orient.OrientEdge;

@EdgeClassName(value="E")
public abstract class DomainRelation<T extends DomainRelation<?>> {

  private OrientEdge oe;

  public DomainRelation(DomainObject<?> fromObject, DomainObject<?> toObject) {
    oe = OrientDBTransactionWrapper.getInstance().createEdge(this.getClass(), fromObject.getOrientVertex(), toObject.getOrientVertex());
  }
  
  public DomainRelation(OrientEdge oe) {
  	this.oe = oe;
  }
	
  protected <R> R getProperty(String propertyName) {
    return oe.getProperty(propertyName);
  }

  protected <R> void setProperty(String propertyName, R value) {
    oe.setProperty(propertyName, value);
  }
  
  protected <R extends DomainObject<?>> R getDomainObject(Direction direction) {
	  return DomainUtils.createDomainObjectInstance(oe.getVertex(direction));
  }
  
}
