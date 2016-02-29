package mapreport.front.page;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.mysql.jdbc.PreparedStatement;










import java.util.Set;

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
import mapreport.filter.time.ThisDayInHistory;
import mapreport.filter.time.TimeFilter;
import mapreport.filter.topic.AllTopics;
import mapreport.filter.topic.Topic;
import mapreport.front.option.Options;
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
		Map<String, NameFilter> childFilters,
		String localLong, String localLat, Options options) throws SQLException {    
		
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
	    newsList = addSecondIcon(newsList);
		addChildNodes(pageFilters, childFilters, localLong, localLat, options);
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
	
	List<News> addSecondIcon(List<News> newsList) {
		Map<String, Integer> iconMap = new HashMap<String, Integer>(20);
		
		int max = 0;
		int flagCntr = 0;
		String commonFlag = "";
		boolean isAllFlagsSame = true;
		
		for (News news : newsList) {
			String icon = news.getIcon();
			
			Log.log("addSecondIcon icon:" + icon);
			if (iconMap.get(icon) == null) {
				iconMap.put(icon, 1);
			} else {
				int newNm = iconMap.get(icon) + 1;
				iconMap.put(icon, newNm);
				max = Math.max(newNm, max);
				
				if (isAllFlagsSame) {
					String flag = null;
					String flag2 = news.getIcon2();
					
					if (icon != null && icon.indexOf("flag") > -1) {
						flag = icon;
					} else if (flag2 != null && flag2.indexOf("flag") > -1) {
						flag = flag2;
					}
					if (flag != null) {
						flagCntr++;
						
						if (!flag.equals(commonFlag)) {
							if (commonFlag.isEmpty()) {
								commonFlag = flag;
							} else {
								isAllFlagsSame = false;
							}
						}
					}
				}
			}
		}
		
		Log.log("addSecondIcon max:" + max);
		
		Set<Map.Entry<String, Integer>> entrySet = iconMap.entrySet();
		
	    for (Map.Entry<String, Integer> entry : entrySet) {
	          Integer cntr = entry.getValue();
	          
	          if (cntr == max) {
	        	  
	        	  Log.log("addSecondIcon cntr == max");
	        	  String maxIcon = entry.getKey();
	        	  
	        	  boolean isStarted = false;
	        	  for (News news : newsList) {
	        			if (news.getIcon() != null && news.getIcon().equals(maxIcon)) {
	        				if (isStarted && news.getIcon2() != null && !news.getIcon2().isEmpty()) {
	        					Log.log("addSecondIcon was news.getIcon()=" + news.getIcon() + " news.getIcon2()=" + news.getIcon2() + " label=" + news.getLabel());
	        					String firstIcon = new String(news.getIcon());
	        					news.setIcon(news.getIcon2());
	        					news.setIcon2(firstIcon);
	        					Log.log("addSecondIcon after news.getIcon()=" + news.getIcon());
	        				}
	        				isStarted = true;
	        			}
	        	  }
	          }
	    }
	    
	    if (isAllFlagsSame) {
      	  for (News news : newsList) {
      			if (news.getIcon() != null && news.getIcon().equals(commonFlag)) {
      				if (news.getIcon2() != null && !news.getIcon2().isEmpty()) {
      				//	Log.log("addSecondIcon was news.getIcon()=" + news.getIcon() + " news.getIcon2()=" + news.getIcon2() + " label=" + news.getLabel());
      					news.setIcon(news.getIcon2());
      				//	Log.log("addSecondIcon after news.getIcon()=" + news.getIcon());
      				} else {
      					news.setIcon("common/list2.gif");
      				}
      			}
      	  }

	    }
		
		return newsList;
	}

	private void addChildNodes(FilterNode pageFilters,
			Map<String, NameFilter> childFilters, String localLong, String localLat, Options options) {
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
		
		Local local = new Local(localLong, localLat);
		 Log.info("NavigationPath local.getRect().getLeft()=" + local.getRect().getLeft());
		if (local.getRect().getLeft() != 0) {		
			navLocations.addChildFilter(local, pageFilters);
			Log.info("NavigationPath local added");
		}
		
		navTopics.addChildFilter(new AllTopics(AllTopics.ALL_TOPICS), pageFilters);
		navDates.addChildFilter(new AllTime(), pageFilters);
		navDates.addChildFilter(new Latest(1), pageFilters);
		navDates.addChildFilter(new ThisDayInHistory(), pageFilters);
        
        navLocations.sort();
        navTopics.sort();
        navDates.sort();
        
        navLocations.limitChildren(options.getIsMoreLocations() != null && options.getIsMoreLocations().getBoolValue());
        navTopics.limitChildren(options.getIsMoreTopics() != null && options.getIsMoreTopics().getBoolValue());
        navDates.limitChildren(options.getIsMoreTime() != null && options.getIsMoreTime().getBoolValue());
        
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
