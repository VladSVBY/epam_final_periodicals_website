package by.epam.periodicials_site.web.command.impl;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.REQUEST_ATTR_TYPES;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.VIEW_503_ERROR;
import static by.epam.periodicials_site.web.util.WebConstantDeclaration.VIEW_EDIT_TYPES;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.periodicials_site.entity.dto.LocalizedType;
import by.epam.periodicials_site.service.ServiceFactory;
import by.epam.periodicials_site.service.TypeService;
import by.epam.periodicials_site.service.exception.ServiceException;
import by.epam.periodicials_site.web.command.Command;

public class EditTypesCommand implements Command{
	
	private static final Logger logger = LogManager.getLogger(EditTypesCommand.class);

	private TypeService typeService = ServiceFactory.getTypeService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			List<LocalizedType> types = typeService.readAllLocalized();
			request.setAttribute(REQUEST_ATTR_TYPES, types);	
			request.getRequestDispatcher(VIEW_EDIT_TYPES).forward(request, response);
		} catch (ServiceException e) {
			logger.error("Exception reading localized types", e);
			request.getRequestDispatcher(VIEW_503_ERROR).forward(request, response);
		}
	}
}
