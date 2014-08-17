package mapreport.filter.time;

import java.util.Calendar;
import java.util.GregorianCalendar;

import mapreport.util.Log;

public class Latest extends TimeFilter {

	public Latest() {
		super("Latest");
		setOrderBySQL(new StringBuilder("\n order by n.dateTime desc, n.priority "));
		begin = new GregorianCalendar();
		begin.add(Calendar.DAY_OF_MONTH, -30);
		begin.add(Calendar.DAY_OF_MONTH, -300);    // TEMPORARY, since no latest data
		end = new GregorianCalendar();
		
		Log.log("Latest begin:" + begin.getTimeInMillis() + " end:" + end.getTimeInMillis() + " orderBySQL=" + getOrderBySQL());	
		
		setLink("");	
		buildTimeSQL(); 
	}
	
	public String getLink() {
		Log.log("Filter getLink() Latest");
		return "";
	}
}
