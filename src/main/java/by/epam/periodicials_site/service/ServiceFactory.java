package by.epam.periodicials_site.service;

import by.epam.periodicials_site.service.impl.PublicationServiceImpl;
import by.epam.periodicials_site.service.impl.UserServiceImpl;

public final class ServiceFactory {
	
	private ServiceFactory() {}
	
	public static UserService getUserService() {
		return new UserServiceImpl();
	}
	
	public static PublicationService getPublicationService() {
		return new PublicationServiceImpl();
	}

}
