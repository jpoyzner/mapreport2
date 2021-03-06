package mapreport.front.page;

import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mapreport.filter.DBFilter;
import mapreport.filter.Filter;
import mapreport.filter.NameFilter;
import mapreport.filter.SearchFilter;
import mapreport.filter.loc.LocationByCoords;
import mapreport.filter.loc.LocationByName;
import mapreport.filter.time.OfficialTimeFilter;
import mapreport.filter.time.TimeFilter;
import mapreport.filter.topic.SecondTopicFilter;
import mapreport.filter.topic.Topic;
import mapreport.util.Log;

public class FilterNode {	
	List<Filter> filterList = new ArrayList<Filter>(4);
	TimeFilter timeFilter = null;

	Topic topicFilter = null;
	SearchFilter searchFilter = null;
	SecondTopicFilter topicFilter2 = null;

	LocationByCoords coordFilter = null;
	LocationByName locationFilter = null;
	LocationByName locationFilter2 = null;
	
	String link = "";
	int dbFilterNo = 0;	

	private StringBuilder whereSQL = new StringBuilder("");
	private StringBuilder fromSQL = new StringBuilder("\n from news n ");
	private StringBuilder selectSQL = new StringBuilder("\n select  n.dateTime, n.addressText as addressText," +
			 " \n (n.addressX / 1000000) as addressX, (n.addressY / 1000000) as addressY,  n.newsId, n.label, n.priority as nPriority," + 
			 " \n n.url as url, n.video as video, n.image as image, n.addressText as addressText," + 
			 " \n n.shortLabel as shortLabel, n.description as description, n.newsText as newsText ");

	public List<Filter> getFilterList() {
		return filterList;
	}

	public void setFilterList(List<Filter> filterList) {
		this.filterList = filterList;
	}

	String header = "";
	String description = "";
	String orderSQL = "\n order by n.dateTime desc, nPriority ";
	String name = "";
	
	public String getOrderSQL() {
		if (timeFilter != null) {
			orderSQL = timeFilter.getOrderBySQL().toString();
		}
		return orderSQL;
	}
		
	public String buildName() {
		StringBuilder nameSb = new StringBuilder();

		for (Filter filter: filterList) {
			       //    Log.log("FilterNode getLink() filter=" + filter);
			if (filter != null) {
				String filterName = filter.getName();
				        Log.log("FilterNode buildName() filterName=" + filterName);
				if (filterName != null && !filterName.isEmpty()) {     
					if (nameSb.length() > 0) {
						nameSb.append(", ");
					}	        
					nameSb.append(filterName);
				}
			}
		}
		name = nameSb.toString();
		     Log.log("FilterNode buildName() name=" + name);
		return name;
	}
	
