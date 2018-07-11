<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="paginator" uri="http://corporation.com/custom-tag/paginator"%>

<fmt:setLocale value="${sessionScope.locale}"/>	
<fmt:bundle basename="localization.local" prefix = "home.">
	<fmt:message key="title" var="title"/>
	<fmt:message key="type_option" var="title"/>
	<fmt:message key="theme_option" var="title"/>
	<fmt:message key="sort_option" var="title"/>
</fmt:bundle>

<!DOCTYPE html>
<html>
	<head>
		<c:set var="contextPath" value="${pageContext.request.contextPath}" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>${title}</title>
		<link href="<c:url value="/resources/css/bootstrap/bootstrap.min.css" />" rel="stylesheet">
		
	</head>
<body>
	
	<%@include file="../nav.jsp" %>
	<div class="content">
		<div class="left">
			${publication.name}<br>
			${publication.rating}<br>
			${publication.description}<br>
			${publication.price}<br>
		</div>
		<div class="right">
	      
		</div>
	</div>
</body>
</html>