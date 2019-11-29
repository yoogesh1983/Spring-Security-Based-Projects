<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>

<head>
<link href="${pageContext.request.contextPath}/static/css/common.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/sign-in.css" rel="stylesheet" type="text/css">
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
         	<td>Remember me:</td><td><input type='checkbox' name='remember-me' /></td>
            <!-- <td>Remember me:</td><td><input type='checkbox' name='_spring_security_remember_me' checked="checked" /></td> -->
    	</tr>
    	
    	<tr>
         	<td colspan='2'><input name="LOGIN" type="submit" value="Login"/></td>
   	 	</tr>
  </table>
</form>


 <div > 
 <p><a href="<c:url value='/sign-up'/>">SignUp</a></p>
 </div>
 
 
  <!-- OAuth2 Logic Started -->
 <div > 
 	<p><a href="<c:url value='/oauth_login'/>">OAuth2</a></p>
 </div>
 
  <c:if test="${urls_oauth2 != null}">
  
	 <!-- We Just need this file at compile time and need not refresh data every-time the file loads since these values are kind of static so that we prefer include directive over include action tag -->
	 <%@include  file = "oauth2.jsp" %>
	 
     <%-- <jsp:include page="oauth2.jsp" /> --%>
  </c:if>
 <!-- OAuth2 Logic Finished -->
  
</body>
</html>

