package no.plasmid.blopp.domain;

import no.plasmid.blopp.orientdb.OrientDBTransactionlessWrapper;

import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.tinkerpop.blueprints.impls.orient.OrientEdgeType;

public class EdgeClass {

	private OrientEdgeType oet;
	
	private EdgeClass(OrientEdgeType oet) {
		this.oet = oet;
	}
	
  public static EdgeClass findOrCreate(String edgeClassName) {
    return new EdgeClass(OrientDBTransactionlessWrapper.getInstance().ensureEdgeClassExists(edgeClassName));
	}
  
  public EdgeClass setFromVertexClass(VertexClass outVertexClass) {
    OProperty op = oet.getProperty("out");
    //Remove property from old linked class
    if (null != op.getLinkedClass()) {
        op.getLinkedClass().dropProperty("out_" + oet.getName());
    }
    
    //Set linked class to new one
    oet.getProperty("out").setLinkedClass(outVertexClass.ovt);
    
    //Set property on new linked class
    outVertexClass.ovt.createProperty("out_" + oet.getName(), OType.LINKBAG);
    
    return this;
  }

  public EdgeClass setToVertexClass(VertexClass inVertexClass) {
    OProperty op = oet.getProperty("in");
    //Remove property from old linked class
    if (null != op.getLinkedClass()) {
        op.getLinkedClass().dropProperty("in_" + oet.getName());
    }
    
    //Set linked class to new one
    oet.getProperty("in").setLinkedClass(inVertexClass.ovt);
    
    //Set property on new linked class
    inVertexClass.ovt.createProperty("in_" + oet.getName(), OType.LINKBAG);
    
    return this;
  }

	public EdgeClass addProperty(String propertyName, OType propertyType, boolean mandatory, boolean readOnly) {
		OrientDBTransactionlessWrapper.getInstance().ensureEdgePropertyExists(oet, propertyName, propertyType, mandatory, readOnly);
	  return this;
	}
}
