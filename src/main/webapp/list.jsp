<%@ page import="java.util.List" %>
<%@ page import="model.Contact" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Список контактов</title>
		<link rel="stylesheet" type="text/css" href="styles\index.css">
		<script type="text/javascript" src="scripts\common.js"> </script>
		<script type="text/javascript" src="scripts\navigation.js"> </script>		
	</head>
	<body>
	<a href="index.html" style="float:left">На главную</a>
		<header>Контакты</header>
		
		<div id="control_panel">
			<input type="checkbox" onclick="toggle(this, 'check_contact')">
			<button class="button red uppercase" onClick="location.href='contact.do'">
				Новый контакт				
			</button>
			<button class="button blue" onClick="location.href='search.html'">
				Найти контакты
			</button>
			<button class="button" form="checked_contacts_form" formaction="delete.do" formmethod="post">
				Удалить контакты
			</button>
			<button class="button" form="checked_contacts_form" formaction="open_mail.do" formmethod="post">
				Отправить письмо
			</button>
			<span class="right">
				<%
					String countValue = request.getParameter("count");
				%>
				<label for="display_contacts">Отображать по</label>					
				<select id="display_contacts_select" onChange="displayContacts()" >
					<option value="10" <%=countValue.equals("10") ? "selected" : "" %>>10</option>
					<option value="20" <%=countValue.equals("20") ? "selected" : "" %>>20</option>
					<option value="30" <%=countValue.equals("30") ? "selected" : "" %>>30</option>
				</select>
				<a class="button grouped" onClick="displayPrev()">Назад</a>
				<a class="button" onClick="displayNext()">Вперёд</a>
			</span>
		</div>
		<form id="checked_contacts_form">
			<table class="list">
				<tbody>						
					<%
						@SuppressWarnings("unchecked")
			            List<Contact> contacts = (List<Contact>) request.getAttribute("contacts");
						if (contacts != null) {
			            	for (Contact c : contacts) {
			        %>
			        <tr>
						<td class="checkbox"><input type="checkbox" name="check_contact" value="<%=c.getId()%>"></td> 
						<td class="fullname" onClick="editContact(<%=c.getId()%>)">
							<%=c.getLastname().equals("") ? "" : c.getLastname()%>
							<%=c.getFirstname().equals("") ? "" : " " + c.getFirstname()%>
							<%=c.getMidname().equals("") ? "" : " " + c.getMidname()%>
						</td>
						<td class="birthday"><%=c.getBirthday()%></td>
						<td class="address">
							<%=c.getCountry()%>
							<%=!c.getCity().equals("") ? ", г." + c.getCity() : ""%>
							<%=!c.getStreet().equals("") ? ", ул." + c.getStreet() : ""%>
							<%=c.getHouse() != 0 ? ", д." + c.getHouse() : ""%>
							<%=c.getApartment() != 0 ? ", кв." + c.getApartment() : ""%>
						</td>
						<td class="job"><%=c.getJob()%></td>
					</tr>
			        <%
			            	}
						}
			        %>				
				</tbody>
			</table>
		</form>
		<footer>© iTechArt Group, 2013</footer>
	</body>
</html>