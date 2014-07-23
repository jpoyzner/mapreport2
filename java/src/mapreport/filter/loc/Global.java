package mapreport.filter.loc;

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
	}

}
