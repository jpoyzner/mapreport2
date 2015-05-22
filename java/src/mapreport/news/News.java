package mapreport.news;

import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;

import mapreport.filter.loc.LocationByName;
import mapreport.util.Log;

public class News implements Comparable{
	
	@Expose String label = null;
	@Expose String url = null;
	@Expose String video = null;
	@Expose String icon = null;
	@Expose String shortLabel = null;  
	@Expose String description = null;
	@Expose String newsText = null;
	@Expose Date dateTime = null;
	int id = 0;
	@Expose int priority = 0;
	String address;
	String location;
	double x = 0;
	double y = 0;
	int newsId = 0;
	int newsFilterPriority = 0;
	String topicExcludeId = null;	
	
	boolean isPrimary = false;
	boolean isLocation = false;
	@Expose boolean isMapShow = false;
	@Expose String rootTopic = null;
	
	public boolean isPrimary() {
		return isPrimary;
	}

	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public boolean isLocation() {
		return isLocation;
	}

	public void setLocation(boolean isLocation) {
		this.isLocation = isLocation;
	}

	public boolean isMapShow() {
		return isMapShow;
	}

	public void setMapShow(boolean isMapShow) {
		this.isMapShow = isMapShow;
	}


	
	public String getRootTopic() {
		return rootTopic;
	}

	public void setRootTopic(String rootTopic) {
		this.rootTopic = rootTopic;
	}

	public String getTopicExcludeId() {
		return topicExcludeId;
	}

	public void setTopicExcludeId(String topicExcludeId) {
		this.topicExcludeId = topicExcludeId;
	}

	public int getNewsFilterPriority() {
		return newsFilterPriority;
	}

	public void setNewsFilterPriority(int newsFilterPriority) {
		this.newsFilterPriority = newsFilterPriority;
	}

	public int getNewsId() {
		return newsId;
	}

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	
	LocationByName primaryLocation;
	List<LocationByName> locationList;
	List<LocationByName> topicList;
	
	public LocationByName getPrimaryLocation() {
		return primaryLocation;
	}

	public void setPrimaryLocation(LocationByName primaryLocation) {
		this.primaryLocation = primaryLocation;
		location = primaryLocation.getName();
		Log.log("setPrimaryLocation: " + location);
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		if (icon != null && !icon.isEmpty() && icon.indexOf("mapreport.com") == -1) {
			icon = icon.replaceAll("/3/", "/2/"); // TEMPORARY !!!!!!!
			this.icon = "http://www.mapreport.com/images/" + icon;
		} else {
			this.icon = icon;
		}
	}
	public String getShortLabel() {
		return shortLabel;
	}
	public void setShortLabel(String shortLabel) {
		this.shortLabel = shortLabel;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNewsText() {
		return newsText;
	}
	public void setNewsText(String newsText) {
		this.newsText = newsText;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public int compareTo(Object compNews) {
		int diff = priority - ((News)compNews).priority;
		return diff;
	}
}
