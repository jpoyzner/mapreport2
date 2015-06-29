package mapreport.filter.time;

import java.util.GregorianCalendar;

public class Millenium extends Year {
	public Millenium(String name) {
		super(name);
		buildTimeSQL(); 
	}

	public Millenium(int millenium) {
		super(String.valueOf(millenium) + getSuffix(millenium) + " Millenium");	
		setPriority(600);	
		this.year = (millenium - 1) * 1000;
		begin = new GregorianCalendar(year, 1, 1);
		end = new GregorianCalendar(year + 999, 11, 31);
		setName(String.valueOf(millenium) + getSuffix(millenium) + " Millenium");
		buildPriority();
		parent = new AllTime();	
		buildTimeSQL(); 
		setImage("common/millenium.jpg");
	}
	
	private static String getSuffix(int num) {
		int reminder = num % 10;
		if (reminder == 1) {
			return "st";
		} else if (reminder == 2) {
			return "nd";
		} else {
			return "rd";
		} 
	}
}
