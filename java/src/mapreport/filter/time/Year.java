package mapreport.filter.time;

import java.util.GregorianCalendar;

import mapreport.db.NewsFilterRow;
import mapreport.util.Log;

public class Year extends OfficialTimeFilter {

	public Year(String name) {
		super(name);
	}
	
	public Year(int year) {
		this(String.valueOf(year));		
		this.year = year;
		begin = new GregorianCalendar(year, 1, 1);
		end = new GregorianCalendar(year, 11, 31);
		setName(String.valueOf(year));
			Log.log("Year year=" + year + " name=" + getName());
	}
	

	@Override
	public void addParent(NewsFilterRow parent) {
			Log.log("Year  addParent  name=" + getName() + "parent.getParentId()=" + parent.getParentId() + "  parent.getFilterId()=" + parent.getFilterId());
	//	getParents().put(parent.getParentId(), new Decade(parent.getParentId(), parent.getLevel()));
	//	getParentList().add(new Decade(parent.getParentId(), parent.getLevel()));
	}

}
