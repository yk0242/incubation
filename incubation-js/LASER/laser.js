//laser.js - main javascript for running the LASER puzzle suite.
//
// 2012.08.31 yybtcbk ver 1.0 completed.
// 2014.09.22 yybtcbk altered to externalize puzzle contents to laser_problems.js
// 2014.09.23 yybtcbk altered to externalize some styling to css file
// 
// specs memo: html must have variable holders with the following ids:
//   rows, cols, rowtxts, colstxt, puzId

//*************************
//******* global vars *****
//*************************
var onLbl = "O";
var offLbl = "X";
var indetLbl = "&nbsp;&nbsp;&nbsp;";
//laser indicators: 1:on, 0:off, -1:indet
var rowsOn=[];
var colsOn=[];
//LASER problem set - to be set from laser_problems.js
var laserProbsArr = [];
//starting Date object for LASER timer counter
var startDate = new Date();
//var for holding timer setInterval
var timerVar=null;

//*************************************
//***** LASER Problems object def *****
//*************************************
var LaserProblem = function(){
	//======= private vars =======
	var id = "";      //puzzle id
	var text = "";    //text for select option
	var author = "";  //author of puzzle
	var content = ""; //puzzle field content
	var answer = "";  //answer string - row / col
}

//*************************
//******* functions *******
//*************************
//***** initialization - drop-down list and default problem loader
function initLoadLaserSet(){
	//--- load LASER problems set as defined in laser_problems.js
	setLaserProbs();
	
	//--- set selector options
	var sel = document.getElementById("puzzleSelect");
	var opt; //for temp storage of each option; call create each iteration
	
	for(var i=0; i<laserProbsArr.length; i++){
		var lp = laserProbsArr[i];
		opt = document.createElement("option");
		opt.value = lp.id;
		opt.text = lp.text;
		sel.add(opt);
	}
	//--- load first puzzle by default
	loadPuzzle(false, sel.value);
}

// ***** utility function to get puzzle index
function getPuzzleIndex(puzzleId){
	var pind = -1;
	for(var i=0; i<laserProbsArr.length; i++){
		if(laserProbsArr[i].id == puzzleId){
			pind = i;
			break;
		}
	}
	return pind;
}

