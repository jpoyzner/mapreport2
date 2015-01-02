package mapreport.filter.time;

import java.util.Calendar;
import java.util.GregorianCalendar;

import mapreport.util.Log;

public class Latest extends TimeFilter {

	public Latest() {
		super("Latest");
		setOrderBySQL(new StringBuilder("\n order by n.dateTime desc, n.priority "));
	//	begin = new GregorianCalendar();
	//	begin.add(Calendar.DAY_OF_MONTH, -30);
	//	begin.add(Calendar.DAY_OF_MONTH, -300);    // TEMPORARY, since no latest data
		end = new GregorianCalendar();
		   Log.info("Latest end:" + end.getTimeInMillis() + " orderBySQL=" + getOrderBySQL());	//  begin:" + begin.getTimeInMillis() + " " +

		parent = new AllTime();	
		setLink("");	
		buildTimeSQL(); 
		
		
		// TEMPORARY !!!!!!!!!!!!!!!!!!!!!
		whereSQL.append(" and n.dateTime < '2008-11-17' ");

	}
	
	public String getLink() {
		Log.log("Filter getLink() Latest");
		return "";
	}
}
