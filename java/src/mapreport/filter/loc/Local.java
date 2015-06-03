package mapreport.filter.loc;

import mapreport.util.Log;
import mapreport.view.map.Rectangle;

public class Local extends LocationByCoords {
    final static public String LOCAL_NAME = "Local";
    final static double WORLD_SPAN = 4;
    final static double BAY_AREA_SPAN = 0.07;
 
	public Local(String longitude, String latitude) {
		super(buildRectangle(longitude, latitude));
		init();
	}

	public Local(Rectangle rect) {
		super(rect);
		init();
	}

	public static Rectangle buildRectangle(String longitude, String latitude) {
		Log.info("Local buildRectangle longitude:" + longitude + " or latitude:" + latitude);
		Rectangle rect = new Rectangle(0, 0, 0, 0);
		
		if (longitude != null && !longitude.isEmpty() && latitude != null && !latitude.isEmpty()) {
			double longitudeNm = 0;
			double latitudeNm = 0;
			
			try {
				longitudeNm = Double.valueOf(longitude);
				latitudeNm = Double.valueOf(latitude);	
				boolean isBayArea = longitudeNm > -122.7 && longitudeNm < -121.5 && latitudeNm > 37.2 && latitudeNm < 37.9;
				double span = isBayArea ? BAY_AREA_SPAN : WORLD_SPAN;	
				rect = new Rectangle(longitudeNm, latitudeNm, span, span);
			} catch (NumberFormatException e) {
				Log.info("Problem in local longitude:" + longitude + " or latitude:" + latitude);
				e.printStackTrace();
			}
		}
		
		return rect;

	}
	
	public void init() {
		setName(LOCAL_NAME);
		setAllFilter(true);
		setPriority(1000000);
	}

}
