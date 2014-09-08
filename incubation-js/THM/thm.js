//thm.js - main javascript for running the TaIHeNnTaI generator suite.
//          call runThm() to start single process. 
//
// specs memo: html must have variable holders with the following ids:
//   username : user name entered
//   output   : div/span area where output will be displayed
//   delim    : delimiter to use when displaying output
// 
// 2014.09.08 yybtcbk ver 1.0 completed.

//***************************
//****** global consts ******
//***************************
//note: const declaration not yet widely supported? 
//const REPSTR=["た","い","へ","ん"];
//var REPSTR=["た","い","へ","ん"];

//***************************
//******* global vars *******
//***************************
var isRunning = false; //flag to signify a running thm loop

//***************************
//******** "Classes" ********
//***************************

//******* TaIHeNnManager Class *******
var TaIHeNnManager = function(){
//	var REPSTR=["た","い","へ","ん"];
	//======= private vars =======
	var lastChar = " ";
	var taihenCtr = 0;
	var hentaiCtr = 0;
	var ctr = 0;
	var username = "";
	var delim = ", ";
	
	//======= privileged functions =======
	//***** advance
	this.advance = function(){
		var repStrLen = this.constructor.REPSTR.length;
		
		//*** increment array by 1
		var rnd = Math.floor((Math.random()*4));
		ctr++;
		lastChar = this.constructor.REPSTR[rnd];
		
		//*** process taihenCtr
		//if ctr is full and advance is called, reset ctr
		if(taihenCtr == repStrLen) taihenCtr = 0;
		//if expected char appears, then ctr++; else reset ctr
		if(rnd == taihenCtr) taihenCtr++;
		else taihenCtr = 0;
		//!NB if counter reset when たた appears, taihenCtr should be 1. 
		if(rnd == 0) taihenCtr=1;
		
		//*** process hentaiCtr
		//if ctr is full and advance is called, reset ctr
		if(hentaiCtr == repStrLen) hentaiCtr = 0;
		//if expected char appears, then ctr++; else reset ctr
		if(rnd == (hentaiCtr+repStrLen/2)%repStrLen ) hentaiCtr++;
		else hentaiCtr = 0;
		//!NB if counter reset when へへ appears, hentaiCtr should be 1. 
		if(rnd == repStrLen/2) hentaiCtr=1;
		
//		window.alert("ctr is "+ctr+"\nlastChar is "+lastChar);//DEBUG
		
		return this;//return self to allow for chained calls
	}
	//***** isTaihen
	this.isTaihen = function(){
//		window.alert("isTaihen: "+(taihenCtr==this.constructor.REPSTR.length));//DEBUG
		return (taihenCtr==this.constructor.REPSTR.length);
	}
	//***** isHentai
	this.isHentai = function(){
//		window.alert("isHentai: "+(hentaiCtr==this.constructor.REPSTR.length));//DEBUG
		return (hentaiCtr==this.constructor.REPSTR.length);
	}
	//***** getLastChar
	this.getLastChar = function(){
		return lastChar;
	}
	//***** getCtr
	this.getCtr = function(){
//		window.alert(isNaN(ctr));//DEBUG
		return ctr;
	}
	//***** setUsername
	this.setUsername = function(name){
		username = name;
	}
	//***** getUsername
	this.getUsername = function(){
		return username;
	}
	//***** setDelim
	this.setDelim = function(dl){
		delim = dl;
	}
	//***** getDelim
	this.getDelim = function(){
		return delim;
	}
}
//======= (static) replacement string array =======
TaIHeNnManager.REPSTR=["た","い","へ","ん"];

//*************************
//******* functions *******
//*************************
//***** main function runThm()
function runThm(){
	//prevent multiple instances from simultaneously running
	if(isRunning) return;
	
//	window.alert("begin");//DEBUG
	isRunning = true;
	var thm = new TaIHeNnManager();
	
	//*** init output
	document.getElementById("output").innerHTML = "";
	
	//*** set user name
	var name = document.getElementById("username").value;
	if(name == "") name = "ななし";
	thm.setUsername(name);
	
	//*** set delimiter
	thm.setDelim(document.getElementById("delim").value);
	
	//*** set timeout wait
	var twait = 333;
	
	//*** loop thm until one of the status flags is true
	loopThm(thm, twait, true);
	
//	window.alert("end");//DEBUG
}

//***** pull out advancement loop for use with setTimeout
function loopThm(thm, twait, isFirst){
//	if(thm.getCtr()<10)window.alert("enter loop");//DEBUG
	//exit thm loop if one of the status flags is true
	if(thm.isTaihen()==true || thm.isHentai()==true){
		ending(thm);
		return;
	}
	//speed up display at given positions
		if(thm.getCtr()==12) twait*=0.2;
		if(thm.getCtr()==100) twait*=0.5;
		if(thm.getCtr()==200) twait*=0.5;
		if(thm.getCtr()==256) twait*=0.5;
	
	//output delimiter unless this is the first call
	if(!isFirst) appout(thm.getDelim());
	
	//advance thm by one unit, and display last char, after twait msecs wait
	appout(thm.advance().getLastChar());
	//recursively call loopThm
//	if(thm.getCtr()<10)window.alert("before next loop");//DEBUG
	setTimeout(function(){loopThm(thm, twait, false)}, twait);
}

//*****pull out ending for use with setTimeout
function ending(thm){
	//*** output result
	appout("\n<br><br>\n");
	var str = "";
	str+="<span class=\"tht-result\">";
	str+=thm.getUsername();
	str+="さんは";
	if(thm.isTaihen()) str+="たいへん";
	else if(thm.isHentai()) str+="へんたい";
	else window.alert("内部エラーです。管理人を連絡してください。");//JIC
	str+="です！<br>\n（";
	str+=thm.getCtr();
	str+="文字で結果が出ました。）</span>";
	appout(str);
	isRunning=false;
}

//***** utilitary function to append to output
function appout(str){
	document.getElementById("output").innerHTML += str;
}
