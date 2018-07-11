package by.epam.periodicials_site.service;

import java.util.List;

import by.epam.periodicials_site.entity.BalanceOperation;

public interface BalanceOperationService {
	
	List<BalanceOperation> readForUser(int userID) throws ServiceException;

}
