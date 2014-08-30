package mapreport.filter.time;

import java.util.Calendar ;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mapreport.filter.Filter;
import mapreport.filter.NameFilter;
import mapreport.filter.topic.Topic;
import mapreport.front.page.FilterNode;
import mapreport.util.Log;

public class TimeFilter extends NameFilter {
	Calendar  begin;
	Calendar  end;
	TimeFilter parent;

	public TimeFilter getParent() {
		return parent;
	}
	public void setParent(TimeFilter parent) {
		this.parent = parent;
	}

	public TimeFilter(String name) {
		super(name);	 
		buildTimeSQL(); 
	}
	protected void buildTimeSQL() {
		whereSQL = new StringBuilder(" \n ");
		orderBySQL = new StringBuilder("\n order by n.priority, n.dateTime ");

		if (begin != null) {
			whereSQL.append(" and n.dateTime > ? ");
		}
		if (end != null) {
			whereSQL.append(" and n.dateTime < ?  ");
		}
	      Log.log("TimeFilter buildTimeSQL begin=" + begin + " end=" + end + " whereSQL=" + whereSQL.toString());
		setWhereSQL(whereSQL); 
		setOrderBySQL(orderBySQL);
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
		for (Filter filter : filterNode.getFilterList()) {
			if (filter instanceof TimeFilter) {
					filterNode.getFilterList().remove(filter);
					break;
			}
		}
		filterNode.setTimeFilter(this);
		filterNode.getFilterList().add(this);
		Log.log("Topic updateFilterNode aft filterNode=" + filterNode);
	}

	
	@Override
	public void bindQuery(PreparedStatement pst)  throws SQLException{				
		System.out.println("TimeFilter bindQuery begin=" + begin);			
		System.out.println("TimeFilter bindQuery end=" + end);
		int col = 0;
	//	pst.setDouble(++col, begin.getTimeInMillis());	
	//	pst.setDouble(++col, end.getTimeInMillis());	
		if (begin != null) {
			pst.setDate(++col, new java.sql.Date(begin.getTimeInMillis()));	
		}
		if (end != null) {
			pst.setDate(++col, new java.sql.Date(end.getTimeInMillis()));	
		}
	}
	
	@Override
	public void processResultSet(ResultSet resultSet) {
		// TODO Auto-generated method stub
		
	}
}
