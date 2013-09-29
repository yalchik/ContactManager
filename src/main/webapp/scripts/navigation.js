var START = "start";
var COUNT = "count";
var SELECT_ID = "display_contacts_select";
var INDEX_PAGE = 'index.html';
var CONTACT_PAGE = 'contact.do';
var ID = 'id';
var FIRST_CONTACT_ID = 0;

/*
 * Переадресация на страницу редактирования указанного контакта
 * number - номер контакта
 */
function editContact(index) {
	if (index >= 0) {
		window.location = CONTACT_PAGE + '?' + ID + '=' + index;
	}
}

/*
 * Получает текущий номер контакта,
 * с которого начинается отображение контактов в таблице
 * из GET-запроса
 * Если атрибут start в GET-запросе отсутствует, то возвращается NaN
 */
function getCurrentStartNumber() {
	return Number(parseGetParams()[START]);
}

/*
 * Получает текущее количество контактов в таблице
 * из GET-запроса
 * Если атрибут count в GET-запросе отсутствует, то возвращается NaN
 */
function getCurrentContactCount() {
	return Number(parseGetParams()[COUNT]);
}

/*
 * Получает выбранное значение в select-е "Отображать по"
 */
function getSelectedContactCount() {
	return document.getElementById(SELECT_ID).value;
}

/*
 * Перенаправляет на страницу по умолчанию, в которой
 * атрибут count равен выбранному значению,
 * атрибут start равен нулю.
 */
function setDefaultPage() {
	var selected = getSelectedContactCount();
	window.location = INDEX_PAGE + '?' + COUNT + '=' + selected + '&' + START + '=' + FIRST_CONTACT_ID;
}

/*
 * Проверяет текущий GET-запрос на валидность
 * Валидным будет запрос, у которого есть атрибуты
 * count и start с целыми неотрицательными значениями
 */
function isValidQueryString() {
	var GETParams = parseGetParams();
	var start_number = Number(GETParams[START]);
	var count_number = Number(GETParams[COUNT]);
	if (!isInteger(start_number) || !isInteger(count_number)) {
		return false;
	}
	if (start_number < 0 || count_number < 0) {
		return false;
	}	
	return true;
}

/*
 * Перенаправляет на страницу с новым GET запросом,
 * в котором заменено значение параметра start
 * c currentStartNumber на newStartNumber
 * В случае неудачи перенаправляет на страницу по умолчанию
 */
function redirectWithNewValue(getParameter, currentValue, newValue) {
	if (isValidQueryString()) {		
		var oldParameter = getParameter + '=' + currentValue;
		var newParameter = getParameter + '=' + newValue;
		window.location = window.location.search.replace(oldParameter, newParameter);
	}
	/*
	else {
		setDefaultPage();
	}*/
}

/*
 * Отображает следующую страницу контактов
 * Их количество принимается из select-a "Отображать по"
 */
function displayNext() {
	var selectedContactCount = getSelectedContactCount();
	var currentStartNumber = getCurrentStartNumber();
	var newStartNumber = Number(currentStartNumber) + Number(selectedContactCount);
	if (newStartNumber < 0) {
		newStartNumber = 0;		
	}
	redirectWithNewValue(START, currentStartNumber, newStartNumber);
	/*
	else {
		setDefaultPage();
	}*/
}

/*
 * Отображает предыдущую страницу контактов
 * Их количество принимается из select-a "Отображать по"
 */
function displayPrev() {
	var selectedContactCount = getSelectedContactCount();	
	var currentStartNumber = getCurrentStartNumber();
	var newStartNumber = Number(currentStartNumber) - Number(selectedContactCount);
	if (newStartNumber < 0) {
		newStartNumber = 0;		
	}
	redirectWithNewValue(START, currentStartNumber, newStartNumber);
	/*
	else {
		setDefaultPage();
	}*/
}

/*
 * Открывает окно с тем количеством контактов,
 * которое указано в select-е "Отображать по"
 */
function displayContacts() {
	var newCount = getSelectedContactCount();	
	var oldCount = getCurrentContactCount();
	redirectWithNewValue(COUNT, oldCount, newCount);
}