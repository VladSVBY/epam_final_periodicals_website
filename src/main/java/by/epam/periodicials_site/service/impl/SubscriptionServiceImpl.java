package by.epam.periodicials_site.service.impl;

import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import by.epam.periodicials_site.dao.DaoException;
import by.epam.periodicials_site.dao.DaoFactory;
import by.epam.periodicials_site.dao.PublicationDao;
import by.epam.periodicials_site.dao.SubscriptionDao;
import by.epam.periodicials_site.entity.BalanceOperation;
import by.epam.periodicials_site.entity.BalanceOperationType;
import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Publication;
import by.epam.periodicials_site.entity.Subscription;
import by.epam.periodicials_site.entity.SubscriptionStatus;
import by.epam.periodicials_site.entity.dto.LocalizedPublication;
import by.epam.periodicials_site.service.PublicationService;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.SubscriptionService;
import by.epam.periodicials_site.service.exception.ServiceException;

public class SubscriptionServiceImpl implements SubscriptionService{
	
	private SubscriptionDao subscriptionDao = DaoFactory.getSubscriptionDao();
	private PublicationService publicationService = ServiceFactory.getPublicationService();

	@Override
	public List<Subscription> readActiveForUser(int userId) {
		List<Subscription> subscriptions = Collections.emptyList();
		try {
			subscriptions = subscriptionDao.readActiveForUser(userId);
			Date currentDate = new Date();
			Iterator<Subscription> iterator = subscriptions.iterator();
			while (iterator.hasNext()) {
				Subscription subscription = iterator.next();
				Date dateOfExpiration = subscription.getEndDate();
				if (dateOfExpiration.before(currentDate)) {
					subscription.setStatus(SubscriptionStatus.EXPIRED);
					subscriptionDao.update(subscription);
					iterator.remove();
				}
			}
		} catch (DaoException e) {
			// TODO logger
			e.printStackTrace();
		}
		return subscriptions;
	}

	@Override
	public List<Subscription> readNonActiveForUser(int userId) throws ServiceException{
		List<Subscription> subscriptions = Collections.emptyList();
		try {
			subscriptions = subscriptionDao.readAllForUser(userId);
			Date currentDate = new Date();
			Iterator<Subscription> iterator = subscriptions.iterator();
			while (iterator.hasNext()) {
				Subscription subscription = iterator.next();
				if (SubscriptionStatus.ACTIVE.equals(subscription.getStatus())) {
					Date dateOfExpiration = subscription.getEndDate();
					if (dateOfExpiration.before(currentDate)) {
						subscription.setStatus(SubscriptionStatus.EXPIRED);
						subscriptionDao.update(subscription);
					} else {
						iterator.remove();
					}
				}
			}
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		return subscriptions;
	}

	@Override
	public void terminateSubscription(int subscriptionId) throws ServiceException{
		try {
			Subscription subscription = subscriptionDao.read(subscriptionId);
			Calendar currentDate = Calendar.getInstance();
			Calendar expirationDate = Calendar.getInstance();
			expirationDate.setTime(subscription.getEndDate());
			int monthToExpiration = expirationDate.get(Calendar.MONTH) - currentDate.get(Calendar.MONTH);
			if (monthToExpiration != 0) {
				double sum = calculateSumForRefund(monthToExpiration, subscription.getPublicationId());
				BalanceOperation balanceOperation = new BalanceOperation();
				balanceOperation.setDate(new Date());
				balanceOperation.setIdUser(subscription.getUserId());
				balanceOperation.setSum(sum);
				balanceOperation.setType(BalanceOperationType.REFUND);
				subscriptionDao.terminate(subscription, balanceOperation);
			} else {
				subscription.setStatus(SubscriptionStatus.TERMINATED);
				subscriptionDao.update(subscription);
			}
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		
	}
	
	@Override
	public Subscription read(int id) throws ServiceException {
		try {
			return subscriptionDao.read(id);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public void create(int userId, int publicationId, int startMonth, int duration) throws ServiceException {
		try {
			LocalizedPublication publication = publicationService.readLocalized(publicationId);
			double subscriptionPrice = publication.getPrice() * duration;
			Calendar startDate = Calendar.getInstance();
			startDate.set(Calendar.DAY_OF_MONTH, 1);
			startDate.set(Calendar.MONTH, startMonth);
			
			Calendar endDate = Calendar.getInstance();
			endDate.set(Calendar.DAY_OF_MONTH, 1);
			endDate.set(Calendar.MONTH, startMonth + duration - 1);
			endDate.add(Calendar.DAY_OF_MONTH, -1);
					
			Subscription subscription = new Subscription();
			subscription.setUserId(userId);
			subscription.setPublicationId(publicationId);
			subscription.setPrice(subscriptionPrice);
			subscription.setStartDate(startDate.getTime());
			subscription.setEndDate(endDate.getTime());
			subscription.setPrice(subscriptionPrice);
			subscription.setStatus(SubscriptionStatus.ACTIVE);
			
			BalanceOperation balanceOperation = new BalanceOperation();
			balanceOperation.setDate(new Date());
			balanceOperation.setIdUser(userId);
			balanceOperation.setType(BalanceOperationType.PAYMENT_OF_SUBSCRIPTION);
			balanceOperation.setSum(subscriptionPrice);
		
			subscriptionDao.create(subscription, balanceOperation);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	private double calculateSumForRefund(int monthToExpiration, int publicationID) {
		Publication publication = publicationService.read(publicationID, LocaleType.EN_US);
		return monthToExpiration * publication.getPrice();
	}

}
