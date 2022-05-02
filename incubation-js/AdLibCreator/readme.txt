Ad Lib Creator ver. 1.01.2 by yybtcbk

        SYNTAX OF SOURCE TEXT:
        ------------------------
        Normal text like this        : will be displayed as is in the output area
        <<SOMETEXT>>             : field that is to be replaced by a string of type SOMETEXT; SOMETEXT is the text used in the input area.
        <<SOMETEXT*LABEL>>  : same as above, but the same input string may be used later on in the output repeatedly
        <<*LABEL>>                  : field that is to be replaced by the string given earlier for <<SOMETEXT*LABEL>>

        Notes: 
          -text cannot contain double quotes (") directly. Use &quot; instead.
          -text can contain HTML tags, and the output will (should) process them correctly. Note that any parameter of a tag must be surrounded by ' ' and not " "; also, any closing tag should be of form <\/TAG>.
          -LABEL should not be of the form "t followed by an integer"(like t2, t17, etc.), be an empty string, or start with a number (e.g., 2names).
          -SOMETEXT can have the same values in different <<SOMETEXT>> fields; they will still be treated separately.
          -LABEL must have different values for <<SOMETEXT*LABEL>> fields that are meant to hold separate strings; 
             i.e., if <<SOMETEXT1*SOMELABEL>> precedes <<SOMETEXT2*SOMELABEL>>, the latter will be taken to be equivalent to <<*SOMELABEL>>.
          -<<SOMETEXT*SOMELABEL>> should precede <<*SOMELABEL>>; if <<*SOMELABEL>> appears first, it will be assumed that this is of the form <<SOMETEXT*LABEL>>, where SOMETEXT is an empty string.
          -Simple customizations of the page created can be done by editing the variables near the beginning of the js script.

               Parameters: 
                 var showScript = 0;                  //set to 1 to show source text in display area on page load
                 var rightMargin = 120;               //size of right margin in pixels; 0 for no margin
                 var btn1val = "Ad Lib!";             //value displayed on ad lib button
                 var btn2val = "clear display";      //value displayed on clear button
                 var bgcolor1 = "#C8D8EE";        //bgcolor of input area of ad lib form
                 var bgcolor2 = "#AABBEE";        //bgcolor of output area of ad lib form
                 var textColor1 = "#000000";        //color of text in input area
                 var textColor2 = "#000000";        //color of text in output area
                 var showTextColor = "#000077";  //color of text only for initial display (if showScript==1)
                 var showFldColor = "#FF0000";    //color of fields only for initial display (if showScript==1)　　　　　　　　

         For an example of an English ad lib page, see adlib_e.html. (uses adlib_e.js)

Warning:
-------
   This Creator is to be taken to be equivalent to freeware. Therefore, it is to be downloaded and used at the user's risk; I will not be held responsible for any damages directly or indirectly caused by this script, and I am not required to provide additional support (although I'll probably do so anyway if it's needed).
   You are free to use this script for purely personal uses. For other cases (including uploading something to a personal website), I ask you to contact me, preferably beforehand, or soon after using it. 
   The script may possibly undergo some bug fixes and improvements; the most recent version of the Creator will be available at my website, so please come visit once in a while if you have some time. 

E-mail: yybtcbk@hotmail.com
HP: http://www.geocities.com/yybtcbk
BBS (Japanese): http://mintclub.jp/cgi-bin/mbs/bbs.cgi?room=yybtcbk

[EOF]