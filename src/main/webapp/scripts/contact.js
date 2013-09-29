var ID = 'id';

var PHOTO_PAGE = 'photo.html';
var PHOTO_PAGE_NAME = 'photo';
var PHOTO_PAGE_HEIGHT = 150;
var PHOTO_PAGE_WIDTH = 300;

var PHONE_ID = 'phone_id';
var PHONE_PAGE = 'phone.do';
var PHONE_PAGE_NAME = 'edit_phone';
var PHONE_PAGE_HEIGHT = 250;
var PHONE_PAGE_WIDTH = 800;

var ATTACHMENT_ID = 'attachment_id';
var ATTACHMENT_PAGE = 'attachment.do';
var ATTACHMENT_PAGE_NAME = 'edit_attachment';
var ATTACHMENT_PAGE_HEIGHT = 200;
var ATTACHMENT_PAGE_WIDTH = 800;


function getCurrentContactQuery() {
	var query = window.location.toString().split('?');
	return '?' + query[1];
}

function popupClosing() {
	window.location.href = window.location.href;	
}

function newPhoto() {
	var contactQuery = getCurrentContactQuery();
	var photo_page = PHOTO_PAGE + contactQuery;
	var popupPhoto = popup(photo_page, PHOTO_PAGE_NAME, PHOTO_PAGE_HEIGHT, PHOTO_PAGE_WIDTH);
	popupPhoto.onunload = function () {		
		window.parent.popupClosing();		  
	};
}

function newPhone() {
	var contactQuery = getCurrentContactQuery();
	var phone_page = PHONE_PAGE;
	if (contactQuery != undefined)
		phone_page += contactQuery;
	var popupPhone = popup(phone_page, PHONE_PAGE_NAME, PHONE_PAGE_HEIGHT, PHONE_PAGE_WIDTH);
	popupPhone.onunload = function () {
		  window.parent.popupClosing();
	};
}

function newAttachment() {
	var contactQuery = getCurrentContactQuery();
	var attachment_page = ATTACHMENT_PAGE + contactQuery;
	var popupAttachment = popup(attachment_page, ATTACHMENT_PAGE_NAME, ATTACHMENT_PAGE_HEIGHT, ATTACHMENT_PAGE_WIDTH);
	popupAttachment.onunload = function () {
		  window.parent.popupClosing();
	};
}

function editPhone(number) {
	if (number >= 0) {
		var contactQuery = getCurrentContactQuery();
		var phone_id = PHONE_ID + '=' + number;
		var phone_page = PHONE_PAGE + contactQuery + '&' + phone_id;
		var popupPhone = popup(phone_page, PHONE_PAGE_NAME, PHONE_PAGE_HEIGHT, PHONE_PAGE_WIDTH);
		popupPhone.onunload = function () {
			  window.parent.popupClosing();
		};
	}
}

function editAttachment(number) {
	if (number >= 0) {
		var contactQuery = getCurrentContactQuery();
		var attachment_id = ATTACHMENT_ID + '=' + number;
		var attachment_page = ATTACHMENT_PAGE + contactQuery + '&' + attachment_id;
		var popupAttachment = popup(attachment_page, ATTACHMENT_PAGE_NAME, ATTACHMENT_PAGE_HEIGHT, ATTACHMENT_PAGE_WIDTH);
		popupAttachment.onunload = function () {
			  window.parent.popupClosing();
		};
	}
}

function setHeader() {
	var id = parseGetParams()[ID];
	var header = document.getElementById("header");
	var actionInput = document.getElementById("hidden_action");
	if (id == undefined) {
		header.innerText = "Создание контакта";
		actionInput.value = "create";
	}
	else {
		header.innerText = "Редактирование контакта";
		actionInput.value = "edit";
		document.getElementById("hidden_id").value = id;
	}	
}

function restoreForm() {
	var inputs, index;

	inputs = document.getElementsByTagName('input');
	for (index = 0; index < inputs.length; ++index) {
		if (inputs[index].type === "radio") {
			/*
			var value = getCookie(inputs[index].id);
		    if (value != null) inputs[index].checked = value;
		    */
			loadCheckbox(inputs[index]);
		}
		else {
			loadValue(inputs[index]);
		}
	}
}

function cleanForm() {
	var index;
	
	var inputs = document.getElementsByTagName('input');
	for (index = 0; index < inputs.length; ++index) {
		if (inputs[index].type === "radio") {
			delCheckbox(inputs[index]);
		}
		else {
			delValue(inputs[index]);
		}
	}
}