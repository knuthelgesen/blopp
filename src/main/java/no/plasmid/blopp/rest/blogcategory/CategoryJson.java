package no.plasmid.blopp.rest.blogcategory;

import java.util.ArrayList;
import java.util.List;

import no.plasmid.blopp.domain.domainobject.BlogEntry;
import no.plasmid.blopp.domain.domainobject.Category;
import no.plasmid.blopp.domain.domainobject.NavigationElement;
import no.plasmid.blopp.rest.NavigationElementJson;
import no.plasmid.blopp.rest.blogentry.BlogEntryJson;

public class CategoryJson extends NavigationElementJson {


	private List<ChildJson> subCategories = new ArrayList<ChildJson>();
	private List<BlogEntryJson> blogEntries = new ArrayList<BlogEntryJson>();
	
	public CategoryJson() {
		super();
	}

	public CategoryJson(Category category) {
		super(category);
		
		for (NavigationElement<?> child : category.getChildren()) {
			if (child instanceof Category) {
				subCategories.add(new ChildJson(child));
			}
			if (child instanceof BlogEntry) {
				blogEntries.add(new BlogEntryJson((BlogEntry)child));
			}
		}
	}
	
	public List<ChildJson> getSubCategories() { return subCategories; }
	public List<BlogEntryJson> getBlogEntries() { return blogEntries; }

	public void setSubCategories(List<ChildJson> subCategories) { this.subCategories = subCategories; }
	public void setBlogEntries(List<BlogEntryJson> blogEntries) { this.blogEntries = blogEntries; }

}
