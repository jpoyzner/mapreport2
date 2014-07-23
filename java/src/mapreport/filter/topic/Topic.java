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
		          Log.log("Topic limitFilter filterNode.getTopicFilter()=" + filterNode.getTopicFilter());

		if (filterNode.getTopicFilter() == null || this.getParents().get(filterNode.getTopicFilter().getName()) != null) {
					Log.log("Topic limitFilter setTopicFilter");
			updateFilterNode(filterNode);
		}
	}		
	
	@Override
	protected void updateFilterNode(FilterNode filterNode) {
		Log.log("Topic updateFilterNode bef filterNode=" + filterNode);
		// filterNode.getFilterList().remove(filterNode.getTopicFilter());
		
		for (Filter filter : filterNode.getFilterList()) {
			if (filter instanceof NameFilter) {
				NameFilter nameFilter = (NameFilter)filter;
				if (getName().equals(nameFilter.getName())) {
					filterNode.getFilterList().remove(filter);
					break;
				}
			}
		}
		filterNode.setTopicFilter(this);
		filterNode.getFilterList().add(this);
		Log.log("Topic updateFilterNode aft filterNode=" + filterNode);
	}


}
