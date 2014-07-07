package mapreport.nav;

import java.util.ArrayList;
import java.util.List;

import mapreport.filter.NameFilter;
import mapreport.front.page.FilterNode;

public class NavigationList {
	String name = null;
	List<NavigationNode> nodeList = new ArrayList<NavigationNode>(5);
	
	public NavigationList(String name) {
		this.name = name;
	}
	
	public void addNode (NavigationNode node) {
		nodeList.add(node);
	}
	
	public void addFilter (NameFilter filter, FilterNode filterNode) {
		NavigationNode navNode = new NavigationNode(filterNode, filter);
		nodeList.add(navNode);
	}
}
