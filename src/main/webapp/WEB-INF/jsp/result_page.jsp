<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import ="by.epam.periodicials_site.entity.LocaleType" %>

<fmt:setLocale value="${sessionScope.locale}"/>	
<fmt:bundle basename="localization.local" prefix = "result_page.">
	<fmt:message key="back_button" var="back_button"/>
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
	
	<%@include file="nav.jsp" %>

	<div class="container-fluid" style="margin-top: 60px">
      <div class="row">
        	
        <%@include file="admin/admin_nav.jsp" %>
			<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
			<div>
				<h2>${message}</h2>
			</div>

		<div class="row">
			<div class="form-group col-2">					
				<a href="${return_page}"><button type="button" class="btn btn-success">${back_button}</button></a>
			</div>
		</div>
		</main>
		
      </div>
    </div>
    
</body>
</html>