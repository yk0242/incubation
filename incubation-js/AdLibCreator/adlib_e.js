/*  adlib.js
          Ad Lib Creator ver. 1.01.2

    a JavaScript program for creating an "Ad Lib" style html document.
   includes the following functions;
   both need to be called from the html document inside script tags:

      makeAdlibForm();   creates the form (incl. table of inputs, output area, etc.) for the ad-lib;
      writeGetAdlib();   writes a js code for the function getAdlib() (couldn't find a way to make indices work directly)
       (getAdlib();  puts the completed ad lib text in the output area created by makeAdlibForm().)

    text is the string containing the source ad lib text.
    see readme.txt for details.

   Want to allow for text to be read from an external txt file...
   also want a way to write getAdlib() directly in this file
          LastEdited by yk, 061003
   edit yk 20220503 - document.all and layers are obsolete, need to use getElementById, cf https://stackoverflow.com/a/15854566
*/

//***************** Variables used for easy customizing *******************
var showScript = 0;             //set to 1 to show source text in display area on page load
var rightMargin = 0;          //size of right margin in pixels; 0 for no margin
var btn1val = "Ad Lib!";        //value displayed on ad lib button
var btn2val = "clear display";  //value displayed on clear button
var bgcolor1 = "#C8D8EE";       //bgcolor of input area of ad lib form
var bgcolor2 = "#AABBEE";       //bgcolor of output area of ad lib form
var textColor1 = "#000000";     //color of text in input area
var textColor2 = "#000000";     //color of text in output area
var showTextColor = "#000077";  //color of text only for initial display (if showScript==1)
var showFldColor = "#FF0000";   //color of fields only for initial display (if showScript==1)

//***********************   Place Ad Lib source text here (no line breaks)   *************************
var text="&nbsp;&nbsp;&nbsp;<b>Hello <font color='#191970' size='+2'><<Your Name*name>><\/font><\/b>! This is a demo ad-lib made with the Ad Lib Creator. Here it goes!<br><br>&nbsp;&nbsp;&nbsp;To start with, here's a bit of &quot;standard&quot; ad lib:<br>--------------------------------------------<br>&nbsp;&nbsp;&nbsp;<i>Once upon a time, there were <<number*n>> <<adjective*a>> <<noun, plural*np>> in <<location>>. They were so <<adjective>> that one day, the <<noun>> came to talk to them. Now as the story goes, the <<*n>> <<*a>> <<*np>> were so <<emotional state>> from the visit that they decided to <<verb>> and <<verb>>. This was the beginning of a long journey that was to be known later as &quot;<<title of story>>&quot;. [...] When the <<*np>> were halfway through their journey, they decided to <<verb>> <<adverb>> the rest of the trip so that they could <<adverb>> <<verb>> the <<noun>>. Owing to this, they later ended up in <<location>>, where it was <<adjective (weather)*w>>; so <<*w>> in fact that <<number>> of them <<verb/past tense>> while the <<adjective>> rest <<verb/past tense>> with a <<noun>>. <\/i><br>...etc.,etc....<br>--------------------------------------------<br><br>&nbsp;&nbsp;&nbsp;Now another way to use this is as a template for creating texts; the specifications could be more specific, and the writer could perhaps choose to display the source text beforehand:<br>--------------------------------------------<br>&nbsp;&nbsp;&nbsp;<i>...well now what's with <<some topic you don't quite see the importance of>>?? All this talk about it just seems like nonsense to me. I mean, what's the point? I would be using time more wisely if I <<some activity that seems a bit more important>> rather than talk about that! But still, the talk goes on... why? Don't they see that <<some topic that seems of greater importance>> is more interesting to discuss? Or better yet, they should all try some <<interesting pastime>>; that might enlighten them a bit.<\/i><br> ... ...<br>--------------------------------------------<br><br>&nbsp;&nbsp;&nbsp;I could even make a psychological test:<br>--------------------------------------------<br>&nbsp;&nbsp;&nbsp;<i><b><<choose a number>><\/b>: the simpler this number, the more conforming you are to social standards.(?)<br>&nbsp;&nbsp;&nbsp;<b><<you've prepared a meal, and upon tasting it, you notice that you needed to add more of some ingredient. What is it?>><\/b>: this signifies somehow the characteristic(s) that you think that you yourself lack.<\/i><br>--------------------------------------------<br><br>&nbsp;&nbsp;&nbsp;Or a quiz/brainteaser:<br><br>&nbsp;&nbsp;&nbsp;<i>Your guess: <b><<Q: What do you sit on, sleep on, and brush your teeth with?>><\/b><br>&nbsp;&nbsp;&nbsp;A: a chair, a bed, and a toothbrush <\/i><br><br>&nbsp;&nbsp;&nbsp;It's up to you, the script writer, to decide what you want to make out of this. <br>&nbsp;&nbsp;&nbsp;Thanks for looking through this demo, <<*name>>!<br><br>The quote of the day:<br><marquee bgcolor='#DFDFFF'><blink><b><<Please make some kind of comment here>><\/b><\/blink><\/marquee><br>[EOF]";
//******************************************************************************************



//******************************************************************************************
//************ Functions; Don't touch unless you know what you're doing... *****************
//******************************************************************************************

