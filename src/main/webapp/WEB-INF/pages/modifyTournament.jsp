<%--
  Created by IntelliJ IDEA.
  User: k
  Date: 20/03/2023
  Time: 19:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${ not empty sessionScope.username && sessionScope.permission == 1 }">
<html>
<head>
	<title>Modify tournament</title>
</head>
<body>
<%@include file="menu.jsp"%>

	<div class="container ">
		<div class="text-center d-flex justify-content-center align-items-center">
			<form action="modifyTournament" method="post">

				<input type="text" aria-label="last name"  value="${ name }"  name="name"  class="form-control mb-2 mt-5" placeholder="Nom">
				<input type="text" aria-label="first name" value="${ code }"  name="code"  class="form-control"           placeholder="Code">

				<br />
				<input type="submit" value="Modifier">
			</form>
		</div>
	</div>

</body>
</html>
</c:if>