package mapreport.front.option;

import mapreport.util.Log;

public class DBWhereFilterOption extends DBWhereOption {

	public DBWhereFilterOption(String filterName) {
		super(" and  f.name=?");
		// TODO Auto-generated constructor stub
	}
	
	public DBWhereFilterOption (String name, String value) {
		super(name, value);
	}

}
