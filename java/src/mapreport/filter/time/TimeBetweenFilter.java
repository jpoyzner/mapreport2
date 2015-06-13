package mapreport.filter.time;

public class TimeBetweenFilter extends TimeFilter {

	public TimeBetweenFilter(String name) {
		super(name);
	}

	public TimeBetweenFilter(String start, String finish) {
		super("Between " + start + " and " + finish);

		OfficialTimeFilter filterBegin = OfficialTimeFilter.parseDateStr(start, 0);
		begin = filterBegin.getBegin();

		OfficialTimeFilter filterEnd = OfficialTimeFilter.parseDateStr(finish, 0);
		end = filterEnd.getEnd();
		
		buildTimeSQL();
	}

}
