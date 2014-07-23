package mapreport.nav;

import mapreport.filter.NameFilter;
import mapreport.front.page.FilterNode;
import mapreport.front.page.PageMetaData;
import mapreport.news.MainNews;
import mapreport.util.Log;

enum nodeType {CHILD, CURRENT, SIBLING, PARENT};

public class NavigationNode {
	FilterNode pageFilters;
	
	public FilterNode getPageFilters() {
		return pageFilters;
	}

	public void setPageFilters(FilterNode pageFilters) {
		this.pageFilters = pageFilters;
	}

	public NavigationNode(FilterNode pageFilters) {		
	}
	
	public NavigationNode(FilterNode pageFilters, NameFilter filter) {
		this(pageFilters, filter, nodeType.CHILD);
	}

	public NavigationNode(FilterNode pageFilters, NameFilter filter, nodeType type) {
		     Log.log("NavigationNode filter=" + filter);
		this.pageFilters = new FilterNode(pageFilters);
		
		if (type == nodeType.CHILD) {
			this.pageFilters.limitFilter(filter);
		} else if (type == nodeType.PARENT) {
			this.pageFilters.upFilter(filter);
		} else if (type == nodeType.SIBLING) {
			//this.pageFilters.limitFilter(filter);
		} else if (type == nodeType.CURRENT) {
			// this.pageFilters.limitFilter(filter);
		} 
		           Log.log("NavigationNode  this.pageFilters.getLink()=" + this.pageFilters.getLink());
		metaData = new PageMetaData(pageFilters);
		metaData.setHeader(filter.getName());
	}

	PageMetaData metaData;

	MainNews mainNews;
}
