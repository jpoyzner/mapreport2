package mapreport.front.page;

import java.util.List;
import java.util.Map;

import mapreport.db.NewsFilterRow;
import mapreport.filter.NameFilter;
import mapreport.filter.loc.LocationByName;
import mapreport.filter.time.OfficialTimeFilter;
import mapreport.filter.topic.Topic;
import mapreport.nav.NavigationList;
import mapreport.nav.NavigationPath;
import mapreport.news.News;
import mapreport.tree.Tree;
import mapreport.util.Log;
import mapreport.view.View;
import mapreport.view.list.NewsList;

public class PagePresentation {
	//private String value2 = "abc";
	View view;
	PageMetaData metaData;
	List<String> keywords;
	Tree navigationTree;
	NavigationPath navigationPath;

	NavigationList navLocations = new NavigationList("Locations");
	NavigationList navTopics = new NavigationList("Topics");
	NavigationList navDates = new NavigationList("Dates");
	
	String googleAnalytics;
	
	public PagePresentation (
		FilterNode pageFilters,
		List<NewsFilterRow> newsFilters,
		List<News> newsList,
		List<NewsFilterRow> filters,	
		Map<String, NameFilter> childFilters) {
		
			System.out.println("PagePresentation newsList=" + newsList + " pageFilters=" + pageFilters);
			
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

		view = new View(new NewsList(newsList, pageFilters));
		//view.setNewsList(new NewsList(newsList, pageFilters));
		metaData = new PageMetaData(pageFilters);
		navigationPath = new NavigationPath(pageFilters, childFilters);
	//	view = new MapView // MapView  just is one of the view, extend later
	//			(Coordinates coords, Rectangle rect, NewsList newsList, String mapUrl, List<MapZoomLink> mapZoomLinks);
		
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
