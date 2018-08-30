<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>	
<fmt:bundle basename="localization.local" prefix = "balance.">
	<fmt:message key="main_title" var="main_title"/>
	<fmt:message key="sum" var="sum"/>
	<fmt:message key="button" var="button"/>
</fmt:bundle>
<fmt:bundle basename="localization.local" prefix = "profile.">
	<fmt:message key="title" var="title"/>
	<fmt:message key="name" var="name"/>
	<fmt:message key="sur_name" var="sur_name"/>
	<fmt:message key="balance" var="balance"/>
	<fmt:message key="email" var="email"/>
	<fmt:message key="money" var="money"/>
</fmt:bundle>

<!DOCTYPE html>
<html>
	<head>
		<c:set var="contextPath" value="${pageContext.request.contextPath}" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>${title}</title>
		<link href="<c:url value="/resources/css/bootstrap/bootstrap.min.css" />" rel="stylesheet">
		<link rel="stylesheet" href="<c:url value='/resources/css/jquery.fancybox.min.css'/>">
		<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	</head>
<body>
	
	<%@include file="../nav.jsp" %>

	<div class="container-fluid" style="margin-top: 60px">
      <div class="row">
        
        <%@include file="user-nav.jsp" %> 
        
        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4"><div style="position: absolute; left: 0px; top: 0px; right: 0px; bottom: 0px; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;" class="chartjs-size-monitor"><div class="chartjs-size-monitor-expand" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"><div style="position:absolute;width:1000000px;height:1000000px;left:0;top:0"></div></div><div class="chartjs-size-monitor-shrink" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"><div style="position:absolute;width:200%;height:200%;left:0; top:0"></div></div></div>

	        	<h2>${title}</h2>
	        	<b>${name}</b>: ${user.name}<br>
	        	<b>${sur_name}</b>: ${user.surName}<br>
	        	<b>${email}</b>: ${user.email}<br>
	        	<br>
	        	${balance}: ${user.balance} ${money }<br>
	        	 
	        	<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
				  ${button }
				</button> 	
        </main>
      </div>
      
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		      	<h4 class="modal-title" id="myModalLabel">${main_title}</h4>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		        <form action="${pageContext.request.contextPath}/controller/user/replenish-balance" method="post" name="balance" id="balance">
		        	<div class="input-group">
							  <div class="input-group-prepend">
							    <span class="input-group-text">${sum}, ${money }:</span>
							  </div>
							  <input class="form-control" name="sum" pattern="[1-9]{1,3}.?[0-9]{0,2}" required></input>
						</div>
			   </form>  
		      </div>
		      <div class="modal-footer">
		        <button type="submit" class="btn btn-primary" form="balance">${button }</button>
		      </div>
		    </div>
		  </div>
		</div>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

</body>
</html>