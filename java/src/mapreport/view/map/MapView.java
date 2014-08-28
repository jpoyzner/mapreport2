package mapreport.view.map;

import java.util.List;

import mapreport.news.NewsPresentation;
import mapreport.view.View;
import mapreport.view.list.NewsList;

public class MapView extends View {

	Rectangle rect;
	MapNewsList newsList;
	String mapUrl;
	List<MapZoomLink> mapZoomLinks;
	
	public MapView(MapNewsList newsList) {
		super(newsList) ;  
		
		double left = Integer.MAX_VALUE;
		double right = Integer.MIN_VALUE;
		double top = Integer.MAX_VALUE;
		double bottom = Integer.MIN_VALUE;
		
		for (NewsPresentation news : newsList.getNewses()) {
			if (news.getX() < left) {
				left = news.getX();
			}
			if (news.getX() > right) {
				right = news.getX();
			}
			if (news.getY() < top) {
				top = news.getX();
			}
			if (news.getY() > bottom) {
				bottom = news.getX();
			}
		}
		
		rect = new Rectangle((left + right) / 2, (top + bottom) / 2, right - left, top - bottom);
	}
	
	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public MapNewsList getNewsList() {
		return newsList;
	}

	public void setNewsList(MapNewsList newsList) {
		this.newsList = newsList;
	}

	public String getMapUrl() {
		return mapUrl;
	}

	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
	}

	public List<MapZoomLink> getMapZoomLinks() {
		return mapZoomLinks;
	}

	public void setMapZoomLinks(List<MapZoomLink> mapZoomLinks) {
		this.mapZoomLinks = mapZoomLinks;
	}

}
