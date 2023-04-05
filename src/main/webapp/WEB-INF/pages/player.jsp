<%--
  Created by IntelliJ IDEA.
  User: k
  Date: 13/03/2023
  Time: 09:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${ not empty sessionScope.username }">
<html>
<head>
	<title>Title</title>
</head>
<body>
<%@include file="menu.jsp"%>
<div class="container ">
<div class="text-center d-flex justify-content-center">
	<form action="player" method="post">

			<input type="text" aria-label="last name"  name="lastName"  class="form-control mb-2 mt-5" placeholder="Nom">
			<input type="text" aria-label="first name" name="firstName" class="form-control"           placeholder="Prenom">

				<input class="form-check-input" type="checkbox" name="gender" value="F" id="femmes" >
				<label class="form-check-label" for="femmes">
					Femme
				</label>
				<input class="form-check-input" type="checkbox" name="gender" value="H" id="hommes" >
				<label class="form-check-label" for="hommes">
					Homme
				</label>
			<br /><br />
		<c:if test="${ sessionScope.permission == 1 }">
			<button type="submit" class="btn btn-danger m-1"  name="button" value="add">
				Ajouter
			</button>
		</c:if>
		<button type="submit" class="btn btn-success m-1" name="button" value="search">
			Rechercher
		</button>
	</form>
</div>
	<h1 class="text-center">You're in the player view</h1>
	<hr>
	<h3 class="text-center mb-2">${ players.size() }</h3>
	<h2 class="text-center mb-2">${ error }</h2>
	<hr>
	<table class="table text-center">
		<thead>
			<tr>
				<th scope="col">Prénom</th>
				<th scope="col">Nom   </th>
				<th scope="col">Sexe  </th>
				<c:if test="${ sessionScope.permission == 1 }">
					<th scope="col">Action</th>
				</c:if>
			</tr>
		</thead>
		<tbody>

			<c:forEach var="player" items="${ players }">
				<tr>
					<td><c:out value="${ player.firstName() }" /></td>
					<td><c:out value="${ player.lastName()  }" /></td>
					<td><c:out value="${ player.gender()    }" /></td>
					<c:if test="${ sessionScope.permission == 1 }">
						<td>
							<button type="button" class="btn btn-warning m-1">
								<a href="modifyPlayer?modify=${ player.id() }" class="text-decoration-none">
									Modifier
								</a>
							</button>
							<button type="button" class="btn btn-danger m-1">
								<a href="deletePlayer?delete=${ player.id() }" class="text-decoration-none">
									Supprimer
								</a>
							</button>
						</td>
					</c:if>
				</tr>
			</c:forEach>

		</tbody>
	</table>

</div>

</body>

</html>
</c:if>