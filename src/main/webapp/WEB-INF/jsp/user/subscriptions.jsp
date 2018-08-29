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
	<fmt:message key="show_issues" var="show_issues"/>
	<fmt:message key="no_subscriptions" var="no_subscriptions"/>
	<fmt:message key="money" var="money"/>
</fmt:bundle>

<c:set var="counter" value="${1}" />

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

          <h2>${title}</h2>
          <div class="table-responsive">
            <table class="table table-striped table-sm">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>${publication_name}</th>
                  <th>${start_date_header}</th>
                  <th>${end_date_header}</th>
                  <th>${price_header}, ${money}</th>
                  <th>${actions_header}</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
              	<c:forEach items="${active_subscriptions}" var="subscription">
              		<tr>
	              		<td>${counter}</td>
	              		<c:set var="counter" value="${counter + 1}" />
		                <td>${publicationNames.get(subscription.publicationId)}</td>
		                <td>${subscription.startDate}</td>
		                <td>${subscription.endDate}</td>
		                <td>${subscription.price}</td>
		                <c:if test="${subscription.endDate.getMonth() - currentDate.getMonth() > 0 || subscription.endDate.getYear() > currentDate.getYear()}">
		                	<td><form method="post" action="${pageContext.request.contextPath}/controller/user/terminate-subscription">
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
		                <td><form method="get" action="${pageContext.request.contextPath}/controller/user/show-issues">
		                		<input type="hidden" name="subscription_id" value="${subscription.id}"/>
		                		<button type="submit" class="btn btn-primary">${show_issues}</button>
		                	</form>
		                </td>	                
		               </tr>
              	</c:forEach>
              </tbody>
            </table>
            <c:if test="${subscriptions.size() == 0}">
            	<br>
            	<h3>${no_subscriptions }</h3>
            </c:if>
          </div>
        </main>
      </div>
    </div>
    
</body>
</html>