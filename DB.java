//-----------------------------------------------------
// SEN 632 Jan 2019 Project
// Student names:
//      Babita Patil
//      Kelsey Kinder
//      Matt Hunter
//      Sam Gebra
//
// File Name: DB.java
//
// This file accepts the username and password and tries to 
// use them to log in to the server-side database
//  
// Date re-created: Jan 31, 2018
// Source: SEN632 Prject Team
//-----------------------------------------------------
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB
{
	private String url;
	private String userName;
	private String passWord;
	private Connection conn = null;
	private boolean isConnected = false;
	
	// connecting to the Database
	public DB(String username, String password)
	{
		this.userName = username;
		this.passWord = password;
		// Since the database is on the server
		// the URL can be at 127.0.0.1:3306
		this.url = "jdbc:mysql://127.0.0.1:3306/";
		try {
            // The newInstance() call is a work around for some
            // broken Java implementations
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            
        } catch (Exception ex) {
            // handle the error
        	System.out.println("something went wrong, please verify your input");
        }
        try {
			conn = DriverManager.getConnection(this.url, this.userName, this.passWord);
			isConnected = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	public Boolean getIsConnected()
	{
		return isConnected;
	}
}