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
		Set<NameFilter> nameFilters = new HashSet<NameFilter>(3);
		//nameFilters.add(OfficialTimeFilter.parseDateStr("2011"));
		nameFilters.add(new DBFilter("San Jose"));
		
//		Rectangle rectangle = null;
//		String latitude = request.getParameter("latitude");
//		String longitude = request.getParameter("longitude");
//		if (latitude != null && longitude != null) {
//			rectangle = Rectangle(longitude, latitude);
//		}
//		
//		if (request.getParameter(arg0)
		
		return DBQueryBuilder.buildJson(null, nameFilters, 100).toString();
	}
}
