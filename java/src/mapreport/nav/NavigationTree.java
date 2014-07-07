package mapreport.nav;

import java.util.List;
import java.util.Map;

import mapreport.filter.NameFilter;
import mapreport.front.page.FilterNode;
import mapreport.tree.Tree;

public class NavigationTree extends Tree {
	@SuppressWarnings("unused")
	public NavigationTree (FilterNode pageFilters, Map<String, NameFilter> idFilters) {
		List<NavigationNode> path = NavigationControlUtil.buildPath(pageFilters, idFilters);
	}
}
