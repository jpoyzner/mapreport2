package mapreport.nav;

import mapreport.filter.NameFilter;
import mapreport.front.page.FilterNode;
import mapreport.front.page.PageMetaData;
import mapreport.news.MainNews;
import mapreport.util.Log;

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
		     Log.log("NavigationNode filter=" + filter);
		this.pageFilters = new FilterNode(pageFilters);
		this.pageFilters.limitFilter(filter);
		           Log.log("NavigationNode  this.pageFilters.getLink()=" + this.pageFilters.getLink());
		metaData = new PageMetaData(pageFilters);
		metaData.setHeader(filter.getName());
		//metaData.s
	}
	
	PageMetaData metaData;

	MainNews mainNews;
}
