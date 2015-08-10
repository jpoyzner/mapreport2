package mapreport.filter;

import java.text.MessageFormat;

import mapreport.front.page.FilterNode;
import mapreport.util.Log;


public class DBFilter extends NameFilter {

	int dbFilterCntr = 0;
	String dbFilterCntrStr = "";
	String filterId = null;
	
	public DBFilter(String name) {
		super(name);
		Log.log("DBFilter 2 name = " + name);
	}

	public String getFilterId() {
		return filterId;
	}

	public void setFilterId(String filterId) {
		this.filterId = filterId;
	}

	final static String SELECT_SQL = " f{0}.priority as filterPriority{0}, f{0}.name as fName{0}, fp{0}.name as pName{0}, ff{0}.level as pLevel{0}, f{0}.priority as nfPriority{0},  nf{0}.isPrimary as isPrimary{0}, nf{0}.isLocation as isLocation{0}, nf{0}.topicExcludeId as topicExcludeId{0}";
	final static String FROM_SQL = " filter f{0}, filter fp{0}, newsfilter nf{0}, filterfilter ff{0} ";
	final static String WHERE_SQL = "  f{0}.filterId = nf{0}.filterId  and nf{0}.newsId = n.newsId and f{0}.filterId = ff{0}.childFilterId  and fp{0}.filterId = ff{0}.parentFilterId and f{0}.legacyType <> KeywordTimeLineFile \n" 
			+ " and (fp{0}.name ='namePlaceHolder' or f{0}.name = 'namePlaceHolder') ";

	public int getDbFilterCntr() {
		return dbFilterCntr;
	}

	public void setDbFilterCntr(int dbFilterCntr) { 
		 Log.log("DBFilter setDbFilterCntr name=" + name + " dbFilterCntr=" + dbFilterCntr);
		this.dbFilterCntr = dbFilterCntr;
		if (dbFilterCntr > 0) {
			dbFilterCntrStr = "" + dbFilterCntr;
		}
	}


	@Override
	public StringBuilder getSelectSQL() {
		StringBuilder sql = new StringBuilder(MessageFormat.format(SELECT_SQL, dbFilterCntrStr));
		return sql;
	}

	@Override	public StringBuilder getFromSQL() {
		StringBuilder sql = new StringBuilder(MessageFormat.format(FROM_SQL, dbFilterCntrStr));
		Log.info("DBFilter getFromSQL getName()=" + getName() + " from sql=" + sql);   
		return sql;
	}


	@Override
	public StringBuilder getWhereSQL() {
	//	Object[] msgArgs = {dbFilterCntrStr, name.replaceAll("\'", "\'\'")};
		
	//	for (Object msgArg : msgArgs) {
	//		Log.info("DBFilter getWhereSQL msgArg=" + msgArg.toString());  
	//	} 
		String sqlStr = MessageFormat.format(WHERE_SQL, dbFilterCntrStr);
		sqlStr = sqlStr.replaceAll("namePlaceHolder", '\'' + name.replaceAll("\'", "\'\'") + '\'');
		sqlStr = sqlStr.replaceFirst("KeywordTimeLineFile", '\'' + "KeywordTimeLineFile" + '\'');
		StringBuilder sql = new StringBuilder(sqlStr);		
		
		Log.info("DBFilter getWhereSQL sql=" + sql);  
		 
		return sql;
	}
	
	protected void updateFilterNode(FilterNode filterNode) {
	}
	
	@Override
	public void upFilter(FilterNode filterNode) {
				Log.log("DBFilter upFilter getName()=" + getName());   
		updateFilterNode(filterNode);
	}
}
