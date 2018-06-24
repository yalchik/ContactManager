<%@ page import="java.util.List" %>
<%@ page import="model.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Список контактов</title>
		<link rel="stylesheet" type="text/css" href="styles\contact.css">
		<script type="text/javascript" src="scripts\common.js"> </script>
		<script type="text/javascript" src="scripts\saveRestoreForm.js"></script>
		<script type="text/javascript" src="scripts\contact.js"> </script>
			
	</head>
	<body onLoad="setHeader(); restoreForm()">
		<h1 id="header">Создание контакта</h1>
		<h2>Информация о контакте</h2>
		<form id="main_info_form" method="post">
			<input type="hidden" id="hidden_id" name="id" value="-1">
			<input type="hidden" id="hidden_action" name="action" value="">
			<%
				Contact c = (Contact) session.getAttribute("contact");
				if (c == null) {
					c = new Contact();
				}
			%>
			<table class="form">
				<tr>
					<td id="photo" rowspan="16">
						<img width="300" src="<%=c.getPhotoPath()%>" onClick="newPhoto()">
					</td>
					<td><label for="firstname">Имя</label></td>
					<td><input type="text" name="firstname" id="firstname" value="<%=c.getFirstname()%>" required onblur="saveValue(this)"></td>					
				</tr>
				<tr>
					<td><label for="lastname">Фамилия</label></td>
					<td><input type="text" name="lastname" id="lastname" value="<%=c.getLastname()%>" onblur="saveValue(this)"></td>
				</tr>
				<tr>
					<td><label for="midname">Отчество</label></td>
					<td><input type="text" name="midname" id="midname" value="<%=c.getMidname()%>" onblur="saveValue(this)"></td>
				</tr>
				<tr>
					<td><label for="birthday">Дата рождения</label></td>
					<td><input type="date" name="birthday" id="birthday" value="<%=c.getBirthday()%>" onblur="saveValue(this)"></td>
				</tr>
				<tr>
					<td><label>Пол</label></td>
					<td>
						<input type="radio" name="sex" value="male" id="male"
							onchange="saveCheckbox(this)"
							<%=c.getSex() == Contact.Sex.MALE ? "checked" : ""%>
							>
						<label for="male">Мужской</label>
						<br>
						<input type="radio" name="sex" value="female" id="female"
							onchange="saveCheckbox(this)"
							<%=c.getSex() == Contact.Sex.FEMALE ? "checked" : ""%>
							>
						<label for="female">Женский</label>
					</td>
				</tr>
				<tr>
					<td><label for="citizenship">Гражданство</label></td>
					<td><input type="text" name="citizenship" id="citizenship" value="<%=c.getCitizenship()%>" onblur="saveValue(this)"></td>
				</tr>
				<tr>
					<td><label>Семейное положение</label></td>
					<td>
						<input type="radio" name="marital status" value="0" id="single"
							onchange="saveCheckbox(this)"
							<%=c.getMartialStatus() == Contact.MartialStatus.SINGLE ? "checked" : ""%>
							>
						<label for="single">Не женат\Не замужем</label>
						<br>
						<input type="radio" name="marital status" value="1" id="married"
							onchange="saveCheckbox(this)"
							<%=c.getMartialStatus() == Contact.MartialStatus.MARRIED ? "checked" : ""%>
							>
						<label for="married">Женат\Замужем</label>
					</td>
				</tr>
				<tr>
					<td><label for="web-site">Web-сайт</label></td>
					<td><input type="text" name="web-site" id="web-site" value="<%=c.getUrl()%>" onblur="saveValue(this)"></td>
				</tr>
				<tr>
					<td><label for="email">E-mail</label></td>
					<td><input type="email" name="email" id="email" value="<%=c.getEmail()%>" onblur="saveValue(this)"></td>
				</tr>
				<tr>
					<td><label for="job">Текущее место работы</label></td>
					<td><input type="text" name="job" id="job" value="<%=c.getJob()%>" onblur="saveValue(this)"></td>
				</tr>
				<tr>
					<td><label for="country">Страна</label></td>
					<td><input type="text" name="country" id="country" value="<%=c.getCountry()%>" onblur="saveValue(this)"></td>
				</tr>
				<tr>
					<td><label for="city">Город</label></td>
					<td><input type="text" name="city" id="city" value="<%=c.getCity()%>" onblur="saveValue(this)"></td>
				</tr>
				<tr>
					<td><label for="street">Улица</label></td>
					<td><input type="text" name="street" id="street" value="<%=c.getStreet()%>" onblur="saveValue(this)"></td>
				</tr>
				<tr>
					<td><label for="house">Дом</label></td>
					<td><input type="text" name="house" id="house" value="<%=c.getHouse() != 0 ? c.getHouse() : ""%>" onblur="saveValue(this)"></td>
				</tr>
				<tr>
					<td><label for="apartment">Квартира</label></td>
					<td><input type="text" name="apartment" id="apartment" value="<%=c.getApartment() != 0 ? c.getApartment() : ""%>" onblur="saveValue(this)"></td>
				</tr>
				<tr>
					<td><label for="postcode">Почтовый индекс</label></td>
					<td><input type="text" name="postcode" id="postcode" value="<%=c.getPostcode() != 0 ? c.getPostcode() : ""%>" onblur="saveValue(this)"></td>
				</tr>
			</table>
		</form>
		<form id="phones_form">
			<!-- Телефоны -->
			<h2>Телефоны</h2>
			<input type="checkbox" onclick="toggle(this, 'check_phone')">
			<span class="right">
				<button class="button red uppercase" form="" onclick="newPhone()">
					Добавить телефон
				</button>
				<button class="button" formaction="delete_phones.do" formmethod="post">
					Удалить телефоны
				</button>
			</span>
			<table class="list">
				<tbody>
					<%
			            List<Phone> phones = c.getPhones();
						for (int i = 0; i < phones.size(); i++) {
		            		Phone p = phones.get(i);
		            		if (p.getPhoneStatus() != Phone.PhoneStatus.REMOVED) {		            			
			        %>
					<tr>
						<td class="checkbox"><input type="checkbox" name="check_phone" value="<%=i %>"></td>
						<td class="phone_number" onClick="editPhone(<%=i %>)">
							<%=p.getCode1() + " (" + p.getCode2() + ") " + p.getPhoneNumber() %>
						</td>
						<td class="phone_describe"><%=p.getPhoneType().toString() %></td>
						<td class="phone_comment"><%=p.getComment()%></td>
					</tr>
			        <%
		            		}
						}
			        %>	
				</tbody>
			</table>
		</form>
		<form id="attachments_form">
			<!-- Присоединения -->
			<h2>Присоединения</h2>
			<input type="checkbox" onclick="toggle(this, 'check_attachment')">
			<span class="right">
				<button class="button red uppercase" form="" onclick="newAttachment()">
					Добавить присоединение
				</button>
				<button class="button" formaction="delete_attachments.do" formmethod="post">
					Удалить присоединения
				</button>
			</span>
			<table class="list">
				<tbody>						
					<%
			            List<Attachment> attachments = c.getAttachments();
						for (int i = 0; i < attachments.size(); i++) {
							Attachment a = attachments.get(i);
		            		if (a.getAttachmentStatus() != Attachment.AttachmentStatus.REMOVED) {		            			
			        %>
					<tr>
						<td class="checkbox"><input type="checkbox" name="check_attachment" value="<%=i %>"></td>
						<td class="attachment_name" onClick="editAttachment(<%=i %>)"><%=a.getName()%></td>
						<td class="attachment_date"><%=a.getDate()%></td>
						<td class="attachment_comment"><%=a.getComment()%></td>
						<td> <a href="<%=a.getPath() %>">Скачать</a></td>
					</tr>
					<%
		            		}
						}
			        %>
				</tbody>
			</table>
		</form>
		<span>
			<button class="button red uppercase" form="main_info_form" onClick="cleanForm()">Сохранить</button>
			<button class="button" onClick="cleanForm(); window.location.href = 'index.do'">Отменить</button>
		</span>
			
		<!-- <button class="button red uppercase" type="submit" value="Сохранить">Сохранить</button> -->
		<!--  
		<div class="result_buttons">
			<input class="button red uppercase" type="submit" value="Сохранить">
			<input class="button" type="reset" value="Отменить" onClick="window.close()">
		</div>	
		-->	
		<footer>© iTechArt Group, 2013</footer>
	</body>
</html>