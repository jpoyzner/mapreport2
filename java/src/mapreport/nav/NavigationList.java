package mapreport.nav;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;

import mapreport.db.NewsFilterRow;
import mapreport.filter.NameFilter;
import mapreport.front.page.FilterNode;
import mapreport.util.Log;

public class NavigationList {
	@Expose String name = null;
	List<NavigationNode> nodeList = new ArrayList<NavigationNode>(5);

	@Expose List<NavigationNode> parents = new ArrayList<NavigationNode>(5);
	@Expose List<NavigationNode> siblings = new ArrayList<NavigationNode>(5);
	@Expose List<NavigationNode> children = new ArrayList<NavigationNode>(25);
	private Map<String, NavigationNode> childrenMap = new HashMap<String, NavigationNode>(25);
	
	public Map<String, NavigationNode> getChildrenMap() {
		return childrenMap;
	}

	public void setChildrenMap(Map<String, NavigationNode> childrenMap) {
		this.childrenMap = childrenMap;
	}

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
	
	public void addChildFilter (NameFilter filter, FilterNode filterNode) {
			Log.log("NavigationList addChildFilter filter=" + filter + " filtergetName3=" + filter.getName());
			
	    if (childrenMap.get(filter.getName()) == null) {	
			NavigationNode navNode = new NavigationNode(filterNode, filter);
			    Log.log("NavigationList addChildFilter filter=" + filter + " filtergetName=" + filter.getName() // + " filtergetName=" + filter.getId() 
			    		+ " navNode.metaData.getHeader()=" + navNode.metaData.getHeader());
			children.add(navNode);
			childrenMap.put(filter.getName(), navNode);
	    }
	}
	
	public void sort() {
		Collections.sort(children);
		for (NavigationNode node : children) {
			// node.getPageFilters().
			Log.log("sort header=" + node.metaData.getHeader() + " priority=" + node.priority);
		}
	}
	
	public void limitChildren() {		
		if (children.size() > 20) {
			children = children.subList(0, 20);
		}
	}
	
	public void addParentFilter (NameFilter filter, FilterNode filterNode) {
		NavigationNode navNode = new NavigationNode(filterNode, filter, nodeType.PARENT);
		parents.add(navNode);
	}
	
	public void addSiblingFilter (NameFilter filter, FilterNode filterNode) {
		NavigationNode navNode = new NavigationNode(filterNode, filter);
		siblings.add(navNode);
	}
}
