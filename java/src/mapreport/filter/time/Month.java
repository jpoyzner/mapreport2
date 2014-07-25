package mapreport.filter.time;

import java.util.Calendar;
import java.util.GregorianCalendar;

import mapreport.db.NewsFilterRow;
import mapreport.util.Log;

public class Month extends OfficialTimeFilter {

	public Month(String name) {
		super(name);
		// TODO Auto-generated constructor stub

		begin = new GregorianCalendar(year, month, 1);
		end = new GregorianCalendar(year, month, begin.getActualMaximum(Calendar.DAY_OF_MONTH));
	}

	public Month(int year, int month) {
		super(String.valueOf(year) + "-" + String.valueOf(month));		
		this.year = year;		
		this.month = month;
		
		begin = new GregorianCalendar(year, month, 1);
		end = new GregorianCalendar(year, month, begin.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		setName(String.valueOf(year) + "-" + String.valueOf(month));		
		           Log.log("Month year=" + year + " month=" + month + " name=" + getName());

		parent = new Year(year);		           
	}
	
	@Override
	public void addParent(NewsFilterRow parent) {
			Log.log("Month addParent  name=" + getName() + "parent.getParentId()=" + parent.getParentId() + "  parent.getFilterId()=" + parent.getFilterId());
			Year yearFilter = new Year(year);
			getParents().put(yearFilter.getName(), yearFilter);
			getParentList().add(yearFilter);
	}
}
