package by.epam.periodicials_site.web.util;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.periodicials_site.entity.Issue;
import by.epam.periodicials_site.entity.LocaleType;
import by.epam.periodicials_site.web.command.impl.RegisterCommand;

public final class HttpUtil {
	
	private HttpUtil() {}
	
	private static final Logger logger = LogManager.getLogger(HttpUtil.class);
	
	private static final String CONTROLLER_PATTERN = "/controller";
	private static final String REQUEST_PARAM_PUBLICATION_PICTURE = "picture";
	private static final String PUBLICATION_PICTURE_PATH = "/resources/img/";
	private static final String ISSUE_FILE_BASE_PATH = "/WEB-INF/issues/";
	private static final String PIC_EXTENSION_REG_EX = "(.[a-z0-9]{3,})$";
	private static final String PIC_EXTENSION_DEFAULT = ".jpg";
	
	
	public static String getReferPage(HttpServletRequest request) {
		return request.getHeader(REQUEST_HEADER_REFER_PAGE);
	}
	
	public static LocaleType getLocale(HttpServletRequest request) {
		String localeName = (String) request.getSession().getAttribute(SESSION_ATTR_LOCALE);
		if (localeName == null) {
			return LocaleType.EN_US;
		}
		try {
			return LocaleType.valueOf(localeName.toUpperCase());
		} catch (IllegalArgumentException e) {
			return LocaleType.EN_US;
		}
	}
	
	public static String formRedirectUrl(HttpServletRequest request, String command) {
		return request.getContextPath() + CONTROLLER_PATTERN + command;
	}
	
	public static void writeIssueIntoRespone(Issue issue, HttpServletResponse response, HttpServletRequest request) throws IOException {
		String filePath = request.getServletContext().getRealPath(ISSUE_FILE_BASE_PATH) + issue.getFile();		
		response.setContentType("application/pdf");
		File srcFile = new File(filePath);
	    response.setHeader("Content-Disposition", "filename=\"hello.pdf\"");
	    Files.copy(srcFile.toPath(), response.getOutputStream());
	}
	
	public static String uploadPublicationPicture(HttpServletRequest request) throws IOException, ServletException {
		Part picturePart = request.getPart(REQUEST_PARAM_PUBLICATION_PICTURE);
		try (InputStream fileContent = picturePart.getInputStream()) {
			String path = request.getServletContext().getRealPath(PUBLICATION_PICTURE_PATH);
			String extension = getPictureExtension(picturePart);
            File file = generateFileForPublicationPicture(path, extension);
            Files.copy(fileContent, file.toPath());
            return file.getName();
        }
	}
	
	public static String addParamToPath(String path, String paramName, String value) {
		try {
            return new URIBuilder(path).setParameter(paramName, value).build().toString();
        } catch (URISyntaxException e) {
            logger.warn("Failed to add parameters to path.", e);
        }
		return path;
	}
	
	public static void uploadIssueFile(Issue issue, HttpServletRequest request) throws IOException, ServletException {
		Part issuePart = request.getPart(REQUEST_PARAM_ISSUE_FILE);
		try (InputStream fileContent = issuePart.getInputStream()) {
			String path = request.getServletContext().getRealPath(ISSUE_FILE_BASE_PATH) +String.format("%d/%d", issue.getPublicationId(), issue.getDateOfPublication().getYear());
			String fileName = issuePart.getSubmittedFileName();
            File file = generateFileForPublicationIssue(path, fileName);
            Files.copy(fileContent, file.toPath());
           issue.setFile(file.getName());
        } 
	}
	
	private static File generateFileForPublicationPicture(String path, String extension) throws IOException {
		String fileName = generateFileId() + extension;
		File file = new File(path, fileName);
		while (file.exists()) {
			file = generateFileForPublicationPicture(path, extension);
		}
		return file;
	}
	
	private static File generateFileForPublicationIssue(String path, String fileName) throws IOException {
		File pathDir = new File(path);
		if (!pathDir.exists()) {
			pathDir.mkdirs();
		}
		File file = new File(path, fileName);
		return file;
	}
	
	private static String generateFileId() {
		return Integer.toString(Math.abs(new Random().nextInt()));
	}
	
	private static String getPictureExtension(Part part) {
		Pattern pattern = Pattern.compile(PIC_EXTENSION_REG_EX);
		Matcher matcher = pattern.matcher(part.getSubmittedFileName());
		if (matcher.find()) {
			return matcher.group();
		}
		return PIC_EXTENSION_DEFAULT;
	}

}