//***** resets main table - 
// swipes contents & lasers and sets new table of size rowstxt and colstxt
// also resets timer if editable is false (i.e., if in problems mode)
function resetMaintable(editable){
	var maintable = document.getElementById("maintable");
	var rows = document.getElementById("rowstxt").value;
	var cols = document.getElementById("colstxt").value;
	document.getElementById("rows").value=rows;
	document.getElementById("cols").value=cols;
	
	//---init laser switches
	rowsOn=[];
	colsOn=[];
	for(var i=0; i<rows; i++) rowsOn[i]=-1;
	for(var i=0; i<cols; i++) colsOn[i]=-1;
	
	//---init table
	var tbl=document.createElement("table");
	var tblBody=document.createElement("tbody");
	
	//place on/off buttons and laser pointer in first three rows
	for (var j=-3; j<0; j++){
		var row = document.createElement("tr");
		
		//leave first three cells empty
		for (var i=0; i<3; i++){
			var cell = document.createElement("td");
			cell.className="laser-tl-td";
			row.appendChild(cell);
		}
		
		//fill rest of row with on/off buttons
		for (var i=0; i <cols; i++){
			var cell = document.createElement("td");
			if (j==-3){
				if(i%2==0) cell.className="laser-on-td-e";
				else cell.className="laser-on-td-o";
				cell.innerHTML='<input type="button" class="laser-on-button" id="col_'+i+'_on" value="'+onLbl+'" onClick="colOn('+i+')">';
			}
			else if(j==-2){
				if(i%2==0) cell.className="laser-off-td-e";
				else cell.className="laser-off-td-o";
				cell.innerHTML='<input type="button" class="laser-off-button" id="col_'+i+'_off" value="'+offLbl+'" onClick="colOff('+i+')">';
			}
			else{
				if(i%2==0) cell.className="laser-src-td-e";
				else cell.className="laser-src-td-o";
				cell.innerHTML='<div id="col_'+i+'_laser">'+indetLbl+'</div>';
				cell.setAttribute("align","center");
			}
			row.appendChild(cell);
		}
		
		tblBody.appendChild(row);
	}
	
	// create remaining rows
	for (var j=0; j<rows; j++){
		var row = document.createElement("tr");
		
		//place on/off buttons and laser pointer in first two cols
		var cell_on = document.createElement("td");
		if(j%2==0) cell_on.className="laser-on-td-e";
		else cell_on.className="laser-on-td-o";
		cell_on.innerHTML='<input type="button" class="laser-on-button" id="row_'+j+'_on" value="'+onLbl+'" onClick="rowOn('+j+')">';
		row.appendChild(cell_on);
		var cell_off = document.createElement("td");
		if(j%2==0) cell_off.className="laser-off-td-e";
		else cell_off.className="laser-off-td-o";
		cell_off.innerHTML='<input type="button" class="laser-off-button" id="row_'+j+'_off" value="'+offLbl+'" onClick="rowOff('+j+')">';
		row.appendChild(cell_off);
		
		//place laser pointers in next col
		var cell_laser = document.createElement("td");
		if(j%2==0) cell_laser.className="laser-src-td-e";
		else cell_laser.className="laser-src-td-o";
		cell_laser.innerHTML='<div id="row_'+j+'_laser">'+indetLbl+'</div>';
		cell_laser.setAttribute("align","center");
		row.appendChild(cell_laser);
		
		//fill remaining cells
		for (var i=0; i<cols; i++) {
			var cell = document.createElement("td");
			if(editable) cell.innerHTML='<input type="textbox" style="width:20px;">';
			else cell.innerHTML='&nbsp;';
			cell.setAttribute("id","cell_"+i+"_"+j);
			if(j%2==0){
				if(i%2==0) cell.className="laser-main-td-ee";
				else cell.className="laser-main-td-oe";
			}else{
				if(i%2==0) cell.className="laser-main-td-oe";
				else cell.className="laser-main-td-oo";
			}
			row.appendChild(cell);
		}
		tblBody.appendChild(row);
	}
	tbl.appendChild(tblBody);
	
	//set table attribs here
//	tbl.setAttribute("border", "2px");
//	tbl.setAttribute("cellpadding", "2px");
	tbl.setAttribute("id", "maintable");
	
	//replace maintable
	maintable.parentNode.replaceChild(tbl,maintable);
	
	//reset timer
	if(!editable){
		resetTimer();
		updateTimer();//zero down timer display
	}
}

//***** loads specified puzzle in solve or edit mode
function loadPuzzle(editable, puzzleId){
	var pind = getPuzzleIndex(puzzleId);
	if(pind<0){ //if puzzle id not found - JIC
		alert("INTERNAL ERROR!");//should not occur
		return;
	}
	var buf = laserProbsArr[pind].content;
	if(buf==undefined || buf=="") return; //probably a divider - don't load
	
	buf = buf.replace(/ /g,"");
	var sb =buf.split("\n");
	
	//set cols to length of first row of buffer input
	var cols=sb[0].length;
	//TODO FIXME: MSIE workaround; cols seems to be +1ed for MSIE?
	if(navigator.appName=="Microsoft Internet Explorer") cols--;
	
	//set rows to number of rows in buffer input, disregarding terminating empty lines
	var rows=sb.length;
	while(sb[rows-1]=="") rows--;
	
	//set row and col sizes and reset maintable
	document.getElementById("rowstxt").value=rows;
	document.getElementById("colstxt").value=cols;
	resetMaintable(editable);
	document.getElementById("puzId").value=puzzleId;
	document.getElementById("results").innerHTML = ''; //clear results field on new puzzle load
	
	//fill grid
	for(var y=0; y<rows; y++){
		for(var x=0; x<cols; x++){
			var ch = sb[y].charAt(x);
			if(ch!="."){
				var cell = document.getElementById("cell_"+x+"_"+y);
				if(editable) cell.innerHTML='<input type="textbox" style="width:20px;" value="'+ch+'">';
				else cell.innerHTML='<div class="laser-text">'+ch+'</div>';
			}
		}
	}
	
	//display author if available and displayable
	var tad = document.getElementById("titleAuthorDisplay");
	if(tad!=null){//if it's null, we're probably on the edit mode page
		var author=laserProbsArr[pind].author;
		var title = laserProbsArr[pind].text;
		var tadstr = "<b><u>";
		if(title!=undefined && title!="") tadstr += title;
		else tadstr += "Untitled";
		tadstr += "</u></b> by ";
		if(author!=undefined && author!="") tadstr += author;
		else tadstr += "(author unknown)";
		tad.innerHTML = tadstr;
	}
}


