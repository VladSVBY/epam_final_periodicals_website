<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>	
<fmt:bundle basename="localization.local" prefix = "home.">
	<fmt:message key="title" var="title"/>
</fmt:bundle>
<fmt:bundle basename="localization.local" prefix = "user_menu.">
	<fmt:message key="profile_option" var="profile_option"/>
	<fmt:message key="active_subscriptions_option" var="active_subscriptions_option"/>
	<fmt:message key="subscription_history_option" var="subscription_history_option"/>
	<fmt:message key="balance_operation_history_option" var="balance_operation_history_option"/>
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
        
        <%@include file="user-nav.jsp" %> 
        
        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4"><div style="position: absolute; left: 0px; top: 0px; right: 0px; bottom: 0px; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;" class="chartjs-size-monitor"><div class="chartjs-size-monitor-expand" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"><div style="position:absolute;width:1000000px;height:1000000px;left:0;top:0"></div></div><div class="chartjs-size-monitor-shrink" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"><div style="position:absolute;width:200%;height:200%;left:0; top:0"></div></div></div>

	        	<h2>Профиль</h2>
	        	Имя: ${user.name}<br>
	        	Имя: ${user.surName}<br>
	        	Имя: ${user.email}<br>
	        	<br>
	        	Баланс: ${user.balance} 
	        	<form action="${pageContext.request.contextPath}/controller/replenish-balance" method="post">
	        		<input type="number" max="1000" name="sum"/>
	        		<input type="submit" value="Пополнить" />
	        	</form>    	
        </main>
      </div>
    </div>
    
</body>
</html>