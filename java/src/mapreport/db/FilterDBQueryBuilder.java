package mapreport.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mapreport.filter.DBFilter;
import mapreport.filter.Filter;
import mapreport.filter.NameFilter;
import mapreport.filter.loc.LocationByName;
import mapreport.filter.time.Day;
import mapreport.filter.time.Decade;
import mapreport.filter.time.Month;
import mapreport.filter.time.Year;
import mapreport.filter.topic.Topic;
import mapreport.news.News;
import mapreport.util.Log;

public class FilterDBQueryBuilder {
	String sql;
	PreparedStatement pst;
	/*
	 * select  f.priority as filterPriority, f.name as fName, fp.name as pName, fp.isLocation as isPLocation, ff.level as pLevel -- ,
 from  filter f, filter fp, newsfilter nf, filterfilter ff, news n  
 where  f.filterId = nf.filterId  and nf.newsId = n.newsId and f.filterId = ff.childFilterId  and fp.filterId = ff.parentFilterId 
 and n.newsId  in (115, 856,... )
 order by n.priority, n.dateTime, nf.isPrimary;
	 */
	private static String sqlBegin = "select f.filterId, f.name, f.label, f.image, f.isLocation, f.name as filterName, ff.level, " + 
			"\n  f.priority as filterPriority,  nf.priority as newsFilterPriority,  ff.priority as filterFilterPriority, " + 
			"\n  fp.filterId as parentId, fp.name as parentName, fp.label as parentLabel, fp.image as parentImage, fp.isLocation as parentLocation, n.newsId" + 
			"\n from filter f, filter fp, newsfilter nf, filterfilter ff, news n  " + 
		"\n where f.filterId = nf.filterId  and nf.newsId = n.newsId and f.filterId = ff.childFilterId  and fp.filterId = ff.parentFilterId  " + 
		"\n and n.newsId  in (";
	
	private static String sqlEnd = ") \n  order by f.priority, n.priority, ff.level, nf.priority, n.dateTime, nf.isPrimary \n limit 2000";
	
	public List <DBFilter> processResultSet(ResultSet res, Map<Integer, News> newsMap) throws SQLException{ 
		List <DBFilter> rows = new ArrayList<DBFilter>(1000);
		
		while (res.next()) {
			String fName = res.getString("name");
			boolean isLocation = res.getBoolean("isLocation");
			DBFilter row = isLocation ? new LocationByName(fName) : new Topic(fName);
		    int filterPriority = res.getInt("filterPriority");
		    int newsFilterPriority = res.getInt("newsFilterPriority");
		    int filterFilterPriority = res.getInt("filterFilterPriority");
		    
		    int priority = filterPriority * newsFilterPriority * filterFilterPriority;
			row.setPriority(priority);
			
		    int level = res.getInt("level");
			String filterId = res.getString("filterId");
			String label = res.getString("label");
			String image = res.getString("image");
			String newsId = res.getString("newsId");
			  
			row.setLevel(level);
			row.setName(fName);
			row.setImage(image);
			row.setFilterId(filterId);		  
	
			rows.add(row);			

			boolean isParentLocation = res.getBoolean("parentLocation");
			String fParentName = res.getString("parentName");
			DBFilter rowParent = isParentLocation ? new LocationByName(fParentName) : new Topic(fParentName);
			rowParent.setPriority(priority);
			String filterParentId = res.getString("parentId");
			String labelParent = res.getString("parentLabel");
			String imageParent = res.getString("parentImage");			  
			rowParent.setLevel(level);
			rowParent.setName(fParentName);
			rowParent.setImage(imageParent);	
			
			rows.add(rowParent);
			
			  Log.log("FilterDBQueryBuilder processResultSet "
					  +  " newsId=" + newsId 
					  +  " priority=" + priority 
					  +  " newsFilterPriority=" + newsFilterPriority 
					  +  " filterFilterPriority=" + filterFilterPriority 
					  +  " filterPriority=" + filterPriority +
					  " label=" + label +  " level=" + level  +  " filterId=" + filterId 
					  +  " fName=" + fName  
					  +  " fParentName=" + fParentName    
					  +  " isLocation=" + isLocation   +  " image=" + image  +
					  " labelParent=" + labelParent +  " filterParentId=" + filterParentId 
					  +  " isParentLocation=" + isParentLocation   +  " imageParent=" + imageParent  );	
			  
			  if (Topic.mainTopics.contains(fName)) {
				  Log.log("FilterDBQueryBuilder processResultSet Topic.mainTopics.contains(fName) Topic.mainTopics fName=" + fName + " newsId=" + newsId);
			  }
			  if (Topic.mainTopics.contains(fParentName)) {
				  Log.log("FilterDBQueryBuilder processResultSet Topic.mainTopics.contains(fParentName) fParentName=" + fParentName + " newsId=" + newsId);
				  News news = newsMap.get(new Integer(newsId));
				  
				  String rootTopic = news.getRootTopic();
				  if (rootTopic == null || rootTopic.isEmpty()) {
					  news.setRootTopic(fParentName);
				  }
			  }
		}
		
		return rows;
	}
	
