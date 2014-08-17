package mapreport.filter.topic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import mapreport.filter.DBFilter;
import mapreport.filter.Filter;
import mapreport.filter.NameFilter;
import mapreport.front.page.FilterNode;
import mapreport.util.Log;

public class Topic extends DBFilter{

	public Topic(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void bindQuery(PreparedStatement pst) {
		// pst.setString(++col, label);
		
	}

	@Override
	public void processResultSet(ResultSet resultSet) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void limitFilter(FilterNode filterNode) {  
		          Log.log("Topic limitFilter filterNode.getTopicFilter()=" + filterNode.getTopicFilter()
		        		  + "  filterNode=" + filterNode);

		if (filterNode.getTopicFilter() == null || this.getParents().get(filterNode.getTopicFilter().getName()) != null) {
					Log.log("Topic limitFilter setTopicFilter");
			updateFilterNode(filterNode);
		}
	}		
	
	@Override
	protected void updateFilterNode(FilterNode filterNode) {
		          Log.log("Topic updateFilterNode name=" + getName() + " bef filterNode=" + filterNode);
		
		for (Filter filter : filterNode.getFilterList()) {
			 Log.log("Topic updateFilterNode filterNode.getTopicFilter()=" + filterNode.getTopicFilter() + " filter=" + filter);
			 if  (filterNode.getTopicFilter() != null && filter != null) {
				 Log.log("Topic updateFilterNode filterNode.getTopicFilter().getName()=" + filterNode.getTopicFilter().getName() + " filter.getName()=" + filter.getName()); 
			 }
			if (filter instanceof Topic
					|| (filterNode.getTopicFilter() != null && filter != null && filterNode.getTopicFilter().getName().equals(filter.getName()))) {
						filterNode.getFilterList().remove(filter);
						Log.log("Topic updateFilterNode removed!");
						break;
			}
		}
			Log.log("Topic updateFilterNode this.name=" + this.getName() + " bef filterNode=" + filterNode);
		filterNode.setTopicFilter(this);
		filterNode.getFilterList().add(this);
        Log.log("Topic updateFilterNode aft filterNode.getTopicFilter()=" + filterNode.getTopicFilter().getName());
        Log.log("Topic updateFilterNode aft filterNode=" + filterNode);
        
		for (Filter filter : filterNode.getFilterList()) {
			if (filter != null) Log.log("Topic updateFilterNode aft filter=" + filter.getName() + " filter instanceof Topic=" + (filter instanceof Topic));
		}

	}


}
