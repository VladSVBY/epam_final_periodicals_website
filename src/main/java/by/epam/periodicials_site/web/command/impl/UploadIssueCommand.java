package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.periodicials_site.entity.Issue;
import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.entity.Publication;
import by.epam.periodicials_site.entity.Subscription;
import by.epam.periodicials_site.service.IssueService;
import by.epam.periodicials_site.service.PublicationService;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.SubscriptionService;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.util.HttpUtil;

public class UploadIssueCommand implements Command {
	
	private IssueService issueService = ServiceFactory.getIssueService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			Issue issue = formIssue(request);
			issueService.create(issue);
			request.getRequestDispatcher(VIEW_AVALIABLE_ISSUES).forward(request, response);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	private Issue formIssue(HttpServletRequest request) throws IOException, ServletException {
		Issue issue = new Issue();
		issue.setPublicationId(Integer.parseInt(request.getParameter(REQUEST_PARAM_ISSUE_ID_OF_PUBLICATION)));
		issue.setDescription(request.getParameter(REQUEST_PARAM_ISSUE_DESCRIPTION));
		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			issue.setDateOfPublication(format1.parse(request.getParameter(REQUEST_PARAM_ISSUE_DATE_OF_PUBLICATION)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpUtil.uploadIssueFile(issue, request);
		
		return issue;
	}
}
