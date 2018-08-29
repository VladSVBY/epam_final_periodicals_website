package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.periodicials_site.entity.BalanceOperation;
import by.epam.periodicials_site.entity.BalanceOperationType;
import by.epam.periodicials_site.service.BalanceOperationService;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;

public class ReplenishBalanceCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger(ReplenishBalanceCommand.class);
	
	private BalanceOperationService balanceOperationService = ServiceFactory.getBalanceOperationService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BalanceOperation balanceOperation = formBalanceOperation(request);		
		try {
			balanceOperationService.create(balanceOperation);
			
			response.sendRedirect(HttpUtil.formRedirectUrl(request, COMMAND_SHOW_USER_PROFILE));
		} catch (ServiceException e) {
			logger.error("Exception replenishing user balance", e);
			request.getRequestDispatcher(VIEW_503_ERROR).forward(request, response);
		}
	}
	
	private BalanceOperation formBalanceOperation(HttpServletRequest request) {
		int userID = (int) request.getSession().getAttribute(SESSION_ATTR_USER_ID);
		double sum = Double.parseDouble(request.getParameter(REQUEST_PARAM_SUM_FOR_REPLENISHMENT));
		
		BalanceOperation balanceOperation = new BalanceOperation();
		balanceOperation.setIdUser(userID);
		balanceOperation.setSum(sum);
		balanceOperation.setType(BalanceOperationType.BALANCE_REPLENISHMENT);
		balanceOperation.setDate(new Date());
		
		return balanceOperation;
	}
	
	
}
