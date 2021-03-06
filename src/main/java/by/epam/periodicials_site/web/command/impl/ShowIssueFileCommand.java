package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.periodicials_site.entity.Issue;
import by.epam.periodicials_site.service.IssueService;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;

public class ShowIssueFileCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger(ShowIssueFileCommand.class);
	
	private IssueService issueService = ServiceFactory.getIssueService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int issueId = Integer.parseInt(request.getParameter(REQUEST_PARAM_ISSUE_ID));
			Issue issue = issueService.read(issueId);
			
			HttpUtil.writeIssueIntoRespone(issue, response, request);
		} catch (ServiceException e) {
			logger.error("Exception showing issue file", e);
			request.getRequestDispatcher(VIEW_503_ERROR).forward(request, response);
		}
	}

}
