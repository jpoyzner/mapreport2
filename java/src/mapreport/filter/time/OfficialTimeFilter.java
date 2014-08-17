package mapreport.filter.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import mapreport.util.Log;

public class OfficialTimeFilter extends TimeFilter {
	// start=01-01-2012&end=01-31-2012
	//		/43rd-street-san-francisco
	// String[] pathparts = path.split("/");
	// /San-Francisco/crime/2012/
	// /San-Francisco/crime/March-2012
	static DateTimeFormatter formatterDay = DateTimeFormatter.ISO_LOCAL_DATE; // ofPattern("MMM-dd-yyyy");  
	static DateTimeFormatter formatterMonth = DateTimeFormatter.ISO_LOCAL_DATE; // ofPattern("MMM-yyyy");  
	static DateTimeFormatter formatterYear = DateTimeFormatter.ofPattern("yyyy");  
//	static DateTimeFormatter formatterDecade = DateTimeFormatter.ofPattern("MMM-dd-yyyy");  //?????????????
	
	int year = -1;
	int month = -1;
	int day = -1;

	OfficialTimeFilter prevPeriod;
	OfficialTimeFilter nextPeriod;	


	// DateTimeParseException if the text cannot be parsed
	public static  OfficialTimeFilter parseDateStr(String dateStr) {
		OfficialTimeFilter ret = null;
		
		LocalDate date;
		try {
			int year = new Integer(dateStr);
			if (year > -5000 && year < 2100) {
				ret = new Year(year);
			}
			   Log.log("format as Year:" + dateStr);
			//date = LocalDate.parse(dateStr, formatterYear);
			//ret = new Year(date.getYear());
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			 if (dateStr.equals(AllTime.ALL_TIME_NAME)) {
				  ret = new AllTime(); 
			 } else  if (dateStr.equals(Future.FUTURE_NAME)) {
				  ret = new Future(); 
			 } else {			
				       Log.log("Can't format as Year:" + dateStr);				  
				 try {
						date = LocalDate.parse(dateStr, formatterDay);
						ret = new Day(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
						   Log.log("format as Day:" + dateStr);		  				  
				 } catch (DateTimeParseException me) {
					//me.printStackTrace();
					  Log.log("Can't format as Day:" + dateStr);
					try {
						date = LocalDate.parse(dateStr + "-01", formatterMonth);
						ret = new Month(date.getYear(), date.getMonthValue());
						  Log.log("format as Month:" + dateStr);
	
					} catch (DateTimeParseException de) {
						// de.printStackTrace();
	
						   Log.log("Can't format as Day:" + dateStr);
						if (dateStr.length() == 5 && dateStr.endsWith("0s")) {
							String yearStr = dateStr.substring(0, 4);
							try {
								int year = Integer.parseInt(yearStr);  
								ret = new Decade(year);
							} catch (NumberFormatException e1) {
								// e1.printStackTrace();
	
								    Log.log("Can't format as Decade:" + dateStr);
							}
						}	
					}
				}
			}
		}
		      Log.log("parseDateStr timeFilter:" + ret);
		return ret;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public OfficialTimeFilter getPrevPeriod() {
		return prevPeriod;
	}

	public void setPrevPeriod(OfficialTimeFilter prevPeriod) {
		this.prevPeriod = prevPeriod;
	}

	public OfficialTimeFilter getNextPeriod() {
		return nextPeriod;
	}

	public void setNextPeriod(OfficialTimeFilter nextPeriod) {
		this.nextPeriod = nextPeriod;
	}

	public OfficialTimeFilter(String name) {
		super(name);
		buildName();
		// TODO Auto-generated constructor stub
	}
	
	public void buildName() {
		setName(month + "/" + day + "/" + year);
	}
	
	public String toString() {
		return "OfficialTimeFilter: year=" + year + " month=" + month + " day=" + day;		
	}

}
