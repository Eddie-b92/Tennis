<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${ not empty sessionScope.username && sessionScope.permission == 1 }">
<html>
<head>
	<title>Modify Player</title>
</head>
<body>
	<%@include file="menu.jsp"%>

	<div class="container ">
		<div class="text-center d-flex justify-content-center align-items-center">
			<form action="modifyPlayer" method="post">

				<input type="text" aria-label="last name"  value="${ lastName }"  name="lastName"  class="form-control mb-2 mt-5" placeholder="Nom">
				<input type="text" aria-label="first name" value="${ firstName }" name="firstName" class="form-control"           placeholder="Prenom">
				<br />
				<div class="btn-group" role="group" aria-label="Basic radio toggle button group">

					<input type="radio" class="btn-check" name="gender" id="Homme" value="H" autocomplete="off" ${ gender.equals("H") ? "checked" : ""}>
					<label class="btn btn-outline-primary" for="Homme">Homme</label>

					<input type="radio" class="btn-check" name="gender" id="Femme" value="F" autocomplete="off" ${ gender.equals("F") ? "checked" : ""}>
					<label class="btn btn-outline-primary" for="Femme">Femme</label>

				</div>
				<br /><br />
				<input type="submit" value="Modifier">
			</form>
		</div>

	</div>


</body>
</html>
</c:if>