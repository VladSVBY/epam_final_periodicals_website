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

	<form action="<c:url value="register" />" method="post">
	    <h1>Sign Up</h1>
	    <fieldset id="inputs">
	    	<label for="login">Login</label><br/>
	        <input name="login" onblur="checkLogin()" /><span id="login_msg"></span><br/>
	        
	        <label for="password">Password</label><br/>   
	        <input name="password" /><br/>
	        
	        <label for="name">First Name</label><br/>
	        <input name="name" /><br/>
	        
	        <label for="surName">Last Name</label><br/>
	        <input name="surName" /><br/>
	        
	        <label for="email">Email</label><br/>
	        <input name="email" />
	        
	        <div><c:out value="${fail_msg}"></c:out></div>
	    </fieldset><br/>
	    <fieldset id="actions">
	        <input type="submit" id="submit" value="Sign Up">
	    </fieldset>
	</form>
</body>
</html>