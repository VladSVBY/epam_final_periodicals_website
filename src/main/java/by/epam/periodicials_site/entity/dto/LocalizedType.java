package by.epam.periodicials_site.entity.dto;

import java.util.Map;

import by.epam.periodicials_site.entity.LocaleType;

public class LocalizedType {
	
	private int id;
	private String defaultName;
	private Map<LocaleType, String> localizedNames;
	
	public LocalizedType() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDefaultName() {
		return defaultName;
	}

	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}

	public Map<LocaleType, String> getLocalizedNames() {
		return localizedNames;
	}

	public void setLocalizedNames(Map<LocaleType, String> localizedNames) {
		this.localizedNames = localizedNames;
	}	

}
