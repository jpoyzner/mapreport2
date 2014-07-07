package mapreport.filter.topic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import mapreport.filter.DBFilter;
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
		          Log.log("LocationByName limitFilter filterNode.getTopicFilter()=" + filterNode.getTopicFilter());

		if (filterNode.getTopicFilter() == null || this.getParents().get(filterNode.getTopicFilter().getName()) != null) {
					Log.log("LocationByName limitFilter setTopicFilter");
				filterNode.getFilterList().remove(filterNode.getTopicFilter());
				filterNode.setTopicFilter(this);
				filterNode.getFilterList().add(this);
		}
	}
		
	@Override
	public void upFilter(FilterNode filterNode) {
		if (filterNode.getTopicFilter() == null || filterNode.getTopicFilter().getParents().containsKey(getName())) {
				filterNode.setTopicFilter(this);
		}
	}


}
