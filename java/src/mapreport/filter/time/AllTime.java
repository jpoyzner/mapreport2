package mapreport.filter.time;

public class AllTime extends OfficialTimeFilter {
    final static public String ALL_TIME_NAME = "All Time";

	public AllTime() {
		super(ALL_TIME_NAME);
	}
	
	public void buildName() {
		setName(ALL_TIME_NAME);
	}
	


}
