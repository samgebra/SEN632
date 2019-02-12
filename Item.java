//-----------------------------------------------------
// SEN 632 Jan 2019 Project
// Student names:
//      Babita Patil
//      Kelsey Kinder
//      Matt Hunter
//      Sam Gebra
//
// File Name: Item.java
//
// This file introduces the Item class that serves as 
// a placeholder for the items to be stored at the server
//  
// Date created: Jan 28, 2018
// Source: SEN632 Prject Team
//-----------------------------------------------------
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Item implements Serializable {

	// Attributes for price, quantity, name, and caloire total
	private String userID;
	private String name;
	private double price;
	private int calories;
	private int quantity;
	private int day;
	private int month;
	private int year;
	private double priceDiff;
	private int caloriesDiff;
	// the below boolean attribute is a flag
	// it will be used by the client class
	// it will always be true, unless
	// the connection would be chosen to be
	// eliminated, then it will be set to false
	// once it is false, the server class
	// will shut down connection	
	
	// Constructors
	public Item() {
		this.userID = "000000";
		this.price = 0.00;
		this.quantity = 0;
		this.name = "null";
		this.calories = 0;
		this.day = 1;
		this.month = 1;
		this.year = 1;
	}

	public Item(String userID, double price, int quantity, String name, int calories, int year, int month, int day) {
		this.userID = userID;
		this.price = price;
		this.quantity = quantity;
		this.name = name;
		this.calories = calories;
		this.day = day;
		this.month = month;
		this.year = year;
	}

	// Get/set and toString
	public String getUID() {
		return userID;
	} //This method has no setter cause UID is not supposed to change for each user
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCaloires() {
		return calories;
	}

	public void setCaloires(int calories) {
		this.calories = calories;
	}

	public LocalDate getDay() {
		return LocalDate.of(year, month, day);
	}

	public void setday(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	@Override
	public String toString() {
		return "Item [price=" + price + ", quantity=" + quantity + ", name=" + name + ", caloires=" + calories
				+ ", day=" + day + "]";
	}

	// Methods
	public double getTotalPrice(int number, double price) {
		return this.quantity * this.price;
	}
	
	public int getCalorieTotal(int number, int calories) {
		return this.quantity * this.calories;
	}
	
	public String comparePrice(Item toBeCompared) {
		priceDiff = this.getPrice() - toBeCompared.getPrice();
		if (priceDiff < 0) {
			return this.getName() + " costs less then " + toBeCompared.getName();
		} else if(priceDiff == 0) {
			return this.getName() + " costs the same as " + toBeCompared.getName();
		} else {
			return toBeCompared.getName() + " costs less than " + this.getName();
		}	
	}
	
	public String compareCalories(Item toBeCompared) {
		caloriesDiff = this.getCaloires() - toBeCompared.getCaloires();
		if (caloriesDiff < 0) {
			return this.getName() + " has fewer calories than " + toBeCompared.getName();
		} else if(caloriesDiff == 0) {
			return this.getName() + " is equal in calories with " + toBeCompared.getName();
		} else {
			return toBeCompared.getName() + " has fewer calories than " + this.getName();
		}	
	}
	
	public String lastTimeBought() {
		return "This item was bought " + ChronoUnit.DAYS.between(this.getDay(), LocalDate.now()) + " days ago!";	
	}
}