//***** rowOn, colOn, rowOff, colOff - turns laser on/off for row/col
function rowOn(row){
	var rows=document.getElementById("rows").value;
	var cols=document.getElementById("cols").value;
	//turn laser pointer on
	rowsOn[row]=1;
	var laser = document.getElementById("row_"+row+"_laser");
	laser.innerHTML='<span class="laser-onsrc-text">'+onLbl+'</span>';
	
	//fill grid accordingly
	for(i=0; i<cols; i++){
		var cell = document.getElementById("cell_"+i+"_"+row);
		cell.className=cell.className.replace(/laser-main-td-off/g,'');
		cell.className+=" laser-main-td-on";
	}
}

function colOn(col){
	var rows=document.getElementById("rows").value;
	var cols=document.getElementById("cols").value;
	//turn laser pointer on
	colsOn[col]=1;
	var laser = document.getElementById("col_"+col+"_laser");
	laser.innerHTML='<span class="laser-onsrc-text">'+onLbl+'</span>';
	
	//fill grid accordingly
	for(j=0; j<rows; j++){
		var cell = document.getElementById("cell_"+col+"_"+j);
		cell.className=cell.className.replace(/laser-main-td-off/g,'');
		cell.className+=" laser-main-td-on";
	}
}

function rowOff(row){
	var rows=document.getElementById("rows").value;
	var cols=document.getElementById("cols").value;
	var laser = document.getElementById("row_"+row+"_laser");
	
	//if row laser is off, then switch the laser value to indeterminate
	if(rowsOn[row]==0){
		rowsOn[row]=-1;
		laser.innerHTML=indetLbl;
		
		//fill grid accordingly
			for(i=0; i<cols; i++){
			var cell = document.getElementById("cell_"+i+"_"+row);
			//turn cell clear iff col laser is not on
			if(colsOn[i]!=1){
				cell.className=cell.className.replace(/laser-main-td-on/g,'');
				cell.className=cell.className.replace(/laser-main-td-off/g,'');
			}
		}
	}
	
	//else switch the laser value to off
	else{
		rowsOn[row]=0;
		laser.innerHTML='<span class="laser-offsrc-text">'+offLbl+'</span>';
		
		//fill grid accordingly
		for(i=0; i<cols; i++){
			var cell = document.getElementById("cell_"+i+"_"+row);
			//turn cell dark iff col laser is off
			if(colsOn[i]==0){
				cell.className=cell.className.replace(/laser-main-td-on/g,'');
				cell.className+=" laser-main-td-off";
			}
			//turn cell indeterminate if col laser is indeterminate
			if(colsOn[i]==-1){
				cell.className=cell.className.replace(/laser-main-td-on/g,'');
				cell.className=cell.className.replace(/laser-main-td-off/g,'');
			}
		}
	}
}

