package mapreport.filter.time;

import java.util.GregorianCalendar;

public class Future extends OfficialTimeFilter {
    final static public String FUTURE_NAME = "Coming events";
    
	public Future() {
		super(FUTURE_NAME);
		begin = new GregorianCalendar();
	//	begin.add(Calendar.DAY_OF_YEAR, 1);
		end = null;  // no need, just for clarification 	
		buildTimeSQL(); 
		// setPriority(2000000);
		setPriority(0);	
		buildPriority();
		setImage("icons/2/g.gif");
	}
	
	public void buildName() {
		setName(FUTURE_NAME);
	}
	


}
