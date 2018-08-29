<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>	

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
	<fmt:message key="picture" var="picture"/>
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
        
        <%@include file="admin_nav.jsp" %>

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
						    <c:forEach items="${types}" var="type">
						    	<option value="${type.id}">${type.name }</option>
						    </c:forEach>
						  </select>
						</div>				
			<div class="row">					
				<div class="col" style="border-bottom: thin solid black; border-right: thin solid black; border-top: thin solid black;">					
					<div class="form-group font-weight-bold">					
						${version_ru }
					</div>
					<div class="form-group">					
						<input type="text" class="form-control" placeholder="${name}" name="name_ru" id="name_ru" required>
					</div>
					<div class="form-group">
						<input type="text" class="form-control" placeholder="${description}" name="description_ru" id="description_ru" required>
					</div>					
				</div>
				
				<div class="col" style="border-bottom : thin solid black; border-top: thin solid black;">					
					<div class="form-group font-weight-bold">					
						${version_en}
					</div>					
					<div class="form-group">					
						<input type="text" class="form-control" placeholder="${name}" name="name_en" id="name_en" required>
					</div>
					<div class="form-group">
						<input type="text" class="form-control" placeholder="${description}" name="description_en" id="description_en" required>
					</div>									
				</div>
			</div>
			<div class="row">	
				<div class="col-2">
					<div class="form-group">
						${price}
						<input type="number" class="form-control" name="price" id="price" value="0" min=0  required>
					</div>
				</div>	
			</div>
			<div class="form-group">
				<div class="form-group">
						<label for="picture">${picture}:</label> 
						<input type="file" class="form-control-file" name="picture" id="picture" required>																						
					</div>
			</div>
			<div style="color: red">${publication_fail_message }</div>	 						
			<button type="submit" class="btn btn-success">${add_button }</button>		
		</form>		
        </main>
      </div>
    </div>
    
</body>
</html>