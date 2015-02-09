package mapreport.db;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mapreport.filter.DBFilter;
import mapreport.filter.Filter;
import mapreport.filter.NameFilter;
import mapreport.filter.time.AllTime;
import mapreport.filter.time.Latest;
import mapreport.filter.time.OfficialTimeFilter;
import mapreport.filter.time.TimeFilter;
import mapreport.filter.topic.Topic;
import mapreport.front.page.FilterNode;
import mapreport.news.News;
import mapreport.resp.ResponseBuilder;
import mapreport.util.Log;
import mapreport.view.map.Rectangle;

public class NewsQueryBuilder extends DBBase {
	FilterNode filterNode = new FilterNode();
	Map<String, DBFilter> dbFilterMap = new HashMap<String, DBFilter>(2);

	public FilterNode getFilterNode() {
		return filterNode;
	}

	public void setFilterNode(FilterNode filterNode) {
		this.filterNode = filterNode;
	}

	static final String SELECT_EXTERNAL_COORD_FILTER = "select n.addressText as addressText,"
			+ " \n (n.addressX / 1000000) as addressX, (n.addressY / 1000000) as addressY,  n.newsId, n.label, n.priority as nPriority, "
			+ " \n n.url as url, n.video as video, n.image as image, n.addressText as addressText,"
			+ " \n n.shortLabel as shortLabel, n.description as description, n.newsText as newsText , n.dateTime as dateTime ";

	static final String SELECT_EXTERNAL = "select n.addressText as addressText,"
			+ " \n (n.addressX / 1000000) as addressX, (n.addressY / 1000000) as addressY,  n.newsId, n.label, n.priority as nPriority, " 
			+ " nf.priority as nfPriority, nf.topicExcludeId as topicExcludeId, "
			+ " \n n.url as url, n.video as video, n.image as image, n.addressText as addressText,"
			+ " \n n.shortLabel as shortLabel, n.description as description, n.newsText as newsText , n.dateTime as dateTime ";

	static final String FROM_EXTERNAL_COORD_FILTER = "\n from  news n \n ";
	static final String FROM_EXTERNAL = "\n from  filter f, filter fp, newsfilter nf, filterfilter ff, news n \n ";
	static final String FROM_EXTERNAL_END_COORD_FILTER = "\n ) nl ";
	static final String FROM_EXTERNAL_END = "";
	static final String WHERE_EXTERNAL_COORD_FILTER = "\n where 1=1 ";
	static final String WHERE_EXTERNAL = "\n where  f.filterId = nf.filterId  and nf.newsId = n.newsId and f.filterId = ff.childFilterId  and fp.filterId = ff.parentFilterId " 
			+ "and f.legacyType <> 'KeywordTimeLineFile' "; // and fp.filterId <> nf.topicExcludeId and f.filterId <> nf.topicExcludeId "; // 

	public void addFilter(Filter filter) {
		Log.info("NewsQueryBuilder addFilter filter=" + filter + " getName=" + filter.getName() + " filter.getOrderBySQL=" + filter.getOrderBySQL());
		filterNode.add(filter);
		// just for logging
		if (filter instanceof DBFilter) {
			DBFilter dBFilter = (DBFilter) filter;
			Log.info("NewsQueryBuilder addFilter dBFilter=" + dBFilter
					+ " getName=" + dBFilter.getName() + " getFilterId=" + dBFilter.getFilterId() + " getDbFilterCntr="+ dBFilter.getDbFilterCntr());
			dbFilterMap.put(dBFilter.getFilterId(), dBFilter);
		}
		
		if (!filter.isAllFilter()) {
			selectSQL.append(filter.getSelectSQL());
			fromSQL.append(filter.getFromSQL());
			Log.log("NewsQueryBuilder addFilter fromSQL=" + fromSQL);
			whereSQL.append(filter.getWhereSQL());
			Log.log("NewsQueryBuilder addFilter filter.getWhereSQL()="
					+ filter.getWhereSQL());
			orderBySQL.append(filter.getOrderBySQL());
			Log.info("NewsQueryBuilder addFilter filter.getOrderBySQL()=" + filter.getOrderBySQL() + " orderBySQL=" + orderBySQL);
		}
	}

