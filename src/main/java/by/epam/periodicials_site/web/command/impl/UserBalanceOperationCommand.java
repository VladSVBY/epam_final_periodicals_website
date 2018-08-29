package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.periodicials_site.entity.BalanceOperation;
import by.epam.periodicials_site.service.BalanceOperationService;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.web.command.Command;

public class UserBalanceOperationCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger(UserBalanceOperationCommand.class);
	
	private BalanceOperationService balanceOperationService = ServiceFactory.getBalanceOperationService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int userId = (int) request.getSession().getAttribute(SESSION_ATTR_USER_ID);
			List<BalanceOperation> balanceOperations = balanceOperationService.readForUser(userId);
			
			request.setAttribute(REQUEST_ATTR_BALANCE_OPERATION_LIST, balanceOperations);
			request.getRequestDispatcher(VIEW_USER_BALANCE_OPERATION_HISTORY).forward(request, response);
		} catch (ServiceException e) {
			logger.error("Exception showng user balance operations", e);
			request.getRequestDispatcher(VIEW_503_ERROR).forward(request, response);
		}
	}

}
