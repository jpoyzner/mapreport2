package mapreport.resp;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mapreport.db.DBQueryBuilder;
import mapreport.db.FilterDBQueryBuilder;
import mapreport.db.NewsFilterRow;
import mapreport.filter.NameFilter;
import mapreport.filter.loc.LocationByCoords;
import mapreport.filter.loc.LocationByName;
import mapreport.filter.time.Latest;
import mapreport.filter.time.TimeFilter;
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
	
	//TODO: should rename this class to some internal manager, and move out of this package

	public static List<NewsFilterRow> buildParents(Rectangle rect,
			Set<NameFilter> nameFilters, DBQueryBuilder queryBuilder)
			throws SQLException {
		LocationByCoords coordFilter;
		if (rect != null && rect.getxSpan() > 0) {
			          Log.log("Controller buildJson rect.getxSpan() > 0");
			coordFilter = new LocationByCoords (rect);
			queryBuilder.addFilter(coordFilter);
		}		
		
		List<NewsFilterRow> parents = new ArrayList<NewsFilterRow>();
		
		if (nameFilters != null) {
			List<String> filterIds = new ArrayList<String>(nameFilters.size());
			for (NameFilter filter: nameFilters) {
				filterIds.add(filter.getName());
			}
				
			FilterDBQueryBuilder filterDBQueryBuilder = new FilterDBQueryBuilder();
			
			if (filterIds.size() > 0) {
				parents = filterDBQueryBuilder.runQuery(filterIds);
			}
			
			for (NameFilter filter: nameFilters) {
				Log.log("\n before queryBuilder.addFilter(filter) filter=" + filter + " filter.getName()=" + filter.getName());
				
				if (filter instanceof TimeFilter) {
					queryBuilder.addFilter(filter);  
				} else {
					boolean isLocation = true;
					for (NewsFilterRow parentNewsFilterRow : parents) {
						       Log.log("before queryBuilder.addFilter(filter) parentNewsFilterRow.getFilterName=" + parentNewsFilterRow.getFilterName() + " filter.getName()=" + filter.getName());
						if (parentNewsFilterRow.getFilterName().equals(filter.getName())) {
							isLocation = parentNewsFilterRow.isFilterLocation();
						       Log.log("before queryBuilder.addFilter(filter) parentNewsFilterRow.filterId.equals(filter.getName()) isLocation=" + isLocation);
							break;
						}
					}
					
					NameFilter newFilter = isLocation ? new LocationByName(filter.getName()) :  new Topic(filter.getName());
			    	queryBuilder.addFilter(newFilter);   //  adding named filters
				}
			}
		}
		
		if (queryBuilder.getFilterNode().getFilterList().size() == 0 || queryBuilder.getFilterNode().getTimeFilter() == null) {   // no filters added, so just global latest
			     Log.log("Controller buildJson ading Latest");
			queryBuilder.addFilter(new Latest());  
		}
		return parents;
	}
	
	public static String buildJson(String url) throws MalformedURLException, UnsupportedEncodingException {
	        Log.info("buildJson url=" + url);
		PageURL pageURL = new PageURL(url);
		pageURL.parseUrlParameters(url);
		pageURL.parseParams();
		Options options = pageURL.getOptions();
		
		Set<NameFilter> nameFilters = pageURL.getFilters();
		
		Rectangle rect = Rectangle.getRectangle(options);
		int size = 200;
		
		if (options.getParam("size") != null) {
			size = Integer.parseInt(options.getParam("size"));
		}
		String json = buildJson(rect, nameFilters, size);
		return json;
	}
	
	public static String buildJson(Rectangle rect, Set<NameFilter> nameFilters, int size) {  
		String json = null;
		
		try {
			// LocationByCoords coordFilter;
			DBQueryBuilder queryBuilder = new DBQueryBuilder(size);
		
				Log.log("buildJson rect=" + rect + " nameFilters=" + nameFilters + " size=" + size);
		
			List<NewsFilterRow> parents = buildParents(rect, nameFilters,	queryBuilder);
			
				Log.log("queryBuilder.filterNode.getFilterList().size()=" + queryBuilder.getFilterNode().getFilterList().size());
				
			queryBuilder.setWhereSQL(queryBuilder.getFilterNode().getWhereSQL());
			queryBuilder.setOrderBySQL(new StringBuilder(queryBuilder.getFilterNode().getOrderSQL())); 
			  
			List<NewsFilterRow> rows = queryBuilder.runQuery();
		
			List<NewsFilterRow> newsFilters = NewsFilterRow.buildNewsFilterPriority(rows);
			List<News> newsList = NewsFilterRow.buildNews(rows);
			List<NewsFilterRow> filters = NewsFilterRow.buildFilters(newsFilters);
			
			Map<String, NameFilter> childFilters = NameFilter.buildChildFilters(filters, parents);
				
			PagePresentation page = new PagePresentation (queryBuilder.getFilterNode(), newsFilters, newsList, filters, childFilters, parents) ;
			   Log.log("buildJson page.getView()=" + page.getView());
			   Log.log("buildJson page.getView().getNewsList()=" + page.getView().getNewsList());
			   Log.log("buildJson page.getView().getNewsList().getNewses()=" + page.getView().getNewsList().getNewses());
			page.setPst(queryBuilder.getPst());
			int newsListsize = page.getView().getNewsList().getNewses().size();
			   Log.log("end main newsListsize=" + newsListsize);
			   Log.log ("page.getNavigationPath=" + page.getNavigationPath());     
		
			   // just for test
			  // if (true) throw new Exception("test exception"); 
			   
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
