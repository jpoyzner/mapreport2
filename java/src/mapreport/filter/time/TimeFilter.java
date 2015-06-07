package mapreport.filter.time;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Set;

import mapreport.filter.Filter;
import mapreport.filter.NameFilter;
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
		
		if (orderBySQL.length() == 0) {
			orderBySQL = new StringBuilder("\n order by n.priority, n.dateTime ");
		}

		if (begin != null) {
			whereSQL.append(" and n.dateTime > ? ");
		}
		if (end != null) {
			whereSQL.append(" and n.dateTime < ?  ");
		}
	      Log.log("TimeFilter buildTimeSQL begin=" + begin + " end=" + end + " whereSQL=" + whereSQL.toString().trim());
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
		
	//	if (filterNode.getTimeFilter() == null || filterNode.getTimeFilter() instanceof Latest || this.getParents().get(filterNode.getTimeFilter().getName()) != null) {
			           Log.log("TimeFilter limitFilter limiting!");
	    filterNode.getFilterList().remove(filterNode.getTimeFilter());
		filterNode.setTimeFilter(this);
		filterNode.getFilterList().add(this);
	//	}
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
	public int bindQuery(PreparedStatement pst, int col)  throws SQLException{		  		
		Log.log("TimeFilter bindQuery begin1=" + begin);	  		
		Log.log("TimeFilter bindQuery end1=" + end);
	//	pst.setDouble(++col, begin.getTimeInMillis());	
	//	pst.setDouble(++col, end.getTimeInMillis());	
		if (begin != null) {
			Log.log("TimeFilter bindQuery beginsql=" + new java.sql.Date(begin.getTimeInMillis()));	
			pst.setDate(++col, new java.sql.Date(begin.getTimeInMillis()));	
		}
		if (end != null) {
			Log.log("TimeFilter bindQuery endsql=" + new java.sql.Date(end.getTimeInMillis()));	
			pst.setDate(++col, new java.sql.Date(end.getTimeInMillis()));	
		}
		return col;
	}
	
	@Override
	public void processResultSet(ResultSet resultSet) {
		// TODO Auto-generated method stub
		
	}
	
	public static int addDate(Set<NameFilter> nameFilters, String date, int dateFilterCnt)
			throws UnsupportedEncodingException {
		Log.info("TimeFilter date:" + date);
		if (date != null) {
			date = URLDecoder.decode(date, "UTF-8");
			nameFilters.add(OfficialTimeFilter.parseDateStr(date, nameFilters.size()));
			dateFilterCnt++;
		}
		return dateFilterCnt;
	}

}
