<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${ not empty sessionScope.username }">
<html>
<head>
	<title>Epreuve</title>
</head>
<body>
<%@include file="menu.jsp"%>
<div class="container">
	<div class="text-center d-flex justify-content-center">
		<form action="event" method="post">

			<input type="year" aria-label="last name" name="year" class="form-control mb-2 mt-5" placeholder="Année">

			<input class="form-check-input" type="checkbox" name="gender" value="F" id="femmes" >
			<label class="form-check-label" for="femmes">
				Femme
			</label>
			<input class="form-check-input" type="checkbox" name="gender" value="H" id="hommes" >
			<label class="form-check-label" for="hommes">
				Homme
			</label>
			<br />
			<button type="submit" class="btn btn-success m-1" name="button" value="search">
				Rechercher
			</button>
		</form>
	</div>
	<h1 class="text-center">You're in the player view</h1>
	<hr>
	<h3 class="text-center mb-2">${ events.size() }</h3>
	<h2 class="text-center mb-2">${ error }</h2>
	<hr>
	<table class="table text-center">
		<thead>
		<tr>
			<th scope="col">Prénom   </th>
			<th scope="col">Nom      </th>
			<th scope="col">Placement</th>
			<th scope="col">Tournoi  </th>
		</tr>
		</thead>
		<tbody>

		<c:forEach var="event" items="${ events }">
			<tr>
				<td><c:out value="${ event.player().firstName() }" /></td>
				<td><c:out value="${ event.player() .lastName() }" /></td>
				<td><c:out value="${ event         .placement() }" /></td>
				<td><c:out value="${ event.tournament() .name() }" /></td>
			</tr>
		</c:forEach>

		</tbody>
	</table>

</div>
</body>
</html>
</c:if>