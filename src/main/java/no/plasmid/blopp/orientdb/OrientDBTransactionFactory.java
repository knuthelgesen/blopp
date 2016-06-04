package no.plasmid.blopp.orientdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;

public class OrientDBTransactionFactory {

  private final static Logger LOG = LoggerFactory.getLogger(OrientDBTransactionFactory.class);

  protected static OrientGraphFactory factory;

  public void connect() {
    if (null == factory) {
        factory = new OrientGraphFactory("plocal:./databases/test").setupPool(1,10);
        LOG.debug("Factory created: " + factory);
    }
  }
  
  public void disconnect() {
    if (null != factory) {
        factory.close();
        LOG.debug("Factory closed");
    }
  }
  
	public OrientDBTransactionlessWrapper getTransactionLess() {
		if (null == factory) { throw new IllegalStateException("Factory not initialized"); }
		LOG.debug("Create transactionless");
		return new OrientDBTransactionlessWrapper(factory.getNoTx());
	}
		
	public OrientDBTransactionWrapper getTransaction() {
		if (null == factory) { throw new IllegalStateException("Factory not initialized"); }
		LOG.debug("Create transaction");
		return new OrientDBTransactionWrapper(factory.getTx());
	}
		
}
