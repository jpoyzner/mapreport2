package mapreport.filter.time;

import java.text.SimpleDateFormat;
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
	static DateTimeFormatter formatterDay = DateTimeFormatter.ISO_LOCAL_DATE; // 2008-11-15
    static DateTimeFormatter formatterDayMMM = DateTimeFormatter.ofPattern("MMM-dd-yyyy");  //date/May-11-2008
	static DateTimeFormatter formatterMonth = DateTimeFormatter.ISO_LOCAL_DATE; 
	static DateTimeFormatter formatterMonthMMM = DateTimeFormatter.ofPattern("MMM-yyyy-dd");  //date/May-2008-11
	static DateTimeFormatter formatterYear = DateTimeFormatter.ofPattern("yyyy");  
	public static SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	int year = -1;
	int month = -1;
	int day = -1;

	OfficialTimeFilter prevPeriod;
	OfficialTimeFilter nextPeriod;	


	// DateTimeParseException if the text cannot be parsed
	public static  OfficialTimeFilter parseDateStr(String dateStr, int nameFilterNm) {
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
			 } else  if (dateStr.equals(Latest.LATEST)) {
				  int futureDays = Latest.buildFutureDays(nameFilterNm); 
				  Log.info("format as LATEST:" + dateStr + " nameFilterNm=" + nameFilterNm + " futureDays=" + futureDays);
				  ret = new Latest(futureDays);  
			 } else  if (dateStr.equals(Future.FUTURE_NAME)) {
				  ret = new Future(); 
				  Log.info("format as Future:" + dateStr);
			 } else  if (dateStr.equals(ThisDayInHistory.THIS_DAY_IN_HISTORY_NAME)) {
				  ret = new ThisDayInHistory(); 
				  Log.info("format as ThisDayInHistory:" + dateStr);
			 } else {			
				       Log.log("Can't format as Year:" + dateStr);				  
				 try {
						date = LocalDate.parse(dateStr, formatterDayMMM);
						ret = new Day(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
						   Log.log("format as Day:" + dateStr);		  				  
				 } catch (DateTimeParseException me) {
					//me.printStackTrace();
					  Log.log("Can't format as Day:" + dateStr);					  
						 try {
								date = LocalDate.parse(dateStr, formatterDayMMM);
								ret = new Day(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
								   Log.log("format as Day:" + dateStr);		  				  
						 } catch (DateTimeParseException de1) {
							//me.printStackTrace();
							  Log.info("Can't format as Day:" + dateStr);

								try {
									date = LocalDate.parse(dateStr + "-01", formatterMonthMMM);
									ret = new Month(date.getYear(), date.getMonthValue());
									  Log.info("format as Month:" + dateStr + "-01");
				
								} catch (DateTimeParseException de) {									
									   Log.info("Can't format as Month:" + dateStr + "-01");
									try {
										date = LocalDate.parse(dateStr + "-01", formatterMonth);
										ret = new Month(date.getYear(), date.getMonthValue());
										  Log.info("format as Month:" + dateStr + "-01");
					
									} catch (DateTimeParseException me2) {			
										   Log.info("Can't format as Month 2:" + dateStr + "-01");
										if (dateStr.length() == 5 && dateStr.endsWith("0s")) {
											String yearStr = dateStr.substring(0, 4);
											try {
												int year = Integer.parseInt(yearStr);  
												ret = new Decade(year);
											} catch (NumberFormatException e1) {				
												    Log.info("Can't format as Decade:" + dateStr);		
											}
										} else {
											if ((dateStr.length() == 12 || dateStr.length() == 11) && dateStr.substring(dateStr.length() - 8).equalsIgnoreCase(" century")) {
												String centuryStr = dateStr.substring(0, dateStr.length() - 10);
												try {
													int century = Integer.parseInt(centuryStr);  
													ret = new Century(century);
												} catch (NumberFormatException e2) {		
													    Log.info("Can't format as Century:" + centuryStr);
												}
											} else {
												Log.info("Can't format as 2 Century:" + dateStr);
												
												if (dateStr.length() == 13 && dateStr.substring(dateStr.length() - 10).equalsIgnoreCase(" millenium")) {
													String milleniumStr = dateStr.substring(0, dateStr.length() - 12);
													try {
														int millenium = Integer.parseInt(milleniumStr);  
														ret = new Millenium(millenium);
													} catch (NumberFormatException e2) {		
														    Log.info("Can't format as Millenium:" + milleniumStr);
													}
												} else {
													Log.info("Can't format as 2 millenium:" + dateStr);
												}

											}
										}
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
		setPriority(100);
		// TODO Auto-generated constructor stub
	}
	
	public void buildName() {
		setName(month + "/" + day + "/" + year);
	}
	
	public String toString() {
		return "OfficialTimeFilter: year=" + year + " month=" + month + " day=" + day;		
	}

}
