package no.plasmid.blopp.domain;

import no.plasmid.blopp.orientdb.OrientDBTransactionlessWrapper;

import com.orientechnologies.orient.core.metadata.schema.OType;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType;

public class VertexClass {

	OrientVertexType ovt;

  private VertexClass(OrientVertexType ovt) {
    this.ovt = ovt;
  }

	public static VertexClass findOrCreate(String vertexClassName) {
	  return new VertexClass(OrientDBTransactionlessWrapper.getInstance().ensureVertexClassExists(vertexClassName));
	}
	
	public VertexClass addProperty(String propertyName, OType propertyType, boolean mandatory, boolean readOnly) {
		OrientDBTransactionlessWrapper.getInstance().ensureVertexPropertyExists(ovt, propertyName, propertyType, mandatory, readOnly);
	  return this;
	}

	public boolean isAbstract() {
		return ovt.isAbstract();
	}
	
	public VertexClass setAbstract(boolean isAbstract) {
		ovt.setAbstract(isAbstract);
		return this;
	}

	public VertexClass setSuperclass(VertexClass superClass) {
		ovt.setSuperClass(superClass.ovt);
		return this;
	}

}
