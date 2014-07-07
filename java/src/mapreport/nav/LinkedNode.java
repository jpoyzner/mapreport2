package mapreport.nav;

import mapreport.filter.NameFilter;
import mapreport.front.page.FilterNode;
import mapreport.front.url.PageURL;

public class LinkedNode extends NavigationNode{	
	PageURL url;
	// metaData

	public LinkedNode(FilterNode pageFilters, NameFilter nodeFilter) {
		super(pageFilters);
		// build url
	}
	public PageURL getUrl() {
		return url;
	}

	public void setUrl(PageURL url) {
		this.url = url;
	}
}
