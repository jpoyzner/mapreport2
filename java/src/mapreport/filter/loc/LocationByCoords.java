package mapreport.filter.loc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.annotations.Expose;

import mapreport.filter.Filter;
import mapreport.filter.NameFilter;
import mapreport.front.page.FilterNode;
import mapreport.util.Log;
import mapreport.view.map.Rectangle;

public class LocationByCoords extends NameFilter implements Location {
	@Expose Rectangle rect;
	final StringBuilder selectStringBuilder = new StringBuilder(" ");
	
	final StringBuilder whereStringBuilder = new StringBuilder(" \n  and ( \n " + 
            "n.addressX <> 0 and n.addressY <> 0 and n.addressX <> -1 and n.addressY <> -1  \n" + 
            "and n.addressY > ? and n.addressY < ? and (n.addressX > ? ");
	
	final StringBuilder whereStringBuilderEnd = new StringBuilder(" n.addressX < ?) and ? > n.span * 5 \n" + 
//	final StringBuilder whereStringBuilderEnd = new StringBuilder(" n.addressX < ?)  \n" + 

        "  \n ) ");
	
	public LocationByCoords (Rectangle rect) {
		super("lat=" + rect.getXCenter() + ", long=" + rect.getYCenter());
		this.setRect(rect);		
		setSelectSQL(selectStringBuilder);
		
		String orAnd = rect.getLeft() <= rect.getRight() ? " and " : " or ";
		StringBuilder where = whereStringBuilder.append(orAnd).append(whereStringBuilderEnd);
		Log.info ("LocationByCoords rect.getLeft() " + rect.getLeft() + " rect.getRight()=" + rect.getRight() + " orAnd=" + orAnd + " where=" + where);
		setWhereSQL(where); 
		setName("lat=" + rect.getXCenter() + ", long=" + rect.getYCenter());
	}
	
	@Override
	public String toString() {
		return "LocationByCoords: " + getRect().toString();
	}
	 
	@Override
	public int bindQuery(PreparedStatement pst, int col) throws SQLException {
		Log.log("LocationByCoords bindQuery " + getRect().toString());
				
				pst.setDouble(++col, getRect().getBottom() * 1000000);	
				pst.setDouble(++col, getRect().getTop() * 1000000);	
				pst.setDouble(++col, getRect().getLeft() * 1000000);	
				pst.setDouble(++col, getRect().getRight() * 1000000);
				pst.setDouble(++col, Math.abs(getRect().getRight() - getRect().getLeft()) * 1000000);
				
				return col;
	}

	@Override
	public void processResultSet(ResultSet resultSet) {
		// TODO Auto-generated method stub
		
	}
	
	public String getLink() {		
		return getRect().buildLink();
	}

	public StringBuilder getFromSQL() {
		return new StringBuilder(" ");
	}
	
	@Override
	public void limitFilter(FilterNode filterNode) { 
		        	Log.log("LocationByCoords limitFilter getName()=" + getName() + " filterNode.getLocationFilter()=" + filterNode.getLocationFilter());
      
		        	/*if (filterNode.getLocationFilter() != null) {
        	Log.log("LocationByName limitFilter this.getParents().size()=" + this.getParents().size()); 
        	
        	Iterator<Entry<String, NameFilter>> iter = getParents().entrySet().iterator();
        	while (iter.hasNext()) {
        	    Entry<String, NameFilter> entry = iter.next();
        	}
        	
        	Log.log("LocationByName limitFilter filterNode.getLocationFilter().getName()=" + filterNode.getLocationFilter().getName());
        	Log.log("LocationByName limitFilter this.getParents().get(filterNode.getLocationFilter().getName())=" + this.getParents().get(filterNode.getLocationFilter().getName()));
		}*/
        
		
//		if (filterNode.getLocationFilter() == null || this.getParents().get(filterNode.getLocationFilter().getName()) != null) {
			updateFilterNode(filterNode);
			 Log.log("LocationByCoords limitFilter LIMITED  filterNode.getLocationFilter()=" + filterNode.getLocationFilter());
			 
			 if (filterNode.getLocationFilter() != null) { 
				 Log.log("LocationByCoords limitFilter getName()=" + filterNode.getLocationFilter().getName() + "  getLink()=" + filterNode.getLocationFilter().getLink());
			 }
//		}
		
	}	
	
	protected void updateFilterNode(FilterNode filterNode) {
		filterNode.getFilterList().remove(filterNode.getLocationFilter());
		filterNode.getFilterList().remove(filterNode.getCoordFilter());
		filterNode.setCoordFilter(this);
		filterNode.getFilterList().add(this);
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}
}
