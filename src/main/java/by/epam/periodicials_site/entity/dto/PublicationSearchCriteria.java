package by.epam.periodicials_site.entity.dto;

import by.epam.periodicials_site.entity.LocaleType;

public class PublicationSearchCriteria {
	
	private LocaleType locale;
	private int themeId;
	private int typeId;
	private int orderId;
	private int currentPage;
	private int itemsPerPage;
	
	public PublicationSearchCriteria() {
		super();
	}
	
	public LocaleType getLocale() {
		return locale;
	}
	
	public void setLocale(LocaleType locale) {
		this.locale = locale;
	}

	public int getThemeId() {
		return themeId;
	}

	public void setThemeId(Integer themeId) {
		this.themeId = themeId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getItemsPerPage() {
		return itemsPerPage;
	}

	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	
}
