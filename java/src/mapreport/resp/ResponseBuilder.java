package mapreport.resp;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import mapreport.controller.Cache;
import mapreport.controller.Endpoints;
import mapreport.db.DBQueryBuilder;
import mapreport.db.FilterDBQueryBuilder;
import mapreport.db.NewsFilterRow;
import mapreport.db.NewsQueryBuilder;
import mapreport.db.URLFilterQueryBuilder;
import mapreport.filter.DBFilter;
import mapreport.filter.NameFilter;
import mapreport.filter.SearchFilter;
import mapreport.filter.loc.ClusterHandler;
import mapreport.filter.loc.Global;
import mapreport.filter.loc.LocationByCoords;
import mapreport.filter.loc.LocationByName;
import mapreport.filter.time.AllTime;
import mapreport.filter.time.Latest;
import mapreport.filter.time.TimeFilter;
import mapreport.filter.topic.AllTopics;
import mapreport.filter.topic.Topic;
import mapreport.front.option.Options;
import mapreport.front.page.PagePresentation;
import mapreport.front.url.PageURL;
import mapreport.news.News;
import mapreport.util.JSONHandler;
import mapreport.util.JsonError;
import mapreport.util.Log;
import mapreport.view.map.Rectangle;

public class ResponseBuilder {
	static int NEWS_LIMIT = 20;	
	static int NEWS_LIMIT_MORE = 50;	

	public static boolean addFiltersToQueryBuilder(Rectangle rect,
			Set<NameFilter> nameFilters, NewsQueryBuilder queryBuilder)
			throws SQLException {
		LocationByCoords coordFilter;
		if (rect != null && rect.getxSpan() > 0) {
			          Log.log("Controller buildJson rect.getxSpan() > 0");
			coordFilter = new LocationByCoords (rect);
			queryBuilder.addFilter(coordFilter);
		}		
		
	//	List<NewsFilterRow> parents = new ArrayList<NewsFilterRow>();
		boolean hasLocationFilter = false;
		
		if (nameFilters != null) {
		//	List<String> filterIds = new ArrayList<String>(nameFilters.size());
		//	for (NameFilter filter: nameFilters) {
		//		if (filter != null && filter.getName() != null) {
		//			filterIds.add(filter.getName());
		//		}
		//	}
				
		//	FilterDBQueryBuilder filterDBQueryBuilder = new FilterDBQueryBuilder();
			
		//	if (filterIds.size() > 0) {
		//		parents = filterDBQueryBuilder.runQuery(filterIds);
		//	}
			
			URLFilterQueryBuilder urlFilterQueryBuilder = new URLFilterQueryBuilder();			
			Map <String, DBFilter> filterMap = urlFilterQueryBuilder.runQuery(nameFilters); 
			hasLocationFilter = urlFilterQueryBuilder.isHasLocationByName();
			
			for (NameFilter filter: nameFilters) {
				Log.info("\n before queryBuilder.addFilter(filter) filter=" + filter);
				if (filter != null && filter.getName() != null) {
					Log.log("\n before queryBuilder.addFilter(filter) filter=" + filter + " filter.getName()=" + filter.getName());
				}
				
				if (filter instanceof TimeFilter || filter instanceof SearchFilter) {
					Log.info("\n before queryBuilder.addFilter(filter) TimeFilter filter=" + filter + " filter.getName()=" + filter.getName() + " where=" + filter.getWhereSQL());
					queryBuilder.addFilter(filter);  
					Log.log("\n after queryBuilder.addFilter(filter) TimeFilter filter=" + filter + " filter.getName()=" + filter.getName() + " where=" + filter.getWhereSQL());
				} else if (filter != null && filter.getName() != null) {
					// boolean isLocation = true;
					/*
					for (NewsFilterRow parentNewsFilterRow : parents) {
						       Log.log("before queryBuilder.addFilter(filter) parentNewsFilterRow.getFilterName=" + parentNewsFilterRow.getFilterName() + " filter.getName()=" + filter.getName());
						if (parentNewsFilterRow.getFilterName().equals(filter.getName())) {
							isLocation = parentNewsFilterRow.isFilterLocation();
						       Log.log("before queryBuilder.addFilter(filter) parentNewsFilterRow.filterId.equals(filter.getName()) isLocation=" + isLocation);
							break;
						}
					}*/
					
					// NameFilter newFilter = isLocation ? new LocationByName(filter.getName()) :  new Topic(filter.getName());
					DBFilter newFilter = filterMap.get(filter.getName());
					if (newFilter == null) {
						Log.info(" CAN''T FIND FILTER: " + filter.getName());
					} else {
						queryBuilder.addFilter(newFilter);   //  adding named filters
					}
				}
			}
		}
		
		if (queryBuilder.getFilterNode().getFilterList().size() == 0 || queryBuilder.getFilterNode().getTimeFilter() == null) {   // no filters added, so just global latest
		     Log.info("Controller buildJson adding Latest");
			 int futureDays = Latest.buildFutureDays(nameFilters.size());
		     queryBuilder.addFilter(new Latest(futureDays));  
		}
	
		return hasLocationFilter;
	}
	
