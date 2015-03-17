package mapreport.filter.loc;

import mapreport.view.map.Rectangle;

public class Local extends LocationByCoords {
    final static public String LOCAL_NAME = "Local";

	public Local(Rectangle rect) {
		super(rect);
		setName(LOCAL_NAME);
		setAllFilter(true);
		setPriority(1000000);
	}

}
