package mapreport.filter.loc;

import java.util.Iterator;
import java.util.Map.Entry;

import mapreport.filter.DBFilter;
import mapreport.filter.NameFilter;
import mapreport.front.page.FilterNode;
import mapreport.util.Log;

public class LocationByName extends DBFilter  implements Location {

	public LocationByName(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unused")
	@Override
	public void limitFilter(FilterNode filterNode) { 
		        	Log.log("LocationByName limitFilter getName()=" + getName() + " filterNode.getLocationFilter()=" + filterNode.getLocationFilter());
        if (filterNode.getLocationFilter() != null) {
        	Log.log("LocationByName limitFilter this.getParents().size()=" + this.getParents().size()); 
        	
        	Iterator<Entry<String, NameFilter>> iter = getParents().entrySet().iterator();
        	while (iter.hasNext()) {
        	    Entry<String, NameFilter> entry = iter.next();
        	}
        	
        	Log.log("LocationByName limitFilter filterNode.getLocationFilter().getName()=" + filterNode.getLocationFilter().getName());
        	Log.log("LocationByName limitFilter this.getParents().get(filterNode.getLocationFilter().getName())=" + this.getParents().get(filterNode.getLocationFilter().getName()));
		}
        
		
		if (filterNode.getLocationFilter() == null || this.getParents().get(filterNode.getLocationFilter().getName()) != null) {
			filterNode.getFilterList().remove(filterNode.getLocationFilter());
			filterNode.setLocationFilter(this);
			filterNode.getFilterList().add(this);
				 Log.log("LocationByName limitFilter LIMITED  filterNode.getLocationFilter()=" + filterNode.getLocationFilter() + 
						 "  getName()=" + filterNode.getLocationFilter().getName() + "  getLink()=" + filterNode.getLocationFilter().getLink());
		}
		
	}
	
	@Override
	public void upFilter(FilterNode filterNode) {
		if (filterNode.getLocationFilter() == null || filterNode.getLocationFilter().getParents().containsKey(getName())) {
				filterNode.setLocationFilter(this);
		}
	}
}
