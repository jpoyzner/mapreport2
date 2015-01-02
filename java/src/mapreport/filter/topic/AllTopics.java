package mapreport.filter.topic;

import mapreport.front.page.FilterNode;
import mapreport.util.Log;

public class AllTopics extends Topic {
    final static public String ALL_TOPICS = "All Topics";

	public AllTopics(String name) {
		super(name);
		setAllFilter(true);
		setPriority(1000000);
	}
		
	@Override
	public void limitFilter(FilterNode filterNode) {
		upFilter(filterNode);
	}

	
	@Override
	public void upFilter(FilterNode filterNode) {
		Log.log("AllTopics name=" + getName());
		filterNode.setTopicFilter(null);
		filterNode.setTopicFilter2(null);
	}

}
