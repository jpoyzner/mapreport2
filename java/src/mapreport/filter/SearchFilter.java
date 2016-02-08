package mapreport.filter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SearchFilter extends NameFilter {
	public SearchFilter(String keywords) {
		super("Search result for '" + keywords + "'");
		this.keywords = keywords;
		whereSQL = new StringBuilder(" (");
		whereSQL.append(buildWhereClause("ShortLabel"));
		whereSQL.append( " or ");
		whereSQL.append(buildWhereClause("Label"));
		whereSQL.append( " or ");
		whereSQL.append(buildWhereClause("Description"));
		whereSQL.append( " or ");
		whereSQL.append(buildWhereClause("NewsText"));
		whereSQL.append( " or ");
		whereSQL.append(buildWhereClause("addressText"));
		whereSQL.append(" )");
			// fromSQL = new StringBuilder("news");
	}
	
	StringBuilder buildWhereClause(String column) {
		StringBuilder ret = new StringBuilder("");
		ret.append(" n.");
		ret.append(column);
		ret.append(" like '%");
		ret.append(keywords);
		ret.append("%' ");
		return ret;
	}
	// where (ShortLabel like ('%speak%') or Label like ('%speak%') or Description  like ('%speak%') or NewsText like ('%speak%') or addressText  like ('%speak%'))
	
	String keywords;

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Override
	public int bindQuery(PreparedStatement pst, int col) {
		// TODO Auto-generated method stub
		return col;
	}

	@Override
	public void processResultSet(ResultSet resultSet) {
		// TODO Auto-generated method stub
		
	}

}
