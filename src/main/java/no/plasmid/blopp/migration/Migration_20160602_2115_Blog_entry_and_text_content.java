package no.plasmid.blopp.migration;

import com.orientechnologies.orient.core.metadata.schema.OType;

import no.plasmid.blopp.domain.EdgeClass;
import no.plasmid.blopp.domain.VertexClass;
import no.plasmid.blopp.orientdb.OrientDBTransactionFactory;

public class Migration_20160602_2115_Blog_entry_and_text_content extends Migration {

	public Migration_20160602_2115_Blog_entry_and_text_content(OrientDBTransactionFactory transactionFactory) {
		super(transactionFactory);
	}

	@Override
	public String getDescription() {
		return "Create types for blog entries and text content.";
	}

	@Override
	public void upgrade() {
		VertexClass blogEntryClass = VertexClass.findOrCreate("BlogEntry").setAbstract(false).setSuperclass(VertexClass.findOrCreate("NavigationElement"));
		
		VertexClass textContentClass = VertexClass.findOrCreate("TextContent").setAbstract(false).setSuperclass(VertexClass.findOrCreate("DomainObject"));
		textContentClass.addProperty("content", OType.LINK, true, false);
		
		EdgeClass.findOrCreate("ArticleLead").setFromVertexClass(blogEntryClass).setToVertexClass(textContentClass);
		EdgeClass.findOrCreate("ArticleBody").setFromVertexClass(blogEntryClass).setToVertexClass(textContentClass);
	}

}
