package mapreport.filter.time;

public class TimeBetweenFilter extends TimeFilter {

	public TimeBetweenFilter(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public TimeBetweenFilter(String start, String end) {
		super("Between " + start + " and " + end);
		// TODO Auto-generated constructor stub
	}

}
