<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>	
<fmt:bundle basename="localization.local" prefix = "register.">
	<fmt:message key="title" var="title"/>
	<fmt:message key="main_header" var="main_header"/>
	<fmt:message key="placeholder_login" var="placeholder_login"/>
	<fmt:message key="placeholder_password" var="placeholder_password"/>
	<fmt:message key="placeholder_name" var="placeholder_name"/>
	<fmt:message key="placeholder_surname" var="placeholder_surname"/>
	<fmt:message key="placeholder_email" var="placeholder_email"/>
	<fmt:message key="button_register" var="button_register"/>
	<fmt:message key="link_login" var="link_register"/>
</fmt:bundle>

<!DOCTYPE html>
<html>
	<head>
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
	        <input id="username" name="login" type="text" placeholder="${placeholder_login}" required autofocus />   
	        <input  id="password" name="password" type="password" placeholder="${placeholder_password}" required />
	        <input  id="password" name="name" type="text" placeholder="${placeholder_name}" required />
	        <input  id="password" name="surName" type="text" placeholder="${placeholder_surname}" required />
	        <input  id="password" name="email" type="text" placeholder="${placeholder_email}" required />
	    </fieldset>
	    <fieldset id="actions">
	        <input type="submit" id="submit" value="${button_register}">
	        <a href="login">${link_login}</a>
	    </fieldset>
	</form>
</body>
</html>