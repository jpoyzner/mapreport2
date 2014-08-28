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
import mapreport.filter.time.Day;
import mapreport.filter.time.Decade;
import mapreport.filter.time.Month;
import mapreport.filter.time.OfficialTimeFilter;
import mapreport.filter.time.Year;
import mapreport.filter.topic.Topic;
import mapreport.news.News;
import mapreport.util.Log;

public class NewsFilterRow implements Comparable<Object>{

	private NameFilter filter = null;	

	int newsPriority = 0;  
	int priority = 0;
	int newsFilterPriority = 0;
	int filterPriority = 0;
	int newsId = 0;
	String filterId = null;
	Date date = null;
	
	String parentId = null;
	int parentLevel = 0;
	
	int level = 0;
	boolean isOfficial = false;
	boolean isLocation = false;
	
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
		return newsPriority;
	}

	public void setPriority(int priority) {
		this.newsPriority = priority;
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

	public NewsFilterRow(int newsPriority, String name, int newsFilterPriority, int filterPriority, Date date, int newsId, String parentId
			, int level) {
		this.newsPriority = newsPriority;
		this.name = name;
		this.newsFilterPriority = newsFilterPriority;
		this.filterPriority = filterPriority;
		this.newsId = newsId;
		this.date = date;
		this.parentId = parentId;
		this.level = level;
	}
	
	public NewsFilterRow(int newsPriority, String name, int newsFilterPriority, int filterPriority, int newsId, String parentId, int level) {
		this.newsPriority = newsPriority;
		this.name = name;
		this.newsFilterPriority = newsFilterPriority;
		this.filterPriority = filterPriority;
		this.newsId = newsId;
		this.parentId = parentId;
		this.level = level;
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
		        System.out.println("calculatePriority newsPriority=" + newsPriority 
		    		+ " newsFilterPriority=" + newsFilterPriority + " filterPriority=" + filterPriority + " priority=" + priority);
	}
	
	public static List<NewsFilterRow> buildNewsFilter(List<NewsFilterRow> srcFilters) {
		System.out.println("buildNewsFilter srcFilters.size()=" + srcFilters.size());
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
			System.out.println("buildNews srcFilters.size()=" + srcFilters.size());
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
		news.setImage(filter.getImage());
		news.setLabel(filter.getName());
		news.setNewsText(filter.getNewsText());
		news.setShortLabel(filter.getShortLabel());
		news.setUrl(filter.getUrl());
		news.setVideo(filter.getVideo());
		news.setPriority(filter.getNewsPriority());
		news.setX(filter.getX());
		news.setY(filter.getY());
		newsList.add(news);
			System.out.println("buildNews add(news) news.getLabel()=" + news.getLabel());
	}
	
	return newsList;		
}

	@SuppressWarnings({ "unused", "deprecation" })
	public static List<NewsFilterRow> buildFilters(List<NewsFilterRow> srcFilters) {		
		Map<Object, NewsFilterRow> filterMap = new HashMap<Object, NewsFilterRow>();
		          System.out.println("buildFilters srcFilters.size()=" + srcFilters.size());
		for (NewsFilterRow filterRow : srcFilters) {
				Log.log("buildFilters filterRow.filterId=" + filterRow.filterId + " filterRow.name=" + filterRow.name + " filterRow.priority=" + filterRow.priority
						 + " filterRow.date.getDay()=" + filterRow.date.getDay() + " filterRow.date.getMonth() + 1=" + (filterRow.date.getMonth() + 1) 
						 + " filterRow.date.getYear() + 1900=" + (filterRow.date.getYear() + 1900));
			String day = String.valueOf(filterRow.date.getDate());
			String month = String.valueOf(filterRow.date.getMonth() + 1);
			String year = String.valueOf(filterRow.date.getYear() + 1900);			

		//	incrementDateFilterMapPriority(filterMap, filter, year);
		//	incrementDateFilterMapPriority(filterMap, filter, month + "/" + year);
		//	incrementDateFilterMapPriority(filterMap, filter, month + "/" + day + "/" + year);
			
			incrementDateFilterMapPriority(filterMap, filterRow, new Year(filterRow.date.getYear() + 1900), filterRow.name);
			incrementDateFilterMapPriority(filterMap, filterRow, new Month(filterRow.date.getYear() + 1900, filterRow.date.getMonth() + 1), filterRow.name);
			incrementDateFilterMapPriority(filterMap, filterRow, new Day(filterRow.date.getYear() + 1900, filterRow.date.getMonth() + 1, filterRow.date.getDate()), filterRow.name);
			
			if (year.length() == 4 && year.startsWith("200")) {
				incrementDateFilterMapPriority(filterMap, filterRow, new Decade("2000's Decade"), filterRow.name);
			}
			
			NameFilter filter = null;			
			if (filterRow.isLocation()) {
				if (filterRow.isOfficial()) {
					filter = new OfficialLocation(filterRow.filterId);
				} else {
					filter = new LocationByName(filterRow.filterId);
				}
			} else {
				filter = new Topic(filterRow.filterId);
			}
			filterRow.setFilter(filter);
			
			incrementFilterMapPriority(filterMap, filterRow, filterRow.filterId);
		}
		
		List<NewsFilterRow> newsFilters = new ArrayList<NewsFilterRow>(filterMap.values());
		Collections.sort(newsFilters);
		
		for (NewsFilterRow filter : newsFilters) {
			 System.out.println("buildFilters filter.filterId=" + filter.filterId + " filter.priority=" + filter.priority);
		}
		return newsFilters;		
	}

	public static void incrementDateFilterMapPriority(Map<Object, NewsFilterRow> filterMap,
			NewsFilterRow filter, OfficialTimeFilter timeFilter, String newsLabel) {	
		            Log.log("incrementDateFilterMapPriority filter.filterId=" + filter.filterId + " timeFilter=" + timeFilter.getName()); // + " filterKey=" + filterKey);
		NewsFilterRow newNewsFilterRow = new NewsFilterRow(filter, timeFilter.getName());
		newNewsFilterRow.setFilter(timeFilter);
		newNewsFilterRow.setName(newsLabel);
		incrementFilterMapPriority(filterMap, newNewsFilterRow, timeFilter.getName());
	}
	
	public static void incrementFilterMapPriority(Map<Object, NewsFilterRow> filterMap,
			NewsFilterRow filter, Object filterKey) {
		if (filterMap.get(filterKey) == null) {
			filterMap.put(filterKey, filter);
		} else {
			filterMap.get(filterKey).priority += filter.priority;
		}
		             System.out.println("incrementFilterMapPriority filterKey=" + filterKey + " label=" + filter.getName() + " filterMap.get(filterKey).priority=" + filterMap.get(filterKey).priority);
	}

	public NameFilter getFilter() {
		return filter;
	}

	void setFilter(NameFilter filter) {
		this.filter = filter;
	}
}
