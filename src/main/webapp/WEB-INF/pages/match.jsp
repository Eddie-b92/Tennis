<%--
  Created by IntelliJ IDEA.
  User: k
  Date: 22/03/2023
  Time: 07:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${ not empty sessionScope.username }">
<html>
<head>
	<title>Matchs</title>
</head>
<body>
<%@include file="menu.jsp"%>
<div class="container ">
	<div class="text-center d-flex justify-content-center">
		<form action="match" method="post">

			<input type="text" aria-label="last name"  name="lastName"  class="form-control mb-2 mt-5" placeholder="Nom">
			<input type="text" aria-label="first name" name="firstName" class="form-control"           placeholder="Prenom">
			<br />
			<input class="form-check-input" type="checkbox" name="gender" value="F" id="femmes" >
			<label class="form-check-label" for="femmes">
				Femme
			</label>
			<input class="form-check-input" type="checkbox" name="gender" value="H" id="hommes" >
			<label class="form-check-label" for="hommes">
				Homme
			</label>
			<br />
			<input class="form-check-input" type="checkbox" name="placement" value="winner" id="winner" >
			<label class="form-check-label" for="winner">
				Vainqueur
			</label>
			<input class="form-check-input" type="checkbox" name="placement" value="finalist" id="finalist" >
			<label class="form-check-label" for="finalist">
				Finaliste
			</label>
			<br />
			<br />
			<button type="submit" class="btn btn-success m-1" name="button" value="search">
				Rechercher
			</button>
		</form>
	</div>
	<h1 class="text-center">You're in the match view</h1>
	<hr>
	<h3 class="text-center mb-2">${ matches.size() }</h3>
	<h2 class="text-center mb-2">${ error }</h2>
	<hr>
	<table class="table text-center">
		<thead>
		<tr>
			<th scope="col">Prénom   </th>
			<th scope="col">Nom      </th>
			<th scope="col">Sexe     </th>
			<th scope="col">N° matchs</th>
		</tr>
		</thead>
		<tbody>

		<c:forEach var="match" items="${ matches }">
			<tr>
				<td><c:out value="${ match.player().firstName() }" /></td>
				<td><c:out value="${ match.player().lastName()  }" /></td>
				<td><c:out value="${ match.player().gender()    }" /></td>
				<td><c:out value="${ match.nbrMatches()           }" /></td>
			</tr>
		</c:forEach>

		</tbody>
	</table>

</div>
</body>
</html>
</c:if>