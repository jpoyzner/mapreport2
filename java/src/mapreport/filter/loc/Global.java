package mapreport.filter.loc;

import mapreport.filter.Filter;
import mapreport.front.page.FilterNode;
import mapreport.util.Log;

public class Global extends OfficialLocation {

	public Global(String name) {
		super(name);
	}
	
	@Override
	public void upFilter(FilterNode filterNode) {
		Log.log("Global name=" + getName());
		filterNode.setLocationFilter(null);
		filterNode.setCoordFilter(null);
		filterNode.setLocationFilter2(null);
		
		Log.log("Global upFilter bef filterNode=" + filterNode);
		
		for (Filter filter : filterNode.getFilterList()) {
			if (filter instanceof LocationByName) {
					filterNode.getFilterList().remove(filter);
					break;
			}
		}

		Log.log("Global upFilter aft filterNode=" + filterNode);
	}

}
