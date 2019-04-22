<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="springform" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
<title>my-account-DBA</title>
 <jsp:include page="/WEB-INF/views/theme/theme-include-my-account-dba.jsp" />
</head>
<body>

<!-- Display image -->
<img src="<c:url value="/static/images/Welcome.gif"/>"/>

<h3>****DBA Account****</h3>

<h3>Welcome, ${username}!</h3>

 <a href="${pageContext.request.contextPath}/my-account-user"> Click here to go for User account page!!!</a> </br>
<a href="${pageContext.request.contextPath}/my-account-admin"> Click here to go for Admin account page!!! </a> </br>
<a href="${pageContext.request.contextPath}/my-account-dba"> Go back to Home!!! </a> </br>

<h3>Method label Access Check</h3>
<a href="${pageContext.request.contextPath}/secure/dba"><span style="color:white">DBA</span></a> | 
<a href="${pageContext.request.contextPath}/secure/admin"><span style="color:white">ADMIN</span></a> |
<a href="${pageContext.request.contextPath}/secure/user"><span style="color:white">USER</span></a> |
<a href="${pageContext.request.contextPath}/secure/authenticated"><span style="color:white">AUTHENTICATED</span></a> |
<a href="${pageContext.request.contextPath}/secure/editPermission"><span style="color:white">EDIT_PERMISSION</span></a> |
<a href="${pageContext.request.contextPath}/secure/events"><span style="color:white">EVENTS</span></a>

<form action='${pageContext.request.contextPath}/impersonate_As_USER' method='GET'>
    <input type="text" name="username" value="user@gmail.com"/>
    <input type="submit" value="Impersonate As User"/>
</form>

<form action='${pageContext.request.contextPath}/logout' method='POST'>
	<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
    <input name="LOGOUT" type="submit" value="Logout"/>
</form>

</body>
</html>

