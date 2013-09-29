<%@ page import="model.Attachment;" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Присоединение</title>
		<link rel="stylesheet" type="text/css" href="styles\popup.css">
		<script type="text/javascript" src="scripts\common.js"> </script>
		<script type="text/javascript" src="scripts\browse-handling.js"> </script>
		<script type="text/javascript">
			function setHiddens() {
				var attachment_id = parseGetParams()['attachment_id'];
				var hidden_id = document.getElementById("hidden_id");
				var hidden_attachment_id = document.getElementById("hidden_attachment_id");
				if (attachment_id != undefined) {
					hidden_attachment_id.value = attachment_id;
				}
				hidden_id.value = parseGetParams()['id'];
			}
		</script>
	</head>
	<body onLoad="setHiddens()">
		<%
			Attachment a = (Attachment) request.getAttribute("attachment");
			String action = (String) request.getAttribute("action");
		%>
		<form action="submit_attachment.do" method="post" enctype="multipart/form-data">
			<input type="hidden" name="action" value="<%=action%>">
			<input type="hidden" id="hidden_id" name="id" value="-1">
			<input type="hidden" id="hidden_attachment_id" name="attachment_id" value="-1">
			
			<table class="form">
				<tbody>
					<tr>		
						<td class="label">
							<label for="name">Имя&nbsp;файла</label>
						</td>
						<td class="form">
							<input type="text" name="attachment_name" id="name" value="<%=a.getName()%>" required>
						</td>
						<td class="comments" rowspan="2">
							<textarea rows="4" name="comment" placeholder="Комментарий"><%=a.getComment()%></textarea>
						</td>
					</tr>
					<tr <%=action.equals("edit_attachment") ? "style=\"display:none\"" : "" %>>
						<td class="label">
							<label for="filename">Путь</label>
						</td>
						<td class="form">
							<input type="file" name="attachment" id="browse" class="invisible" onChange="Handlechange()"/>
							<input type="text" id="filename" value="<%=a.getPath()%>" readonly />							
							<input type="button" value="Найти" class="button" onClick="HandleBrowseClick()"/>
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