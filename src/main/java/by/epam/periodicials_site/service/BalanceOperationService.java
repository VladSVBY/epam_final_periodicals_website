package by.epam.periodicials_site.service;

import java.util.List;

import by.epam.periodicials_site.entity.BalanceOperation;
import by.epam.periodicials_site.service.exception.ServiceException;

public interface BalanceOperationService {
	
	List<BalanceOperation> readForUser(int userID) throws ServiceException;
	
	void create(BalanceOperation balanceOperation) throws ServiceException;

}
