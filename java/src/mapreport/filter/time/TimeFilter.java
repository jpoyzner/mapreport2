package mapreport.filter.time;

import java.util.Calendar ;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mapreport.filter.NameFilter;
import mapreport.front.page.FilterNode;
import mapreport.util.Log;

public class TimeFilter extends NameFilter {
	Calendar  begin;
	Calendar  end;

	// final StringBuilder whereStringBuilder = new StringBuilder(" \n and UNIX_TIMESTAMP(n.dateTime) > ? and UNIX_TIMESTAMP(n.dateTime) < ? ");
	final StringBuilder whereSQL = new StringBuilder(" \n and n.dateTime > ? and n.dateTime < ? ");
	StringBuilder orderSQL = new StringBuilder("\n order by n.priority, n.dateTime ");
//	final StringBuilder selectStringBuilder = new StringBuilder(" \n  n.dateTime > ? and n.dateTime < ? ");
	
	public TimeFilter(String name) {
		super(name);	
		setWhereSQL(whereSQL); 
		setOrderBySQL(orderSQL); 
	}
	public Calendar getBegin() {
		return begin;
	}

	public void setBegin(Calendar begin) {
		this.begin = begin;
	}

	public Calendar getEnd() {
		return end;
	}

	public void setEnd(Calendar end) {
		this.end = end;
	}
	
	@Override
	public void limitFilter(FilterNode filterNode) {
			    Log.log("TimeFilter limitFilter filterNode.getTimeFilter()=" + filterNode.getTimeFilter()  + " this.getParents()=" + this.getParents().size());
			if (filterNode.getTimeFilter() != null) {
				 Log.log("TimeFilter limitFilter filterNode.getTimeFilter().getName()=" + filterNode.getTimeFilter().getName()
						 + "getParents().get(filterNode.getTimeFilter().getName()" + getParents().get(filterNode.getTimeFilter().getName()));
				 
				 if ( this.getParents().size() > 0) {
					 Log.log("TimeFilter limitFilter this.getParents()=" + this.getParents().toString());
				 }
			}
		if (filterNode.getTimeFilter() == null || filterNode.getTimeFilter() instanceof Latest || this.getParents().get(filterNode.getTimeFilter().getName()) != null) {
			           Log.log("TimeFilter limitFilter limiting!");
			    filterNode.getFilterList().remove(filterNode.getTimeFilter());
				filterNode.setTimeFilter(this);
				filterNode.getFilterList().add(this);
		}
	}
	
	@Override
	public void upFilter(FilterNode filterNode) {
		if (filterNode.getTimeFilter() == null || filterNode.getTimeFilter().getParents().containsKey(getName())) {
				filterNode.setTimeFilter(this);
		}
	}

	
	@Override
	public void bindQuery(PreparedStatement pst)  throws SQLException{				
		System.out.println("TimeFilter bindQuery ");
		int col = 0;
	//	pst.setDouble(++col, begin.getTimeInMillis());	
	//	pst.setDouble(++col, end.getTimeInMillis());	
		pst.setDate(++col, new java.sql.Date(begin.getTimeInMillis()));	
		pst.setDate(++col, new java.sql.Date(end.getTimeInMillis()));	
	}
	
	@Override
	public void processResultSet(ResultSet resultSet) {
		// TODO Auto-generated method stub
		
	}
}
