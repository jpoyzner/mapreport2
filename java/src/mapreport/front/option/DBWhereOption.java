package mapreport.front.option;

public class DBWhereOption extends Option {
	
	public DBWhereOption(String value) {
		super(value);
		// TODO Auto-generated constructor stub
	}

	public String getWhereSql() {
		return whereSql;
	}

	public void setWhereSql(String whereSql) {
		this.whereSql = whereSql;
	}

	String whereSql;
}
