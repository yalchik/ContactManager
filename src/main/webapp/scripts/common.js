/*
 * Устанавливает значение всех checkbox-ов с именем name
 * в соответствии со значением checkbox-а source
 */
function toggle(source, name) {
	checkboxes = document.getElementsByName(name);
	for(var i=0, n=checkboxes.length; i<n; i++) {
		checkboxes[i].checked = source.checked;
	}
}

/*
 * Разбор текущего URL-a для получения GET-параметров и их значений
 * Возвращает ассоциативный массив "параметр:значение"
 */
function parseGetParams() { 
   var $_GET = {}; 
   var __GET = window.location.search.substring(1).split("&"); 
   for (var i = 0; i < __GET.length; i++) { 
      var getVar = __GET[i].split("="); 
      $_GET[getVar[0]] = typeof(getVar[1])=="undefined" ? "" : getVar[1]; 
   }
   return $_GET; 
}

/*
 * Определить, является ли число num целым
 */
function isInteger(num) {
  return (num ^ 0) === num;
}

function popup(url, windowName, height, width) {
	var h = 'height=' + height;
	var w = 'width=' + width;
	newWindow = window.open(url, windowName, h + ',' + w);
	if (window.focus) {
		newWindow.focus();
	}
	return newWindow;
}