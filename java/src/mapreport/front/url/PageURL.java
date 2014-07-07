package mapreport.front.url;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import mapreport.filter.DBFilter;
import mapreport.filter.Filter;
import mapreport.filter.NameFilter;
import mapreport.filter.time.OfficialTimeFilter;
import mapreport.front.option.Options;
import mapreport.front.param.RequestParameter;
import mapreport.util.Log;

public class PageURL {


	Set<NameFilter> filters= new HashSet<NameFilter>(3);	
	URL url;	
	Map<String, RequestParameter> paramMap;
	
	Options options;
	public Options getOptions() {
		return options;
	}

	public void setOptions(Options options) {
		this.options = options;
	}

	public Set<NameFilter> getFilters() {
		return filters;
	}

	public void setFilters(Set<NameFilter> filters) {
		this.filters = filters;
	}
	
	public void parseParams() throws UnsupportedEncodingException {
        String path = url.getPath();
             System.out.println("buildParams path = " + path);
        String[] pathparts = path.split("/");
        
        for (int i = 0; i < pathparts.length; i++) {
            String partPath =  pathparts[i];   
        		System.out.println("PageUrl parseParams() partPath = " + partPath);     
            if (!partPath.trim().isEmpty() && !partPath.equals("news")) {	
            	OfficialTimeFilter timeFilter = OfficialTimeFilter.parseDateStr(partPath);            	
            	if (timeFilter != null) {
            		       Log.log("buildParams timeFilter != null timeFilter:" + timeFilter);
		        	filters.add(timeFilter);
            	} else  {
		        	DBFilter dbFilter = new DBFilter(partPath);
	                     Log.log("buildParams timeFilter == null dbFilter:" + dbFilter);
		        	filters.add(dbFilter);
            	}
            }
        }
        String query = url.getQuery();
        parseUrlParameters(query);
	}
	
	public void buildUrlParameters() throws MalformedURLException {
		StringBuilder url = new StringBuilder("");
		for (Filter filter : filters) {			
				Log.log("buildUrlParameters filter=" + filter);
			if (filter instanceof NameFilter) {
				NameFilter idFilter = (NameFilter)filter;
				url.append(idFilter.getName().replace(' ', '-'));
				url.append('/');
			}
		}
		this.url = new URL(url.toString());
	}
	
	public void parseUrlParameters(String url)
	        throws UnsupportedEncodingException {
	   // Map<String, String> params = new HashMap<String, String>();
	    String[] urlParts = url.split("\\?");
	                System.out.println("PageURL buildUrlParameters urlParts.length:" + urlParts.length);
	    if (urlParts.length > 1) {
	        String query = urlParts[1];
	        for (String param : query.split("&")) {
	            String pair[] = param.split("=");
	            String key = URLDecoder.decode(pair[0], "UTF-8");
	            String value = "";
	            if (pair.length > 1) {
	                value = URLDecoder.decode(pair[1], "UTF-8");
	            } 
	                     System.out.println("PageURL buildUrlParameters key:" + key + " value:" + value);
	            options.addParam(key, value);
	        }
	    }
	  //  return params;
	}
	
	public PageURL(String url) throws MalformedURLException {
		this.url = new URL(url);
		options = new Options();
	}
	
	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public Map<String, RequestParameter> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, RequestParameter> paramMap) {
		this.paramMap = paramMap;
	}

	
    public static void main(String[] args) throws Exception {

        URL aURL = new URL("http://example.com:80/docs/books/tutorial"
                           + "/index.html?name=networking#DOWNLOADING");

        System.out.println("protocol = " + aURL.getProtocol());
        System.out.println("authority = " + aURL.getAuthority());
        System.out.println("host = " + aURL.getHost());
        System.out.println("port = " + aURL.getPort());
        System.out.println("path = " + aURL.getPath());
        System.out.println("query = " + aURL.getQuery());
        System.out.println("filename = " + aURL.getFile());
        System.out.println("ref = " + aURL.getRef());
        /*
         * protocol = http
authority = example.com:80
host = example.com
port = 80
path = /docs/books/tutorial/index.html
query = name=networking
filename = /docs/books/tutorial/index.html?name=networking
ref = DOWNLOADING
         */
    }
}
