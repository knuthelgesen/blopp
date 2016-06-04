package no.plasmid.blopp.orientdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.metadata.sequence.OSequence;
import com.orientechnologies.orient.core.metadata.sequence.OSequence.SEQUENCE_TYPE;
import com.orientechnologies.orient.core.metadata.sequence.OSequenceLibrary;
import com.tinkerpop.blueprints.impls.orient.OrientBaseGraph;
import com.tinkerpop.blueprints.impls.orient.OrientEdgeType;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType;

public class OrientDBTransactionlessWrapper extends AbstractOrientDBWrapper {

  private final static Logger LOG = LoggerFactory.getLogger(OrientDBTransactionlessWrapper.class);

  private static OrientDBTransactionlessWrapper instance;

  private OrientGraphNoTx graph;

  public OrientDBTransactionlessWrapper(OrientGraphNoTx graph) {
    pushInstance(this);
    this.graph = graph;
  }

  private static void pushInstance(OrientDBTransactionlessWrapper transaction) {
    instance = transaction;
  }

  public static OrientDBTransactionlessWrapper getInstance() {
    return instance;
  }

  private static void closeInstance() {
    instance = null;
  }

	@Override
	protected OrientBaseGraph getGraph() {
		return graph;
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

  public OrientVertexType ensureVertexClassExists(String vertexClassName) {
    OrientVertexType ovt = graph.getVertexType(vertexClassName);
    if (null == ovt) {
        LOG.debug("Creating vertex class " + vertexClassName);
        ovt = graph.createVertexType(vertexClassName);
        ovt.setClusterSelection("local");
    }
    ovt.setStrictMode(true);
    graph.commit();
    return ovt;
  }

	public void ensureVertexPropertyExists(OrientVertexType ovt, String propertyName, OType propertyType, boolean mandatory, boolean readOnly) {
    LOG.debug("Ensure vertex class " + ovt.getName() + " has property " + propertyName + " of type " + propertyType.name());
    OProperty op = ovt.getProperty(propertyName);
    if (null == op) {
        op = ovt.createProperty(propertyName, propertyType);
    }
    op.setType(propertyType);
    op.setMandatory(mandatory);
    op.setReadonly(readOnly);
	}

  public OrientEdgeType ensureEdgeClassExists(String edgeClassName) {
    OrientEdgeType oet = graph.getEdgeType(edgeClassName);
    if (null == oet) {
        LOG.debug("Creating edge class " + edgeClassName);
        oet = graph.createEdgeType(edgeClassName);
        oet.setClusterSelection("local");
    }
    oet.setStrictMode(true);
    
    OProperty inProperty = oet.getProperty("in");
    if (null == inProperty) {
        inProperty = oet.createProperty("in", OType.LINK);
    }
    inProperty.setType(OType.LINK);
    
    OProperty outProperty = oet.getProperty("out");
    if (null == outProperty) {
        outProperty = oet.createProperty("out", OType.LINK);
    }
    outProperty.setType(OType.LINK);
    
    return oet;
  }

	public void ensureEdgePropertyExists(OrientEdgeType oet, String propertyName, OType propertyType, boolean mandatory, boolean readOnly) {
    LOG.debug("Ensure edge class " + oet.getName() + " has property " + propertyName + " of type " + propertyType.name());
    OProperty op = oet.getProperty(propertyName);
    if (null == op) {
        op = oet.createProperty(propertyName, propertyType);
    }
    op.setType(propertyType);
    op.setMandatory(mandatory);
    op.setReadonly(readOnly);
	}
  
	public void createSequence(String name) {
  	OSequenceLibrary sequenceLib = getGraph().getRawGraph().getMetadata().getSequenceLibrary();
  	sequenceLib.createSequence(name, SEQUENCE_TYPE.ORDERED, new OSequence.CreateParams().setStart(0L).setIncrement(1));
  }

}
