<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<title>Login</title>
</head>
<body>
<div class="container text-center">
	<h1>Login</h1><br/>

	<form action="login" method="post">
		<label for="username"> Identifiant:
			<input type="text" id="username" name="username" class="mb-2">
		</label>
		<br />
		<label for="password"> Mot de passe:
			<input type="password" id="password" name="password" class="mb-2">
		</label>
		<br />
		<input type="submit" value="Se connecter">
	</form>
	<h2 class="text-center">${ error }</h2>
</div>
</body>
</html>