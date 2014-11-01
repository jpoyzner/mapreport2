package mapreport.controller;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import mapreport.db.DBQueryBuilder;
import mapreport.filter.DBFilter;
import mapreport.filter.NameFilter;
import mapreport.util.Log;
import mapreport.view.map.Rectangle;

public class Endpoints {
	public static final String news(HttpServletRequest request)  throws MalformedURLException, UnsupportedEncodingException{
    	Log.log("Endpoints news");
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
		
		return Controller.buildJson(getFullURL(request)).toString();
		//return DBQueryBuilder.buildJson(rectangle, nameFilters, 100).toString();

	}
	
	public static final String api(HttpServletRequest request) throws MalformedURLException, UnsupportedEncodingException {	
    	Log.log("api(HttpServletRequest request)");
		return Controller.buildJson(getFullURL(request)).toString();
	}
	
	private static String getFullURL(HttpServletRequest request) {
	    StringBuffer requestURL = request.getRequestURL();
	    String queryString = request.getQueryString();

	    return queryString == null ? requestURL.toString() : requestURL.append('?').append(queryString).toString();
	}
}
