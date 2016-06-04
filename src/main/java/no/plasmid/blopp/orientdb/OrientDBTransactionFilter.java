package no.plasmid.blopp.orientdb;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.LoggerFactory;

public class OrientDBTransactionFilter implements Filter {

  private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(OrientDBTransactionFilter.class);

  private OrientDBTransactionFactory transactionFactory;

  @Override
	public void destroy() {
  	transactionFactory.disconnect();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    request.setCharacterEncoding("UTF-8");
    OrientDBTransactionWrapper transaction = transactionFactory.getTransaction();
    LOG.debug("Created transaction: " + transaction);
    chain.doFilter(request, response);
    transaction.finish();
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
    transactionFactory = new OrientDBTransactionFactory();
    transactionFactory.connect();
	}

}