	public static String buildJson(String url, String paramStr) throws MalformedURLException, UnsupportedEncodingException {
	        Log.info("ResponseBuilder buildJson url=" + url);
		PageURL pageURL = new PageURL(url);
		Options options = buildOptionsFromUrl(url, pageURL);
		
		Set<NameFilter> nameFilters = pageURL.getFilters();
		
		Rectangle rect = Rectangle.getRectangle(options);
		int size = 200;
		
	//	if (options.get != null) {
	//		size = Integer.parseInt(options.getParam("size"));
	//	}
		
    	int timeFilterCntr = url.indexOf("date/") > -1 ? 1 : 0;
		 
		String json = buildJson(rect, nameFilters, timeFilterCntr, size, "", "", options, paramStr);
		   Log.log("ResponseBuilder buildJson json=" + json);
		return json;
	}

	public static final Options buildOptionsFromRequest(HttpServletRequest request) throws MalformedURLException, UnsupportedEncodingException {	
		Options options = new Options();
		Map<String, String[]> parameterMap = request.getParameterMap();    	
    	Set<String> keys = parameterMap.keySet();
    	List<String> keyList = new ArrayList<String>(keys);
    	
    	for (String key : keyList) {
    		options.addParam(Endpoints.getCleanParam(key), Endpoints.getCleanParam(request, key));
    	} 
    	
    	// just for testing
        //	options.addParam("isShowFuture", "false");
        // 	options.addParam("moreTopic", "true");
        // 	options.addParam("moreNews", "true");
        // 	options.addParam("hints", "false");
    	return options;
	}

	public static Options buildOptionsFromUrl(String url, PageURL pageURL)
			throws UnsupportedEncodingException {
		Log.info("ResponseBuilder buildOptionsFromUrl url=" + url);
		pageURL.parseUrlParameters(url);
		pageURL.parseParams();
		Options options = pageURL.getOptions();
		return options;
	}

	public static Options buildOptionsFromUrl(String url)
			throws UnsupportedEncodingException, MalformedURLException {
		PageURL pageURL = new PageURL(url);
		return buildOptionsFromUrl(url, pageURL);
	}
	
