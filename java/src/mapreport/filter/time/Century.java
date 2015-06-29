package mapreport.filter.time;

import java.util.GregorianCalendar;

public class Century extends Year {

	public Century(String name) {
		super(name);
		buildTimeSQL(); 
	}

	public Century(int century) {
		super(String.valueOf(century) + getSuffix(century) + " Century");	
		setPriority(600);	
		this.year = (century - 1) * 100;
		begin = new GregorianCalendar(year, 1, 1);
		end = new GregorianCalendar(year + 99, 11, 31);
		setName(String.valueOf(century) + getSuffix(century) + " Century");	
		buildPriority();
		parent = new AllTime();	
		buildTimeSQL(); 
		setImage("common/century.jpg");
	}
	
	private static String getSuffix(int num) {
		int reminder = num % 10;
		if (reminder == 1) {
			return "st";
		} else if (reminder == 2) {
			return "nd";
		} else if (reminder == 3) {
			return "rd";
		} else {
			return "th";
		}
	}

}
