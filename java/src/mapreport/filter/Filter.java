package mapreport.filter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mapreport.front.dimension.Dimension;
import mapreport.util.Log;

public abstract class Filter {

	private StringBuilder selectSQL = new StringBuilder("");
	private StringBuilder fromSQL = new StringBuilder("");
	private StringBuilder whereSQL = new StringBuilder("");
	private StringBuilder orderBySQL = new StringBuilder("");
	
	Dimension dimension;
	int priority;
	//  treeNode ????
	
	String link = "";
	
	public String getLink() {
		//	Log.log("Filter getLink() this=" + this + " link=" + link);
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	public StringBuilder getSelectSQL() {
		return selectSQL;
	}

	public void setSelectSQL(StringBuilder selectSQL) {
		this.selectSQL = selectSQL;
	}

	public StringBuilder getFromSQL() {
		return fromSQL;
	}

	public void setFromSQL(StringBuilder fromSQL) {
		this.fromSQL = fromSQL;
	}

	public StringBuilder getWhereSQL() {
		return whereSQL;
	}

	public void setWhereSQL(StringBuilder whereSQL) {
		this.whereSQL = whereSQL;
	}

	public StringBuilder getOrderBySQL() {
			Log.log("getOrderBySQL this=" + this + " orderBySQL=" + orderBySQL);
		return orderBySQL;
	}

	public void setOrderBySQL(StringBuilder orderBySQL) {
		this.orderBySQL = orderBySQL;
	}

	

	public abstract void bindQuery(PreparedStatement pst) throws SQLException; 
	
  //  while (resultSet.next()) {
//	      String label = resultSet.getString("Label");
  //  }	
	public abstract void processResultSet(ResultSet resultSet); 
	
	public void addFromSQL(String toAddSQL) {
		//return queryBuilder.addFromSQL(toAddSQL);
	}
	
	public void addSelectSQL(String toAddSQL) {
		/*
	select  f.priority as filterPriority, 
     f.name as fName, n.newsId, n.label, n.priority as nPriority, nf.priority as nfPriority, 
      
     ft.priority as filterTopicPriority, fpt.priority as filterTopicParentPriority, -- for topic   
                 ft.name as ftName, nft.priority as nftPriority, -- for topic   
                 fpt.name topicParent, fpt.filterId, fft.level  as topicLevel  -- for topic        
                 
     l.topCoord , l.bottomCoord , l.leftCoord , l.rightCoord, l.isOfficial,
    abs(l.topCoord - l.bottomCoord) * abs(l.leftCoord - l.rightCoord) / 1000000000 as span,  
     fpl.priority as filterLocParentPriority, fl.priority as filterLocPriority,  -- for location 
                 fl.name as flName, nfl.priority as nflPriority, -- for location
                 fpl.name locParent, fpl.filterId, ffl.level as locLevel,  -- for location
		*/
		// return queryBuilder.addSelectSQL(toAddSQL);
	}

}