	public String buildSql(Map<Integer, News> newsMap) {
		     Log.log("FilterDBQueryBuilder buildSql newsMap.size()=" + newsMap.size());
		StringBuilder sqlBuff = new StringBuilder();
		sqlBuff.append(sqlBegin);
		
		boolean isStarted = false;
		for (Integer newsId : newsMap.keySet()) {
			if (isStarted) {
				sqlBuff.append(", ");
			} else {
				isStarted = true;
			}
			sqlBuff.append(newsId);	
		}
		sqlBuff.append(sqlEnd);
		sql = sqlBuff.toString();

	       Log.info("FilterDBQueryBuilder buildSql sql=" + sql);
		return sql;
	}
	
	public PreparedStatement begin(){
        return pst;
	}    
		
	
	public PreparedStatement prepareStmt() {
		try {
			if (DBQueryBuilder.con == null) {
				DBQueryBuilder.buildConnection();
			}
			pst = DBQueryBuilder.con.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pst;
	}
	
	public List <DBFilter> runQuery(Map<Integer, News> newsMap) throws SQLException {
		if (newsMap.size() == 0) {
			return new ArrayList <DBFilter>(1);
		}
		begin();
		Log.info("FilterDBQueryBuilder start executeQuery");
   	    buildSql(newsMap);
   	    prepareStmt();
		Log.info("FilterDBQueryBuilder pst=\n" + pst.toString());
		ResultSet resultSet = pst.executeQuery();
		Log.info("FilterDBQueryBuilder start processResultSet");		
		List <DBFilter> rows = processResultSet(resultSet, newsMap);
	    return rows;
	}
	
	public List <NameFilter> addTimeFilters(List <NameFilter> dbFilters, Map<Integer, News> newsMap) throws SQLException {
		Map<String, NameFilter> filterMapResult = new HashMap<String, NameFilter> (60);
		for (News news : newsMap.values()) {
			incrementFilterMapPriority(filterMapResult, new Year(news.getDateTime().getYear() + 1900));
			incrementFilterMapPriority(filterMapResult, new Month(news.getDateTime().getYear() + 1900, news.getDateTime().getMonth()));
			incrementFilterMapPriority(filterMapResult, new Day(news.getDateTime().getYear() + 1900, news.getDateTime().getMonth(), news.getDateTime().getDate()));		
			incrementFilterMapPriority(filterMapResult, new Decade((news.getDateTime().getYear() + 1900) / 10 * 10));
		}
		Log.info("addTimeFilters filterMapResult.size() = " + filterMapResult.size());
		dbFilters.addAll(filterMapResult.values());
	    return dbFilters;
	}
	
	public static Map<String, NameFilter> incrementFilterMapPriority(List <DBFilter> filterMapSrc) {
		Map<String, NameFilter> filterMapResult = new HashMap<String, NameFilter> (60);
		
		for (NameFilter filter : filterMapSrc) {
			incrementFilterMapPriority(filterMapResult, filter);
		}
			
		for (Map.Entry<String, NameFilter> entry : filterMapResult.entrySet()) {
			Log.log("incrementFilterMapPriority Key = " + entry.getKey() + ", Value = " + entry.getValue().getPriority());
		}
			
		return filterMapResult;
	}
	  
	public static int reversePriority(int source) {
		int result = source == 0 ? 100000 : 100000 / source;
		result = (int)Math.pow(result, 1.0/5);
		return result;
	}
	
	public static void incrementFilterMapPriority(Map<String, NameFilter> filterMap, NameFilter filter) {
			 //     Log.log("addLocationTopicFilters incrementFilterMapPriority San Jose Downtown=" + filterMap.get("San Jose Downtown"));
			 //       if (filterMap.get("San Jose Downtown") != null) Log.log("addLocationTopicFilters bef incrementFilterMapPriority San Jose Downtown=" + filterMap.get("San Jose Downtown").getFilter().getName());
				String filterName = filter.getName();

				int reversePr = reversePriority(filter.getPriority());
				
				if (filterMap.get(filterName) == null) {
					filter.setPriority(reversePr);
					filterMap.put(filterName, filter);
					  Log.log("incrementFilterMapPriority put filterName=" + filterName );
				} else {
					  Log.log("incrementFilterMapPriority get filterName=" + filterName  + " priority=" + filterMap.get(filterName).getPriority());
					double oldPriority = filterMap.get(filterName).getPriority();
					double newPriority = reversePr + oldPriority;
					filterMap.get(filterName).setPriority((int)newPriority);
					         Log.log("incrementFilterMapPriority  oldPriority=" + oldPriority
					        		 + " filter.priority=" + filter.getPriority() 
					        		// + " reversePr=" + reversePr
					        		 + " newPriority=" + newPriority);
				}
				             Log.log("incrementFilterMapPriority filterKey=" + filterName + " getFilter().getName()=" + filter.getName() 
				            		 + " filterMap.get(filterKey).priority=" + filterMap.get(filterName).getPriority());
				 //  	      Log.log("addLocationTopicFilters end incrementFilterMapPriority San Jose Downtown=" + filterMap.get("San Jose Downtown"));
				//	        if (filterMap.get("San Jose Downtown") != null) Log.log("addLocationTopicFilters bef incrementFilterMapPriority San Jose Downtown=" + filterMap.get("San Jose Downtown").getFilter().getName());
	}
}
