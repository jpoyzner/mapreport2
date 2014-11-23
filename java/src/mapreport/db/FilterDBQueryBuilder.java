package mapreport.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mapreport.util.Log;

public class FilterDBQueryBuilder {
	String sql;
	PreparedStatement pst;
	private static String sqlBegin = "select p.filterId, p.name, p.label, p.image, p.priority, p.isLocation, c.isLocation as filterLocation, c.name as filterName, ff.level " + 
		"\n from filter p, filter c, filterfilter ff " + 
		"\n where p.filterId = ff.parentFilterId and c.filterId = ff.childFilterId " + 
		"\n and c.name in (";
	
	public List<NewsFilterRow> processResultSet(ResultSet res) throws SQLException{ 
		List<NewsFilterRow> rows = new ArrayList<NewsFilterRow>(100);
		while (res.next()) {
			NewsFilterRow row = new NewsFilterRow();
			//	select  f.priority as filterPriority, 
			//     f.name as fName, n.newsId, n.label, n.priority as nPriority, nf.priority as nfPriority, 
		    int filterPriority = res.getInt("priority");
		    int level = res.getInt("level");
			String fName = res.getString("name");
			String filterId = res.getString("filterId");
			String label = res.getString("label");
			String image = res.getString("image");
			boolean isLocation = res.getBoolean("isLocation");
			boolean isFilterLocation = res.getBoolean("filterLocation");
			String filterName = res.getString("filterName");
			  
			row.setFilterPriority(filterPriority);
			row.setLevel(level);
			row.setName(fName);
			row.setFilterId(filterId);
			row.setLocation(isLocation);
			row.setImage(image);
			row.setFilterLocation(isFilterLocation);
			row.setFilterName(filterName);
			  
			  Log.log("FilterDBQueryBuilder processResultSet label=" + label +  " level=" + level  +  " filterPriority=" + filterPriority +  " filterId=" + filterId 
					  +  " fName=" + fName   +  " filterPriority=" + filterPriority   +  " isLocation=" + isLocation   +  " image=" + image  );		
			rows.add(row);
		}
		
		return rows;
	}
	public String buildSql(List<String> filterIds) {
		     Log.log("FilterDBQueryBuilder buildSql filterIds.size()=" + filterIds.size());
		StringBuilder sqlBuff = new StringBuilder();
		sqlBuff.append(sqlBegin);
		
		for (int i = 0; i < filterIds.size(); i++) {
			if (i > 0) {
				sqlBuff.append(", ");
			} 
			sqlBuff.append("'");
			sqlBuff.append(filterIds.get(i));	
			sqlBuff.append("'");		
		}
		sqlBuff.append(") \n order by ff.level, ff.childFilterId, p.filterId");
		sql = sqlBuff.toString();

	       Log.info("FilterDBQueryBuilder buildSql sql=" + sql);
		return sql;
	}
	
	public PreparedStatement begin(){
   		//pst = prepareStmt();
		
        // select  f.priority as filterPriority, 
        // f.name as fName, n.newsId, n.label, n.priority as nPriority, nf.priority as nfPriority, 
        
      //  System.out.println("DBQueryBuilder con=" + DBQueryBuilder.con);
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
	
	public List<NewsFilterRow> runQuery(List<String> filterIds) throws SQLException {
		begin();
		Log.info("FilterDBQueryBuilder start executeQuery");
   	    buildSql(filterIds);
   	    prepareStmt();
		Log.info("FilterDBQueryBuilder pst=\n" + pst.toString());
		ResultSet resultSet = pst.executeQuery();
		Log.info("FilterDBQueryBuilder start processResultSet");		
	    List<NewsFilterRow> rows = processResultSet(resultSet);
	    return rows;
	}
}
