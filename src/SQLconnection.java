import java.sql.*;

import javax.swing.*;
public class SQLconnection {
	Connection conn=null;
	public static Connection dbConnector()
	{
		try{
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","marchELE1994");
			JOptionPane.showMessageDialog(null,"Welcome :)");
			return conn;
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(null,ex);
			return null;
		}
	}
}
