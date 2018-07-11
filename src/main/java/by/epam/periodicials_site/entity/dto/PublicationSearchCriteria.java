package by.epam.periodicials_site.entity.dto;

import by.epam.periodicials_site.entity.LocaleType;

public class PublicationSearchCriteria {
	
	private LocaleType locale;
	private int themeId;
	private int orderId;
	
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

	
}
