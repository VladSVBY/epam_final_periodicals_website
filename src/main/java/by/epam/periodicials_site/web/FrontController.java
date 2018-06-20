package by.epam.periodicials_site.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.periodicials_site.web.command.Command;
import by.epam.periodicials_site.web.command.CommandProvider;

public class FrontController extends HttpServlet {
	
	private static final long serialVersionUID = -648733403178379343L;

    public FrontController() {
        super();
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String commandName = request.getPathInfo();
		Command command = CommandProvider.defineCommand(commandName);
		if (command == null) request.getRequestDispatcher("/index.jsp").forward(request, response);
		else command.execute(request, response);
	}

}
