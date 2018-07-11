package by.epam.periodicials_site.entity;

import java.util.Date;

public class Review {
	
	private int id;
	private int userId;
	private int publicationId;
	private Date dateOfPublication;
	private String text;
	private byte mark;
	
	public Review() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getPublicationId() {
		return publicationId;
	}
	public void setPublicationId(int publicationId) {
		this.publicationId = publicationId;
	}
	public Date getDateOfPublication() {
		return dateOfPublication;
	}
	public void setDateOfPublication(Date dateOfPublication) {
		this.dateOfPublication = dateOfPublication;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public byte getMark() {
		return mark;
	}
	public void setMark(byte mark) {
		this.mark = mark;
	}
	
	

}
