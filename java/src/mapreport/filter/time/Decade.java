package mapreport.filter.time;

import java.util.GregorianCalendar;

public class Decade extends Year {
	public Decade(String name) {
		super(name);
	}
	
	public Decade(int year) {
		super(String.valueOf(year) + "s");	
		setPriority(600);	
		this.year = year;
		begin = new GregorianCalendar(year, 1, 1);
		end = new GregorianCalendar(year + 9, 11, 31);
		setName(String.valueOf(year) + "s");
		parent = new Century(year / 100);	
		buildTimeSQL(); 
	}
}
