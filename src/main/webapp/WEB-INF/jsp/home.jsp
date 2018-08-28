<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="paginator" uri="http://corporation.com/custom-tag/paginator"%>

<fmt:setLocale value="${sessionScope.locale}"/>	
<fmt:bundle basename="localization.local" prefix = "home.">
	<fmt:message key="title" var="title"/>
	<fmt:message key="main_header" var="main_header"/>
	<fmt:message key="show_button" var="show_button"/>
	<fmt:message key="theme_option" var="theme_option"/>
	<fmt:message key="sort_option" var="sort_option"/>
	<fmt:message key="index_header" var="index_header"/>
	<fmt:message key="publication_header" var="publication_header"/>
	<fmt:message key="type_header" var="type_header"/>
	<fmt:message key="rating_header" var="rating_header"/>
	<fmt:message key="price_header" var="price_header"/>
	<fmt:message key="theme_option_all" var="theme_option_all"/>
	<fmt:message key="sort_option_alphbetically" var="sort_option_alphabetically"/>
	<fmt:message key="sort_option_rating" var="sort_option_rating"/>
	<fmt:message key="sort_option_price" var="sort_option_price"/>
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
		  <div class="input-group mb-4">
			  <div class="input-group-prepend">
			    <label class="input-group-text" for="inputGroupSelect01">${theme_option}:</label>
			  </div>			  
				<select class="custom-select" id="theme" name="theme" onchange="goToPage()">
					<c:if test="${search_criteria.themeId == 0}">
						<option value="0" selected>${theme_option_all}</option>
					</c:if>
					<c:if test="${search_criteria.themeId != 0}">
						<option value="0">${theme_option_all}</option>
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
			    <label class="input-group-text" for="inputGroupSelect02">${sort_option}:</label>
			  </div>			  
				<select class="custom-select" id="order" name="order" onchange="goToPage()">
					<c:if test="${search_criteria.orderId == 1}">
						<option value="1" selected>${sort_option_alphabetically}</option>
					</c:if>
					<c:if test="${search_criteria.orderId != 1}">
						<option value="1">${sort_option_alphabetically}</option>
					</c:if>
					<c:if test="${search_criteria.orderId == 2}">
						<option value="2" selected>${sort_option_price}</option>>
					</c:if>
					<c:if test="${search_criteria.orderId != 2}">
						<option value="2">${sort_option_price}</option>
					</c:if>
					<c:if test="${search_criteria.orderId == 3}">
						<option value="3" selected>${sort_option_rating}</option>
					</c:if>
					<c:if test="${search_criteria.orderId != 3}">
						<option value="3">${sort_option_rating}</option>
					</c:if>
				</select>
		  </div>
		</div>
		<div class="right">
			<h2>${main_header}</h2>
	      <ul class="list-group">
	      <li class="list-group-item">
	      	<div class="row">
	      		<div class=col-md-1>
	                        ${index_header}
	                    </div>
	                    <div class=col-md-4>
	                        ${publication_header}
	                    </div>
	                    <div class=col-md-1>
	                        ${type_header}
	                    </div>
	                    <div class=col-md-1>
	                        ${rating_header}
	                    </div>
	                    <div class=col-md-1>
	                        ${price_header}
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
	                        ${typeNames.get(publucation.id)}
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
			
			<div class="row">
			<div class="col">
				${pages} 
				<a class="text-success" href="${contextPath}/controller/home?theme=${search_criteria.themeId}&order=${search_criteria.orderId}&currentPage=1&itemsPerPage=3">3</a>
				<a class="text-success" href="${contextPath}/controller/home?theme=${search_criteria.themeId}&order=${search_criteria.orderId}&currentPage=1&itemsPerPage=5">5</a>
				<a class="text-success" href="${contextPath}/controller/home?theme=${search_criteria.themeId}&order=${search_criteria.orderId}&currentPage=1&itemsPerPage=30">30</a>
			</div>		
			<div class="col">
				<paginator:display currentPage="${search_criteria.currentPage}" 
					totalPageCount="50" viewPageCount="3" 
					urlPattern="${contextPath}/controller/home?theme=${search_criteria.themeId}&order=${search_criteria.orderId}&itemsPerPage=${search_criteria.itemsPerPage}&" />
			</div>	
		</div>
		</div>
	</div>
	
	<script type= "text/javascript">
		function goToPage()
			{
				var theme = document.getElementById("theme").value;
				var order = document.getElementById("order").value;
				document.location.href = '${contextPath}/controller/home?theme=' + theme + '&order=' + order;
			}
	</script>
	
</body>
</html>