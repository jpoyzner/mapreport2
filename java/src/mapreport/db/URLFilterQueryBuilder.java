package mapreport.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import mapreport.filter.DBFilter;
import mapreport.filter.NameFilter;
import mapreport.filter.loc.LocationByName;
import mapreport.filter.topic.Topic;
import mapreport.util.Log;

public class URLFilterQueryBuilder extends FilterDBQueryBuilder {
	private static String sqlBegin = "select f.name, f.isLocation, f.filterId " + 
			"\n from filter f" + 
		"\n where f.name  in (";
	private static String sqlEnd = ")";
	private boolean hasLocationByName = false;
	
	public boolean isHasLocationByName() {
		return hasLocationByName;
	}

	public void setHasLocationByName(boolean hasLocationByName) {
		this.hasLocationByName = hasLocationByName;
	}

	public Map <String, DBFilter> buildFilters(ResultSet res) throws SQLException{ 
		Map <String, DBFilter> rows = new HashMap<String, DBFilter>(3);
		
		while (res.next()) {
			String fName = res.getString("name");
			String filterId = res.getString("filterId");
			boolean isLocation = res.getBoolean("isLocation");
			DBFilter row = isLocation ? new LocationByName(fName) : new Topic(fName);
			
			if (!hasLocationByName) hasLocationByName = isLocation;
			
			row.setFilterId(filterId);  
			  Log.info("URLFilterQueryBuilder processResultSet "
					  +  " fName=" + fName   
					  +  " isLocation=" + isLocation );		
			rows.put(fName, row);
		}
		
		return rows;
	}
	
	public String buildSql(Set<NameFilter> nameFilters) {
	     Log.info("URLFilterQueryBuilder buildSql nameFilters.size()=" + nameFilters.size());
		StringBuilder sqlBuff = new StringBuilder();
		sqlBuff.append(sqlBegin);
		
		boolean isStarted = false;
		for (NameFilter filter : nameFilters) {
			if (filter == null || filter.getName() == null || filter.getName().isEmpty()) {
				continue;
			}
			if (isStarted) {
				sqlBuff.append(", ");
			} else {
				isStarted = true;
			}
			sqlBuff.append("'");
			sqlBuff.append(filter.getName().replaceAll("'","''"));
			sqlBuff.append("'");		
		}
		
		if (!isStarted) {
			return null;
		}
		sqlBuff.append(sqlEnd);
		sql = sqlBuff.toString();
	
	      Log.info("URLFilterQueryBuilder buildSql sql=" + sql);
		return sql;
	}
	
	public Map <String, DBFilter> runQuery(Set<NameFilter> nameFilters) throws SQLException {
		if (nameFilters.size() == 0) {
			return new  HashMap <String, DBFilter>(1);
		}
		Log.info("URLFilterQueryBuilder start executeQuery");
   	    String sql = buildSql(nameFilters);
   	    
   	    if (sql == null) {
   	    	return new  HashMap <String, DBFilter>(1);
   	    }
   	    Connection connection = DriverManager.getConnection(DBBase.url, DBBase.user, DBBase.password);
   	    PreparedStatement pst = prepareStmt(connection);
		Log.info("URLFilterQueryBuilder pst=\n" + pst.toString());
		ResultSet resultSet = pst.executeQuery();
		Log.info("URLFilterQueryBuilder start processResultSet");		
		Map <String, DBFilter> rows = buildFilters(resultSet);
		end(pst, connection);
	    return rows;
	}
}
