//thm.js - main JavaScript for running the TaIHeNnTaI generator suite.
//          call runThm() to start single process. 
//
// specs memo: html must have variable holders with the following ids:
//   username : user name entered
//   output   : div/span area where output will be displayed
//   delim    : delimiter to use when displaying output
// 
// 2014.09.08 yybtcbk ver 2014.09.08 init ver completed.
// 2014.09.09 yybtcbk ver 2014.09.09b added twitter button

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
//ideally, I would make THM singleton, but I can't be bothered... 

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
	var userName = "";
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
	//***** setUserName
	this.setUserName = function(name){
		userName = name;
	}
	//***** getUserName
	this.getUserName = function(){
		return userName;
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
//***** main function runThm() - to be called from html
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
	thm.setUserName(name);
	
	//*** set delimiter
	thm.setDelim(document.getElementById("delim").value);
	
	//*** set (initial) timeout wait
	var twait = 136; //fallback jic element not displayed
	
	//*** loop thm until one of the status flags is true
	loopThm(thm, twait, true);
	
//	window.alert("end");//DEBUG
}

//***** pull out advancement loop for use with setTimeout
function loopThm(thm, twait, isFirst){
//	if(thm.getCtr()<10)window.alert("enter loop");//DEBUG
	//call ending and exit thm loop if one of the status flags is true
	if(thm.isTaihen()==true || thm.isHentai()==true){
		ending(thm);
		return;
	}
	//output delimiter unless this is the first call
	if(!isFirst) appout(thm.getDelim());
	
	//advance thm by one unit, and display last char
	appout(thm.advance().getLastChar());
	
//	//speed up display at given intervals
//	if(thm.getCtr()==7) twait*=0.2;
//	if(thm.getCtr()==50) twait*=0.5;
//	if(thm.getCtr()==128) twait*=0.5;
//	if(thm.getCtr()==256) twait*=0.5;
	//get display speed interactively from range input
	twait = document.getElementById("speedrange").value;
	twait *= twait/12;
	
	//recursively call loopThm with twait waiting time
//	if(thm.getCtr()<10)window.alert("before next loop");//DEBUG
	setTimeout(function(){loopThm(thm, twait, false)}, twait);
}

//*****pull out ending - called when isHentai or isTaihen
function ending(thm){
	//*** output result
	appout("\n<br><br>\n");
	
	//formulate results string so as to be reusable later for tweet button
	var resstr1 = "";
	var resstr2 = "";
	resstr1+=thm.getUserName();
	resstr1+="さんは";
	if(thm.isTaihen()) resstr1+="たいへん";
	else if(thm.isHentai()) resstr1+="へんたい";
	else window.alert("内部エラーです。管理人を連絡してください。");//JIC
	resstr1+="です！\n";
	resstr2+="（";
	resstr2+=thm.getCtr();
	resstr2+="文字で結果が出ました。）\n\n";
	
	appout("<span class=\"tht-result\">\n"+resstr1+"<br>"+resstr2+"</span><br><br>\n");
	
	//*** append results tweet button
	appout("<span id=\"restwbtn\">結果をツイートする： </span>\n");
	
	var twtxt = resstr1 + resstr2 + "たいへんたいジェネレーター\n";
	
	var twa = document.createElement("a");
	twa.className = "twitter-share-button";
	twa.href = "http://twitter.com/share";
	
	//HACK: need to treat hyphenated properties separately
	var DATAURL = "https://02f41be48d339da0cde243ece33c283e3f0c321e.googledrive.com/host/0B9f3hv6KkjXcS19ZMmxxdGE5bUE/THM/tht-gen.html";
	if(twa.setProperty){
		twa.setProperty("data-url", DATAURL, null);
		twa.setProperty("data-text", twtxt, null);
	}else{
		twa.setAttribute("data-url", DATAURL);
		twa.setAttribute("data-text", twtxt);
	}
	
	document.getElementById("restwbtn").appendChild(twa);
	
	//HACK: call twitterAPI function directly; could change?
	twttr.widgets.load();
	
	//turn off active flag to enable user to re-run app
	isRunning=false;
}

//***** utility function to append to output
function appout(str){
	document.getElementById("output").innerHTML += str;
}

