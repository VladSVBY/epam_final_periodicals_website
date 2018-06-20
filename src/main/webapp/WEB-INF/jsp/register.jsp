<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
		<c:set var="contextPath" value="${pageContext.request.contextPath}" />
		<meta http-equiv="Content-Type" content="text/html; UTF-8">
		<title>SignUp Page</title>
</head>

<body>
	
	<script type="text/javascript">
		function checkLogin(){
			$.ajax({
				url: '${contextPath}/registration/check_login',
				data: ({login: $('#login').val()}),
				success: function(data){
					$('#login_msg').html(data);
				}
			});
		}
	</script>

	<form modelAttribute="user" action="${contextPath}/registration/register_user" method="post">
	    <h1>Sign Up</h1>
	    <fieldset id="inputs">
	    	<label path="login">Login</label><br/>
	        <input path="login" onblur="checkLogin()" /><span id="login_msg"></span><br/>
	        
	        <label path="password">Password</label><br/>   
	        <input path="password" /><br/>
	        
	        <label path="login">First Name</label><br/>
	        <input path="firstName" /><br/>
	        
	        <label path="login">Last Name</label><br/>
	        <input path="lastName" /><br/>
	        
	        <label path="login">Email</label><br/>
	        <input path="email" />
	        
	        <div><c:out value="${fail_msg}"></c:out></div>
	    </fieldset><br/>
	    <fieldset id="actions">
	        <input type="submit" id="submit" value="Sign Up">
	    </fieldset>
	</form>
</body>
</html>