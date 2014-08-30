package mapreport.controller;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import mapreport.db.DBQueryBuilder;
import mapreport.filter.DBFilter;
import mapreport.filter.NameFilter;
import mapreport.view.map.Rectangle;

public class Endpoints {
	public static final String news(HttpServletRequest request) {
		Rectangle rectangle = null;
		String left = request.getParameter("left");
		String right = request.getParameter("right");
		String top = request.getParameter("top");
		String bottom = request.getParameter("bottom");
		if (left != null && right != null && top != null && bottom != null) {
			rectangle =
				new Rectangle(
					new Rectangle.Bounds(
						Double.valueOf(left),
						Double.valueOf(right),
						Double.valueOf(top),
						Double.valueOf(bottom)));
		}
		
		Set<NameFilter> nameFilters = null;
		if (rectangle == null) {
			nameFilters = new HashSet<NameFilter>(1);
			nameFilters.add(new DBFilter("California"));
			//nameFilters.add(OfficialTimeFilter.parseDateStr("2011"));
		}
		
		return DBQueryBuilder.buildJson(rectangle, nameFilters, 100).toString();
	}
}
