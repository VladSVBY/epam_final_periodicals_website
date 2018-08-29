package by.epam.periodicials_site.service.impl;

import java.util.Collections;
import java.util.List;

import by.epam.periodicials_site.dao.BalanceOperationDao;
import by.epam.periodicials_site.dao.DaoException;
import by.epam.periodicials_site.dao.DaoFactory;
import by.epam.periodicials_site.entity.BalanceOperation;
import by.epam.periodicials_site.service.BalanceOperationService;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.service.exception.ValidationException;
import by.epam.periodicials_site.service.util.Validator;

public class BalanceOperationServiceImpl implements BalanceOperationService {
	
	private BalanceOperationDao balanceOperationDao = DaoFactory.getBalanceOperationDao();
	
	public List<BalanceOperation> readForUser(int userID) throws ServiceException{
		List<BalanceOperation> balanceOperations = Collections.emptyList();
		try {
			balanceOperations = balanceOperationDao.readForUser(userID);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		return balanceOperations;
	}

	@Override
	public void create(BalanceOperation balanceOperation) throws ServiceException {
		if (!Validator.balanceOperationIsValid(balanceOperation)) {
			throw new ValidationException("Balance operation data is not valid");
		}
		try {
			balanceOperationDao.create(balanceOperation);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

}
