//-----------------------------------------------------
// SEN 632 Jan 2019 Project
// Student names:
//      Babita Patil
//      Kelsey Kinder
//      Matt Hunter
//      Sam Gebra
//
// File Name: Umbrella.java
//
// This file is an umbrella Class that combine the three
// other classes so that it can be transmitted to the
// server
//  
// Date re-created: Jan 31, 2018
// Source: SEN632 Prject Team
//-----------------------------------------------------
import java.io.Serializable;

public class Umbrella implements Serializable{
	private Item embeddedItem;
	private DatabaseLoginInfo embeddedDatabaseLoginInfo;
	private String embeddedString;
	private boolean doesItemExist;
	private boolean doesDatabaseLoginInfoExist;
	private boolean doesStringExist;
	
	// this number indicates what is the umbrella carrying at any moment
	// it does not have a setter, only a getter
	// 1 means that Umbrella class is carrying an Item
	// 2 means that Umbrella class is carrying a DBLI
	// 3 means that Umbrella class is carrying a String
	private int switcher; 
	
	public Umbrella()
	{
		this.doesItemExist = false;
		this.doesDatabaseLoginInfoExist = false;
		this.doesStringExist = false;
		this.embeddedItem = new Item();
		this.embeddedDatabaseLoginInfo = new DatabaseLoginInfo();
		this.embeddedString = new String("");
		this.switcher = -1;
	}	
	// getters/setters
	public void setEmbeddedItem(Item item)
	{
		this.embeddedItem = item;
		switcher = 1;
		setItemExistance(true);
	}
	public Item getEmbeddedItem()
	{
		return this.embeddedItem;
	}
	public void setEmbeddedDatabaseLoginInfo(DatabaseLoginInfo DBLI)
	{
		this.embeddedDatabaseLoginInfo = DBLI;
		switcher = 2;
		setDBLIExistance(true);
	}
	public DatabaseLoginInfo getEmbeddedDatabaseLoginInfo()
	{
		return this.embeddedDatabaseLoginInfo;
	}
	public void setEmbeddedString(String string)
	{
		this.embeddedString = string;
		switcher = 3;
		setStringExistance(true);
	}
	public String getEmbeddedString()
	{
		return this.embeddedString;
	}
	public void setItemExistance(boolean trueFalse)
	{
		if(trueFalse)
		{
			switcher = 1;
		}
		doesItemExist = trueFalse;
		
	}
	public boolean getItemExistance()
	{
		return this.doesItemExist;
	}
	public void setDBLIExistance(boolean trueFalse)
	{
		if(trueFalse)
		{
			switcher = 2;
		}
		doesDatabaseLoginInfoExist = trueFalse;
	}
	public boolean getDBLIExistance()
	{
		return this.doesDatabaseLoginInfoExist;
	}
	public void setStringExistance(boolean trueFalse)
	{
		if(trueFalse)
		{
			switcher = 3;
		}
		doesStringExist = trueFalse;
	}
	public boolean getStringExistance()
	{
		return this.doesStringExist;
	}
	public int getSwitcher()
	{
		return this.switcher;
	}
}
