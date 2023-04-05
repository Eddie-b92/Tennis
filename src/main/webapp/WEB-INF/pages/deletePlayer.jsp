<%--
  Created by IntelliJ IDEA.
  User: k
  Date: 18/03/2023
  Time: 17:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${ not empty sessionScope.username && sessionScope.permission == 1 }">
<html>
<head>
	<title>Supprimer</title>
</head>
<body>
<%@include file="menu.jsp"%>
	<h2 class="text-center">Êtes vous sûres de vouloir supprimer ${ name }?</h2>
	<div class="container d-flex justify-content-center align-content-center">
		<form action="deletePlayer" method="post">
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