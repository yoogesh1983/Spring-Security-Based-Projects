<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> --%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>
<!-- <html> -->
<!-- 	<head> -->
<!-- 		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
<!-- 		<title>Login page</title> -->
<%-- 		<link href="<c:url value='/static/css/bootstrap.css' />"  rel="stylesheet"></link> --%>
<%-- 		<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link> --%>
<!-- 		<link rel="stylesheet" type="text/css" href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.css" /> -->
<!-- 	</head> -->

<!-- 	<body> -->
<!-- 		<div id="mainWrapper"> -->
<!-- 			<div class="login-container"> -->
<!-- 				<div class="login-card"> -->
<!-- 					<div class="login-form"> -->
<%-- 						<c:url var="loginUrl" value="/do-sign-in" /> --%>
<%-- 						<form action="${loginUrl}" method="post" class="form-horizontal"> --%>
<%-- 							<c:if test="${param.error != null}"> --%>
<!-- 								<div class="alert alert-danger"> -->
<!-- 									<p>Invalid username and password.</p> -->
<!-- 								</div> -->
<%-- 							</c:if> --%>
<%-- 							<c:if test="${param.logout != null}"> --%>
<!-- 								<div class="alert alert-success"> -->
<!-- 									<p>You have been logged out successfully.</p> -->
<!-- 								</div> -->
<%-- 							</c:if> --%>
<!-- 							<div class="input-group input-sm"> -->
<!-- 								<label class="input-group-addon" for="username"><i class="fa fa-user"></i></label> -->
<!-- 								<input type="text" class="form-control" id="username" name="username" placeholder="Enter Username" required> -->
<!-- 							</div> -->
<!-- 							<div class="input-group input-sm"> -->
<!-- 								<label class="input-group-addon" for="password"><i class="fa fa-lock"></i></label>  -->
<!-- 								<input type="password" class="form-control" id="password" name="password" placeholder="Enter Password" required> -->
<!-- 							</div> -->
<%-- 							<input type="hidden" name="${_csrf.parameterName}" 	value="${_csrf.token}" /> --%>
								
<!-- 							<div class="form-actions"> -->
<!-- 								<input type="submit" -->
<!-- 									class="btn btn-block btn-primary btn-default" value="Log in"> -->
<!-- 							</div> -->
<!-- 						</form> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->

<!-- 	</body> -->
<!-- </html> -->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
<link href="${pageContext.request.contextPath}/static/css/mycss.css" rel="stylesheet" type="text/css">
<title>Login Page</title>

</head>
 <!-- Display the image -->
<img src="<c:url value="/static/images/Spring1.png"/>"/>

<h3>Login with Username and Password</h3>

<c:if test="${param.error != null}">
	<p class="error">Login failed. Check that your username and password are correct.</p>
</c:if>


<%
//    String error = request.getParameter("error");
//    if(error !=null)
//    {
%>	 
<!-- <label style="color:red;">Login failed!! Check that your username and password are correct!!</label> -->
<%  
//    }
%>
	

<form action='${pageContext.request.contextPath}/do-sign-in' method='POST'>
 <table class="formtable">
    	<tr>
        	<td>NAME:</td><td><input type='text' name='username' value=''></td>
    	</tr>
    
    	<tr>
        	<td>PASSWORD:</td><td><input type='password' name='password'/></td>
    	</tr>
		
		 <tr>
        	<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" /> <!-- This must be provided -->
    	</tr>
    
	
    	<tr>
         	<td>Remember me:</td><td><input type='checkbox' name='_spring_security_remember_me' /></td>
<!--             <td>Remember me:</td><td><input type='checkbox' name='_spring_security_remember_me' checked="checked" /></td> -->
    	</tr>
    	
    	<tr>
         	<td colspan='2'><input name="LOGIN" type="submit" value="Login"/></td>
   	 	</tr>
  </table>
</form>


 <div > 
 <p><a href="<c:url value='/sign-up'/>">SignUp</a></p>
 </div>
 
 
</body>
</html>

