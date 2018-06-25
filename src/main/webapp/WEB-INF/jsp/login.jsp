<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>	
<fmt:bundle basename="localization.local" prefix = "login.">
	<fmt:message key="title" var="title"/>
	<fmt:message key="main_header" var="main_header"/>
	<fmt:message key="placeholder_login" var="placeholder_login"/>
	<fmt:message key="placeholder_password" var="placeholder_password"/>
	<fmt:message key="button_login" var="button_login"/>
	<fmt:message key="link_register" var="link_register"/>
	<fmt:message key="msg_login_failed" var="msg_login_failed"/>
</fmt:bundle>

<!DOCTYPE html>
<html>
	<head>
		<c:set var="contextPath" value="${pageContext.request.contextPath}" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>${title}</title>
		<link href="<c:url value="/resources/css/login.css" />" rel="stylesheet" />
		<link href="<c:url value="/resources/css/bootstrap/bootstrap.min.css" />" rel="stylesheet"> 
	</head>
<body>

	<%@include file="nav.jsp" %>
	
	<form action="<c:url value="login" />" id="login" method="post">
	    <h1>${main_header}</h1>
	    <fieldset id="inputs">
	        <input id="username" name="login_or_email" type="text" placeholder="${placeholder_login}" required autofocus />   
	        <input  id="password" name="password" type="password" placeholder="${placeholder_password}" required />
	    </fieldset>
	    <span style="color: red"><c:if test="${loginFailedMsg != null}">${msg_login_failed}</c:if></span>
	    <fieldset id="actions">
	        <input type="submit" id="submit" value="${button_login}">
	        <a href="register">${link_register}</a>
	    </fieldset>
	</form>
</body>
</html>