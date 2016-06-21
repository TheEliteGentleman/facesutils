"use strict";
function capitalize(str) {
    return str.charAt(0).toUpperCase() + str.slice(1);
}

function uncapitalize(str) {
    return str.charAt(0).toLowerCase() + str.slice(1);
}

function contains(str, s) {
	return str.indexOf(s) > -1;
}

function startsWith(str, prefix, position) {
	return str.slice(position, prefix.length) === prefix;
}

function endsWith(str, suffix) {
	//return str.indexOf(suffix, str.length - suffix.length) !== -1; //Is there a better algorithm using slice()
	return str.slice(-suffix.length) === suffix;
}

//finally
if (typeof String.prototype !== "underfined") {
	if (typeof String.prototype.capitalize !== "function") {
		String.prototype.capitalize = function() {
		    return capitalize(this);
		};
	}
	
	if (typeof String.prototype.uncapitalize !== "function") {
		String.prototype.uncapitalize = function() {
		    return uncapitalize(this);
		};
	}
	
	if (typeof String.prototype.contains !== "function") {
		String.prototype.contains = function(s) {
		    return contains(this, s);
		};
	}
	
	if (typeof String.prototype.startsWith !== "function") {
		String.prototype.startsWith = function(prefix, position) {
		    return startsWith(this, prefix, position);
		};
	}
	
	if (typeof String.prototype.endsWith !== "function") {
		String.prototype.endsWith = function(suffix) {
		    return endsWith(this, suffix);
		};
	}
}
