<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import ="by.epam.periodicials_site.entity.Role" %>

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
		<link href="<c:url value="/resources/css/publication_page.css" />" rel="stylesheet">
	</head>
<body>
	
	<%@include file="nav.jsp" %>
	
	<div class="content">
		<div class="center">
			<div id="publication">
				<img src="<c:url value="/resources/img/14454972910.jpg" />" id="picture"/>
				<div id="description">
					<p> Издание: <b>${publication.name}</b></p>
					<p> Рейтинг: <b>${publication.rating}</b></p>
					<p>${publication.description}</p>
					<p>Цена: <b>${publication.price} руб/мес</b><br>
				</div>
			</div>	
			<div id="subscription">				
					<%-- <c:if test="${userRole.equals(Role.CUSTOMER)}">
						<hr>
						Оформите подписку:
						<input type="month" />
						<hr>
					</c:if> --%>	
					<c:if test="${userRole == null}">
						<hr>
						Войдите, чтобы оставить отзыв
					</c:if>						
			</div>
			<div id="reviews">
				<c:if test="${userRole == Role.CUSTOMER || userRole == Role.ADMIN}">
					<hr>
					<h3>Оставьте ваш отзыв:</h3>
					<form action="${contextPath}/controller/add-review" method="post">
						<input type="hidden" name="id_publication" value="${publication.id}" />
						<div class="input-group mb-2" >
						  <div class="input-group-prepend">
						    <label class="input-group-text" for="inputGroupSelect01">Оценка: </label>
						  </div>
						  <select class="custom-select" name="mark">
						    <option value="0">0</option>
						    <option value="1">1</option>
						    <option value="2">2</option>
						    <option value="3">3</option>
						    <option value="4">4</option>
						    <option value="5">5</option>
						  </select>
						</div>
						<div class="input-group">
						  <div class="input-group-prepend">
						    <span class="input-group-text">Text:</span>
						  </div>
						  <textarea class="form-control" aria-label="With textarea" name="text"></textarea>
						</div>
						<button type="submit" class="btn btn-primary">Отправить</button>
					</form>
				</c:if>
				<hr>
				<h2>Отзывы:</h2>
				<c:forEach items="${reviews}" var="review">
					<b>${review.dateOfPublication}</b><br>
					Оценка: ${review.mark}<br>
					${review.text}<br>
					<hr>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>