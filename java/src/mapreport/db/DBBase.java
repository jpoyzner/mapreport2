package mapreport.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import mapreport.util.Log;

public class DBBase {
	protected PreparedStatement pst = null;
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

	public PreparedStatement getPst() {
		return pst;
	}

	public void setPst(PreparedStatement pst) {
		this.pst = pst;
	}

	protected static ResultSet resultSet = null;

	static String url = "jdbc:mysql://localhost:3306/new_schema2"; // testdb";
	static String user = "root";
	static String password = "hadera";
	static Connection con;

	static {
		try {
			Log.info("com.mysql.jdbc.Driver starts");
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		}
	}

	public static void buildConnection() {
		try {
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String buildSql(int nameFiltersNo, boolean isCoordFilter) {
		return null;
	}

	public PreparedStatement prepareStmt() {
		try {
			pst = con.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pst;
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

	public PreparedStatement begin(int nameFilterNo, boolean isCoordFilter) {
		buildConnection();

		buildSql(nameFilterNo, isCoordFilter);
		pst = prepareStmt();

		Log.log("DBQueryBuilder con=" + con);
		return pst;
	}

	void startBindQuery() throws SQLException {

	}

	public void end() {
		try {
			pst.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					con.close();
				}

			} catch (SQLException ex) {
				// Logger lgr = Logger.getLogger(Test.class.getName());
				// lgr.log(Level.SEVERE, ex.getMessage(), ex);
				Log.info(ex.getMessage());
				ex.printStackTrace(System.out);
			}
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

	public ResultSet executeQuery() {
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
