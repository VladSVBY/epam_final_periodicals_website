package by.epam.periodicials_site.entity;

import java.util.Date;

public class BalanceOperation {
	
	private int id;
	private int idUser;
	private Date date;
	private double sum;
	private BalanceOperationType type;
	
	public BalanceOperation() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getSum() {
		return sum;
	}
	public void setSum(double sum) {
		this.sum = sum;
	}
	public BalanceOperationType getType() {
		return type;
	}
	public void setType(BalanceOperationType type) {
		this.type = type;
	}

}
