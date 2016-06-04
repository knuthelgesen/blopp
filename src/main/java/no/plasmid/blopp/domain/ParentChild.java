package no.plasmid.blopp.domain;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.impls.orient.OrientEdge;

@EdgeClassName(value="ParentChild")
public class ParentChild extends DomainRelation<ParentChild> {

	public ParentChild(NavigationElement<? extends DomainObject<?>> parentObject, NavigationElement<? extends DomainObject<?>> childObject) {
		super(parentObject, childObject);
	}
  
  	public ParentChild(OrientEdge oe) {
  		super(oe);
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
