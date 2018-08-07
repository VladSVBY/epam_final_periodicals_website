<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import ="by.epam.periodicials_site.entity.Role" %>

<fmt:setLocale value="${sessionScope.locale}"/>	
<fmt:bundle basename="localization.local" prefix = "publication_page.">
	<fmt:message key="publication" var="publication_header"/>
	<fmt:message key="theme" var="theme_header"/>
	<fmt:message key="type" var="type_header"/>
	<fmt:message key="price" var="price_header"/>
	<fmt:message key="rating" var="rating_header"/>
	<fmt:message key="login_message" var="login_message"/>
	<fmt:message key="money_per_month" var="money_per_month"/>
</fmt:bundle>

<!DOCTYPE html>
<html>
	<head>
		<c:set var="contextPath" value="${pageContext.request.contextPath}" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>${publication.name}</title>
		<link href="<c:url value="/resources/css/bootstrap/bootstrap.min.css" />" rel="stylesheet">
		<link href="<c:url value="/resources/css/publication_page.css" />" rel="stylesheet">
	</head>
<body>
	
	<%@include file="nav.jsp" %>
	
	<div class="content">
		<div class="center">
			<div id="publication">
				<img src="<c:url value="/resources/img/${publication.picturePath}" />" id="picture"/>
				<div id="description">
					<p><b>${publication_header}:</b> ${publication.name}</p>
					<p><b>${theme_header}:</b> ${publication.name}</p>
					<p><b>${type_header}:</b> ${publication.name}</p>
					<p><b>${rating_header}:</b> ${publication.rating}</p>
					<p>${publication.description}</p>
					<p><b>${price_header}:</b> ${publication.price} ${money_per_month}</p>
				</div>
			</div>	
			<div id="subscription">					
					<c:if test="${userRole == null}">
						<hr>
						${login_message}
					</c:if>
					<c:if test="${userRole == Role.CUSTOMER}">
						<hr>
						Subscribe now:<br>
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
						<div class="input-group mb-2" >
						  <div class="input-group-prepend">
						    <label class="input-group-text" for="inputGroupSelect01">Оценка: </label>
						  </div>
						  <select class="custom-select" name="duration" id="duration" onchange="countPrice()">
						    <option value="1">1</option>
						    <option value="2">2</option>
						    <option value="3">3</option>
						    <option value="4">4</option>
						    <option value="5">5</option>
						    <option value="6">6</option>
						    <option value="7">7</option>
						    <option value="8">8</option>
						    <option value="9">9</option>
						    <option value="10">10</option>
						    <option value="11">11</option>
						    <option value="12">12</option>
						  </select>
						</div>
						<div>Price: <span id="total_price"></span></div>
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
					<b><fmt:formatDate value="${review.dateOfPublication}" pattern="yyyy-MM-dd HH:mm" /></b><br>
					Оценка: ${review.mark}<br>
					${review.text}<br>
					<hr>
				</c:forEach>
			</div>
		</div>
	</div>
	
	<script type= "text/javascript">
		function countPrice()
			{
				var price = ${publication.price};
				var duration = document.getElementById("duration").value;
				document.getElementById("total_price").innerText = price * duration;
			}
	</script>
</body>
</html>