	public NewsQueryBuilder(int limit) {
		this.limit = limit;

		selectSQL = new StringBuilder("");

		fromSQL = new StringBuilder(
				"\n from  filter f, filter fp, newsfilter nf, filterfilter ff, news n \n ");
		whereSQL = new StringBuilder(" f.filterId = l.filterId "
				+ " and n.newsId = nf.newsId "
				+ "  and f.filterId = nf.filterId ");

		orderBySQL = new StringBuilder("");

	}

	public String buildSql(int nameFilterNo, boolean isCoordFilter) {
		Log.info("NewsQueryBuilder buildSql nameFilterNo:" + nameFilterNo + " isCoordFilter:" + isCoordFilter);
		StringBuilder sql = new StringBuilder();
		
		if (nameFilterNo == 0) {
			sql.append(SELECT_EXTERNAL_COORD_FILTER);
		} else  {
			sql.append(SELECT_EXTERNAL);
		}

		sql.append(selectSQL);
		
		if (nameFilterNo == 0) {
			sql.append(FROM_EXTERNAL_COORD_FILTER);
		} else  {
			sql.append(fromSQL);
		}
		sql.append("\r\n");
		
		if (nameFilterNo == 0) {
			sql.append(WHERE_EXTERNAL_COORD_FILTER);
		} else  {
			sql.append(WHERE_EXTERNAL);
		}

		sql.append("\n");
		sql.append(whereSQL);
		sql.append("\r\n\r\n");
		
	//	if (nameFilterNo > 0) {
	//		orderBySQL.append(", topicExcludeId desc ");
	//	}
		sql.append(orderBySQL);
		sql.append(" limit ");
		sql.append(limit);

		this.sql = sql.toString();

		Log.log("buildSql selectSQL=" + selectSQL);
		Log.log("buildSql fromSQL=" + fromSQL);
		Log.log("buildSql whereSQL=" + whereSQL);
		Log.log("buildSql orderBySQL=" + orderBySQL);
		Log.info("\n\n buildSql() sql=" + sql);
		return this.sql;
	}

	public static void main(String args[]) {
		Log.info("start main");

		@SuppressWarnings("unused")
		String json = null;
		/*
		 * Json by URL
		 * 
		 * 
		 * try { json = buildJson(
		 * "http://mapreport.com/news?long=-15.000000&lat=-65.000000&longspan=3.0000000&latspan=10.0000000&size=20"
		 * ); // buildJson("http://mapreport.com/Angola/news?size=20"); //
		 * buildJson("http://mapreport.com/2011/news?size=20"); // not working
		 * yet buildJson("http://mapreport.com/May-2011/news?size=20"); } catch
		 * (MalformedURLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (UnsupportedEncodingException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 */
		// Json by URL by Java objects
		Set<NameFilter> nameFilters = new HashSet<NameFilter>(3);
   //     nameFilters.add(new DBFilter("Business"));
    //    nameFilters.add(new NameFilter("Latest"));
	//	nameFilters.add(new DBFilter("San Francisco Bay Area"));
		 nameFilters.add(new DBFilter("France"));

		// OfficialTimeFilter timeFilter = parseDateStr(partPath);
		 nameFilters.add(OfficialTimeFilter.parseDateStr("2011"));
		// nameFilters.add(OfficialTimeFilter.parseDateStr("2010s"));
	//	nameFilters.add(OfficialTimeFilter.parseDateStr(AllTime.ALL_TIME_NAME));
	//	 nameFilters.add(OfficialTimeFilter.parseDateStr("2011-02-03"));
		// nameFilters.add(OfficialTimeFilter.parseDateStr("2011-04"));
		json = ResponseBuilder.buildJson(null, nameFilters, 200);
		// json = ResponseBuilder.buildJson(
			//	new Rectangle(-65.0, -15.0, 27.0, 20.0), nameFilters, 20);
		Log.log("end main");
	}

	void bindFilters() throws SQLException {
		filterNode.bindFilters(pst);
	}

	public List<News> processResultSet(ResultSet res, int nameFilterNo) throws SQLException {
		List<News> rows = new ArrayList<News>(100);
		
		Set<Integer> excludedNesIds = new HashSet<Integer>(5); 
		while (res.next()) {
			News row = createNewsRow(res, nameFilterNo);
			
			if (dbFilterMap.get(row.getTopicExcludeId()) != null || excludedNesIds.contains(row.getNewsId())){
				Log.info("dbFilterMap.get(row.getTopicExcludeId() row:" + row.getLabel());
				if (!excludedNesIds.contains(row.getNewsId())) {
					excludedNesIds.add(row.getNewsId());
				}
			} else {
				rows.add(row);
			}
		}

		return rows;
	}

