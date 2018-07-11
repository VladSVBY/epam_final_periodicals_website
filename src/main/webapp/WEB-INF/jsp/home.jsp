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
	
	<%@include file="nav.jsp" %>
	<div class="content">
		<div class="left">
		<form action="${contextPath}/controller/home" method="get"> 
		  <div class="input-group mb-4">
			  <div class="input-group-prepend">
			    <label class="input-group-text" for="inputGroupSelect01">Theme:</label>
			  </div>			  
				<select class="custom-select" id="inputGroupSelect01" name="theme">
					<c:if test="${search_criteria.themeId == 0}">
						<option value="0" selected>All</option>
					</c:if>
					<c:if test="${search_criteria.themeId != 0}">
						<option value="0">All</option>
					</c:if>
				 	<c:forEach items="${themes}" var="theme">
				 		<c:if test="${search_criteria.themeId == theme.id}">
							<option value="${theme.id}" selected>${theme.name}</option>
						</c:if>
						<c:if test="${search_criteria.themeId != theme.id}">
							<option value="${theme.id}">${theme.name}</option>
						</c:if>
					</c:forEach>
				</select>
		  </div>
		  <div class="input-group mb-4">
			  <div class="input-group-prepend">
			    <label class="input-group-text" for="inputGroupSelect02">Sort by:</label>
			  </div>			  
				<select class="custom-select" id="inputGroupSelect02" name="order">
					<c:if test="${search_criteria.orderId == 1}">
						<option value="1" selected>Alphabetically</option>
					</c:if>
					<c:if test="${search_criteria.orderId != 1}">
						<option value="1">Alphabetically</option>
					</c:if>
					<c:if test="${search_criteria.orderId == 2}">
						<option value="2" selected>Price</option>>
					</c:if>
					<c:if test="${search_criteria.orderId != 2}">
						<option value="2">Price</option>
					</c:if>
					<c:if test="${search_criteria.orderId == 3}">
						<option value="3" selected>Rating</option>
					</c:if>
					<c:if test="${search_criteria.orderId != 3}">
						<option value="3">Rating</option>
					</c:if>
				</select>
		  </div>
		  <button type="submit">Show</button>
		  </form>
		</div>
		<div class="right">
	      <ul class="list-group">
	      <li class="list-group-item">
	      	<div class="row">
	      		<div class=col-md-1>
	                        Индекс
	                    </div>
	                    <div class=col-md-2>
	                        Индекс
	                    </div>
	                    <div class=col-md-1>
	                        Индекс
	                    </div>
	            </div>
	       </li>
	      <c:forEach items="${publications}" var="publication">
			  <a href="${contextPath}/controller/publication?id=${publication.id}"><li class="list-group-item">
	      		<div class="row">
	      				<div class=col-md-1>
	                        ${publication.id}
	                    </div>
	                    <div class=col-md-4>
	                        ${publication.name}
	                    </div>
	                    <div class=col-md-1>
	                        ${publication.price}
	                    </div>
	                    <div class=col-md-1>
	                        ${publication.rating}
	                    </div>
		            </div>
		       </li></a>
			</c:forEach>	
			</ul>
		</div>
	</div>
</body>
</html>