package mapreport.filter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SearchFilter extends Filter {

	@Override
	public int bindQuery(PreparedStatement pst, int col) {
		// TODO Auto-generated method stub
		return col;
	}

	@Override
	public void processResultSet(ResultSet resultSet) {
		// TODO Auto-generated method stub
		
	}

}
