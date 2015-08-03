package mapreport.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mapreport.filter.NameFilter;
import mapreport.filter.loc.LocationByName;
import mapreport.filter.loc.OfficialLocation;
import mapreport.filter.time.Century;
import mapreport.filter.time.Day;
import mapreport.filter.time.Decade;
import mapreport.filter.time.Month;
import mapreport.filter.time.OfficialTimeFilter;
import mapreport.filter.time.Year;
import mapreport.filter.topic.Topic;
import mapreport.news.News;
import mapreport.util.Log;

public class NewsFilterRow implements Comparable<Object>{

	int filterPriority = 0;
	private NameFilter filter = null;	

	int newsPriority = 0;  
	int priority = 0;
	int newsFilterPriority = 0;
	int newsId = 0;
	String filterId = null;
	Date date = null;
	
	String parentId = null;
	int parentLevel = 0;
	
	int level = 0;
	boolean isOfficial = false;
	boolean isLocation = false;

	boolean isParentLocation = false;
	
	String url = null;
	String video = null;
	String image = null;
	String addressText = null;
	String shortLabel = null;
	String description = null;
	String newsText = null;		

	boolean isFilterLocation = false;  
	String filterName = null;

	double x = 0;
	double y = 0;
	
	public boolean isPrimary() {
		return isPrimary;
	}

	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	boolean isPrimary = false;
	//String addressText = null;
	
	public boolean isParentLocation() {
		return isParentLocation;
	}