function colOff(col){
	var rows=document.getElementById("rows").value;
	var cols=document.getElementById("cols").value;
	var laser = document.getElementById("col_"+col+"_laser");
	
	//if col laser is off, then switch the laser value to indeterminate
	if(colsOn[col]==0){
		colsOn[col]=-1;
		laser.innerHTML=indetLbl;
		
		//fill grid accordingly
		for(j=0; j<rows; j++){
			var cell = document.getElementById("cell_"+col+"_"+j);
			//turn cell clear iff row laser is not on
			if(rowsOn[j]!=1){
				cell.className=cell.className.replace(/laser-main-td-on/g,'');
				cell.className=cell.className.replace(/laser-main-td-off/g,'');
			}
		}
	}
	
	//else switch the laser value to off
	else{
		colsOn[col]=0;
		laser.innerHTML='<span class="laser-offsrc-text">'+offLbl+'</span>';
		
		//fill grid accordingly
		for(j=0; j<rows; j++){
			var cell = document.getElementById("cell_"+col+"_"+j);
			//turn cell dark iff row laser is off
			if(rowsOn[j]==0){
				cell.className=cell.className.replace(/laser-main-td-on/g,'');
				cell.className+=" laser-main-td-off";
			}
			//turn cell indeterminate if row laser is indeterminate
			if(rowsOn[j]==-1){
				cell.className=cell.className.replace(/laser-main-td-on/g,'');
				cell.className=cell.className.replace(/laser-main-td-off/g,'');
			}
		}
	}
}

//***** clears all lasers in main table
function clearLasers(){
	var rows=document.getElementById("rows").value;
	var cols=document.getElementById("cols").value;
	
	//turn col lasers indeterminate
	for(i=0; i<cols; i++){
		colsOn[i]=-1;
		document.getElementById("col_"+i+"_laser").innerHTML=indetLbl;
	}
	
	//iterate each row
	for(j=0; j<rows; j++){
		//turn row laser indeterminate
		rowsOn[j]=-1;
		document.getElementById("row_"+j+"_laser").innerHTML=indetLbl;
		
		//clear grid row
		for(i=0; i<cols; i++){
			var cell = document.getElementById("cell_"+i+"_"+j);
			cell.className=cell.className.replace(/laser-main-td-on/g,'');
			cell.className=cell.className.replace(/laser-main-td-off/g,'');
		}
	}
}

//***** dumpPuzzle
//only for the edit mode with the puzzleBuffer textarea
function dumpPuzzle(){
	var rows=document.getElementById("rows").value;
	var cols=document.getElementById("cols").value;
	var buf="";
	
	for(y=0; y<rows; y++){
		for(x=0; x<cols; x++){
			var cell = document.getElementById("cell_"+x+"_"+y);
			//assuming cell includes the input tag if this function is called...
			var val = cell.getElementsByTagName("input")[0].value;
			val = val.replace(/ /g, "");
			
			if(val=="1"||val=="2"||val=="3"||val=="4"||val=="0") buf+=val;
			else buf+=".";
		}
		buf+="\n";
	}
	document.getElementById("puzzleBuffer").value=buf;
}

