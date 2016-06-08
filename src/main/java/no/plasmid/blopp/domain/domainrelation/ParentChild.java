package no.plasmid.blopp.domain.domainrelation;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.impls.orient.OrientEdge;

import no.plasmid.blopp.domain.EdgeClassName;
import no.plasmid.blopp.domain.domainobject.DomainObject;
import no.plasmid.blopp.domain.domainobject.NavigationElement;

@EdgeClassName(value="ParentChild")
public class ParentChild extends DomainRelation<ParentChild> {

	public ParentChild(NavigationElement<? extends DomainObject<?>> parentObject, NavigationElement<? extends DomainObject<?>> childObject, String urlFragment) {
		super(parentObject, childObject);
		setUrlFragment(urlFragment);
	}
  
  	public ParentChild(OrientEdge oe) {
  		super(oe);
  	}

	public String getUrlFragment() {
		return super.getProperty("urlFragment");
	}

	public ParentChild setUrlFragment(String urlFragment) {
		super.setProperty("urlFragment", urlFragment);
		return this;
	}

	public <R extends NavigationElement<?>> R getParentDomainObject() {
		return getDomainObject(Direction.OUT);
	}
	
	public <R extends NavigationElement<?>> R getChildDomainObject() {
		return getDomainObject(Direction.IN);
	}
	
}
