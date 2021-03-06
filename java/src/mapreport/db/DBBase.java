package mapreport.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

import mapreport.util.Log;

public class DBBase {
	int limit = 2;

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
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
		return orderBySQL;
	}

	public void setOrderBySQL(StringBuilder orderBySQL) {
		this.orderBySQL = orderBySQL;
	}

	protected String sql = null;

	protected StringBuilder selectSQL = new StringBuilder("");

	protected StringBuilder fromSQL = new StringBuilder("\n ");
	protected StringBuilder whereSQL = new StringBuilder("");

	protected StringBuilder orderBySQL = new StringBuilder("");

	protected static ResultSet resultSet = null;
	
	static Map<String, String> env = System.getenv();
	
	static final String url = "jdbc:mysql://" + env.get("DBHOST") + ":3306/new_schema2"; 
	static final String user = "root";
	static final String password = env.get("DBPASSWORD");
	
	static {
		try { 
			Log.info("com.mysql.jdbc.Driver starts url;" + url + " user:" + user + " password:" + password);
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		}
	}

	public String buildSql(int nameFiltersNo, boolean isCoordFilter) {
		return null;
	}

	public PreparedStatement prepareStmt(Connection connection) {
		try {			
			Log.log("DBQueryBuilder con=" + connection);
			
			return connection.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static boolean hasColumn(ResultSet rs, String columnName)
			throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columns = rsmd.getColumnCount();
		for (int x = 1; x <= columns; x++) {
			if (columnName.equals(rsmd.getColumnName(x))) {
				return true;
			}
		}
		return false;
	}

	public PreparedStatement begin(Connection connection, int nameFilterNo, boolean isCoordFilter) {
		Log.info("DBase begin nameFilterNo:" + nameFilterNo + " isCoordFilter:" + isCoordFilter);
		buildSql(nameFilterNo, isCoordFilter);
		return prepareStmt(connection);
	}

	public void end(PreparedStatement ps, Connection conn) {
		try {
			ps.close();
			conn.close();
		} catch (SQLException ex) {
			Log.info(ex.getMessage());
			ex.printStackTrace();
		}
	}

	public static StringBuilder addComaSQL(StringBuilder sql, String toAddSQL) {
		if (sql.length() > 0) {
			sql.append(", ");
		}
		sql.append(toAddSQL);
		return sql;
	}

	public StringBuilder addWhereSQL(String toAddSQL) {
		if (whereSQL.length() > 0) {
			whereSQL.append(" and ");
		}
		whereSQL.append(toAddSQL);
		return whereSQL;
	}

	public ResultSet executeQuery(PreparedStatement pst) {
		try {
			Log.info("DBQueryBuilder pst=\n" + pst.toString());
			resultSet = pst.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSet;
	}

	public StringBuilder addOrderSQL(String toAddSQL) {
		return addComaSQL(orderBySQL, toAddSQL);
	}

	public StringBuilder addFromSQL(String toAddSQL) {
		return addComaSQL(fromSQL, toAddSQL);
	}

	public StringBuilder addSelectSQL(String toAddSQL) {
		return addComaSQL(selectSQL, toAddSQL);
	}

}
