package mapreport.filter.time;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Future extends OfficialTimeFilter {
    final static public String FUTURE_NAME = "Future";
    
	public Future() {
		super(FUTURE_NAME);
		begin = new GregorianCalendar();
		end = null;  // no need, just for clarification 	
		buildTimeSQL(); 
	}
	
	public void buildName() {
		setName(FUTURE_NAME);
	}
	


}
