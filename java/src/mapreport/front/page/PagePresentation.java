package mapreport.front.page;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

//import com.mysql.jdbc.PreparedStatement;






import mapreport.db.FilterDBQueryBuilder;
import mapreport.db.NewsFilterRow;
import mapreport.filter.DBFilter;
import mapreport.filter.Filter;
import mapreport.filter.NameFilter;
import mapreport.filter.loc.Global;
import mapreport.filter.loc.Local;
import mapreport.filter.loc.LocationByName;
import mapreport.filter.time.AllTime;
import mapreport.filter.time.Latest;
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
import mapreport.view.map.MapNewsList;
import mapreport.view.map.MapView;
import mapreport.view.map.Rectangle;

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
	PreparedStatement pst;
	
	public PreparedStatement getPst() {
		return pst;
	}

	public void setPst(PreparedStatement pst) {
		this.pst = pst;
	}

	public PagePresentation (
		FilterNode pageFilters,
		List<News> newsList,	
		Map<String, NameFilter> childFilters) throws SQLException {    
		
		    Log.info("PagePresentation newsList.size()=" + newsList.size() + " pageFilters=" + pageFilters);
			
		//	for (NewsFilterRow filter : filters) {
		//		 Log.log("PagePresentation getParentId=" + filter.getParentId()  + " filter.getParentLevel()=" + filter.getParentLevel());
		//	}			  
		
		if (pageFilters.getTimeFilter() != null && pageFilters.getTimeFilter() instanceof Latest) {
			 Log.info("PagePresentation getTimeFilter() instanceof Latest");
				Collections.sort(newsList, new Comparator() {
		            public int compare(Object o1, Object o2) 
		            {
		                News news1 = (News)o1;
		                News news2 = (News)o2; 
		                return news1.getDateTime().after(news2.getDateTime()) ? 1 : 0;
		                // it can also return 0, and 1
		            }
		 		});
		} else {		
			 Log.info("PagePresentation getTimeFilter() NOT instanceof Latest");
			Collections.sort(newsList, new Comparator() {
	            public int compare(Object o1, Object o2) 
	            {
	                News news1 = (News)o1;
	                News news2 = (News)o2; 
	                
	                if (news1.getDateTime().after(news2.getDateTime())) {
	                	return 1;
	                } else if (news1.getDateTime().before(news2.getDateTime())) {
	                	return -1;
	                }
	                return 0;
	             //   return news1.getDateTime().after(news2.getDateTime()) ? 0 : 1;
	                // it can also return 0, and 1
	            }
	 		});
		}

		for (News n : newsList) {
			Log.log("PagePresentation after sort getDateTime=" + n.getDateTime());
		}

	    newsList = buildIsMapShow(newsList);
		addChildNodes(pageFilters, childFilters);
		title = pageFilters.buildName();
	//	view = new View(new NewsList(newsList, pageFilters));
		//view.setNewsList(new NewsList(newsList, pageFilters));
		metaData = new PageMetaData(pageFilters);
		navigationPath = new NavigationPath(pageFilters, childFilters);
		view = new MapView(new MapNewsList(newsList, pageFilters)); // MapView  just is one of the view, extend later
		   Log.info("PagePresentation view.getNewsList()=" + view.getNewsList());
		   
				for (News news : newsList) {
					 Log.log("PagePresentation  news.getLabel()=" +  news.getLabel() + "  isMapShow=" + news.isMapShow());
				}			  
  	}
	
	List<News> buildIsMapShow(List<News> newsList) {
		int notMapShowCntr = 0;
		
		for (News news : newsList) {
			if (!news.isMapShow()) {
				notMapShowCntr++;
			} 
			if (notMapShowCntr > newsList.size() * 0.4) {
				for (News newsToChange : newsList) {
					newsToChange.setMapShow(true);
				}
				break;
			}
		}
		
		return newsList;
	}

	private void addChildNodes(FilterNode pageFilters,
			Map<String, NameFilter> childFilters) {
		for (String filterName : childFilters.keySet()) {
				NameFilter filter = childFilters.get(filterName);
	                      Log.log("PagePresentation filter=" + filter + " page filterName=" + filterName  + " filter.getName()=" + filter.getName() );
	                      
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

		navLocations.addChildFilter(new Global(), pageFilters);
		navLocations.addChildFilter(new Local(new Rectangle(0, 0, 0, 0)), pageFilters);
		navTopics.addChildFilter(new AllTopics(AllTopics.ALL_TOPICS), pageFilters);
		navDates.addChildFilter(new AllTime(), pageFilters);
		navDates.addChildFilter(new Latest(), pageFilters);
        
        navLocations.sort();
        navTopics.sort();
        navDates.sort();
        
        navLocations.limitChildren();
        navTopics.limitChildren();
        navDates.limitChildren();    
        
        navLocations.setChildrenMap(null);
        navTopics.setChildrenMap(null);
        navDates.setChildrenMap(null);
	}

	private void addParentNodes(FilterNode pageFilters, List<NewsFilterRow> parents) throws SQLException {
		List<String> filterIds = new ArrayList<String>(pageFilters.getFilterList().size());
		
		for (Filter filter : pageFilters.getFilterList()) {
			if (filter instanceof DBFilter) {
				filterIds.add(((DBFilter)filter).getName());
			}
		}
		
		if (filterIds.size() > 0) {	
			@SuppressWarnings("unused")
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
