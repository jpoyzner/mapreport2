package mapreport.filter.time;

import java.util.Calendar;
import java.util.GregorianCalendar;

import mapreport.util.Log;

public class Latest extends OfficialTimeFilter {
	final public static String LATEST = "Latest";
	public Latest(int futureDays) {
		super(LATEST);
		setOrderBySQL(new StringBuilder("\n order by n.dateTime desc, n.priority "));
	//	begin = new GregorianCalendar();
	//	begin.add(Calendar.DAY_OF_MONTH, -30);
	//	begin.add(Calendar.DAY_OF_MONTH, -300);    // TEMPORARY, since no latest data
		end = new GregorianCalendar();
		end.add(Calendar.DAY_OF_YEAR, futureDays);
		parent = new AllTime();	
		setLink("");	
		buildTimeSQL(); 
		   Log.info("Latest end:" + new java.sql.Date(end.getTimeInMillis()) + " futureDays=" + futureDays + " whereSQL=" + whereSQL + " orderBySQL=" + getOrderBySQL());	//  begin:" + begin.getTimeInMillis() + " " +

		setPriority(2000000);
		
		// TEMPORARY !!!!!!!!!!!!!!!!!!!!!
		// whereSQL.append(" and n.dateTime < '2008-11-17' ");
		setName(LATEST);
	}
	
	@Override
	public String getLink() {
		Log.log("Filter getLink() Latest");
		return LATEST;
	}
	
	public static int buildFutureDays(int nameFilterNm) {
		int futureDays = 1;
		  
		  if (nameFilterNm > 0) {
			  futureDays = nameFilterNm * 3;
		  }
		return futureDays;
	}


}
