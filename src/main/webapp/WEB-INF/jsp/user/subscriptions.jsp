<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="currentDate" class="java.util.Date" />

<fmt:setLocale value="${sessionScope.locale}"/>	
<fmt:bundle basename="localization.local" prefix = "subscriptions_page.">
	<fmt:message key="title_active_subscriptions" var="title"/>
	<fmt:message key="publication_name" var="publication_name"/>
	<fmt:message key="start_date_header" var="start_date_header"/>
	<fmt:message key="end_date_header" var="end_date_header"/>
	<fmt:message key="price_header" var="price_header"/>
	<fmt:message key="actions_header" var="actions_header"/>
	<fmt:message key="action_terminate" var="action_terminate"/>
	<fmt:message key="action_extend" var="action_extend"/>
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

        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4"><div style="position: absolute; left: 0px; top: 0px; right: 0px; bottom: 0px; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;" class="chartjs-size-monitor"><div class="chartjs-size-monitor-expand" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"><div style="position:absolute;width:1000000px;height:1000000px;left:0;top:0"></div></div><div class="chartjs-size-monitor-shrink" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"><div style="position:absolute;width:200%;height:200%;left:0; top:0"></div></div></div>

          <h2>${title}</h2>
          <div class="table-responsive">
            <table class="table table-striped table-sm">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>${publication_name}</th>
                  <th>${start_date_header}</th>
                  <th>${end_date_header}</th>
                  <th>${price_header}</th>
                  <th>${actions_header}</th>
                </tr>
              </thead>
              <tbody>
              	<c:forEach items="${active_subscriptions}" var="subscription">
              		<tr>
	              		<td>${subscription.id}</td>
		                <td>${publicationNames.get(subscription.publicationId)}</td>
		                <td>${subscription.startDate}</td>
		                <td>${subscription.endDate}</td>
		                <td>${subscription.price}</td>
		                <c:if test="${subscription.endDate.getMonth() - currentDate.getMonth() > 0}">
		                	<td><form method="post" action="${pageContext.request.contextPath}/controller/terminate-subscription">
		                		<input type="hidden" name="subscription_id" value="${subscription.id}"/>
		                		<button type="submit" class="btn btn-danger">${action_terminate}</button>
		                	</form></td>
		                </c:if>
		                <c:if test="${subscription.endDate.getMonth() - currentDate.getMonth() == 0}">
		                	<td><form method="get" action="${pageContext.request.contextPath}/controller/publication">
		                		<input type="hidden" name="id" value="${subscription.publicationId}"/>
		                		<button type="submit" class="btn btn-primary">${action_extend}</button>
		                	</form></td>
		                </c:if>
		               </tr>
              	</c:forEach>
              </tbody>
            </table>
          </div>
        </main>
      </div>
    </div>
    
</body>
</html>