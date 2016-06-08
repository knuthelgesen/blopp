package no.plasmid.blopp;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import no.plasmid.blopp.domain.domainobject.NavigationElement;
import no.plasmid.blopp.domain.domainobject.NavigationPage;

public class URLResolverFilter implements Filter {

  private final static Logger LOG = LoggerFactory.getLogger(URLResolverFilter.class);

  @Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String uri = httpRequest.getRequestURI();
		LOG.debug("Resolving for URI: " + uri);
		
		NavigationElement<?> found = NavigationElement.findDecendent(NavigationPage.getFrontPage(), uri);
		
		LOG.debug("Found: " + found);
		if (null != found) {
			httpRequest.getRequestDispatcher("/index.html").forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
