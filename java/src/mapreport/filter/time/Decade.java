package mapreport.filter.time;

public class Decade extends Year {

	public Decade(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public Decade(int year) {
		super(String.valueOf(year) + "'s");		
		this.year = year;
	}
}
