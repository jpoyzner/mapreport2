package mapreport.filter.time;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.GregorianCalendar;

import mapreport.db.NewsFilterRow;
import mapreport.util.Log;

public class Day extends OfficialTimeFilter {

	public Day(String name) {
		super(name);
		// TODO Auto-generated constructor stub
		begin = new GregorianCalendar(year, month, day);
		end = new GregorianCalendar(year, month, day, 11, 59, 59);
	}

	public Day(int year, int month, int day) {
		super(String.valueOf(day) + "/" + String.valueOf(month) + "/" + String.valueOf(year));		
		this.year = year;		
		this.month = month;	
		this.day = day;		
// 2011-12-03
		setName(String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day));	

		begin = new GregorianCalendar(year, month, day);
	//	end = new GregorianCalendar(year, month, begin.get(Calendar.DAY_OF_MONTH) + 2);
		end = new GregorianCalendar(year, month, day);
		end.setTimeInMillis(begin.getTimeInMillis() + 24 * 60 * 60 * 1000);
		// end.add(Calendar.DAY_OF_YEAR, 2);
		// end = new GregorianCalendar(year, month, day, 11, 59, 59);
		parent = new Month(year, month);
		           Log.log("Day year=" + year + " month=" + month + " day=" + day + 
		        		    " name=" + getName() + " begin:" + begin + "\n  end:" + end + " begin:" + begin.getTimeInMillis() + "  end:" + end.getTimeInMillis());
		
		setWhereSQL(new StringBuilder(" \n and datetime BETWEEN '" + year + "-" + month + "-" + day + "' AND '" + year + "-" + month + "-" + day + " 23:59:59' ")); 
	}
	
	@Override
	public int bindQuery(PreparedStatement pst, int col)  throws SQLException{				
		Log.log("Day bindQuery ");
		return col;
	}
	
	@Override
	public void addParent(NewsFilterRow parent) {
				Log.log("Day addParent  name=" + getName() + "parent.getParentId()=" + parent.getParentId() + "  parent.getFilterId()=" + parent.getFilterId());
		    Month monthFilter = new Month(year, month);		
		    getParents().put(monthFilter.getName(), monthFilter);
			getParentList().add(monthFilter);
			
			Year yearFilter = new Year(year);
			getParents().put(yearFilter.getName(), yearFilter);
			getParentList().add(yearFilter);
	}

}
