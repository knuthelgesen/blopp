package no.plasmid.blopp.migration;

import no.plasmid.blopp.domain.DomainObject;
import no.plasmid.blopp.domain.VertexClassName;

import com.tinkerpop.blueprints.impls.orient.OrientVertex;

@VertexClassName (value="FinishedMigration")
public class FinishedMigration extends DomainObject<FinishedMigration> {
	
	public FinishedMigration(OrientVertex ov) {
		super(ov);
	}
	
}
