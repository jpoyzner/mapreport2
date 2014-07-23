package mapreport.filter.topic;

import mapreport.front.page.FilterNode;
import mapreport.util.Log;

public class AllTopics extends Topic {

	public AllTopics(String name) {
		super(name);
	}
	
	@Override
	public void upFilter(FilterNode filterNode) {
		Log.log("AllTopics name=" + getName());
		filterNode.setTopicFilter(null);
		filterNode.setTopicFilter2(null);
	}

}
