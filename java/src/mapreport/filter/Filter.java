package mapreport.filter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.annotations.Expose;

import mapreport.front.dimension.Dimension;
import mapreport.util.Log;
  
public abstract class Filter {

	private StringBuilder selectSQL = new StringBuilder("");
	private StringBuilder fromSQL = new StringBuilder("");
	protected StringBuilder whereSQL = new StringBuilder("");
	protected StringBuilder orderBySQL = new StringBuilder("");
	
	Dimension dimension;
	int priority;
	//  treeNode ????
	
	String link = "";

	@Expose String name = null;
	  
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
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

	

	public abstract int bindQuery(PreparedStatement pst, int col) throws SQLException; 
	
	public abstract void processResultSet(ResultSet resultSet); 
	
	public void addFromSQL(String toAddSQL) {
		
	}
	
	public void addSelectSQL(String toAddSQL) {
	}

}
