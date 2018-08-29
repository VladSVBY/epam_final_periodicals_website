<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
    
<fmt:setLocale value="${sessionScope.locale}"/>	
	<fmt:bundle basename="localization.local" prefix = "navbar.">
	<fmt:message key="home" var="home"/>
	<fmt:message key="login" var="login"/>
	<fmt:message key="register" var="register"/>
	<fmt:message key="logout" var="logout"/>
</fmt:bundle>

	<nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-dark">
      <a class="navbar-brand mr-auto mr-lg-0" href="<c:url value='home'/>">${home}</a>
      <button class="navbar-toggler p-0 border-0" type="button" data-toggle="offcanvas">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="navbar-collapse offcanvas-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
          
        </ul>

        
        <ul class="navbar-nav navbar-right">
        	<c:if test="${userName != null}">
        		<li class="nav-item active">
        		<c:if test="${userRole == Role.CUSTOMER}">
            		<a class="nav-link" href="${contextPath}/controller/user/profile">${userName}</a>
            	</c:if>
            	<c:if test="${userRole == Role.ADMIN}">
            		<a class="nav-link" href="${contextPath}/controller/admin/add-publicatio-form">${userName}</a>
            	</c:if>
	         	</li>
	         	<li class="nav-item active">
	            	<a class="nav-link" href="<c:url value='logout'/>">${logout}</a>
	         	</li>
        	</c:if>
        	<c:if test="${userName == null}">
	        	<li class="nav-item active">
	            	<a class="nav-link" href="${contextPath}/controller/login">${login}</a>
	         	</li>
	         	<li class="nav-item active">
	            	<a class="nav-link" href="${contextPath}/controller/register">${register}</a>
	         	</li>
         	</c:if>
        	<li class="nav-item active">
            	<a class="nav-link" style="color: white" href="${contextPath}/controller/change-locale?locale=ru_BY">RU</a>
          	</li>
          	<li class="nav-item active">
            	<a class="nav-link" style="color: white" href="${contextPath}/controller/change-locale?locale=en_US">EN</a>
          	</li>
        </ul>
      </div>
    </nav>

