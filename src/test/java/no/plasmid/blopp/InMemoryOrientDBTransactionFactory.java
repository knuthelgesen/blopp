package no.plasmid.blopp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;

import no.plasmid.blopp.orientdb.OrientDBTransactionFactory;

public class InMemoryOrientDBTransactionFactory extends OrientDBTransactionFactory {

  private final static Logger LOG = LoggerFactory.getLogger(InMemoryOrientDBTransactionFactory.class);
  
  @Override
  public void connect() {
    if (null == factory) {
      factory = new OrientGraphFactory("memory:test").setupPool(1,10);
      LOG.debug("Factory created: " + factory);
    }
  }
  
  @Override
  public void disconnect() {
    if (null != factory) {
      factory.getDatabase().drop();
      factory.getDatabase().close();
      factory.close();
      LOG.debug("Factory closed");
    }
  }
  
  public OrientGraphFactory getGraphFactory() {
  	return factory;
  }
	
}
