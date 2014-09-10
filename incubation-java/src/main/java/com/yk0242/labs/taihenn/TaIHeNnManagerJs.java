package com.yk0242.labs.taihenn;

/** Java port of THM class of thm.js - the JavaScript version of the original THM. 
 *   TaIHeNnManagerJs (THMJs) - class to manage TaIHeNn generation - 
 *   an array arr representing random configurations of 'た','い','へ', and 'ん'. 
 *   Advances arr and returns necessary parameters when called for. <br>
 *   <br>
 *   (NB - is customizable to a degree, but expected strings are fixed to <br>
 *     1) original REPSTR array (たいへん)<br>
 *     2) REPSTR array from middle looped once (へんたい) )
 *  
 */
public class TaIHeNnManagerJs {
	private static final char[] REPSTR={'た','い','へ','ん'};
	
//======= private vars =======
	private char lastChar = ' ';
	private int taihenCtr = 0;
	private int hentaiCtr = 0;
	private int ctr = 0;
	private String userName = "";
	private String delim = ", ";
	
	//======= privileged functions =======
	//***** advance
	public TaIHeNnManagerJs advance(){
		int repStrLen = REPSTR.length;
		
		//*** increment array by 1
		int rnd = (int) Math.floor(Math.random()*4);
		ctr++;
		lastChar = REPSTR[rnd];
		
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
	public boolean isTaihen(){
//		window.alert("isTaihen: "+(taihenCtr==this.constructor.REPSTR.length));//DEBUG
		return (taihenCtr==REPSTR.length);
	}
	//***** isHentai
	public boolean isHentai(){
//		window.alert("isHentai: "+(hentaiCtr==this.constructor.REPSTR.length));//DEBUG
		return (hentaiCtr==REPSTR.length);
	}
	//***** getLastChar
	public char getLastChar(){
		return lastChar;
	}
	//***** getCtr
	public int getCtr(){
//		window.alert(isNaN(ctr));//DEBUG
		return ctr;
	}
	//***** setUserName
	public void setUserName(String name){
		userName = name;
	}
	//***** getUserName
	public String getUserName(){
		return userName;
	}
	//***** setDelim
	public void setDelim(String dl){
		delim = dl;
	}
	//***** getDelim
	public String getDelim(){
		return delim;
	}
}
