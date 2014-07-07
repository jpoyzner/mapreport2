package mapreport.nav;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mapreport.filter.NameFilter;
import mapreport.front.page.FilterNode;
import mapreport.util.Log;


public class NavigationPath  {
	List<NavigationNode> nodeList = new ArrayList<NavigationNode>(5);
	
	public NavigationPath(FilterNode filterNode, Map<String, NameFilter> idFilters) {		
		for (String filterName : idFilters.keySet()) {
			NameFilter filter = idFilters.get(filterName);
                      Log.log("NavigationPath filter=" + filter + " filterName=" + filterName  + " filter.getName()=" + filter.getName() );
			NavigationNode navNode = new NavigationNode(filterNode, filter);
                       Log.log("NavigationPath navNode=" + navNode + " navNode.pageFilters=" + navNode.pageFilters);
			addNode (navNode);
		}
	}
	
	public void addNode (NavigationNode e) {
		//nodeList = NavigationControlUtil.buildPath(pageFilters, null);
		nodeList.add(e);
	}
}
