package mapreport.filter.time;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;

import mapreport.db.NewsFilterRow;
import mapreport.util.Log;

public class Month extends OfficialTimeFilter {

	public Month(String name) {
		super(name);
		// TODO Auto-generated constructor stub

		begin = new GregorianCalendar(year, month - 1, 1);
		end = new GregorianCalendar(year, month - 1, begin.getActualMaximum(Calendar.DAY_OF_MONTH));
	}

	public Month(int year, int month) {
		// 		super(new DateFormatSymbols().getMonths()[month].substring(0, 3) + "-" + (day < 10 ? "0" : "") + String.valueOf(day)  + "-" + String.valueOf(year));		
	//	super(String.valueOf(year) + "-" + String.valueOf(month));		
		super(new DateFormatSymbols().getMonths()[month].substring(0, 3) + "-" + String.valueOf(year));
		this.year = year;		
		this.month = month;
		
		begin = new GregorianCalendar(year, month - 1, 1);
		end = new GregorianCalendar(year, month - 1, begin.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		setName(new DateFormatSymbols().getMonths()[month].substring(0, 3) + "-" + String.valueOf(year));	
		           Log.log("Month year=" + year + " month=" + month + " name=" + getName());

		parent = new Year(year);		
		buildTimeSQL(); 	           
	}
	
	@Override
	public void addParent(NewsFilterRow parent) {
			Log.log("Month addParent  name=" + getName() + "parent.getParentId()=" + parent.getParentId() + "  parent.getFilterId()=" + parent.getFilterId());
			Year yearFilter = new Year(year);
			getParents().put(yearFilter.getName(), yearFilter);
			getParentList().add(yearFilter);
	}
}
