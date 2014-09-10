package com.yk0242.labs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.yk0242.labs.htmltag.A;
import com.yk0242.labs.taihenn.TaIHeNnManagerJs;

/**
 * Java port of main part of thm.js - the JavaScript version of the original THM.
 * Main class for TaIHeNn-Js suite. 
 * @author yk242
 */
public class TaIHeNnJsMain {
	private static boolean isRunning = false; //flag to signify a running thm loop
	
	public static void main(String[] args){
		if(isRunning) return;
		
//	window.alert("begin");//DEBUG
	isRunning = true;
	TaIHeNnManagerJs thm = new TaIHeNnManagerJs();
	
	//*** init output
//	document.getElementById("output").innerHTML = "";
	
	//*** set user name
//	var name = document.getElementById("username").value;
	//!replace by user input
	String name="ななし";
	System.out.print("あなたの名前を入力してください：");
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	try {
		name = br.readLine();
	} catch (IOException e) {
		e.printStackTrace();
//	} finally {
//		try{
//			if(br!=null) br.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	//if user name input is empty, replace by default name
//	if(name == "") name = "ななし";
	if(name.equals("")) name="ななし";
	thm.setUserName(name);
	
	//*** set delimiter
//	thm.setDelim(document.getElementById("delim").value);
	//!replace by user input
	String delim=", ";
	System.out.print("文字間表示を入力してください（デフォルトは「, 」）：");
	try {
		delim = br.readLine();
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		try{
			if(br!=null) br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	System.out.println();
	//!if delim input is empty, replace by default name
	if(delim.equals("")) delim=", ";
	thm.setDelim(delim);
	
	//*** set (initial) timeout wait
	int twait = 333;
	
	//*** loop thm until one of the status flags is true
	loopThm(thm, twait, true);
	}
	
	//***** pull out advancement loop for use with setTimeout
	private static void loopThm(TaIHeNnManagerJs thm, int twait, boolean isFirst){
	//	if(thm.getCtr()<10)window.alert("enter loop");//DEBUG
		//call ending and exit thm loop if one of the status flags is true
		if(thm.isTaihen()==true || thm.isHentai()==true){
			ending(thm);
			return;
		}
		//output delimiter unless this is the first call
		if(!isFirst) appout(thm.getDelim());
		
		//advance thm by one unit, and display last char
		appout(""+thm.advance().getLastChar());
		
		//speed up display at given intervals
		if(thm.getCtr()==7) twait*=0.2;
		if(thm.getCtr()==50) twait*=0.5;
		if(thm.getCtr()==128) twait*=0.5;
		if(thm.getCtr()==256) twait*=0.5;
		
		//recursively call loopThm with twait waiting time
	//	if(thm.getCtr()<10)window.alert("before next loop");//DEBUG
		setTimeout("loopThm(thm, twait, false)",thm, twait);
	}
	
	//*****pull out ending - called when isHentai or isTaihen
	private static void ending(TaIHeNnManagerJs thm){
		//*** output result
		appout("\n<br><br>\n");
		
		//formulate results string so as to be reusable later for tweet button
		String resstr1 = "";
		String resstr2 = "";
		resstr1+=thm.getUserName();
		resstr1+="さんは";
		if(thm.isTaihen()) resstr1+="たいへん";
		else if(thm.isHentai()) resstr1+="へんたい";
		else System.err.println("内部エラーです。管理人を連絡してください。");//JIC
		resstr1+="です！\n";
		resstr2+="（";
		resstr2+=thm.getCtr();
		resstr2+="文字で結果が出ました。）\n\n";
		
		appout("<span class=\"tht-result\">\n"+resstr1+"<br>"+resstr2+"</span><br><br>\n");
		
		//*** append results tweet button
		appout("<span id=\"restwbtn\">結果をツイートする： </span>\n");
		
		String twtxt = resstr1 + resstr2 + "たいへんたいジェネレーター\n";
		
		A twa = new A();
		twa.className = "twitter-share-button";
		twa.href = "http://twitter.com/share";
		
		//HACK: need to treat hyphenated properties separately
		String DATAURL = "https://googledrive.com/host/0B9f3hv6KkjXcS19ZMmxxdGE5bUE/THM/tht-gen.html";
		if(twa.className=="twitter-share-button"){//!dummy condition added
			twa.setProperty("data-url", DATAURL, null);
			twa.setProperty("data-text", twtxt, null);
		}else{
			twa.setAttribute("data-url", DATAURL);
			twa.setAttribute("data-text", twtxt);
		}
		
//		document.getElementById("restwbtn").appendChild(twa);
		appout(twa.toString());
		
		//HACK: call twitterAPI function directly; could change?
//		twttr.widgets.load();
		
		//turn off active flag to enable user to re-run app
		isRunning=false;
	}
	
	//***** utility function to append to output
	private static void appout(String str){
		System.out.print(str);
	}
	
	//***** ! emulate setTimeout natively present in JavaScript
	//      ! only works for method in this class
	private static void setTimeout(String methName, TaIHeNnManagerJs thm, int twait){
		try {
			Thread.sleep(twait);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		loopThm(thm, twait, false);
	}
}
