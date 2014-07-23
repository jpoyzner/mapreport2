package mapreport.filter;

import mapreport.front.page.FilterNode;
import mapreport.util.Log;


public class DBFilter extends NameFilter {

	int dbFilterCntr = 0;

	final static String SECOND_SELECT_SQL = " \n , f2.filterPriority as filterPriority2, f2.fName as fName2, f2.pName as pName2, f2.pLevel as pLevel2, f2.nfPriority as nfPriority2 \n ";
	final static String SECOND_FROM_SQL_START = ", \n ( select n.newsId, f.priority as filterPriority,  f.name as fName, fp.name as pName, ff.level as pLevel, nf.priority as nfPriority \n "
			+ "from news n, filter f, filter fp, newsFilter nf , filterfilter ff \n "
			+ "where n.newsid = nf.newsid \n "
			+ "and nf.filterid = f.filterid \n "
			+ "and nf.filterid = ff.childfilterid \n "
			+ "and fp.filterid = ff.parentfilterid \n "
			+ "and  fp.name = '"; // Africa') f2"
	final static String SECOND_FROM_SQL_END = "') f2 \n ";
	
	final static String SECOND_WHERE_SQL = "  and n.newsId = f2.newsId ";


	public DBFilter(String name) {
		super(name);
		 System.out.println("DBFilter name = " + name);
	}
	
	
	public int getDbFilterCntr() {
		return dbFilterCntr;
	}

	public void setDbFilterCntr(int dbFilterCntr) { 
		 Log.log("DBFilter setDbFilterCntr name=" + name + " dbFilterCntr=" + dbFilterCntr);
		this.dbFilterCntr = dbFilterCntr;
	}


	@Override
	public StringBuilder getSelectSQL() {
		StringBuilder sql = null;
		Log.log("DBFilter getSelectSQL name=" + name + " dbFilterCntr=" + dbFilterCntr);
		if (dbFilterCntr < 2) {
			sql = super.getSelectSQL();
		} else {
			sql = new StringBuilder(SECOND_SELECT_SQL);
		}
		return sql;
	}

	@Override
	public StringBuilder getFromSQL() {
		StringBuilder sql = null;

		Log.log("DBFilter getFromSQL name=" + name + " dbFilterCntr=" + dbFilterCntr);
		if (dbFilterCntr < 2) {
			sql = super.getFromSQL();
		} else {
			sql = new StringBuilder(SECOND_FROM_SQL_START);
			sql.append(name);
			sql.append(SECOND_FROM_SQL_END);
		}
		return sql;
	}


	@Override
	public StringBuilder getWhereSQL() {
		StringBuilder sql = null;

		Log.log("DBFilter getWhereSQL name=" + name + " dbFilterCntr=" + dbFilterCntr);
		if (dbFilterCntr < 2) {
			sql = super.getWhereSQL();
		} else {
			sql = new StringBuilder(SECOND_WHERE_SQL);
		}
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
