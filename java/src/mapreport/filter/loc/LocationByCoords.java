package mapreport.filter.loc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.annotations.Expose;

import mapreport.filter.Filter;
import mapreport.view.map.Rectangle;

public class LocationByCoords extends Filter implements Location {
	@Expose Rectangle rect;
	final StringBuilder selectStringBuilder = new StringBuilder(" ");
	
	final StringBuilder whereStringBuilder = new StringBuilder(" \n and f.filterid = l.filterid  \n and ( \n (   \n" + 
            "n.addressX <> 0 and n.addressY <> 0 and n.addressX <> -1 and n.addressY <> -1  \n" + 
            "and n.addressY > ? and n.addressY < ? and n.addressX > ? and n.addressX < ?  \n" + 
        ")  \n" + 
                " or  \n" + 
        "(  \n" + 
                " l.topCoord > ? and l.bottomCoord < ? and l.leftCoord > ? and l.rightCoord < ?  and topCoord <> 0 \n" + 
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
	public void bindQuery(PreparedStatement pst) throws SQLException {
				System.out.println("bindQuery " + rect.toString());
			    int col = 0;
				pst.setDouble(++col, rect.getTop() * 1000000);	
				pst.setDouble(++col, rect.getBottom() * 1000000);	
				pst.setDouble(++col, rect.getLeft() * 1000000);	
				pst.setDouble(++col, rect.getRight() * 1000000);	

				pst.setDouble(++col, rect.getTop() * 1000000);	
				pst.setDouble(++col, rect.getBottom() * 1000000);		
				pst.setDouble(++col, rect.getLeft() * 1000000);	
				pst.setDouble(++col, rect.getRight() * 1000000);	
	}

	@Override
	public void processResultSet(ResultSet resultSet) {
		// TODO Auto-generated method stub
		
	}
/*
 * select l.topCoord , l.bottomCoord , l.leftCoord , l.rightCoord, l.isOfficial,
 *  abs(l.topCoord - l.bottomCoord) * abs(l.leftCoord - l.rightCoord) / 1000000000 as span 
 *  
 *             (  
                n.addressX <> 0 and n.addressY <> 0
                and nf.isPrimary = true
                and n.addressY < 28166454 and n.addressY > 23513626 and n.addressX > -106353149 and n.addressX < -101502686
            )
                     or
            (
                     l.topCoord > 23513626 and l.bottomCoord < 28166454 and l.leftCoord > -106353149 and l.rightCoord < -101502686
                     and nf.isPrimary = true
                    and l.topCoord <> 1000000000
            )
            
         and f.filterid = l.filterid
        and n.newsid = nf.newsid
        and f.filterid = nf.filterid
        and nf.isPrimary = 1  
        
        */
	
	public String getLink() {		
		return rect.buildLink();
	}

	public StringBuilder getFromSQL() {
		return new StringBuilder(", location l ");
	}
	
}
