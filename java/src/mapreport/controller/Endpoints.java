package mapreport.controller;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import mapreport.filter.DBFilter;
import mapreport.filter.NameFilter;
import mapreport.filter.loc.Global;
import mapreport.filter.loc.LocationByName;
import mapreport.filter.time.OfficialTimeFilter;
import mapreport.filter.topic.Topic;
import mapreport.resp.ResponseBuilder;
import mapreport.util.Log;
import mapreport.view.map.Rectangle;

public class Endpoints {
	public static final String news(HttpServletRequest request) throws Exception {
    	Log.info("Endpoints news");
		
    	Rectangle rectangle = null;
		String left = request.getParameter("left");
		String right = request.getParameter("right");
		String top = request.getParameter("top");
		String bottom = request.getParameter("bottom");		

		String location = request.getParameter("location");
		
		if (location == null && left != null && right != null && top != null && bottom != null) {
			rectangle =
				new Rectangle(
					new Rectangle.Bounds(
						Double.valueOf(left),
						Double.valueOf(right),
						Double.valueOf(top),
						Double.valueOf(bottom)));
		}
		
		Set<NameFilter> nameFilters = new HashSet<NameFilter>();
		
		String topic = request.getParameter("topic");
		if (topic != null) {
			topic = URLDecoder.decode(topic, "UTF-8");
			nameFilters.add(new Topic(topic));
			Log.info("Endpoints topic added:" + topic);
		}
		
		if (location != null && !location.equals(Global.GLOBAL)) {
			location = URLDecoder.decode(location, "UTF-8");
			nameFilters.add(new LocationByName(location));
			Log.info("Endpoints Location added:" + location);
		}
		
		int dateFilterCnt = 0;
		
		String date = request.getParameter("date");
		if (date != null) {
			date = URLDecoder.decode(date, "UTF-8");
			nameFilters.add(OfficialTimeFilter.parseDateStr(date, nameFilters.size()));
			dateFilterCnt++;
			Log.info("Endpoints date added:" + date);
		} 
		Log.info("Endpoints news topic;" + topic + " location:" + location + " date:" + date + " left:" + left + " right:" + right + " top:" + top + " bottom:" + bottom);
		return ResponseBuilder.buildJson(rectangle, nameFilters, dateFilterCnt, 500).toString();
	}
	
	//TODO: extract all params here and pass into functional classes
	public static final String api(HttpServletRequest request) throws MalformedURLException, UnsupportedEncodingException {	
    	Log.log("api(HttpServletRequest request)");
		return ResponseBuilder.buildJson(getFullURL(request)).toString();
	}
	
	//TODO: remove
	private static String getFullURL(HttpServletRequest request) {
	    StringBuffer requestURL = request.getRequestURL();
	    String queryString = request.getQueryString();

	    return queryString == null ? requestURL.toString() : requestURL.append('?').append(queryString).toString();
	}
}
