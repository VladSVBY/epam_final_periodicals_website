package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.periodicials_site.entity.BalanceOperation;
import by.epam.periodicials_site.service.BalanceOperationService;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.web.command.Command;

public class UserBalanceOperationCommand implements Command {
	
	private BalanceOperationService balanceOperationService = ServiceFactory.getBalanceOperationService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userId = (int) request.getSession().getAttribute(SESSION_ATTR_USER_ID);
		
		List<BalanceOperation> balanceOperations = Collections.emptyList();
		try {
			balanceOperations = balanceOperationService.readForUser(userId);
		} catch (ServiceException e) {
			// TODO logger
			e.printStackTrace();
		}
		
		request.setAttribute(REQUEST_ATTR_BALANCE_OPERATION_LIST, balanceOperations);
		request.getRequestDispatcher(VIEW_USER_BALANCE_OPERATION_HISTORY).forward(request, response);
	}

}
