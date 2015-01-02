package mapreport.filter.loc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.annotations.Expose;

import mapreport.filter.Filter;
import mapreport.util.Log;
import mapreport.view.map.Rectangle;

public class LocationByCoords extends Filter implements Location {
	@Expose Rectangle rect;
	final StringBuilder selectStringBuilder = new StringBuilder(" ");
	
	final StringBuilder whereStringBuilder = new StringBuilder(" \n and f.filterId = l.filterId  \n and ( \n (   \n" + 
            "n.addressX <> 0 and n.addressY <> 0 and n.addressX <> -1 and n.addressY <> -1  \n" + 
            "and n.addressY > ? and n.addressY < ? and n.addressX > ? and n.addressX < ?  \n" + 
        ")  \n" + 
                " or  \n" + 
        "(  \n" + 
                " f.isLocation = true and l.topCoord > ? and l.bottomCoord < ? and l.leftCoord > ? and l.rightCoord < ?  and topCoord <> 0 \n" + 
               " and l.topCoord <> 1000000000  \n" + 
        ") ) ");
	
	public LocationByCoords (Rectangle rect) {
		this.rect = rect;		
		setSelectSQL(selectStringBuilder);		
		setWhereSQL(whereStringBuilder); 
	}
	
	@Override
	public String toString() {
		return "LocationByCoords: " + rect.toString();
	}
	 
	@Override
	public int bindQuery(PreparedStatement pst, int col) throws SQLException {
		Log.log("LocationByCoords bindQuery " + rect.toString());
				
				pst.setDouble(++col, rect.getBottom() * 1000000);	
				pst.setDouble(++col, rect.getTop() * 1000000);	
				pst.setDouble(++col, rect.getLeft() * 1000000);	
				pst.setDouble(++col, rect.getRight() * 1000000);	

				pst.setDouble(++col, rect.getBottom() * 1000000);	
				pst.setDouble(++col, rect.getTop() * 1000000);		
				pst.setDouble(++col, rect.getLeft() * 1000000);	
				pst.setDouble(++col, rect.getRight() * 1000000);
				
				return col;
	}

	@Override
	public void processResultSet(ResultSet resultSet) {
		// TODO Auto-generated method stub
		
	}
	
	public String getLink() {		
		return rect.buildLink();
	}

	public StringBuilder getFromSQL() {
		return new StringBuilder(", location l ");
	}
	
}
