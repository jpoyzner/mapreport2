package mapreport.filter.time;

import java.util.GregorianCalendar;

public class Decade extends Year {

//	private static final String ALL_TIME_NAME = "All Time";

	public Decade(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public Decade(int year) {
		super(String.valueOf(year) + "'s");		
		this.year = year;
		begin = new GregorianCalendar(year, 1, 1);
		end = new GregorianCalendar(year + 9, 11, 31);
		setName(String.valueOf(year) + "'s");
		parent = new AllTime();	
		buildTimeSQL(); 
	}
}
