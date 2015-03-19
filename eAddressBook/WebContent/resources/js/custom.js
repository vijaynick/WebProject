function restrictArabic(myfield, e) {
	if (!e)
		var e = window.event;
	if (e.keyCode)
		code = e.keyCode;
	else if (e.which)
		code = e.which;
	var character = String.fromCharCode(code);

	// if user pressed esc... remove focus from field...
	if (code == 27) {
		this.blur();
		return false;
	}

	// ignore if the user presses other keys
	// strange because code: 39 is the down key AND ' key...
	// and DEL also equals .
	if (!e.ctrlKey && code != 9 && code != 8 && code != 36 && code != 37
			&& code != 38 && (code != 39 || (code == 39 && character == "'"))
			&& code != 40) {
		if (code > 1631 && code < 1642) {
			return true;
		} else {
			return false;
		}
	}

}

// Restrict user input in a text field
// create as many regular expressions here as you need:

var digitsOnly = /[1234567890]/g;
var integerOnly = /[0-9\.]/g;
var alphaOnly = /[A-Za-z]/g;
var usernameOnly = /[0-9A-Za-z\._-]/g;

function restrictInput(myfield, e) {
	if (!e)
		var e = window.event;
	if (e.keyCode)
		code = e.keyCode;
	else if (e.which)
		code = e.which;
	var character = String.fromCharCode(code);

	// if user pressed esc... remove focus from field...
	if (code == 27) {
		this.blur();
		return false;
	}

	// ignore if the user presses other keys
	// strange because code: 39 is the down key AND ' key...
	// and DEL also equals .
	if (!e.ctrlKey && code != 9 && code != 8 && code != 36 && code != 37
			&& code != 38 && (code != 39 || (code == 39 && character == "'"))
			&& code != 40) {
		if (character.match(digitsOnly)) {
			return true;
		} else {
			return false;
		}
	}
}
function arNum(numb, lang) {
	var str = numb.toString();

	var eng = {
		'٠' : "0",
		'١' : "1",
		'٢' : "2",
		'٣' : "3",
		'٤' : "4",
		'٥' : "5",
		'٦' : "6",
		'٧' : "7",
		'٨' : "8",
		'٩' : "9"
	};

	var arabic = {
		"0" : '٠',
		"1" : '١',
		"2" : '٢',
		"3" : '٣',
		"4" : '٤',
		"5" : '٥',
		"6" : '٦',
		"7" : '٧',
		"8" : '٨',
		"9" : '٩'
	};
	var chars = str.split("");
	var newnum = new Array();

	for ( var i = 0; i < chars.length; i++) {

		if (lang == 'arb') {
			newnum[i] = arabic[chars[i]];
		}

		if (lang == 'engl') {
			newnum[i] = eng[chars[i]];
		}

	}
	return newnum.join("");
}

function HasArabicCharacters(e) {

	if (!e)
		var e = window.event;
	if (e.keyCode)
		code = e.keyCode;
	else if (e.which)
		code = e.which;
	var character = String.fromCharCode(code);
	
	
	if (code == 8 || code == 46 || code==32 || code==13 || code==9) {
		return true;
	}
	
	if(code>=48 && code<=57){
		return true;
	}
	
	var arregex = /[\u0600-\u06FF]/;

	return arregex.test(character);

}

function HasEnglishCharacters(e) {

	if (!e)
		var e = window.event;
	if (e.keyCode)
		key = e.keyCode;
	else if (e.which)
		key = e.which;
	
		
	if (code == 8 || code == 46 || code==32 || code==13 || code==9) {
		return true;
	}

	if (key > 128)
		return false;
	else
		return true;
}

