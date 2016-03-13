package mapreport.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ThreadLocalConnection {
	private static final ThreadLocal<Connection> connection =
        new ThreadLocal<Connection>() {
            @Override protected Connection initialValue() {
            	try {
            		return DriverManager.getConnection(DBBase.url, DBBase.user, DBBase.password);
            	} catch(SQLException e) {
            		e.printStackTrace();
            		return null;
            	}
        }
    };

    public static Connection get() {
        return connection.get();
    }
}
