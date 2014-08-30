package mapreport.front.page;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mapreport.db.FilterDBQueryBuilder;
import mapreport.db.NewsFilterRow;
import mapreport.filter.DBFilter;
import mapreport.filter.Filter;
import mapreport.filter.NameFilter;
import mapreport.filter.loc.Global;
import mapreport.filter.loc.LocationByName;
import mapreport.filter.time.OfficialTimeFilter;
import mapreport.filter.time.TimeFilter;
import mapreport.filter.topic.AllTopics;
import mapreport.filter.topic.Topic;
import mapreport.nav.NavigationList;
import mapreport.nav.NavigationPath;
import mapreport.news.News;
import mapreport.tree.Tree;
import mapreport.util.Log;
import mapreport.view.View;
import mapreport.view.list.NewsList;
import mapreport.view.map.MapNewsList;
import mapreport.view.map.MapView;

public class PagePresentation {
	//private String value2 = "abc";
	View view;
	PageMetaData metaData;
	List<String> keywords;
	Tree navigationTree;
	NavigationPath navigationPath;
	String title = null;

	NavigationList navLocations = new NavigationList("Locations");
	NavigationList navTopics = new NavigationList("Topics");
	NavigationList navDates = new NavigationList("Dates");
	
	String googleAnalytics;
	
	public PagePresentation (
		FilterNode pageFilters,
		List<NewsFilterRow> newsFilters,
		List<News> newsList,
		List<NewsFilterRow> filters,	
		Map<String, NameFilter> childFilters,
		List<NewsFilterRow> parents) throws SQLException {    
		
			System.out.println("PagePresentation newsList=" + newsList + " pageFilters=" + pageFilters);
			
			for (NewsFilterRow filter : filters) {
				 Log.log("PagePresentation getParentId=" + filter.getParentId()  + " filter.getParentLevel()=" + filter.getParentLevel());
			}			
		
		addParentNodes(pageFilters, parents); 			
		addChildNodes(pageFilters, childFilters);
		title = pageFilters.buildName();
	//	view = new View(new NewsList(newsList, pageFilters));
		//view.setNewsList(new NewsList(newsList, pageFilters));
		metaData = new PageMetaData(pageFilters);
		navigationPath = new NavigationPath(pageFilters, childFilters);
		view = new MapView(new MapNewsList(newsList, pageFilters)); // MapView  just is one of the view, extend later
		   Log.log("PagePresentation view.getNewsList()=" + view.getNewsList());
	//			(Coordinates coords, Rectangle rect, NewsList newsList, String mapUrl, List<MapZoomLink> mapZoomLinks);
		
	}

	private void addChildNodes(FilterNode pageFilters,
			Map<String, NameFilter> childFilters) {
		for (String filterName : childFilters.keySet()) {
				NameFilter filter = childFilters.get(filterName);
	                      Log.log("PagePresentation filter=" + filter + " filterName=" + filterName  + " filter.getName()=" + filter.getName() );
	                      
                if (filter instanceof LocationByName) {       
  	            	navLocations.addChildFilter(filter, pageFilters); 
  	            } else if (filter instanceof Topic) {       
  	            	navTopics.addChildFilter(filter, pageFilters); 
	            } else if (filter instanceof OfficialTimeFilter) {       
	            	navDates.addChildFilter(filter, pageFilters); 
	            }
			//	NavigationNode navNode = new NavigationNode(filterNode, filter);
	         //              Log.log("NavigationPath navNode=" + navNode + " navNode.pageFilters=" + navNode.pageFilters);
			//	addNode (navNode);  
		}
	}

	private void addParentNodes(FilterNode pageFilters, List<NewsFilterRow> parents) throws SQLException {
		List<String> filterIds = new ArrayList<String>(pageFilters.getFilterList().size());
		
		for (Filter filter : pageFilters.getFilterList()) {
			if (filter instanceof DBFilter) {
				filterIds.add(((DBFilter)filter).getName());
			}
		}
		
		if (filterIds.size() > 0) {	
			FilterDBQueryBuilder filterDBQueryBuilder = new FilterDBQueryBuilder();
		//	List<NewsFilterRow> parents = filterDBQueryBuilder.runQuery(filterIds);
			
			for (NewsFilterRow parent : parents) {
				       Log.log("PagePresentation parent.getName()=" + parent.getName()  + " parent.isLocation()=" + parent.isLocation());
				if (parent.isLocation()) {
					NameFilter filter = new LocationByName(parent.getName());
		            navLocations.addParentFilter(filter, pageFilters); 				
				} else  {
					NameFilter filter = new Topic(parent.getName());
					navTopics.addParentFilter(filter, pageFilters); 				
				}
			}
	
			navLocations.addParentFilter(new Global("Global"), pageFilters); 
			navTopics.addParentFilter(new AllTopics("AllTopics"), pageFilters);
		}
		
		TimeFilter timeFilter = pageFilters.getTimeFilter();
		while(timeFilter != null && timeFilter.getParent() != null) {
			navDates.addParentFilter(timeFilter.getParent(), pageFilters);	
			timeFilter = timeFilter.getParent();
		}
	
	}
	/*
	private void addFilter (LocationByName filter, FilterNode filterNode) {
		navLocations.addFilter(filter, filterNode);
	}
	
	private void addFilter (Topic filter, FilterNode filterNode) {
		navTopics.addFilter(filter, filterNode);
	}
	
	private void addFilter (OfficialTimeFilter filter, FilterNode filterNode) {
		navDates.addFilter(filter, filterNode);
	}
	*/
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("News ["); //?????????????????????????????????????????????
		// "DataObject [data1=" + data1 + ", data2=" + data2 + ", list=" + list + "]"

	//	return sb.toString();
		return "DataObject [data1=" + "data1" + ", data2=" + "data2" + ", list=" + "list" + "]";
	}
	
	public View getView() {
		return view;
	}
	public void setView(View view) {
		this.view = view;
	}
	public PageMetaData getMetaData() {
		return metaData;
	}
	public void setMetaData(PageMetaData metaData) {
		this.metaData = metaData;
	}
	public List<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}
	public Tree getNavigationTree() {
		return navigationTree;
	}
	public void setNavigationTree(Tree navigationTree) {
		this.navigationTree = navigationTree;
	}
	public NavigationPath getNavigationPath() {
		return navigationPath;
	}
	public void setNavigationPath(NavigationPath navigationPath) {
		this.navigationPath = navigationPath;
	}
	public String getGoogleAnalytics() {
		return googleAnalytics;
	}
	public void setGoogleAnalytics(String googleAnalytics) {
		this.googleAnalytics = googleAnalytics;
	}

}
