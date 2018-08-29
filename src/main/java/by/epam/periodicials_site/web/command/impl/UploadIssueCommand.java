package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.periodicials_site.entity.Issue;
import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.service.IssueService;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.service.exception.ValidationException;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;
import by.epam.periodicials_site.web.util.MessageResolver;

public class UploadIssueCommand implements Command {
	
	private static final Logger logger = LogManager.getLogger(UploadIssueCommand.class);
	
	private static final String FAIL_MESSAGE_KEY = "add_issue.fail";
	private static final String SUCCESS_MESSAGE_KEY = "add_issue.succcess";
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	
	private IssueService issueService = ServiceFactory.getIssueService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LocaleType locale = HttpUtil.getLocale(request);
		try {			
			Issue issue = formIssue(request);
			issueService.create(issue);

			String message = MessageResolver.getMessage(SUCCESS_MESSAGE_KEY, locale);
			String returnPage = HttpUtil.getReferPage(request);
			String path = HttpUtil.formRedirectUrl(request, COMMAND_SHOW_RESULT_PAGE);
			path = HttpUtil.addParamToPath(path, REQUEST_ATTR_MESSAGE, message);
			path = HttpUtil.addParamToPath(path, REQUEST_ATTR_RETURN_PAGE, returnPage);
			response.sendRedirect(path);			
		} catch (ValidationException e) {
			String message = MessageResolver.getMessage(FAIL_MESSAGE_KEY, locale);
			request.setAttribute(FAIL_MESSAGE_ADD_ISSUE, message);
			request.getRequestDispatcher(COMMAND_ADD_ISSUE).forward(request, response);
		} catch (ServiceException e) {
			logger.error("Exception uploading issue", e);
			request.getRequestDispatcher(VIEW_503_ERROR).forward(request, response);
		}
	}

	private Issue formIssue(HttpServletRequest request) throws IOException, ServletException {
		Issue issue = new Issue();
		issue.setPublicationId(Integer.parseInt(request.getParameter(REQUEST_PARAM_ISSUE_ID_OF_PUBLICATION)));
		issue.setDescription(request.getParameter(REQUEST_PARAM_ISSUE_DESCRIPTION));
		
		SimpleDateFormat format1 = new SimpleDateFormat(DATE_FORMAT);
		try {
			issue.setDateOfPublication(format1.parse(request.getParameter(REQUEST_PARAM_ISSUE_DATE_OF_PUBLICATION)));
		} catch (ParseException e) {
			logger.warn("Exception parsing date", e);
		}

		HttpUtil.uploadIssueFile(issue, request);
		
		return issue;
	}
}
