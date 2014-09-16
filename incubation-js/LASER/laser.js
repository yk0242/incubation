//laser.js - main javascript for running the LASER puzzle suite.
//
// 2012.08.31 yybtcbk ver 1.0 completed.
//
// specs memo: html must have variable holders with the following ids:
//   rows, cols, rowtxts, colstxt, puzId

//*************************
//******* global vars *****
//*************************
var onColor = "#3333FF";
var offColor = "#000000";
var onLbl = "0";
var offLbl = "x";
var indetLbl = "&nbsp;&nbsp;";
var rows;
var cols;
//laser indicators: 1:on, 0:off, -1:indet
var rowsOn=[];
var colsOn=[];

//*************************
//******* functions *******
//*************************
//***** resetMaintable
function resetMaintable(editable){
  var maintable = document.getElementById("maintable");
  rows = document.getElementById("rowstxt").value;
  cols = document.getElementById("colstxt").value;
  document.getElementById("rows").value=rows;
  document.getElementById("cols").value=cols;
  
  //---init laser switches
  for(var i=0; i<rows; i++) rowsOn[i]=-1;
  for(var i=0; i<cols; i++) colsOn[i]=-1;

  //---init table
  var tbl=document.createElement("table");
  var tblBody=document.createElement("tbody");
  
  //place on/off buttons and laser pointer in first three rows
  for (var j=-3; j<0; j++){
    var row = document.createElement("tr");
    
    //leave first three cells empty
    for (var i=0; i <3; i++){
      var cell = document.createElement("td");
      row.appendChild(cell);
    }
    
    //fill rest of row with on/off buttons
    for (var i=0; i <cols; i++){
      var cell = document.createElement("td");
      if (j==-3){
        cell.innerHTML='<input type="button" style="width:20px;" id="col_'+i+'_on" value="'+onLbl+'" onClick="colOn('+i+')">';
      }
      else if(j==-2) {
        cell.innerHTML='<input type="button" style="width:20px;" id="col_'+i+'_off" value="'+offLbl+'" onClick="colOff('+i+')">';
      }
      else {
        cell.innerHTML='<div id="col_'+i+'_laser">'+indetLbl+'</div>';
        cell.setAttribute("align","center")
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
    cell_on.innerHTML='<input type="button" style="width:20px;" id="row_'+j+'_on" value="'+onLbl+'" onClick="rowOn('+j+')">';
    row.appendChild(cell_on);
    var cell_off = document.createElement("td");
    cell_off.innerHTML='<input type="button" style="width:20px;" id="row_'+j+'_off" value="'+offLbl+'" onClick="rowOff('+j+')">';
    row.appendChild(cell_off);
    
    //place laser pointers in next col
    var cell_laser = document.createElement("td");
    cell_laser.innerHTML='<div id="row_'+j+'_laser">'+indetLbl+'</div>';
    cell_laser.setAttribute("align","center")
    row.appendChild(cell_laser);
    
    //fill remaining cells
    for (var i=0; i<cols; i++) {
      var cell = document.createElement("td");
      if(editable) cell.innerHTML='<input type="textbox" style="width:20px;">';
      else cell.innerHTML='&nbsp;';
      cell.setAttribute("id","cell_"+i+"_"+j);
      row.appendChild(cell);
    }
    tblBody.appendChild(row);
  }
  tbl.appendChild(tblBody);
  
  //set table attribs here
  tbl.setAttribute("border", "2");
  tbl.setAttribute("cellpadding", "5");
  tbl.setAttribute("id", "maintable");
  
  //replace maintable
  maintable.parentNode.replaceChild(tbl,maintable);
}


//***** loadPuzzle
function loadPuzzle(editable, bufferId){
  var buf = document.getElementById(bufferId).value;
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
  document.getElementById("puzId").value=bufferId;
  
  //fill grid
  for(var y=0; y<rows; y++){
    for(var x=0; x<cols; x++){
      var cell = document.getElementById("cell_"+x+"_"+y);
      var ch = sb[y].charAt(x);
      if(ch!="."){
        if(editable) cell.innerHTML='<input type="textbox" style="width:20px;" value="'+ch+'">';
        else cell.innerHTML='<div class="laser-text">'+ch+'</div>';
      }
    }
  }
  
  //display author if available and displayable
  var ad=document.getElementById("authorDisplay");
  var authorLbl=document.getElementById(bufferId+"au");
  if(ad!=undefined && authorLbl!=undefined) ad.innerHTML="by "+authorLbl.value;
  else if(ad!=undefined) ad.innerHTML="";
}


//***** rowOn, colOn, rowOff, colOff
function rowOn(row){
  //turn laser pointer on
  rowsOn[row]=1;
  var laser = document.getElementById("row_"+row+"_laser");
  laser.innerHTML=onLbl;
  
  //fill grid accordingly
  for(i=0; i<cols; i++){
    var cell = document.getElementById("cell_"+i+"_"+row);
    cell.setAttribute("bgColor", onColor);
  }
}

function colOn(col){
  //turn laser pointer on
  colsOn[col]=1;
  var laser = document.getElementById("col_"+col+"_laser");
  laser.innerHTML=onLbl;
  
  //fill grid accordingly
  for(j=0; j<rows; j++){
    var cell = document.getElementById("cell_"+col+"_"+j);
    cell.setAttribute("bgColor", onColor);
  }
}

function rowOff(row){
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
        cell.removeAttribute("bgColor");
      }
    }
  }
  
  //else switch the laser value to off
  else{
    rowsOn[row]=0;
    laser.innerHTML=offLbl;
    
    //fill grid accordingly
    for(i=0; i<cols; i++){
      var cell = document.getElementById("cell_"+i+"_"+row);
      //turn cell dark iff col laser is off
      if(colsOn[i]==0){
        cell.setAttribute("bgColor", offColor);
      }
      //turn cell indeterminate if col laser is indeterminate
      if(colsOn[i]==-1){
        cell.removeAttribute("bgColor");
      }
    }
  }
}

function colOff(col){
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
        cell.removeAttribute("bgColor");
      }
    }
  }
  
  //else switch the laser value to off
  else{
    colsOn[col]=0;
    laser.innerHTML=offLbl;
    
    //fill grid accordingly
    for(j=0; j<rows; j++){
      var cell = document.getElementById("cell_"+col+"_"+j);
      //turn cell dark iff row laser is off
      if(rowsOn[j]==0){
        cell.setAttribute("bgColor", offColor);
      }
      //turn cell indeterminate if row laser is indeterminate
      if(rowsOn[j]==-1){
        cell.removeAttribute("bgColor");
      }
    }
  }
}

//***** clearLasers
function clearLasers(){
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
      document.getElementById("cell_"+i+"_"+j).removeAttribute("bgColor");
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
//requires an answer string within the html
// with id (problemId)a of form (colLasersAns)/(rowLasersAns)
// where answer uses 0 for on lasers and x for off lasers
function checkAnswer(puzId){
  var check = 1; //1:ok, 0:ng, -1:?
  var answer= document.getElementById(puzId+"a").value;
  var indetAlertStr="the puzzle is incomplete...";
  var okAlertStr="correct!";
  var ngAlertStr="something is wrong...";
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
      if(answer.charAt(x)!="0") check*=0;
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
      if(answer.charAt(cols+1+y)!="0") check*=0;
    }else alert("error in row "+y); //should not occur
  }
  
  //display result
  if(check==-1) alert(indetAlertStr);
  else if(check==0) alert(ngAlertStr);
  else if(check==1) alert(okAlertStr);
}