	private News createNewsRow(ResultSet res, int nameFilterNo) throws SQLException {
		News row = new News();

		String newsId = res.getString("newsId");
		String label = res.getString("label");
		String nPriority = res.getString("nPriority");
		String nfPriority = "0";
		
		if (nameFilterNo > 0) {
			nfPriority = res.getString("nfPriority");
			String topicExcludeId = res.getString("topicExcludeId");
			row.setTopicExcludeId(topicExcludeId);
			Log.info("processResultSet label=" + label + " newsId=" + newsId + " topicExcludeId=" + topicExcludeId);
		}
		
		Date date = res.getDate("dateTime");

		String url = res.getString("url");
		String video = res.getString("video");
		String image = res.getString("image");
		// String addressText = res.getString("addressText");
		String shortLabel = res.getString("shortLabel");
		String description = res.getString("description");

		double x = res.getDouble("addressX");
		double y = res.getDouble("addressY");

		String addressText = res.getString("addressText");

		row.setAddress(addressText);
		row.setX(x);
		row.setY(y);
		row.setDateTime(date);
		row.setLabel(label);
		row.setId(Integer.parseInt(newsId));
		row.setNewsId(Integer.parseInt(newsId));
		row.setPriority(Integer.parseInt(nPriority));
		row.setNewsFilterPriority(Integer.parseInt(nfPriority));
		row.setUrl(url);
		row.setVideo(video);
		row.setImage(image);
		row.setShortLabel(shortLabel);
		row.setDescription(description);
		Log.info("processResultSet label=" + label + " date=" + date
				+ " newsId=" + newsId + " nPriority=" + nPriority
				+ " addressText=" + addressText);
		
      /* if (Topic.mainTopics.contains(fName)) {
			  Log.info("FilterDBQueryBuilder processResultSet Topic.mainTopics fName=" + fName);
	   }
	   if (Topic.mainTopics.contains(fParentName)) {
			  Log.info("FilterDBQueryBuilder processResultSet Topic.mainTopics fParentName=" + fParentName);
	   }*/

		return row;
	}

	public List<News> runQuery(int nameFilterNo, boolean isCoordFilter) throws SQLException {
		Log.info("NewsQueryBuilder runQuery nameFilterNo:" + nameFilterNo + " isCoordFilter:" + isCoordFilter);
		begin(nameFilterNo, isCoordFilter);
		Log.log("start startBindQuery");
		startBindQuery();
		Log.log("start bindFilters");
		bindFilters();
		Log.log("start executeQuery");
		Log.info("NewsQueryBuilder runQuery() pst=\n" + pst.toString());
		ResultSet resultSet = pst.executeQuery();
		Log.log("start processResultSet");
		List<News> rows = processResultSet(resultSet, nameFilterNo);
		return rows;
	}

	public static List<News> buildNewsList(Map<Integer, News> newsMap, TimeFilter timeFilter) {
		Log.log("buildNewsList newsMap.size()=" + newsMap.size());
		List<News> newsList = new ArrayList<News>(300);

		for (Integer key : newsMap.keySet()) {
			newsList.add(newsMap.get(key));
		}
		
		if (timeFilter != null && timeFilter instanceof Latest) {
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
			Collections.sort(newsList);
		}
		return newsList;
	}

	public static Map<Integer, News> buildNewsMap(List<News> newsList) {
		Log.log("buildNewsMap newsList.size()=" + newsList.size());
		Map<Integer, News> newsMap = new HashMap<Integer, News>(300);

		for (News news : newsList) {
			if (newsMap.get(news.getNewsId()) == null) {
				newsMap.put(news.getNewsId(), news);
			} else {
				int nfPriority = newsMap.get(news.getNewsId())
						.getNewsFilterPriority();
				if (news.getNewsFilterPriority() < nfPriority) { 
					// lower better: 'Berlin','Khodorkovsky vows to help prisoners', '100'
					nfPriority = news.getNewsFilterPriority();
					News existNews = newsMap.get(news.getNewsId());
					existNews.setNewsFilterPriority(nfPriority);
					newsMap.put(news.getNewsId(), existNews);
				}
			}
		}
		return newsMap;
	}

}
