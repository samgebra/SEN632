//-----------------------------------------------------
// SEN 632 Jan 2019 Project
// Student names:
//      Babita Patil
//      Kelsey Kinder
//      Matt Hunter
//      Sam Gebra
//
// File Name: DatabaseLoginInfo.java
//
// This file carries the authentication data to be
// transmitted to the server so it can be verified 
// and once verified the user's connection with the 
// server can continue, otherwise it will be closed
//  
// Date created: Jan 31, 2018
// Source: SEN632 Prject Team
//-----------------------------------------------------
import java.io.Serializable;

public class DatabaseLoginInfo implements Serializable{
	   private String UID; // placeholder for the entered user ID 
	   private String PW; // placeholder for the entered user password
	   
	   public DatabaseLoginInfo()
	   {
		   this.UID = "null";
		   this.PW = "null";
	   }
	   public DatabaseLoginInfo(String UID, String PW)
	   {
		   this.UID = UID;
		   this.PW = PW;
	   }
	   // this class is very simple
	   // it does not have setters
	   // it merely is just acting
	   // as a placeholder for UID and PW
	   // during initiation
	   public String getUID()
	   {
		   return UID;
	   }
	   public String getPW()
	   {
		   return PW;
	   }
}