package mapreport.news;

import mapreport.front.page.FilterNode;
import mapreport.front.page.PageMetaData;

public class NewsPresentation extends News {

	PageMetaData locationColumn;
	PageMetaData topicColumn;
	PageMetaData timeColumn;
	
	String mapLabel = "";
	
	public String getMapLabel() {
		return mapLabel;
	}


	public void setMapLabel(String mapLabel) {
		this.mapLabel = mapLabel;
	}

	public NewsPresentation (News news, FilterNode filters, String mapLabel) {
		this(news, filters);
		this.mapLabel = mapLabel;
	}
	
	public NewsPresentation (News news, FilterNode filters) {
		label = news.getLabel();
		url = news.getUrl();
		video = news.getVideo();
		image = news.getImage();
		shortLabel = news.getShortLabel();
		description = news.getDescription();  
		newsText = news.getNewsText();
		dateTime = news.getDateTime();
		id = news.getId();
		priority = news.getPriority();
		address = news.getAddress();
		x = news.getX();
		y = news.getY();
		location = news.getLocation();
		rootTopic = news.getRootTopic();
	}

	
	// String json = gson.toJson(obj); "data1":100,"data2":"hello","list":["String 1","String 2","String 3"]
	//  "DataObject [data1=" + data1 + ", data2=" + data2 + ", list=" + list + "]";
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("News [");
		sb.append("label=");
		sb.append(label);
		sb.append(", url=");
		sb.append(url);
		sb.append(", video=");
		sb.append(video);
		sb.append(", image=");
		sb.append(image);
		sb.append(", description=");
		sb.append(description);
		sb.append(", id=");
		sb.append(id);
		sb.append(", dateTime=");
		sb.append(dateTime);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", address=");
		sb.append(address);
		sb.append(", x=");
		sb.append(x);
		sb.append(", y=");
		sb.append(y);
		sb.append(", location=");
		sb.append(location);
		sb.append(", mapLabel=");
		sb.append(mapLabel);
		sb.append(", rootTopic=");
		sb.append(rootTopic);
		sb.append("]");
		return sb.toString();
	}
}
