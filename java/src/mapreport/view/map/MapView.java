package mapreport.view.map;

import java.util.List;

import mapreport.view.View;
import mapreport.view.list.NewsList;

public class MapView extends View {
	Coordinates coords;
	Rectangle rect;
	MapNewsList newsList;
	String mapUrl;
	List<MapZoomLink> mapZoomLinks;
	
	public MapView(Coordinates coords, Rectangle rect, NewsList newsList, String mapUrl, List<MapZoomLink> mapZoomLinks) {
		super(newsList) ;
	}
}
