<%@ page import="model.Phone" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Телефон</title>		
		<link rel="stylesheet" type="text/css" href="styles\popup.css">
		<script type="text/javascript" src="scripts\common.js"> </script>
		<script type="text/javascript">
			function setHiddens() {
				var phone_id = parseGetParams()['phone_id'];
				var hidden_id = document.getElementById("hidden_id");
				var hidden_phone_id = document.getElementById("hidden_phone_id");
				if (phone_id !== undefined) {
					hidden_phone_id.value = phone_id;
				}
				hidden_id.value = parseGetParams()['id'];
			}
		</script>
	</head>
	<body onLoad="setHiddens()">
		<%
			Phone p = (Phone) request.getAttribute("phone");
			String action = (String) request.getAttribute("action");
		%>
		<form action="phone.do" method="post">
			<!-- 
			<input type="hidden" name="id" value="<%--=p.getId()--%>">
			<input type="hidden" name="phone_id" value="<%--=p.getContactID()--%>">
			 -->
			<input type="hidden" name="action" value="<%=action%>">
			<input type="hidden" id="hidden_id" name="id" value="-1">
			<input type="hidden" id="hidden_phone_id" name="phone_id" value="-1">

			<table class="form">
				<tbody>
					<tr>		
						<td class="label">
							<label for="code1">Код&nbsp;страны</label>
						</td>
						<td class="form">
							<input type="text" name="code1" id="code1" value="<%=p.getCode1()%>" required>
						</td>
						<td class="comments" rowspan="4">
							<textarea name="comment" rows="10" placeholder="Комментарий"><%=p.getComment() %></textarea>
						</td>
					</tr>
					<tr>
						<td class="label">
							<label for="code2">Код&nbsp;оператора</label>
						</td>
						<td class="form">
							<input type="text" name="code2" id="code2" value="<%=p.getCode2()%>" required>
						</td>
					</tr>
					<tr>
						<td class="label">
							<label for="phone_number">Телефонный&nbsp;номер</label>
						</td>
						<td class="form">
							<input type="tel" name="phone_number" id="phone_number" value="<%=p.getPhoneNumber()%>" required>
						</td>
					</tr>
					<tr>
						<td class="label">
							<label for="phone_type_select">Тип&nbsp;телефона</label>
						</td>
						<td class="form">
							<select name="phone_type_select" id="phone_type_select">
								<option value="mobile" <%=p.getPhoneType() == Phone.PhoneType.MOBILE ? "selected" : "" %>>
									Мобильный
								</option>
								<option value="home" <%=p.getPhoneType() == Phone.PhoneType.HOME ? "selected" : "" %>>
									Домашний
								</option>
							</select>
						</td>
					</tr>					
				</tbody>
			</table>
			<div class="result_buttons">
				<input class="button red uppercase" type="submit" value="Сохранить">
				<input class="button" type="reset" value="Отменить" onClick="window.close()">
			</div>			
		</form>		
	</body>
</html>