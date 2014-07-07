package mapreport.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilterDBQueryBuilder {
	String sql;
	PreparedStatement pst;
	private static String sqlBegin = "select p.filterId, p.name, p.label, p.image, p.priority, p.isLocation " + 
		"from filter p, filter c, filterFilter ff " + 
		"where p.filterId = ff.parentFilterId and c.filterId = ff.childFilterId " + 
		"and ff.childFilterId in (";
	
	public List<NewsFilterRow> processResultSet(ResultSet res) throws SQLException{ 
		List<NewsFilterRow> rows = new ArrayList<NewsFilterRow>(100);
		while (res.next()) {
			NewsFilterRow row = new NewsFilterRow();
			//	select  f.priority as filterPriority, 
			//     f.name as fName, n.newsId, n.label, n.priority as nPriority, nf.priority as nfPriority, 
				  int filterPriority = res.getInt("priority");
				  String fName = res.getString("name");
				  String filterId = res.getString("filterId");
				  String label = res.getString("label");
				  String image = res.getString("image");
				  boolean isLocation = res.getBoolean("isLocation");
				  
				  row.setFilterPriority(filterPriority);
				  row.setName(fName);
				  row.setFilterId(filterId);
				  row.setLocation(isLocation);
				  row.setImage(image);
				  
				  System.out.println("FilterDBQueryBuilder processResultSet label=" + label +  " filterPriority=" + filterPriority +  " filterId=" + filterId 
						  +  " fName=" + fName   +  " filterPriority=" + filterPriority   +  " isLocation=" + isLocation   +  " image=" + image  );		
			rows.add(row);
		}
		
		return rows;
	}
	public String buildSql(List<String> filterIds) {
		StringBuilder sqlBuff = new StringBuilder();
		sqlBuff.append(sqlBegin);
		
		for (int i = 0; i < filterIds.size(); i++) {
			if (i > 0) {
				sqlBuff.append(", ");
			} 
			sqlBuff.append(filterIds.get(i));			
		}
		sqlBuff.append(") order by ff.childFilterId, p.filterId");
		sql = sqlBuff.toString();

	       System.out.println("buildSql sql=" + sql);
		return sql;
	}
	
	public PreparedStatement begin(){
   		pst = prepareStmt();
		
        // select  f.priority as filterPriority, 
        // f.name as fName, n.newsId, n.label, n.priority as nPriority, nf.priority as nfPriority, 
        
      //  System.out.println("DBQueryBuilder con=" + DBQueryBuilder.con);
        return pst;
	}
		
	
	public PreparedStatement prepareStmt() {
		try {
			pst = DBQueryBuilder.con.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pst;
	}
	
	public List<NewsFilterRow> runQuery() throws SQLException {
		begin();
   	    	System.out.println("start executeQuery");
		ResultSet resultSet = pst.executeQuery();
	    	System.out.println("start processResultSet");		
	    List<NewsFilterRow> rows = processResultSet(resultSet);
	    return rows;
	}
}
