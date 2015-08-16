package mapreport.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import mapreport.filter.NameFilter;
import mapreport.filter.loc.Global;
import mapreport.filter.loc.Local;
import mapreport.filter.loc.LocationByName;
import mapreport.filter.time.OfficialTimeFilter;
import mapreport.filter.time.TimeFilter;
import mapreport.filter.topic.AllTopics;
import mapreport.filter.topic.Topic;
import mapreport.front.option.Options;
import mapreport.resp.ResponseBuilder;
import mapreport.util.Log;
import mapreport.view.map.Rectangle;

public class Endpoints {
	/*  code for the future 
    static  
    {    	 
        java.util.Properties properties = new java.util.Properties();
        FileInputStream fis = null;
   
        try {
        	  fis = new FileInputStream("../properties.txt");
              properties.load(fis);
              dbHost = properties.getProperty("db-host"); 
              // db-host=localhost  or mapreportdb.cd9pgtzoc8c0.us-west-1.rds.amazonaws.com
              
              Log.info("Endpoints dbHost:" + dbHost);
            } catch (IOException e) {
            	Log.info("Trouble to read properties file:" + fis);
            	e.printStackTrace();
            }
    }
    */
    
	public static final String news(HttpServletRequest request) throws Exception {
    	Log.info("Endpoints news");
		
    	Log.info("Endpoints java.class.path:" + System.getProperty("java.class.path"));  
    	Log.info("Endpoints news 	System.getenv().toString:" + 	System.getenv().toString());
    	Log.info("Endpoints news 	System.getenv().get(DBHOST):" + 	System.getenv().get("DBHOST"));    	
    
    	Rectangle rectangle = null;
    	
    	String paramStr = buildParamStr(request);
    	Log.info("ParameterMap:" + paramStr);
    	
    	String jsonCache = null; //Cache.retrieveFromCache(paramStr);
    	
    	if (jsonCache != null) {
    		Log.info("jsonCache found");
    		return jsonCache;
    	} else {
    		Log.info("jsonCache not found:" + paramStr);
    	}
    	
		String left = request.getParameter("left");
		String right = request.getParameter("right");
		String top = request.getParameter("top");
		String bottom = request.getParameter("bottom");		

		String localLong = request.getParameter("local-long");
		String localLat = request.getParameter("local-lat");
		
		// &local-long=-122.00&local-lat=37.519774999999936
		// localLong = "-115.00"; // bay area: -122.0
		// localLat = "37.519774999999936";
		
		Log.info("Endpoints topic localLong:" + localLong + " localLat:" + localLat);
		
		Enumeration<String> params = request.getParameterNames();
		
		while(params.hasMoreElements()){
			String param = (String) params.nextElement();
			Log.info("Endpoints param:" + param);
		}
		
		String location = request.getParameter("location");
		
		if ((location == null) 
				&& left != null && right != null && top != null && bottom != null) {
			rectangle =
				new Rectangle(
					new Rectangle.Bounds(
						Double.valueOf(left),
						Double.valueOf(right),
						Double.valueOf(top),
						Double.valueOf(bottom)));
		}	
		
		if (location != null && location.equals(Local.LOCAL_NAME)) {
			try {
				Local local = new Local(localLong, localLat);
				Log.info("Endpoints local:" + local);
				rectangle =	local.getRect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		Set<NameFilter> nameFilters = new HashSet<NameFilter>();			
		
		String topic = request.getParameter("topic");
		      Log.log("Endpoints topic to add:" + topic);
		if (topic != null) {	
			topic = URLDecoder.decode(topic, "UTF-8");

			if (!topic.equals(AllTopics.ALL_TOPICS)) {		
				int delimeterIndex = topic.indexOf(';');
				if (delimeterIndex > 1) {
					nameFilters.add(new Topic(topic.substring(0, delimeterIndex)));
					Log.info("Endpoints topic added:" + topic.substring(0, delimeterIndex));
					nameFilters.add(new Topic(topic.substring(delimeterIndex + 1)));
					Log.info("Endpoints topic added:" + topic.substring(delimeterIndex + 1));
				} else {
					nameFilters.add(new Topic(topic));
					Log.info("Endpoints topic added:" + topic + " ALL_TOPICS=" + AllTopics.ALL_TOPICS + " !topic.equals(AllTopics.ALL_TOPICS)=" + !topic.equals(AllTopics.ALL_TOPICS));
				}
			}
		}
		
		if (location != null && !location.equals(Global.GLOBAL) && !location.equals(Local.LOCAL_NAME)) {
			location = URLDecoder.decode(location, "UTF-8");
			nameFilters.add(new LocationByName(location));
			Log.info("Endpoints Location added:" + location);
		}
		
		int dateFilterCnt = 0;
		
		String dates[] = request.getParameterValues("date"); // for test {"May-11-2008", "May-11-2009"}; 
		String date = request.getParameter("date");
		
		if (dates != null && dates.length > 1) {
			Log.info("Endpoints dates:" + dates.toString() + " dates.length:" + dates.length);
			try {
				dateFilterCnt = TimeFilter.add2Dates(nameFilters, dates[0], dates[1], dateFilterCnt);
			} catch (Exception e) {
				Log.info("Endpoints problem to add 2 dates dates[0]:" + dates[0] + " dates[1]:" + dates[1] + "\n e:" + e.toString());
				e.printStackTrace();
			}
		} else {
			dateFilterCnt = TimeFilter.addDate(nameFilters, date, dateFilterCnt);
		}
		
		Log.info("Endpoints news topic;" + topic + " location:" + location + " date:" + date + " dates:" + dates + " left:" + left + " right:" + right + " top:" + top + " bottom:" + bottom);
		
		Options options = ResponseBuilder.buildOptionsFromRequest(request);
		String json = ResponseBuilder.buildJson(rectangle, nameFilters, dateFilterCnt, 500, localLong, localLat, options).toString();
		
		// Cache.putInCache(paramStr, json);

		Log.info("jsonCache Cache.putInCache:" + paramStr);
		return json;
	}

	public static String buildParamStr(HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();
    	
    	Set<String> keys = parameterMap.keySet();
    	List<String> keyList = new ArrayList<String>(keys);
    	java.util.Collections.sort(keyList);
    	
    	StringBuilder params = new StringBuilder();
    	
    	for (String key : keyList) {
       	    params.append(key);
    	    params.append('=').append('"');
    	    params.append(Arrays.toString(parameterMap.get(key)));
    	    params.append('"');
    	        params.append(',').append(' ');
    	}   
    	
    	String paramStr = params.toString();
		return paramStr;
	}
	
	//TODO: extract all params here and pass into functional classes
	public static final String api(HttpServletRequest request) throws MalformedURLException, UnsupportedEncodingException {	
    	Log.log("api(HttpServletRequest request)");
		return ResponseBuilder.buildJson(getFullURL(request)).toString();
	}
	
	public static String getFullURL(HttpServletRequest request) {
	    StringBuffer requestURL = request.getRequestURL();
	    String queryString = request.getQueryString();

	    return queryString == null ? requestURL.toString() : requestURL.append('?').append(queryString).toString();
	}
}
