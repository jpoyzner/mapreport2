package mapreport.news;

import java.net.URL;
import mapreport.filter.loc.Location;

public class NewsMapLink {	
	URL url;	
	Location locFilter;
	String addressStr;
	
	public URL getUrl() {
		return url;
	}
	public void setUrl(URL url) {
		this.url = url;
	}
	public Location getLocFilter() {
		return locFilter;
	}
	public void setLocFilter(Location locFilter) {
		this.locFilter = locFilter;
	}
	public String getAddressStr() {
		return addressStr;
	}
	public void setAddressStr(String addressStr) {
		this.addressStr = addressStr;
	}


}
