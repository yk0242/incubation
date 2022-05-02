/*  adlib.js
          Ad Lib Creator ver. 1.01.2

    JavaScriptを使って、Ad Lib（アドリブ）風htmlを作成するためのスクリプトです。
   関数：（ともにhtmlからの呼び出し必要）

      makeAdlibForm();   アドリブのフォーム作成（入力・出力両方とも）；
      writeGetAdlib();   getAdlib()をhtml内に書く（直接書く方法が見つからないので）
       (getAdlib();　　makeAdlibForm()が作成した出力エリアにアドリブを書き込む。)

    アドリブのソーステキストは、textへ代入して下さい。
    詳しくはリードミー.txtをご覧下さい。

   外部のtxtファイルからの読み込みを出来ない物か…
   getAdlib() を直接ここに書く方法は？
          LastEdited by yk, 061003
   edit yk 20220503 - document.all and layers are obsolete, need to use getElementById, cf https://stackoverflow.com/a/15854566
*/

//***************** 簡単カスタマイズ用パラメータ *******************
var showScript = 0;          //1ならページ表示と同時に、表示エリアにソーステキストをそのまま表示します。
var rightMargin = 0;         //右マージンの幅（ピクセル数）；0だとマージンなし
var btn1val = "アドリブ！";    //アドリブ作成ボタンの表示
var btn2val = "表示クリア";    //クリアボタンの表示
var bgcolor1 = "#C8D8EE";      //入力エリアの背景色
var bgcolor2 = "#AABBEE";      //出力エリアの背景色
var textColor1 = "#000000";    //入力エリアのテキスト色
var textColor2 = "#000000";    //出力エリアのテキスト色
var showTextColor = "#000077"; //ソーステキスト初期表示の、テキストの色（showScript==1の場合）
var showFldColor = "#FF0000";  //ソーステキスト初期表示の、フィールド部分の色（showScript==1の場合）

//********************** アドリブのソーステキストをここに書く（改行無しで） ************************
var text="　　　<b><font color='#191970' size='+2'><<あなたの名前*name>><\/font><\/b>さん、ようこそ！これは、アドリブクリエイターを使って作成されたアドリブ（Ad lib）風のページのデモです。始まり始まり～！<br><br>　　　まずは、『通常』のAd Libをやってみましょう：<br>--------------------------------------------<br>　　　<i>昔々、ある所に、<<名詞*a>>と<<名詞*b>>がいました。ある日、<<*a>>は<<場所>>へ<<動詞>>に、<<*b>>は<<場所>>に<<動詞>>ために行きました。すると．．．…．．．という訳で、<<名前*tarou>>は<<名詞*inu>>、<<名詞*saru>>、<<名詞*kiji>>を連れて、<<偉業*ig>>をしに出かけました。それぞれが要求した<<名詞>>の数は、<<数*n1>>、<<数>>、<<数*n2>>だったとか、<<*kiji>>は最初<<数>>いたうち、唯一の生き残りだったり、<<*saru>>はただ腹が減ってただけで、<<*tarou>>の言う事は聞いてなかったとか、<<*inu>>は実は別の有名な昔話に出ていた物と同一だとか、適当に言ってみてるだけでは話が進まないんですが、まあこう言う部分もあってこその物語ですからね．．．...なんてやってるうちに、<<*tarou>>が<<*ig>>を成し遂げて、<<*n1>>個の珊瑚のかけらと、<<*n2>>ｋｇの金銀財宝を持って帰ってきました。え、話が飛んでるって？では最初からゆっくり行きましょうか…　昔々、ある所に、<<*a>>と<<*b>>が．．．<\/i><br>...　...　...<br>--------------------------------------------<br><br>　　　他にも、たとえばある形式のテキストを作成するテンプレートも作れます。表示される項目名をもっと詳しくして、また、作者の意向によって、最初からソースを見せるのもありです：<br>--------------------------------------------<br>　　　<i>．．．何なんだろうね、最近、話の種になってる<<自分にはあまり重要さが見えない物事>>って？？一体何が楽しくて一生懸命そんな事について話すのか、よく分からん。それがどうした、って感じなんだけど、俺的には。そんな事よりも<<もう少し興味がある物事>>の話をしてた方が時間を有効に使えるね。それなのに、みんなは話をやめない...そんな時間があったら、<<もっと重要な事柄>>の話でもしてろってんだ。<<趣味>>でもやってみればいいんだ、みんな。そうすれば、少しは分かるさ。<\/i><br> ... ... ...<br>--------------------------------------------<br><br>　　　心理テストも出来ちゃいます。（これはいい加減ですが；）<br>--------------------------------------------<br>　　　<i><b><<任意の整数を選んで下さい。>><\/b>：この数が単純なほど、あなたは社会的に一般な規定に合わせ易いです、とか<br>　　　<b><<あなたは作った料理を味見してみました。すると、何かが足りない気がする。それは何でしょう？>><\/b>：この答えが、あなた自身に足りない物を表してるとか何とか。<\/i><br>--------------------------------------------<br><br>　　　クイズとかパズルとかも：<br><br>　　　<i>あなたの答え：<b><<生麦生米生卵。「ま」って何回言った？>><\/b><br>　　　答え：四回。<\/i><br><br>　　　何を作りたいか。それは、製作者のあなたが決める事です。<br>　　　<<*name>>さん、このデモをお読みくださって、どうもありがとうございました！<br><br>今日の一言：<br><marquee bgcolor='#DFDFFF'><blink><b><<何か一言、コメントをお願いします！>><\/b><\/blink><\/marquee><br>[EOF]";
//******************************************************************************************



//******************************************************************************************
//********************* 関数；よく分からない人はいじらない様に。 ***************************
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
  document.writeln('<'+'/script>');
}