	public String getLink() {
		link = "";
		if (getLocationFilter() != null){
	    	  Log.log("getLink()  locationFilter().getLink()=" + getLocationFilter().getLink());
	    }
		if (getTopicFilter() != null){
	    	  Log.log("getLink() topicFilter().getLink()=" + getTopicFilter().getLink());
	    }
		if (getTimeFilter() != null){
	    	  Log.log("getLink()  timeFilter().getLink()=" + getTimeFilter().getLink());
	    }
		    Log.log("FilterNode getLink() filterList.size()=" + filterList.size());
		for (Filter filter: filterList) {
			       //    Log.log("FilterNode getLink() filter=" + filter);
			if (filter != null) {
				String filterLink = URLEncoder.encode(filter.getLink());
			//	        Log.log("FilterNode getLink() filterLink=" + filterLink);
				if (filterLink != null && !filterLink.isEmpty()) {     
					link += filterLink + '/';
				}
			}
		}
		     Log.log("FilterNode getLink() link=" + link);
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public FilterNode(FilterNode oldFilterNode) {
        	Log.log("FilterNode(FilterNode oldFilterNode) oldFilterNode=" + oldFilterNode.toString() + "  getLink()=" + getLink());
		addFilterType(oldFilterNode.getLocationFilter());
		addFilterType(oldFilterNode.getLocationFilter2());
		addFilterType(oldFilterNode.getTopicFilter());
		addFilterType(oldFilterNode.getTopicFilter2());
		addFilterType(oldFilterNode.getTimeFilter());
		addFilterType(oldFilterNode.getCoordFilter());
                    Log.log("FilterNode(FilterNode oldFilterNode) new toString()=" + toString() + "  getLink()=" + getLink());
	}
	
	public FilterNode() {
		// TODO Auto-generated constructor stub
	}
	
	public StringBuilder getSelectSQL() {
		return selectSQL;
	}

	public void setSelectSQL(StringBuilder selectSQL) {
		this.selectSQL = selectSQL;
	}

	public StringBuilder getFromSQL() {
		return fromSQL;
	}

	public void setFromSQL(StringBuilder fromSQL) {
		this.fromSQL = fromSQL;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	public void bindFilters(PreparedStatement pst) throws SQLException {
		int col = 0;
		for (Filter filter :  filterList) {
			col = filter.bindQuery(pst, col);
		}
	}

	
	/*
    public void addFilter(Filter filter) {
    	if (filter != null) {
    		if (filter instanceof IdFilter) {    
    			IdFilter idFilter = (IdFilter)filter;
		    	String filterHeader = idFilter.getName();
		    	header += " " + filterHeader;
		    	description += " " + filterHeader; //???????????????????????????????
    		}
    	}
    }
    */
	public void add(Filter filter) {   
		Log.info("FilterNode add filter:" + filter + " filter:" + filter.getName());    
		if (filter instanceof LocationByCoords) {
			LocationByCoords locationByCoords = (LocationByCoords)filter;
			Log.log("FilterNode add locationByCoords");    
			addFilterType(locationByCoords);     
		} else if (filter instanceof TimeFilter) {
			TimeFilter timeFilter = (TimeFilter)filter;
			Log.log("FilterNode add TimeFilter");   
	          addFilterType(timeFilter);
	 
		} else if (filter instanceof Topic) {
			Topic topic = (Topic)filter;
			  Log.log("FilterNode add Topic");   
	          addFilterType(topic);
		} else if (filter instanceof LocationByName) {
			LocationByName locationByName = (LocationByName)filter;
			Log.log("FilterNode add LocationByName");   
	          addFilterType(locationByName);
	               Log.log("FilterNode add(Filter filter) name=" + locationByName.getName() + " dbFilterCntr=" + locationByName.getDbFilterCntr());
		} else if (filter instanceof SearchFilter) {
			SearchFilter searchFilter = (SearchFilter)filter;
			Log.info("FilterNode add SearchFilter");   
	        addFilterType(searchFilter);
	        Log.log("FilterNode add(Filter filter) name=" + searchFilter.getName());
		}
	}
	
	
	public void addFilterType(LocationByCoords filter) {
               Log.log("addFilterType(LocationByCoords filter) filter=" + filter);
		   Log.log("addFilterType(LocationByCoords filter)");
		coordFilter = filter;	
		filterList.add(filter);	
	}

	public void addFilterType(TimeFilter filter) {
         //        Log.log("addFilterType(TimeFilter filter) filter=" + filter);
                 if (filter instanceof OfficialTimeFilter)  {
                	 OfficialTimeFilter officialTimeFilter = (OfficialTimeFilter)filter;
                	 Log.log("addFilterType(TimeFilter filter) getBegin=" + officialTimeFilter.getBegin());
                 }
		   if (filter != null)   Log.log("addFilterType(TimeFilter filter) filter.getName=" + filter.getName());
		timeFilter = filter;	
		filterList.add(filter);	
	}

	public void addFilterType(Topic filter) {
                 //      Log.log("addFilterType(Topic filter) filter=" + filter);
		if (filter != null) { 
				   Log.log("addFilterType(Topic filter) filter.getName=" + filter.getName());
			incrementDBFilterCntr(filter);
			if (topicFilter == null) {
				topicFilter = filter;		
				filterList.add(filter);
			} else {
				 Log.log("addFilterType(Topic filter) topicFilter2 = new SecondTopicFilter  filter.getName=" + filter.getName());
				topicFilter2 = new SecondTopicFilter(filter.getName());	
				filterList.add(topicFilter2);
			}
		}
	}

	public void addFilterType(SearchFilter filter) {
        //      Log.log("addFilterType(SearchFilter filter) filter=" + filter);
		if (filter != null) { 
				   Log.log("addFilterType(SearchFilter filter) filter.getName=" + filter.getName());
			// incrementDBFilterCntr(filter);
			if (searchFilter == null) {
				searchFilter = filter;		
				filterList.add(filter);
			}
		}
	}


	private void incrementDBFilterCntr(DBFilter filter) {
		if (filter == null) {
			return;
		}
		dbFilterNo++;
		filter.setDbFilterCntr(dbFilterNo);
	}

	public void addFilterType(LocationByName filter) {
		          Log.log("addFilterType(LocationByName filter) filter=" + filter);
			if (filter != null) Log.log("addFilterType(LocationByName filter) filter.getName=" + filter.getName());
		incrementDBFilterCntr(filter);
			
		if (locationFilter == null) {
			locationFilter = filter;		
		} else {
			locationFilter2 = filter;	
		}
		filterList.add(filter);
	}

	public TimeFilter getTimeFilter() {
		return timeFilter;
	}
	public void setTimeFilter(TimeFilter timeFilter) {
		this.timeFilter = timeFilter;
	}
	public Topic getTopicFilter() {
		return topicFilter;
	}
	public void setTopicFilter(Topic topicFilter) {
		this.topicFilter = topicFilter;
	}
	public Topic getTopicFilter2() {
		return topicFilter2;
	}
	public void setTopicFilter2(SecondTopicFilter topicFilter2) {
		this.topicFilter2 = topicFilter2;
	}

	public LocationByCoords getCoordFilter() {
		return coordFilter;
	}
	public void setCoordFilter(LocationByCoords coordFilter) {
		this.coordFilter = coordFilter;
	}
	public LocationByName getLocationFilter() {
		return locationFilter;
	}
	public void setLocationFilter(LocationByName locationFilter) {
		this.locationFilter = locationFilter;
	}
	public LocationByName getLocationFilter2() {
		return locationFilter2;
	}
	public void setLocationFilter2(LocationByName locationFilter2) {
		this.locationFilter2 = locationFilter2;
	}

	public String toString() {
		return filterToString("timeFilter", timeFilter)
				+ filterToString("topicFilter", topicFilter)
				+ filterToString("topicFilter2", topicFilter2)
				+ filterToString("coordFilter", coordFilter)
				+ filterToString("locationFilter", locationFilter)
				+ filterToString("locationFilter2", locationFilter2)
				+ filterToString("searchFilter", searchFilter);
	}
	
	public String filterToString( String type, Filter filter) {
		 if (filter != null) {
			 String ret = " \n    type=" + type + " " + filter.toString();
			 if (filter instanceof NameFilter) {
				 ret += " name=" + ((NameFilter)filter).getName();
			 }
			 return ret;
		 }
		 else return "";
	}
	
	public void limitFilter(NameFilter limitFilter) {
		Log.log("FilterNode limitFilter this.toString()=" + this.toString());
		Log.log("FilterNode limitFilter.getName()=" + limitFilter.getName() + " limitFilter=" + limitFilter);
		limitFilter.limitFilter(this);
	}

	public void upFilter(NameFilter upFilter) {
		Log.log("FilterNode upFilter this.toString()=" + this.toString());
		Log.log("FilterNode upFilter.getName()=" + upFilter.getName() + " limitFilter=" + upFilter);
		upFilter.upFilter(this);
	}

	public StringBuilder getWhereSQL() {
		return whereSQL;
	}
	
	public StringBuilder buildWhereSQL() {
		      Log.info("FilterNode buildWhereSQL filterList.size()=" + filterList.size() + " timeFilter=" + timeFilter);
		
		whereSQL.append("\n where 1=1 ");
		
		if (coordFilter != null) {
			whereSQL.append(coordFilter.getWhereSQL());
		//	whereSQL.append("\n and ");
		}
		
		boolean anyIdFilter = false;
		for (Filter filter : filterList) {
				Log.log("FilterNode buildWhereSQL filter=" + filter);			
		    
			if (filter instanceof DBFilter) {
					String filterName = ((DBFilter) filter).getName();
					Log.log("FilterNode buildWhereSQL filter.getName()=" + filterName);
					if (filterName == null || filterName.equals("news") || filterName.isEmpty()) {
						continue;
					}
		
					whereSQL.append("\n and ");
					whereSQL.append(((DBFilter) filter).getWhereSQL());
					//  and  f.name='Farallon Islands, San Francisco, California, USA' 

					anyIdFilter = true;
					
			} else if (filter instanceof SearchFilter) {
				String keywords = ((SearchFilter) filter).getKeywords();
				Log.info("FilterNode buildWhereSQL SearchFilter filter.keywords()=" + keywords);
				whereSQL.append("\n and ");
				whereSQL.append(filter.getWhereSQL());
			}		
		}
		Log.log("FilterNode buildWhereSQL anyIdFilter=" + anyIdFilter);
		
		if (timeFilter != null) {
			   Log.log("FilterNode buildWhereSQL  timeFilter=" + timeFilter + " timeFilter.getWhereSQL()=" + timeFilter.getWhereSQL());
			whereSQL.append(timeFilter.getWhereSQL());
		}
		      Log.info("FilterNode buildWhereSQL whereSQL=" + whereSQL);
		return whereSQL;
	}
	
	public StringBuilder buildFromSQL() {
	      Log.log("FilterNode buildFromSQL filterList.size()=" + filterList.size() + " timeFilter=" + timeFilter);
		boolean anyIdFilter = false;
		for (Filter filter : filterList) {
				Log.log("FilterNode buildFromSQL filter=" + filter);			
		    
			if (filter instanceof DBFilter) {
					String filterName = ((DBFilter) filter).getName();
					Log.log("FilterNode buildFromSQL filter.getName()=" + filterName);
					if (filterName == null || filterName.equals("news") || filterName.isEmpty()) {
						continue;
					}
					fromSQL.append("\n, ");

					fromSQL.append(((DBFilter) filter).getFromSQL());
					//  and  f.name='Farallon Islands, San Francisco, California, USA' 
	
					anyIdFilter = true;
			}			
		}
		Log.info("FilterNode buildFromSQL anyIdFilter=" + anyIdFilter + " fromSQL=" + fromSQL);
		return fromSQL;
	}

	public StringBuilder buildSelectSQL() {
	      Log.log("FilterNode buildSelectSQL filterList.size()=" + filterList.size() + " timeFilter=" + timeFilter);
		boolean anyIdFilter = false;
		for (Filter filter : filterList) {
				Log.log("FilterNode buildSelectSQL filter=" + filter);			
		    
			if (filter instanceof DBFilter) {
					String filterName = ((DBFilter) filter).getName();
					Log.log("FilterNode buildSelectSQL filter.getName()=" + filterName);
					if (filterName == null || filterName.equals("news") || filterName.isEmpty()) {
						continue;
					}
					selectSQL.append("\n, ");
					selectSQL.append(((DBFilter) filter).getSelectSQL());
					//  and  f.name='Farallon Islands, San Francisco, California, USA' 
	
					anyIdFilter = true;
			}			
		}
		Log.info("FilterNode buildSelectSQL anyIdFilter=" + anyIdFilter + " selectSQL=" + selectSQL);
		return selectSQL;
	}

	public void setWhereSQL(StringBuilder whereSQL) {

		this.whereSQL = whereSQL;
	}
}
