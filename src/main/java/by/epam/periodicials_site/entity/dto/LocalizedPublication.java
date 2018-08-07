package by.epam.periodicials_site.entity.dto;

import java.util.Map;

import by.epam.periodicials_site.entity.LocaleType;

public class LocalizedPublication {
	
	private int id;
	private Map<LocaleType, String> names;
	private Map<LocaleType, String> descriptions;
	private int periodicity;
	private short themeId;
	private short typeID;
	private double price;
	private double rating;
	private String picturePath;
	
	public LocalizedPublication() {
		super();
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Map<LocaleType, String> getNames() {
		return names;
	}
	
	public void setNames(Map<LocaleType, String> names) {
		this.names = names;
	}
	
	public Map<LocaleType, String> getDescriptions() {
		return descriptions;
	}
	
	public void setDescriptions(Map<LocaleType, String> descriptions) {
		this.descriptions = descriptions;
	}
	
	public int getPeriodicity() {
		return periodicity;
	}
	
	public void setPeriodicity(int periodicity) {
		this.periodicity = periodicity;
	}
	
	public short getThemeId() {
		return themeId;
	}
	
	public void setThemeId(short themeId) {
		this.themeId = themeId;
	}
	
	public short getTypeID() {
		return typeID;
	}
	
	public void setTypeID(short typeID) {
		this.typeID = typeID;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

}
