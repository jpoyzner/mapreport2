package mapreport.nav;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mapreport.filter.NameFilter;
import mapreport.front.page.FilterNode;

public class NavigationControlUtil  {
	
	public static List<NavigationNode> buildPath (FilterNode pageFilters, Map<String, NameFilter> idFilters) {
		List<NavigationNode> nodes = new ArrayList<NavigationNode>(idFilters.size());
		
	    Iterator<Entry<String, NameFilter>> it = idFilters.entrySet().iterator();
	    while (it.hasNext()) {
	        Entry<String, NameFilter> filterEntry = it.next();
	        System.out.println(filterEntry.getKey() + " = " + filterEntry.getValue());
	        NavigationNode node = new NavigationNode(pageFilters, filterEntry.getValue());
	        nodes.add(node);
	    }
		return nodes; 
	}
}
