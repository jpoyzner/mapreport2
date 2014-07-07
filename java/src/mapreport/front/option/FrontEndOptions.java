package mapreport.front.option;

import mapreport.filter.loc.LocationByCoords;

public class FrontEndOptions extends Options {
	public UserOptions getUserOptions() {
		return userOptions;
	}
	public void setUserOptions(UserOptions userOptions) {
		this.userOptions = userOptions;
	}
	public int getScreenSize() {
		return screenSize;
	}
	public void setScreenSize(int screenSize) {
		this.screenSize = screenSize;
	}
	public LocationByCoords getUserLocation() {
		return userLocation;
	}
	public void setUserLocation(LocationByCoords userLocation) {
		this.userLocation = userLocation;
	}
	UserOptions userOptions;
	int screenSize = 0;
	LocationByCoords userLocation;
}
