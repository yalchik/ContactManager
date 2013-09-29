var BROWSE_HIDDEN_ID = 'browse';
var BROWSE_LINE_ID = 'filename';

function HandleBrowseClick() {	
	var fileinput = document.getElementById(BROWSE_HIDDEN_ID);
	fileinput.click();
}
function Handlechange() {
	var fileinput = document.getElementById(BROWSE_HIDDEN_ID);
	var textinput = document.getElementById(BROWSE_LINE_ID);
	textinput.value = fileinput.value;
}