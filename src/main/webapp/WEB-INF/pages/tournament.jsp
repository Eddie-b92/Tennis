<%--
  Created by IntelliJ IDEA.
  User: k
  Date: 13/03/2023
  Time: 09:41
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
<div class="container text-center">
	<div class="text-center d-flex justify-content-center">
		<form action="tournament" method="post">

			<input type="text" aria-label="name" name="name" class="form-control mb-2 mt-5" placeholder="Nom">
			<input type="text" aria-label="code" name="code" class="form-control"           placeholder="Code">

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
	<h1>You're in the tournament view</h1>
	<h3 class="text-center mb-2">${ tournaments.size() }</h3>
	<h2 class="text-center mb-2">${ error }</h2>
	<hr>
	<table class="table text-center">
		<thead>
		<tr>
			<th scope="col">Nom   </th>
			<th scope="col">Code  </th>
			<c:if test="${ sessionScope.permission == 1 }">
				<th scope="col">Action</th>
			</c:if>
		</tr>
		</thead>
		<tbody>

		<c:forEach var="tournament" items="${ tournaments }">
			<tr>
				<td><c:out value="${ tournament.name() }" /></td>
				<td><c:out value="${ tournament.code() }" /></td>
				<c:if test="${ sessionScope.permission == 1 }">
					<td>
						<button type="button" class="btn btn-warning m-1">
							<a href="modifyTournament?modify=${ tournament.id() }" class="text-decoration-none">
								Modifier
							</a>
						</button>
						<button type="button" class="btn btn-danger m-1">
							<a href="deleteTournament?delete=${ tournament.id() }" class="text-decoration-none">
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