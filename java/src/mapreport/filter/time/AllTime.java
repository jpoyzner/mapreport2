package mapreport.filter.time;

import mapreport.front.page.FilterNode;
import mapreport.util.Log;

public class AllTime extends OfficialTimeFilter {
    final static public String ALL_TIME_NAME = "All Time";

	public AllTime() {
		super(ALL_TIME_NAME);
		setAllFilter(true);
		setPriority(1000000);
	}
	
	public void buildName() {
		setName(ALL_TIME_NAME);
	}
	
	@Override
	public void limitFilter(FilterNode filterNode) {
		upFilter(filterNode);
	}
	
	@Override
	public void upFilter(FilterNode filterNode) {
		Log.log("AllTime name=" + getName());
		filterNode.setTimeFilter(null);
	}


}
