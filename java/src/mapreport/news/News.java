package mapreport.news;

import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;

import mapreport.filter.loc.LocationByName;

public class News {
	
	@Expose String label = null;
	@Expose String url = null;
	@Expose String video = null;
	@Expose String image = null;
	@Expose String shortLabel = null;
	@Expose String description = null;
	@Expose String newsText = null;
	@Expose Date dateTime = null;
	int id = 0;
	@Expose int priority = 0;
	String address;

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	LocationByName primaryLocation;
	List<LocationByName> locationList;
	List<LocationByName> topicList;
	
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
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
}
