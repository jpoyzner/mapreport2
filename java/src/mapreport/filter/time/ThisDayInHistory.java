package mapreport.filter.time;

import mapreport.util.Log;

public class ThisDayInHistory extends AllTime {

    final static public String THIS_DAY_IN_HISTORY_NAME = "This Day In History";
    
	public ThisDayInHistory() {
		super();
		setName(THIS_DAY_IN_HISTORY_NAME);
		setImage("common/thisdayinhistory.jpg");
	}	
	
	public void buildName() {
		setName(THIS_DAY_IN_HISTORY_NAME);
	}
	
	public void buildTimeSQL() {
		whereSQL = new StringBuilder(" \n ");
		
		if (orderBySQL.length() == 0) {
			orderBySQL = new StringBuilder("\n order by n.priority, n.dateTime ");
		}

		whereSQL.append(" and MONTH(n.dateTime) = MONTH(CURDATE()) and DAY(n.dateTime) = DAY(CURDATE()) ");

		Log.log("ThisDayInHistory buildTimeSQL begin=" + begin + " end=" + end + "\n whereSQL=" + whereSQL.toString().trim());
		setWhereSQL(whereSQL); 
		setOrderBySQL(orderBySQL);
	}
	
	@Override
	public String getLink() {
		Log.log("ThisDayInHistory getLink()");
		return THIS_DAY_IN_HISTORY_NAME;
	}

}
