package mapreport.nav;

import mapreport.filter.NameFilter;
import mapreport.front.page.FilterNode;
import mapreport.front.page.PageMetaData;
import mapreport.news.MainNews;
import mapreport.util.Log;

enum nodeType {CHILD, CURRENT, SIBLING, PARENT};

public class NavigationNode implements Comparable{
	FilterNode pageFilters;
	PageMetaData metaData;
	MainNews mainNews;	
	int priority = 0;
	
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
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
		metaData = new PageMetaData(pageFilters);
		metaData.setHeader(filter.getName());
		metaData.setImage(filter.getImage());
		priority = filter.getPriority();
                 Log.log("NavigationNode filter.getName()=" + filter.getName() + " priority=" + priority + " pageFilters.getLink()=" + this.pageFilters.getLink());
	}



	@Override
	public int compareTo(Object arg0) {
		int diff = ((NavigationNode)arg0).getPriority() - this.getPriority() ;
	//	int diff =  this.getPriority() - ((NavigationNode)arg0).getPriority();
		return diff;
	}
}
