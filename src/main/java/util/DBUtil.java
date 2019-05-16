package util;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBUtil {
	private static String user="root";
	private static String password="199712272012";
	private static String url="jdbc:mysql://localhost:3306/rmi?useSSL=FALSE&serverTimezone=UTC";
	private static String driver="com.mysql.cj.jdbc.Driver";

	public static Connection openDB(){
		try {
			Class.forName(driver);
			return DriverManager.getConnection(url,user,password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}
	public static void closeDB(Connection cnn){
		if(cnn!=null){
			try {
		
			
				cnn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	

}
