<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
			<h4>${main_header }</h4>
		</div>

		<form action="${contextPath}/controller/admin/add-publication" method="post" enctype="multipart/form-data">	
						<div class="input-group mb-2" >
						  <div class="input-group-prepend">
						    <label class="input-group-text" for="theme">${theme_select}:</label>
						  </div>
						  <select class="custom-select col-md-5" name="theme" id="theme">
						    <c:forEach items="${themes}" var="theme">
						    	<option value="${theme.id}">${theme.name }</option>
						    </c:forEach>
						  </select>
						 </div>
						<div class="input-group mb-2" >
						  <div class="input-group-prepend">
						    <label class="input-group-text" for="type">${type_select}:</label>
						  </div>
						  <select class="custom-select col-md-5" name="type" id="type">
						    <option value="0">0</option>
						    <option value="1">1</option>
						    <option value="2">2</option>
						    <option value="3">3</option>
						    <option value="4">4</option>
						    <option value="5">5</option>
						  </select>
						</div>				
			<div class="row">					
				<div class="col" style="border-bottom: thin solid black; border-right: thin solid black; border-top: thin solid black;">					
					<div class="form-group font-weight-bold">					
						${version_ru }
					</div>
					<div class="form-group">					
						<input type="text" class="form-control" placeholder="${name}" name="name_ru" id="name_ru">
					</div>
					<div class="form-group">
						<input type="text" class="form-control" placeholder="${description}" name="description_ru" id="description_ru">
					</div>					
				</div>
				
				<div class="col" style="border-bottom : thin solid black; border-top: thin solid black;">					
					<div class="form-group font-weight-bold">					
						${version_en}
					</div>					
					<div class="form-group">					
						<input type="text" class="form-control" placeholder="${name}" name="name_en" id="name_en">
					</div>
					<div class="form-group">
						<input type="text" class="form-control" placeholder="${description}" name="description_en" id="description_en">
					</div>									
				</div>
			</div>
			<div class="row">	
				<div class="col">
					<div class="form-group">
						${price}
						<input type="number" class="form-control" name="price" id="price" value="0" min=0 >
					</div>
				</div>	
			</div>
			<div class="form-group">
				<div class="form-group">
						<label for="pivture">Picture:</label> 
						<input type="file" class="form-control-file" name="picture" id="picture">																						
					</div>
			</div>	 	
					
			<button type="submit" class="btn btn-success">${add_button }</button>		
		</form>		
        </main>
      </div>
    </div>
    
</body>
</html>