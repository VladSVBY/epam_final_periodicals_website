<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import ="by.epam.periodicials_site.entity.LocaleType" %>

<fmt:setLocale value="${sessionScope.locale}"/>	
<fmt:bundle basename="localization.local" prefix = "user_menu.">
	<fmt:message key="profile_option" var="profile_option"/>
	<fmt:message key="active_subscriptions_option" var="active_subscriptions_option"/>
	<fmt:message key="subscription_history_option" var="subscription_history_option"/>
	<fmt:message key="balance_operation_history_option" var="balance_operation_history_option"/>
</fmt:bundle>

<fmt:bundle basename="localization.local" prefix = "add_publication_form.">
	<fmt:message key="title" var="title"/>
	<fmt:message key="main_header" var="main_header"/>
	<fmt:message key="theme_select" var="theme_select"/>
	<fmt:message key="type_select" var="type_select"/>
	<fmt:message key="version_ru" var="version_ru"/>
	<fmt:message key="version_en" var="version_en"/>
	<fmt:message key="name" var="name"/>
	<fmt:message key="description" var="description"/>
	<fmt:message key="price" var="price"/>
	<fmt:message key="add_button" var="add_button"/>
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

	<div class="container-fluid" style="margin-top: 60px">
      <div class="row">
        <nav class="col-md-2 d-none d-md-block bg-light sidebar">
          <div class="sidebar-sticky">
            <ul class="nav flex-column mb-2">
            	<li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/controller/profile">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file-text"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path><polyline points="14 2 14 8 20 8"></polyline><line x1="16" y1="13" x2="8" y2="13"></line><line x1="16" y1="17" x2="8" y2="17"></line><polyline points="10 9 9 9 8 9"></polyline></svg>
                  ${profile_option}
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/controller/subscriptions">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file-text"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path><polyline points="14 2 14 8 20 8"></polyline><line x1="16" y1="13" x2="8" y2="13"></line><line x1="16" y1="17" x2="8" y2="17"></line><polyline points="10 9 9 9 8 9"></polyline></svg>
                  ${active_subscriptions_option}
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/controller/subscription-history">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file-text"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path><polyline points="14 2 14 8 20 8"></polyline><line x1="16" y1="13" x2="8" y2="13"></line><line x1="16" y1="17" x2="8" y2="17"></line><polyline points="10 9 9 9 8 9"></polyline></svg>
                  ${subscription_history_option}
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/controller/balance-operation-history">
                  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file-text"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path><polyline points="14 2 14 8 20 8"></polyline><line x1="16" y1="13" x2="8" y2="13"></line><line x1="16" y1="17" x2="8" y2="17"></line><polyline points="10 9 9 9 8 9"></polyline></svg>
                  ${balance_operation_history_option}
                </a>
              </li>
            </ul>
          </div>
        </nav>

        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">

		<div class="bg-success text-white text-center" >
			<h4>Add new theme:</h4>
		</div>

		<form action="${contextPath}/controller/admin/add-theme" method="post">			 	
			<div class="row">
					<div class="form-group col-5">					
						<input type="text" class="form-control" placeholder="${name}" name="name_ru" id="name_ru">
					</div>
					<div class="form-group col-5">					
						<input type="text" class="form-control" placeholder="${name}" name="name_en" id="name_en">
					</div>
					<div class="form-group col-2">					
						<button type="submit" class="btn btn-success">${add_button }</button>
					</div>
			</div>				
		</form>	
		
		<c:forEach items="${themes}" var="theme">
			<form action="${contextPath}/controller/admin/update-theme" method="post">			 	
				<div class="row">
						<div class="form-group col-1">					
							<input type="text" class="form-control" name="theme" value="${theme.id}" readonly="readonly"/>
						</div>
						<div class="form-group col-5">					
							<input type="text" class="form-control" placeholder="${name}" name="name_ru" id="name_ru" value="${theme.getLocalizedNames().get(LocaleType.RU_BY)}">
						</div>
						<div class="form-group col-5">					
							<input type="text" class="form-control" placeholder="${name}" name="name_en" id="name_by" value="${theme.getLocalizedNames().get(LocaleType.EN_US)}">
						</div>
						<div class="form-group col-1">					
							<button type="submit" class="btn btn-success">Update</button>
						</div>
				</div>				
			</form>
		</c:forEach>	
        </main>
      </div>
    </div>
    
</body>
</html>