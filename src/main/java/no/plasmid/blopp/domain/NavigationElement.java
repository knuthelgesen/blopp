package no.plasmid.blopp.domain;

import no.plasmid.blopp.URLResolverFilter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;

@VertexClassName(value="NavigationElement")
public abstract class NavigationElement<T> extends DomainObject<NavigationElement<T>> {

  private final static Logger LOG = LoggerFactory.getLogger(URLResolverFilter.class);
	
	public NavigationElement(String name) {
		super(name);
	}
	
	public NavigationElement(OrientVertex ov) {
		super(ov);
	}
	
	@SuppressWarnings({"unchecked"})
	public <R extends NavigationElement<?>> R getParent() {
		R rc = (R) getRelatedDomainObject(ParentChild.class, Direction.IN);
		return rc;
	}
	
	@SuppressWarnings({"unchecked"})
	public <R extends NavigationElement<?>> R findChild(String urlFragment) {
		return (R)getRelatedDomainObject(ParentChild.class, Direction.OUT, new EdgeProperty("urlFragment", urlFragment));
	}
	
	@SuppressWarnings("unchecked")
	public String getUrlString() {
		ParentChild relationToParent = getRelation(ParentChild.class, Direction.IN);
		if (null != relationToParent) {
			return relationToParent.getParentDomainObject().getUrlString() + relationToParent.getProperty("urlFragment") + "/";
		} else {
			return "/";	//Root node, so no url fragment :)
		}
	}
	
	@SuppressWarnings({"unchecked" })
	public static <R extends NavigationElement<?>> R findDecendent(NavigationElement<?> ancestor, String url) {
		LOG.debug("Find decendent with URL " + url);
		NavigationElement<R> rc = (NavigationElement<R>) ancestor;
		String[] urlParts = url.split("/");
		for (String urlPart : urlParts) {
			if (StringUtils.isEmpty(urlPart)) {
				continue;
			}
			rc = rc.findChild(urlPart);
			if (null == rc) {
				break;
			}
		}
		
		return (R) rc;
	}
	
}
