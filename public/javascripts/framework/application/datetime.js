'use strict';

var timeMap = new Object();
timeMap['Browser UTC Time'] = new Date().toISOString();
timeMap['Browser Local Time'] = new Date().toLocaleString();
timeMap['Browser toString()'] = new Date().toString();

var timeKeys = Object.keys(timeMap);
timeKeys.sort();

$.each(timeKeys, function(i, key){

	$("tbody").append("<tr></tr>");
	$("tbody > tr:last-child").append("<td>"+key+"</td><td>"+timeMap[key]+"</td>");
});