<%--
  Created by IntelliJ IDEA.
  User: k
  Date: 19/03/2023
  Time: 09:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${ not empty sessionScope.username }">
<html>
<head>
	<title>Status</title>
</head>
<body>
<%@include file="menu.jsp"%>
	<h1 class="text-center">${ status }</h1>
</body>
</html>
</c:if>