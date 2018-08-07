package by.epam.periodicials_site.web.util;

import static by.epam.periodicials_site.web.util.WebConstantDeclaration.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import by.epam.periodicials_site.entity.LocaleType;

public final class HttpUtil {
	
	private HttpUtil() {}
	
	private static final String CONTROLLER_PATTERN = "/controller";
	private static final String REQUEST_PARAM_PUBLICATION_PICTURE = "picture";
	private static final String PUBLICATION_PICTURE_PATH = "/resources/img/";
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
	
	private static File generateFileForPublicationPicture(String path, String extension) throws IOException {
		String fileName = generateFileId() + extension;
		File file = new File(path, fileName);
		while (file.exists()) {
			file = generateFileForPublicationPicture(path, extension);
		}
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
