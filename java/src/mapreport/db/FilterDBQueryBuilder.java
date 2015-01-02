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
import mapreport.filter.topic.Topic;
import mapreport.news.News;
import mapreport.util.Log;

public class FilterDBQueryBuilder {
	String sql;
	PreparedStatement pst;
	/*
	 * select  f.priority as filterPriority, f.name as fName, fp.name as pName, fp.isLocation as isPLocation, ff.level as pLevel -- ,
 from  filter f, filter fp, newsfilter nf, filterfilter ff, news n  
 where  f.filterId = nf.filterId  and nf.newsid = n.newsid and f.filterId = ff.childFilterId  and fp.filterId = ff.parentFilterId 
 and n.newsId  in (115, 856,... )
 order by n.priority, n.dateTime, nf.isPrimary;
	 */
	private static String sqlBegin = "select f.filterId, f.name, f.label, f.image, f.isLocation, f.name as filterName, ff.level, " + 
			"\n  f.priority as filterPriority,  nf.priority as newsFilterPriority,  ff.priority as filterFilterPriority " + 
			"\n from filter f, filter fp, newsfilter nf, filterfilter ff, news n  " + 
		"\n where f.filterId = nf.filterId  and nf.newsid = n.newsid and f.filterId = ff.childFilterId  and fp.filterId = ff.parentFilterId  " + 
		"\n and n.newsId  in (";
	
	private static String sqlEnd = ") \n  order by n.priority, nf.priority, f.priority, n.dateTime, nf.isPrimary \n limit 1000";
	
	public List <NameFilter> processResultSet(ResultSet res) throws SQLException{ 
		List <NameFilter> rows = new ArrayList<NameFilter>(1000);
		
		while (res.next()) {
			String fName = res.getString("name");
			boolean isLocation = res.getBoolean("isLocation");
			NameFilter row = isLocation ? new LocationByName(fName) : new Topic(fName);
		    int filterPriority = res.getInt("filterPriority");
		    int newsFilterPriority = res.getInt("newsFilterPriority");
		    int filterFilterPriority = res.getInt("filterFilterPriority");
		    
		    int priority = filterPriority * newsFilterPriority * filterFilterPriority;
			row.setPriority(priority);
			
		    int level = res.getInt("level");
			String filterId = res.getString("filterId");
			String label = res.getString("label");
			String image = res.getString("image");
		//	String filterName = res.getString("filterName");
			  
			row.setLevel(level);
			row.setName(fName);
		//	row.setFilterId(filterId);
			row.setImage(image);
		//	row.setFilterName(filterName);
			  
			  Log.log("FilterDBQueryBuilder processResultSet "
					  +  " priority=" + priority 
					  +  " newsFilterPriority=" + newsFilterPriority 
					  +  " filterFilterPriority=" + filterFilterPriority 
					  +  " filterPriority=" + filterPriority +
					  " label=" + label +  " level=" + level  +  " filterId=" + filterId 
					  +  " fName=" + fName   
					  +  " isLocation=" + isLocation   +  " image=" + image  );		
			rows.add(row);
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
	
	public List <NameFilter> runQuery(Map<Integer, News> newsMap) throws SQLException {
		if (newsMap.size() == 0) {
			return new ArrayList <NameFilter>(1);
		}
		begin();
		Log.info("FilterDBQueryBuilder start executeQuery");
   	    buildSql(newsMap);
   	    prepareStmt();
		Log.info("FilterDBQueryBuilder pst=\n" + pst.toString());
		ResultSet resultSet = pst.executeQuery();
		Log.info("FilterDBQueryBuilder start processResultSet");		
		List <NameFilter> rows = processResultSet(resultSet);
	    return rows;
	}
	
	public static Map<String, NameFilter> incrementFilterMapPriority(List <NameFilter> filterMapSrc) {
		Map<String, NameFilter> filterMapResult = new HashMap<String, NameFilter> (60);
		
		for (NameFilter entry : filterMapSrc) {
			    incrementFilterMapPriority(filterMapResult, entry);
		}
			
		for (Map.Entry<String, NameFilter> entry : filterMapResult.entrySet()) {
			Log.info("incrementFilterMapPriority Key = " + entry.getKey() + ", Value = " + entry.getValue().getPriority());
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
