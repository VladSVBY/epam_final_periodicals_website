package by.epam.periodicials_site.entity;

import java.io.Serializable;
import java.util.Date;

public class Review implements Serializable{

	private static final long serialVersionUID = -5981868236654468347L;
	
	private int id;
	private int userId;
	private int publicationId;
	private Date dateOfPublication;
	private String text;
	private byte mark;
	
	public Review() {
		super();
	}
	
	public Review(int id, int userId, int publicationId, Date dateOfPublication, String text, byte mark) {
		super();
		this.id = id;
		this.userId = userId;
		this.publicationId = publicationId;
		this.dateOfPublication = dateOfPublication;
		this.text = text;
		this.mark = mark;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateOfPublication == null) ? 0 : dateOfPublication.hashCode());
		result = prime * result + id;
		result = prime * result + mark;
		result = prime * result + publicationId;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + userId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Review other = (Review) obj;
		if (dateOfPublication == null) {
			if (other.dateOfPublication != null)
				return false;
		} else if (!dateOfPublication.equals(other.dateOfPublication))
			return false;
		if (id != other.id)
			return false;
		if (mark != other.mark)
			return false;
		if (publicationId != other.publicationId)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", userId=" + userId + ", publicationId=" + publicationId + ", dateOfPublication="
				+ dateOfPublication + ", text=" + text + ", mark=" + mark + "]";
	}
	
}
