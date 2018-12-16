<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="springform" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
<title>my-account-Admin</title>
 <jsp:include page="/WEB-INF/views/theme/theme-include-my-account-admin.jsp" />
</head>
<body>

<!-- Display image -->
<img src="<c:url value="/static/images/Welcome.gif"/>"/>

<h3>****Admin Account****</h3>

<h3>Welcome, ${username}!</h3>

 <a href="${pageContext.request.contextPath}/my-account-user"> Click here to go for User account page!!!</a> </br>
<a href="${pageContext.request.contextPath}/my-account-dba"> Click here to go for DBA account page!!! </a> </br>
<a href="${pageContext.request.contextPath}/my-account-admin"> Go back to Home!!! </a> </br>
<a href="${pageContext.request.contextPath}/sign-out"> Click here for to signout!!!</a> </br>

</body>
</html>

