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
	<fmt:message key="login_button" var="login_button"/>
	<fmt:message key="register_button" var="register_button"/>
	<fmt:message key="money_per_month" var="money_per_month"/>
	<fmt:message key="add_review_header" var="add_review_header"/>
	<fmt:message key="make_subscription_header" var="make_subscription_header"/>
	<fmt:message key="reviews" var="reviews_header"/>
	<fmt:message key="review_mark" var="review_mark"/>
	<fmt:message key="review_text" var="review_text"/>
	<fmt:message key="add_review_button" var="add_review_button"/>
	<fmt:message key="update_review_button" var="update_review_button"/>
	<fmt:message key="delete_review_button" var="delete_review_button"/>
	<fmt:message key="add_issue_header" var="add_issue_header"/>
	<fmt:message key="add_issue_button" var="add_issue_button"/>
	<fmt:message key="issue_description" var="issue_description"/>
	<fmt:message key="subscription_price" var="subscription_price"/>
	<fmt:message key="subscription_start_month" var="subscription_start_month"/>
	<fmt:message key="subscription_duration" var="subscription_duration"/>
	<fmt:message key="create_subscription_button" var="create_subscription_button"/>
	<fmt:message key="issue_date" var="issue_date"/>
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
					<p><b>${theme_header}:</b> ${theme.name}</p>
					<p><b>${type_header}:</b> ${type.name}</p>
					<p><b>${rating_header}:</b> ${publication.rating}</p>
					<p>${publication.description}</p>
					<p><b>${price_header}:</b> ${publication.price} ${money_per_month}</p>
				</div>
			</div>	
			<div id="subscription">					
					<c:if test="${userRole == null}">
						<hr>
						<div style="margin: 0 auto; width:500px">
							<b><a href="${contextPath}/controller/login">${login_button}</a></b> ${login_message} <a href="${contextPath}/controller/register" style="float:right">${register_button}</a>
						</div>
					</c:if>
					<c:if test="${userRole == Role.CUSTOMER}">
						<hr>
						<h2>${make_subscription_header}:</h2><br>
						<form method="get" action="${pageContext.request.contextPath}/controller/user/subscribe">
							<input type="hidden" name="publication_id" value="${publication.id}" />
							<div class="input-group mb-2" >
							  <div class="input-group-prepend">
							    <label class="input-group-text" for="inputGroupSelect01">${subscription_start_month }: </label>
							  </div>
							  <select class="custom-select" name="start_month">
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
							<div class="input-group mb-2" >
							  <div class="input-group-prepend">
							    <label class="input-group-text" for="inputGroupSelect01">${subscription_duration }: </label>
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
							<div>${subscription_price} <span id="total_price"></span></div>
							<button type="submit" class="btn btn-primary">${create_subscription_button}</button>
						</form>
					</c:if>					
			</div>
			<c:if test="${userRole == Role.ADMIN}">
				<div>
					<hr>
					<h3>${add_issue_header}:</h3>
					<form action="${contextPath}/controller/admin/upload-issue" method="post" enctype="multipart/form-data">
						<input type="hidden" name="publication_id" value="${publication.id }">
						<div class="input-group mb-3">
						  <div class="input-group-prepend">
						    <span class="input-group-text" id="inputGroup-sizing-default">${issue_date }</span>
						  </div>
						  <input type="date" name="date_of_publication" required="required" class="form-control" />
						</div>
						<div class="input-group">
							  <div class="input-group-prepend">
							    <span class="input-group-text">${issue_description}:</span>
							  </div>
							  <textarea class="form-control" aria-label="With textarea" name="description" required></textarea>
						</div>
						<div>
					  		<input type="file" required="required" name="issue_file" id="issue_file">
						</div>
						<button type="submit" class="btn btn-primary" style="margin-top: 10px">${add_issue_button }</button>
					</form>		
					
				</div>
			</c:if>
			<div id="reviews">
				<c:if test="${userRole == Role.CUSTOMER || userRole == Role.ADMIN}">
					<hr>
					<h3>${add_review_header}:</h3>
					<form action="${contextPath}/controller/user/add-review" method="post">
						<input type="hidden" name="id_publication" value="${publication.id}" />
						<div class="input-group mb-2" >
						  <div class="input-group-prepend">
						    <label class="input-group-text" for="inputGroupSelect01">${review_mark}: </label>
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
						    <span class="input-group-text">${review_text}:</span>
						  </div>
						  <textarea class="form-control" aria-label="With textarea" name="text" required></textarea>
						</div>
						<button type="submit" class="btn btn-primary" style="margin-top: 10px">${add_review_button }</button>
					</form>
				</c:if>
				<hr>
				<c:if test="${reviews.size() != 0}">
					<h2>${reviews_header }:</h2>
				</c:if>
				<c:forEach items="${reviews}" var="review">
					<c:if test="${userRole != Role.ADMIN}">
						<b><fmt:formatDate value="${review.dateOfPublication}" pattern="yyyy-MM-dd HH:mm" /></b><br>
						${review_mark}: ${review.mark}<br>
						${review.text}<br>
						<hr>
					</c:if>
					<c:if test="${userRole == Role.ADMIN}">
						<b><fmt:formatDate value="${review.dateOfPublication}" pattern="yyyy-MM-dd HH:mm" /></b><br>
						<form method="post">
							<input type="hidden" name="review_id" value="${review.id}" />
							<div class="input-group mb-2" >
							  <div class="input-group-prepend">
							    <label class="input-group-text" for="inputGroupSelect01">${review_mark}: </label>
							  </div>
							  <select class="custom-select" name="mark">
							    <option value="0">0</option>
							    <option value="1">1</option>
							    <option value="2">2</option>
							    <option value="3">3</option>
							    <option value="4">4</option>
							    <option value="5">5</option>
							    <option value="${review.mark}" selected>${review.mark}</option>
							  </select>
							</div>
							<div class="input-group">
							  <div class="input-group-prepend">
							    <span class="input-group-text">${review_text}:</span>
							  </div>
							  <textarea class="form-control" aria-label="With textarea" name="text">${review.text}</textarea>
							</div>
							<button type="submit" formaction="${contextPath}/controller/admin/update-review" class="btn btn-primary" style="margin-top: 10px">${update_review_button }</button>
							<button type="submit" formaction="${contextPath}/controller/admin/delete-review" class="btn btn-danger" style="margin-top: 10px">${delete_review_button }</button>
						</form>
						<hr>
					</c:if>
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