function makeAdlibForm() {
  var textbit = text;        //portion of source text remaining to be analyzed
  var name = "";             //string used to store the name displayed for each input
  var sLabel = "";           //string containing the specified name of the input-field-to-be
  var index = 0;             //counts number of <<text>> sets encountered;
                             //default name of input field is the letter t followed by the index
  var i = 0;                 //index of <<
  var j = 0;                 //index of >>
  var k = 0;                 //index of *
  arrLabels = new Array();   //array of strings of already used specified names of input fields
  var found = 0;             //1 if sLabel has been found in arrLabels
  var ptext = "";            //string used for displaying source text as is

  if (showScript == 1) ptext += "<font color='"+showTextColor+"'>";

  document.write('<form name="adlibForm">');
  if (rightMargin != 0) document.write('<table width="100%"><tr><td>');
  document.write('<table width="100%" border="3" bgcolor="'+bgcolor1+'"><tr><td><table width="100%">');

  while (textbit != "") {
    index++;
    i = textbit.indexOf("<<");
    if (i == -1) break;
    j = textbit.indexOf(">>");
    if (j == -1) break;
    
    if (showScript == 1) ptext += textbit.slice(0,i)+"<font color='"+showFldColor+"'>&lt;&lt;"+textbit.slice(i+2,j)+"&gt;&gt;</font>";

    name = textbit.slice(i+2,j);
    textbit = textbit.slice(j+2);

    k = name.indexOf("*");

    if (k == -1) {
      document.write('<tr><td width="50%" align="right"><b>'+name+':<\/b><\/td>');
      document.write('<td width="50%" align="left"><input type="text" name="t'+index+'"><\/td><\/tr>');
    }

    else {     //specified label name
      sLabel = name.slice(k+1);

//first check if label has appeared before using arrLabels
    found = 0;
    for (var i = 0; i < arrLabels.length; i++) {      
      if (arrLabels[i] == sLabel) {         
        found=1;
        break; }
    }

//if first occurrance of label, output edited input table and add label to arrLabels
    if (found == 0) {
        name = name.slice(0,k);
        document.write('<tr><td width="50%" align="right"><b>'+name+':<\/b><\/td>');
        document.write('<td width="50%" align="left"><input type="text" name="'+sLabel+'"><\/td><\/tr>');
        arrLabels[arrLabels.length] = sLabel;
      }
    }
  } // end while loop

  document.write('<\/table><\/td><\/tr><\/table>');
  document.write('<table width="100%" bgcolor="'+bgcolor2+'"><tr><td valign="top" align="center" width="120"><br><input type="button" name="go" value="'+btn1val+'" onClick="getAdlib()">');
  document.write('<br><input type="button" name="clear" value="'+btn2val+'" onClick="');

  if (document.all) document.write('dispArea.innerHTML=\'\'">');
  else if (document.layers) document.write('document.layers.dispArea.document.write(\'\');document.layers.dispArea.document.close();">');
  else document.write('document.getElementById(\'dispArea\').innerHTML=\'\'">');

  document.write('<\/td><td><span id="dispArea"></span>');
  document.write('<\/td><\/tr><\/table>');
  if (rightMargin != 0) document.write('<\/td><td width="'+rightMargin+'"><\/td><\/tr><\/table>');
  document.write('<\/form>');

  if (showScript == 1){
    ptext += textbit+"<\/font>";

    if (document.all) dispArea.innerHTML=ptext;
    else if (document.layers){
      document.layers.dispArea.document.write(ptext);
      document.layers.dispArea.document.close();
    }
    else document.getElementById("dispArea").innerHTML=ptext;
  }
}

//*******************************************************************************************

function writeGetAdlib() {
  document.writeln('<script type="text/javascript" language="javascript">');
  document.writeln('<!--');

  document.write('function getAdlib() {');

  if (document.all) document.write('dispArea.innerHTML="');
  else if (document.layers) document.write('document.layers.dispArea.document.write("');
  else document.write('document.getElementById("dispArea").innerHTML="');

  document.write("<font color='"+textColor2+"'>");
  document.write('"+');

  var resstr = "";      //resulting string output to getAdlib
  var textbit = text;   //portion of source text remaining to be analyzed
  var outtext = "";     //portions of (normal) text to be copied as is to output
  var name = "";        //string used to store the text in between the << and the >>
  var sLabel = "";      //string containing the specified name of the input field to be used
  var index = 0;        //counts number of <<text>> sets encountered;
                        //default name of input field is the letter t followed by the index
  var i = 0;            //index of <<
  var j = 0;            //index of >>
  var k = 0;            //index of *

  while (textbit != "" && index < 100) {
    index++; 
    i = textbit.indexOf("<<"); 
    if (i == -1) break; 
    j = textbit.indexOf(">>"); 
    if (j == -1) break;

    name = textbit.slice(i+2,j);
    outtext = textbit.slice(0,i);
    textbit = textbit.slice(j+2);

    k = name.indexOf("*");
   
    if (k == -1) {
      resstr += '"'+outtext+'"+adlibForm.t'+index+'.value+';
    }
    else { //specified label name
      sLabel = name.slice(k+1);
      resstr += '"'+outtext+'"+adlibForm.'+sLabel+'.value+';
    }
  }//end while loop

  resstr += '"'+textbit+'"';

  if (document.all) document.write(resstr+'+"<'+'/font>";}');
  else if (document.layers) document.write(resstr+'+"<'+'/font>");document.layers.dispArea.document.close();}');
  else document.write(resstr+'+"<'+'/font>";}');

  document.writeln('');
  document.writeln('-->');
  document.write('<'+'/script>');
}