	public static String buildJson(Rectangle rect, Set<NameFilter> nameFilters, int dateFilterCnt, int size, String localLong, String localLat, Options options, String paramStr) {  
		Log.info("buildJson  options=" + options);
		String json = null;
		
		try {
			boolean isDBFilterExists = false;
			if (nameFilters != null) {
				for (NameFilter filter: nameFilters) {
					if (filter instanceof DBFilter) {
						isDBFilterExists = true;
						break;
					}
				}
			}
			
			if (!isDBFilterExists) size *= 5;

			NewsQueryBuilder newsBuilder = new NewsQueryBuilder(size);
		
				Log.info("buildJson  isDBFilterExists=" + isDBFilterExists + " size=" + size  + " rect=" + rect + " nameFilters=" + nameFilters);
			Set<String> filterNameSet = new HashSet<String>(nameFilters.size()); 
				
			if (nameFilters != null) {
					for (NameFilter filter: nameFilters) {
						Log.info("buildJson NameFilter: " + filter);
						if (filter != null) {
							Log.info("buildJson NameFilter.getName(): " + filter.getName());
							filterNameSet.add(filter.getName());
						}
					}
			}
		
			boolean hasLocationFilter = addFiltersToQueryBuilder(rect, nameFilters,	newsBuilder);
			
				Log.info("queryBuilder.filterNode.getFilterList().size()=" + newsBuilder.getFilterNode().getFilterList().size());
				
			newsBuilder.setSelectSQL(newsBuilder.getFilterNode().buildSelectSQL());
			newsBuilder.setFromSQL(newsBuilder.getFilterNode().buildFromSQL());
			newsBuilder.setWhereSQL(newsBuilder.getFilterNode().buildWhereSQL());
			       Log.info("newsBuilder.getWhereSQL()=" + newsBuilder.getWhereSQL() + " newsBuilder.getFilterNode().getWhereSQL()=" + newsBuilder.getFilterNode().getWhereSQL());
			newsBuilder.setOrderBySQL(new StringBuilder(newsBuilder.getFilterNode().getOrderSQL())); 
			  
			List<News> newsList = newsBuilder.runQuery(nameFilters.size() - dateFilterCnt, rect != null, hasLocationFilter, options);
			Map<Integer, News> newsMap = null;	
			
			String newsMapCacheKey = "newsMap " + paramStr;
			if (options.getNewsOnly() != null && (options.getNewsOnly().getBoolValue())) {
				newsMap = NewsQueryBuilder.buildNewsMap(newsList);	
				Cache.putInCache(newsMapCacheKey, newsMap);
			} else {
				Object newsMapObj = null;
				// newsMapObj = Cache.retrieveFromCache(newsMapCacheKey);
				
				if (newsMapObj != null) {
					Log.info("buildJson newsMap exists in cache");
					newsMap = (Map<Integer, News>)newsMapObj;
				} else {		
					Log.info("buildJson newsMap NOT in cache");		
					newsMap = NewsQueryBuilder.buildNewsMap(newsList);	
				}
			}

			FilterDBQueryBuilder filterBuilder = new FilterDBQueryBuilder();
			
			List <DBFilter> dbFilters = options.getHints() == null || options.getHints().getBoolValue() ?
					filterBuilder.runQuery(newsMap) : new ArrayList <DBFilter> (0);
			
			Map<String, NameFilter> dbFiltersResult = filterBuilder.incrementFilterMapPriority(dbFilters);

			List<NameFilter> filterList = new ArrayList<NameFilter>(dbFiltersResult.values());
			List <NameFilter> allHintList = filterBuilder.addTimeFilters(filterList, newsMap);
			
			Log.info("buildJson filterNameSet.size()=" + filterNameSet.size());
			
			for (String filterName: filterNameSet) {
				Log.info("buildJson filterName=" + filterName);
			}
				
			Map<String, NameFilter> allHintMap = new HashMap<String, NameFilter>(111); 
			for (NameFilter filter : allHintList) {
				Log.log("buildJson filter.getName()=" + filter.getName() + " filterNameSet.contains(filter.getName()=" + filterNameSet.contains(filter.getName()));
				if (!filterNameSet.contains(filter.getName())) {
					allHintMap.put(filter.getName(), filter);
				}
			}
			
			newsList = NewsQueryBuilder.buildNewsList(newsMap, newsBuilder.getFilterNode().getTimeFilter()); 
			
			int newsLimit = options.getIsMoreNews() != null && options.getIsMoreNews().getBoolValue() ? NEWS_LIMIT_MORE : NEWS_LIMIT;			
			if (newsList.size() > newsLimit + 1) {
				newsList = newsList.subList(0, newsLimit);
			}
			Log.info("buildJson options.getIsMoreNews()=" + options.getIsMoreNews() + " newsList.size()=" + newsList.size());
			
			PagePresentation page = new PagePresentation (newsBuilder.getFilterNode(), newsList, allHintMap, localLong, localLat, rect, options);
			   Log.log("buildJson page.getView()=" + page.getView());
			   Log.log("buildJson page.getView().getNewsList()=" + page.getView().getNewsList());
			   Log.log("buildJson page.getView().getNewsList().getNewses()=" + page.getView().getNewsList().getNewses());
			page.setPst(newsBuilder.getPst());
			int newsListsize = page.getView().getNewsList().getNewses().size();
			   Log.log("end main newsListsize=" + newsListsize);
			   Log.log ("page.getNavigationPath=" + page.getNavigationPath());     

			   // just for test
			  // if (true) throw new Exception("test exception"); 

			PagePresentation.buildMainNews(page.getView().getNewsList().getNewses());  
			
			for (News news : page.getView().getNewsList().getNewses()) {
				 Log.log("ResponseBuilder news.getLabel()=" +  news.getLabel() + " isMapShow=" + news.isMapShow() + " isMain=" + news.isMain());
			}			  
  
			json = JSONHandler.gson.toJson(page); //"data1":100,"data2":"hello","list":["String 1","String 2","String 3"]
		
		   Log.info("buildJson end json=" + json);
		} catch (Exception e) {		
			   Log.log ("catch (Exception e) e.getMessage()" + e.getMessage());	
			e.printStackTrace();
			   Log.log ("printStackTrace");	
			json = new JsonError(e.getMessage(), e).getText();
			   Log.log ("buildJson ends");	
		}
		return json;
	}
}
