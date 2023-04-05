<%--
  Created by IntelliJ IDEA.
  User: k
  Date: 26/03/2023
  Time: 10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${ not empty sessionScope.username }">
<html>
<head>
	<title>Déconnexion</title>
</head>
<body>
<%@include file="menu.jsp"%>
	<div class="container d-flex justify-content-center align-content-center">
		<form action="logout" method="post" class="text-center">
			<h3>Voulez vous vous déconnecter ?</h3>
			<button type="submit" class="btn btn-danger m-1"  name="button" value="Yes">
				Ouuuuiiii
			</button>
			<button type="submit" class="btn btn-success m-1" name="button" value="No">
				Nooooonnn
			</button>
		</form>
	</div>
</body>
</html>
</c:if>