	public void setParentLocation(boolean isParentLocation) {
		this.isParentLocation = isParentLocation;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public boolean isFilterLocation() {
		return isFilterLocation;
	}

	public void setFilterLocation(boolean isFilterLocation) {
		this.isFilterLocation = isFilterLocation;
	}

	public int getParentLevel() {
		return parentLevel;
	}

	public void setParentLevel(int parentLevel) {
		this.parentLevel = parentLevel;
	}
	public String getFilterId() {
		return filterId;
	}

	public void setFilterId(String filterId) {
		this.filterId = filterId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getNewsPriority() {
		return newsPriority;
	}

	public void setNewsPriority(int newsPriority) {
		this.newsPriority = newsPriority;
	}

	public int getNewsFilterPriority() {
		return newsFilterPriority;
	}

	public void setNewsFilterPriority(int newsFilterPriority) {
		this.newsFilterPriority = newsFilterPriority;
	}

	public int getFilterPriority() {
		return filterPriority;
	}

	public void setFilterPriority(int filterPriority) {
		this.filterPriority = filterPriority;
	}

	public int getNewsId() {
		return newsId;
	}

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getAddressText() {
		return addressText;
	}

	public void setAddressText(String addressText) {
		this.addressText = addressText;
	}

	public String getShortLabel() {
		return shortLabel;
	}

	public void setShortLabel(String shortLabel) {
		this.shortLabel = shortLabel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getNewsText() {
		return newsText;
	}

	public void setNewsText(String newsText) {
		this.newsText = newsText;
	}

	public boolean isLocation() {
		return isLocation;
	}

	public void setLocation(boolean isLocation) {
		this.isLocation = isLocation;
	}

	public boolean isOfficial() {
		return isOfficial;
	}

	public void setOfficial(boolean isOfficial) {
		this.isOfficial = isOfficial;
	}

	String name = "";
	
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Object o) {
		if (priority > ((NewsFilterRow)o).priority) {
			return -1;
		} else if (priority < ((NewsFilterRow)o).priority) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	public NewsFilterRow() {
	}
	
	public NewsFilterRow(int newsPriority, String name, int newsFilterPriority, int filterPriority, int newsId, String parentId, int level, int priority) {
		this.newsPriority = newsPriority;
		this.name = name;
		this.newsFilterPriority = newsFilterPriority;
		this.filterPriority = filterPriority;
		this.newsId = newsId;
		this.parentId = parentId;
		this.level = level;
		this.priority = priority;
	}
	
	/*
	public static List<NewsFilterRow> createFilters(int newsPriority, 
			String nameLoc, int newsFilterPriorityLoc, int filterPriorityLoc, String parentIdLoc, int levelLoc,
			String nameTopic, int newsFilterPriorityTopic, int filterPriorityTopic, String parentIdTopic, int levelTopic, 
			Date date, int newsId) {
		
		List<NewsFilterRow> filters = new ArrayList<NewsFilterRow>();
		
		NewsFilterRow locNewsFilter = new NewsFilterRow(newsPriority, nameLoc, newsFilterPriorityLoc, filterPriorityLoc, newsId, parentIdLoc, levelLoc);
		NewsFilterRow topicNewsFilter = new NewsFilterRow(newsPriority, nameTopic, newsFilterPriorityTopic, filterPriorityTopic, newsId, parentIdTopic, levelTopic);
		
		filters.add(locNewsFilter);
		filters.add(topicNewsFilter);
		
		String day = String.valueOf(date.getDay());
		String month = String.valueOf(date.getMonth());
		String year = String.valueOf(date.getYear());	
		
		// incrementDateFilterMap(filterMap, filter, year);
		// incrementDateFilterMap(filterMap, filter, month + "/" + year);
		// incrementDateFilterMap(filterMap, filter, month + "/" + day + "/" + year);
		
		NewsFilterRow yearNewsFilter = new NewsFilterRow(newsPriority, "date:" + year, 1, 10, newsId, "", 4);
		NewsFilterRow monthNewsFilter = new NewsFilterRow(newsPriority, "date:" + month + "/" + year, 1, 10, newsId, "", 5);
		NewsFilterRow dayNewsFilter = new NewsFilterRow(newsPriority, "date:" + month + "/" + day + "/" + year, 1, 10, newsId, "", 6);

		filters.add(yearNewsFilter);
		filters.add(monthNewsFilter);
		filters.add(dayNewsFilter);
		
		if (year.length() == 4 && year.startsWith("200")) {
			NewsFilterRow decadeNewsFilter = new NewsFilterRow(newsPriority, "date:200s Decade" + year, newsFilterPriorityTopic, 10, newsId, parentIdTopic, 3);
			filters.add(decadeNewsFilter);
		}
		
		return filters;

	}*/
	
	public NewsFilterRow(NewsFilterRow clonedNewsFilter, String name) {	
		this.name = name;
		this.newsPriority = clonedNewsFilter.newsPriority;
		this.priority = clonedNewsFilter.priority;
		this.newsFilterPriority = clonedNewsFilter.newsFilterPriority;
		this.filterPriority = clonedNewsFilter.filterPriority;
		this.newsId = clonedNewsFilter.newsId;
		this.date = clonedNewsFilter.date;
		this.parentId = clonedNewsFilter.parentId;
		this.level = clonedNewsFilter.level;
		this.filterId = name;		
		this.x = clonedNewsFilter.x;		
		this.y = clonedNewsFilter.y;		
	}
	
	public void calculatePriority(int newsNm) {
			//priority = 100 * newsPriority * newsFilterPriority * filterPriority * (int)(new Date().getTime() - date.getTime()) / newsNm;
			priority = newsPriority * newsFilterPriority * filterPriority;
			Log.log("calculatePriority newsPriority=" + newsPriority 
		    		+ " newsFilterPriority=" + newsFilterPriority + " filterPriority=" + filterPriority + " priority=" + priority);
	}
	
	public static List<NewsFilterRow> buildNewsFilterPriority(List<NewsFilterRow> srcFilters) {
		Log.log("buildNewsFilter srcFilters.size()=" + srcFilters.size());
	Map<Object, Integer> newsMap = new HashMap<Object, Integer>();
	
	for (NewsFilterRow filter : srcFilters) {
		if (newsMap.get(filter.newsId) == null) {
			newsMap.put(filter.newsId, 1);
		} else {
			newsMap.put(filter.newsId, newsMap.get(filter.newsId) + 1);
		}
	}
	
	for (NewsFilterRow filter : srcFilters) {
		filter.calculatePriority(newsMap.get(filter.newsId));
	}
	
	return srcFilters;		
}

	public static List<News> buildNews(List<NewsFilterRow> srcFilters) {
		Log.log("buildNews srcFilters.size()=" + srcFilters.size());
		List<News> newsList = new ArrayList<News>(srcFilters.size());
		Set<String> labelTimes = new HashSet<String>(srcFilters.size());  
		
		for (NewsFilterRow filter : srcFilters) {
			News news = new News();
			String labelTime = filter.getName() + filter.getDate();
		
			if (labelTimes.contains(labelTime)) {
			//	System.out.println("buildNews already contains news.getLabel()=" + news.getLabel());
				continue;
			} 
			
			labelTimes.add(labelTime);
			news.setDateTime(filter.getDate());
			news.setDescription(filter.getDescription());
			news.setId(filter.getNewsId());
			news.setIcon(filter.getImage());
			news.setLabel(filter.getName());
			news.setNewsText(filter.getNewsText());
			news.setShortLabel(filter.getShortLabel());
			news.setUrl(filter.getUrl());
			news.setVideo(filter.getVideo());
			news.setPriority(filter.getNewsPriority());
			news.setX(filter.getX());
			news.setY(filter.getY());
			news.setAddress(filter.getAddressText());
			
			Log.info(" NewsFilterRow buildNews isPrimary=" + filter.isPrimary + " getFilterId():" + filter.getFilterId() 
					+ " filterName:" + filter.filterName + " label:" + filter.name 
					+ " filterPriority:" + filter.filterPriority + " newsFilterPriority:" + filter.newsFilterPriority + " priority:" + filter.filterPriority * filter.newsFilterPriority);
			
			if (filter.isPrimary) {
				LocationByName primaryLocation = new LocationByName(filter.getFilterId());
				news.setPrimaryLocation(primaryLocation);
			}
			
			newsList.add(news);		
		
			Log.log("buildNews add(news) news.getLabel()=" + news.getLabel());
	}
	
	return newsList;		
}

	@SuppressWarnings({ "unused", "deprecation" })
	public static List<NewsFilterRow> buildFilters(List<NewsFilterRow> srcFilters) {		
		Map<Object, NewsFilterRow> filterMap = new HashMap<Object, NewsFilterRow>();
		Log.info("buildFilters srcFilters.size()=" + srcFilters.size());
		for (NewsFilterRow filterRow : srcFilters) {
				Log.info("buildFilters filterRow.filterId=" + filterRow.filterId + " filterRow.name=" + filterRow.name + " parentId=" + filterRow.parentId
						+ " filterRow.priority=" + filterRow.priority
						 + " filterRow.date.getDay()=" + filterRow.date.getDay() + " filterRow.date.getMonth() + 1=" + (filterRow.date.getMonth() + 1) 
						 + " filterRow.date.getYear() + 1900=" + (filterRow.date.getYear() + 1900));
			addDateFilters(filterMap, filterRow);			
			addLocationTopicFilters(filterMap, filterRow, filterRow.filterId, false);
			
			NewsFilterRow parentFilterRow = new NewsFilterRow(filterRow.newsPriority, filterRow.name, filterRow.newsFilterPriority, 
					filterRow.filterPriority, filterRow.newsId, filterRow.parentId, filterRow.level, 0);
			parentFilterRow.setFilterId(filterRow.parentId);
			parentFilterRow.setLocation(filterRow.isLocation);
			parentFilterRow.setParentLocation(filterRow.isParentLocation);
			parentFilterRow.setOfficial(filterRow.isOfficial);
			
			addLocationTopicFilters(filterMap, parentFilterRow, filterRow.parentId, true);
		}
		
	//	Log.log("buildFilters San Jose Downtown=" + filterMap.get("San Jose Downtown").getFilter().getName());
		
		List<NewsFilterRow> newsFilters = new ArrayList<NewsFilterRow>(filterMap.values());
		Collections.sort(newsFilters);
		
		for (NewsFilterRow newsFilter : newsFilters) {
			 Log.info("buildFilters filter.filterId=" + newsFilter.filterId  + " newsFilter.filter.Name()=" + newsFilter.getFilter().getName()
					 + " filter.name=" + newsFilter.name + " filter.priority=" + newsFilter.priority);
		}
		return newsFilters;		
	}

	public static void addLocationTopicFilters(Map<Object, NewsFilterRow> filterMap, NewsFilterRow filterRow, String id, boolean isParent) {
		if (filterRow.getFilter() == null) {		
			NameFilter filter = null;	
			boolean isLocation = isParent ? filterRow.isParentLocation() : filterRow.isLocation();
			    Log.info("addLocationTopicFilters id=" + id + " isLocation=" + isLocation + " isLocation()=" + filterRow.isLocation() + " isParentLocation()=" + filterRow.isParentLocation());
			if (isLocation) {
				if (filterRow.isOfficial()) {
					filter = new OfficialLocation(id);
				} else {
					filter = new LocationByName(id);
				}
			} else {
				filter = new Topic(id);
			}
			filter.setName(id);
			filter.setPriority(filterRow.filterPriority);
			
	   //     Log.log("addLocationTopicFilters befbef incrementFilterMapPriority San Jose Downtown=" + filterMap.get("San Jose Downtown"));
	  //      if (filterMap.get("San Jose Downtown") != null) Log.log("addLocationTopicFilters befbef incrementFilterMapPriority San Jose Downtown=" + filterMap.get("San Jose Downtown").getFilter().getName());
	
			filterRow.setFilter(filter);
		}

        Log.log("addLocationTopicFilters bef incrementFilterMapPriority San Jose Downtown=" + filterMap.get("San Jose Downtown"));
        if (filterMap.get("San Jose Downtown") != null) Log.log("addLocationTopicFilters bef incrementFilterMapPriority San Jose Downtown=" + filterMap.get("San Jose Downtown").getFilter().getName());

		incrementFilterMapPriority(filterMap, filterRow, id);
             Log.log("addLocationTopicFilters end id=" + id + " filterMap.get(id).getFilter().getName()=" +  filterMap.get(id).getFilter().getName() + " getFilter.name=" + filterRow.getFilter().getName()); // + " name=" + filter.getName());

       //      Log.log("addLocationTopicFilters San Jose Downtown=" + filterMap.get("San Jose Downtown"));
      //       if (filterMap.get("San Jose Downtown") != null) Log.log("addLocationTopicFilters San Jose Downtown=" + filterMap.get("San Jose Downtown").getFilter().getName());
	}

	public static void addDateFilters(Map<Object, NewsFilterRow> filterMap,
			NewsFilterRow filterRow) {
		incrementDateFilterMapPriority(filterMap, filterRow, new Year(filterRow.date.getYear() + 1900), filterRow.name);
		incrementDateFilterMapPriority(filterMap, filterRow, new Month(filterRow.date.getYear() + 1900, filterRow.date.getMonth() + 1), filterRow.name);
		incrementDateFilterMapPriority(filterMap, filterRow, new Day(filterRow.date.getYear() + 1900, filterRow.date.getMonth() + 1, filterRow.date.getDate()), filterRow.name);		
		incrementDateFilterMapPriority(filterMap, filterRow, new Decade((filterRow.date.getYear() + 1900) / 10 * 10), filterRow.name);		
		incrementDateFilterMapPriority(filterMap, filterRow, new Century((filterRow.date.getYear() + 2000) / 100), filterRow.name);		
		
		      Log.info("addDateFilters filterRow.date.getYear()=" + (filterRow.date.getYear()  + 1900) + " decade=" + ((filterRow.date.getYear() + 1900) / 10 * 10)
		    		  + " Century=" + ((filterRow.date.getYear() + 2000) / 100)); 
		   //       + " 2006=" + (2006) / 10 * 10);
	}

	public static void incrementDateFilterMapPriority(Map<Object, NewsFilterRow> filterMap,
			NewsFilterRow filter, OfficialTimeFilter timeFilter, String newsLabel) {	
		            Log.log("incrementDateFilterMapPriority filter.filterId=" + filter.filterId + " timeFilter=" + timeFilter.getName()); // + " filterKey=" + filterKey);
		NewsFilterRow newNewsFilterRow = new NewsFilterRow(filter, timeFilter.getName());
		newNewsFilterRow.setFilter(timeFilter);
		newNewsFilterRow.setName(newsLabel);
		incrementFilterMapPriority(filterMap, newNewsFilterRow, timeFilter.getName());
	}
	
	public static void incrementFilterMapPriority(Map<Object, NewsFilterRow> filterMap,	NewsFilterRow filter, String filterKey) {
	 //     Log.log("addLocationTopicFilters incrementFilterMapPriority San Jose Downtown=" + filterMap.get("San Jose Downtown"));
	 //       if (filterMap.get("San Jose Downtown") != null) Log.log("addLocationTopicFilters bef incrementFilterMapPriority San Jose Downtown=" + filterMap.get("San Jose Downtown").getFilter().getName());

		if (filterMap.get(filterKey) == null) {
			filter.setFilterId(filterKey);  
			filter.priority = 0;
			filterMap.put(filterKey, filter);
			  Log.info("incrementFilterMapPriority put filterKey=" + filterKey );
		} else {
			  Log.info("incrementFilterMapPriority get filterKey=" + filterKey  + filterMap.get(filterKey).priority);
		//	int oldPr = filterMap.get(filterKey).priority; 
		//	int resPr = calculatePriority(oldPr, filter.priority);
			double oldPriority = filterMap.get(filterKey).priority;
			double reversePr = filter.priority == 0 ? 1000 : 1000 / filter.priority;
			double newPriority = Math.pow(reversePr, 1.0/5) + oldPriority;
			filterMap.get(filterKey).setPriority((int)newPriority);
			         Log.info("incrementFilterMapPriority  oldPriority=" + oldPriority
			        		 + " filter.priority=" + filter.priority + " reversePr=" + reversePr + " newPriority=" + newPriority);
		}
		             Log.info("incrementFilterMapPriority filterKey=" + filterKey + " getFilter().getName()=" + filter.getFilter().getName() + " label=" + filter.getName() 
		            		 + " filterMap.get(filterKey).priority=" + filterMap.get(filterKey).priority);
		 //  	      Log.log("addLocationTopicFilters end incrementFilterMapPriority San Jose Downtown=" + filterMap.get("San Jose Downtown"));
		//	        if (filterMap.get("San Jose Downtown") != null) Log.log("addLocationTopicFilters bef incrementFilterMapPriority San Jose Downtown=" + filterMap.get("San Jose Downtown").getFilter().getName());

	}
	
	public static int calculatePriority(int oldPr, int newPr) {
		double min = java.lang.Math.min(oldPr, newPr);
		double max = java.lang.Math.max(oldPr, newPr);
		double sum = oldPr + newPr;
		double ratio = max == 0 ? 2 : sum / max;
		double res = ratio == 0 ? 0 : min / ratio;
		    Log.info("calculatePriority oldPr=" + oldPr + " newPr=" + newPr + " min=" + min  + " max=" + max  + " sum=" + sum + " ratio=" + ratio + " res=" + res);
		return (int)res;
	}

	public NameFilter getFilter() {
		return filter;
	}

	void setFilter(NameFilter filter) {
		this.filter = filter;
	}
}
