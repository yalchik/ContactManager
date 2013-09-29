<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
			<title>Страница ошибки</title>
		</head>
	<body>
		<div>
			Извините, у нас произошла ошибка:
			<%=request.getAttribute("message") %>
		</div>
		<div>
			Сообщите, пожалуйста, об этом администратору.
		</div>
		<div>
			E-mail: <%=application.getInitParameter("admin_email") %>
		</div>
		<div>
			<a href="index.html">На главную</a>
		</div>
	</body>
</html>