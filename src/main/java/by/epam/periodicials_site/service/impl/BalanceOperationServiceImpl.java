package by.epam.periodicials_site.service.impl;

import java.util.Collections;
import java.util.List;

import by.epam.periodicials_site.dao.BalanceOperationDao;
import by.epam.periodicials_site.dao.DaoException;
import by.epam.periodicials_site.dao.DaoFactory;
import by.epam.periodicials_site.entity.BalanceOperation;
import by.epam.periodicials_site.service.BalanceOperationService;
import by.epam.periodicials_site.service.ServiceException;

public class BalanceOperationServiceImpl implements BalanceOperationService {
	
	private BalanceOperationDao balanceOperationDao = DaoFactory.getBalanceOperationDao();
	
	public List<BalanceOperation> readForUser(int userID) throws ServiceException{
		List<BalanceOperation> balanceOperations = Collections.emptyList();
		try {
			balanceOperations = balanceOperationDao.readForUser(userID);
		} catch (DaoException e) {
			throw new ServiceException("Exception reading balance operations", e);
		}
		return balanceOperations;
	}

	@Override
	public void create(BalanceOperation balanceOperation) throws ServiceException {
		try {
			balanceOperationDao.create(balanceOperation);
		} catch (DaoException e) {
			throw new ServiceException("Exception creating balance operations", e);
		}
	}

}
