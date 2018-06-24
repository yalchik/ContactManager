<%@ page import="java.util.List" %>
<%@ page import="model.Contact" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Отправить письмо</title>
		<link rel="stylesheet" type="text/css" href="styles\sendmail.css">
		<script type="text/javascript">
			function useTemplate() {
				var textarea = document.getElementById("text");
				var selected_value = document.getElementById("template").value;
				switch (selected_value) {
				case "none":
					textarea.value = "";
					break;
				case "birthday":
					textarea.value = "Happy birthday. " + textarea.value;
					break;
				case "new_year":
					textarea.value = "Happy New Year. " + textarea.value;
					break;
				case "anniversary":
					textarea.value = "Congratulations! " + textarea.value;
					break;
				}
			}
		</script>
	</head>
	<body>
		<header>Отправить письмо</header>
		<form action="send_mail.do" method="post">		
			<table class="form">
				<tbody>
					<tr>		
						<td class="label">
							<label for="to">Кому</label>
						</td>
						<td class="form">
							<input type="text" name="to" id="to" value="<%=request.getAttribute("recipients")%>" required>
						</td>						
					</tr>
					<tr>
						<td class="label">
							<label for="subject">Тема</label>
						</td>
						<td class="form">
							<input type="text" name="subject" id="subject" required>
						</td>
					</tr>
					<tr>
						<td class="label">
							<label for="template">Шаблон</label>
						</td>
						<td class="form">
							<select name="template" id="template" onChange="useTemplate()">
								<option value="none">Пустое сообщение</option>
								<option value="birthday">С Днём Рожденья</option>
								<option value="new_year">С Новым Годом</option>
								<option value="anniversary">С Юбилеем</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="form" colspan="2">
							<textarea name="text" id="text" placeholder="Текст письма"></textarea>
						</td>
					</tr>
					<tr>
						<td class="form" colspan="2">
							<input class="button red uppercase" type="submit" value="Отправить">
							<input class="button" type="reset" value="Очистить">
						</td>
					</tr>
				</tbody>
			</table>
		</form>		
	</body>
</html>