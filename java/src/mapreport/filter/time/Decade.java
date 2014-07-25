package mapreport.filter.time;

public class Decade extends Year {

	private static final String ALL_TIME_NAME = "All Time";

	public Decade(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public Decade(int year) {
		super(String.valueOf(year) + "'s");		
		this.year = year;
		setName(String.valueOf(year) + "'s");
		parent = new AllTime(ALL_TIME_NAME);	
	}
}