//***** checkAnswer
//requires an answer string within the LaserProblem object
// of form (colLasersAns)/(rowLasersAns)
// where answer uses o for on lasers and x for off lasers
function checkAnswer(puzzleId){
	var check = 1; //1:ok, 0:ng, -1:incomplete
	var indetAlertStr="the puzzle is incomplete...";
	var okAlertStr="Correct!\nSee below for results!";
	var ngAlertStr="something is wrong...";
	var rows = document.getElementById("rows").value;
	var cols = document.getElementById("cols").value;
	
	var pind = getPuzzleIndex(puzzleId);
	if(pind<0){ //if puzzle id not found - JIC
		alert("INTERNAL ERROR!");//should not occur
		return;
	}
	var answer = laserProbsArr[pind].answer;
	
	//since the var cols is mistakenly thought to be a string,
	// we cast it into a number here so we can use it later
	cols*=1;
	
	//check col lasers
	for(x=0; x<cols; x++){
		if(colsOn[x]==-1){
			check=-1;
			break;
		}
		 else if(colsOn[x]==0){
			if(answer.charAt(x)!="x") check*=0;
		}else if(colsOn[x]==1){
			if(answer.charAt(x)!="o") check*=0;
		}else alert("error in col "+x); //should not occur
	}
	if(check==-1){
		alert(indetAlertStr);
		return;
	}
	
	//check row lasers
	for(y=0; y<rows; y++){
		if(rowsOn[y]==-1){
			check=-1;
			break;
		}
		else if(rowsOn[y]==0){
			if(answer.charAt(cols+1+y)!="x") check*=0;
		}else if(rowsOn[y]==1){
			if(answer.charAt(cols+1+y)!="o") check*=0;
		}else alert("error in row "+y); //should not occur
	}
	
	//display result
	if(check==-1) alert(indetAlertStr);
	else if(check==0) alert(ngAlertStr);
	else if(check==1){
		clearInterval(timerVar);
		timerVar = null;
		updateTimer();
		dispResults();
		alert(okAlertStr);
	}
}

//***** load puzzle from puzzleBuffer 
// assumes html has (textarea) element with id puzzleBuffer
function loadFromPuzzleBuffer(){
	var pBuf = document.getElementById("puzzleBuffer").value;
	
	//append to head of laserProbsArr to be able to reuse loadPuzzle()
	// needs to be added to head, since repeated addition needs to be possible,
	// and array searching takes first match
	var puz = new LaserProblem();
	puz.id = "pbuf";
	puz.content = pBuf;
	laserProbsArr.unshift(puz);
	
	//re-use loadPuzzle()
	loadPuzzle(true, "pbuf");
}

//***** resets and starts timer (if it isn't already moving)
function resetTimer(){
	startDate = new Date();
	if(!timerVar) {
		timerVar = setInterval(function(){updateTimer()}, 1000);
	}
}

//***** updates timer display
function updateTimer(){
	document.getElementById("lasertimer").innerHTML = getTimeDiff(startDate);
}

//***** returns string representing time as mm:ss of elapsed time from given date
function getTimeDiff(sDate){
	var nDate = new Date();
	var ets = Math.floor((nDate.getTime() 
			- sDate.getTime())/1000);
	var min = Math.floor(ets/60);
	var sec = ets%60;
	var str = "";
	if(min<10) str += "0";
	str += min + ":";
	if(sec<10) str += "0";
	str += sec;
	return str;
}

//***** displays results upon solving puzzle
function dispResults(){
	var clrtime = getTimeDiff(startDate);//call here to minimize difference in display
	var res = document.getElementById("results");
	
	//formulate results string so as to be reusable later for tweet button
	var str = 'cleared the LASER Puzzle "';
	var puzzleId = document.getElementById("puzId").value;
	var pind = getPuzzleIndex(puzzleId);
	if(pind<0){ //if puzzle id not found - JIC
		alert("INTERNAL ERROR!");//should not occur
		return;
	}
	var puz = laserProbsArr[pind];
	str += puz.text;
	str += '" in ' + clrtime + " !";
	
	res.innerHTML = '<span class="laser-result">\nYou ' + str + '</span>\n<br>\n';
	
	//*** append results tweet button
	res.innerHTML += "<span id=\"restwbtn\">Tweet Results: </span>\n";
	
	var twtxt = "I " + str;
	
	var twa = document.createElement("a");
	twa.className = "twitter-share-button";
	twa.href = "http://twitter.com/share";
	
	//HACK: need to treat hyphenated properties separately
	var DATAURL = "https://02f41be48d339da0cde243ece33c283e3f0c321e.googledrive.com/host/0B9f3hv6KkjXcS19ZMmxxdGE5bUE/LASER/puz_LASER.html";
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
}

