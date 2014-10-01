//twbtn.js - JavaScript for loading output with tweet button.
// 2014.10.01 yybtcbk init ver completed.

//*************************
//******* functions *******
//*************************
//***** main function to be called from html
function changeContents(){
	//formulate results string so as to be reusable later for tweet button
	var text = document.getElementById("inputarea").value;
	
	var out = "<span style=\"font-size:x-large;font-weight:bold;color:#3355FF;\">\n" 
		              + text + "\n</span><br><br>\n" 
		              + "<span id=\"restwbtn\">Tweet: </span>\n";
	
	document.getElementById("output").innerHTML = out;
	
	//*** append results tweet button
	var twtxt = text;
	if(twtxt.length > 95){
		twtxt = twtxt.substring(0,92) + "...";
	}
	twtxt += "\nTwitter-Button-Test\n";
	
	var twa = document.createElement("a");
	twa.className = "twitter-share-button";
	twa.href = "http://twitter.com/share";
	
	//HACK: need to treat hyphenated properties separately
	var DATAURL = "https://02f41be48d339da0cde243ece33c283e3f0c321e.googledrive.com/host/0B9f3hv6KkjXcS19ZMmxxdGE5bUE/twbtn-test/twitter-button-test.html";
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
	
	//change display for start button
	document.getElementById("startbutton").value="v Change Contents v";
	
}
