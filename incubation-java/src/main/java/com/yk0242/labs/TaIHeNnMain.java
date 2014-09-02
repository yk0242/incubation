package com.yk0242.labs;

import com.yk0242.labs.taihenn.TaIHeNnManager;

/**
 * Main class for TaIHeNn suite. 
 * @author yk242
 */
public class TaIHeNnMain {
	private static final boolean DEBUG = false; //enable/disable debug mode
	private static final boolean FAST = false; //enable/disable fast mode (use thm.setNoHistory())
	private static final String DELIM = ", ";//delimiter to use between each letter display
	private static final int LINELEN = 24;//CRLF output every this value (set 0 to disable)
	
	public static void main(String [ ] args){
		String name;
		boolean isFirst = true;
		
		//initialize thm
		TaIHeNnManager thm = new TaIHeNnManager();
		if(FAST) thm.setNoHistory();//speeds up process and prevents Java heap space error
		
		//input user name
		name = "test name";//TODO FIXME input user name
		
		//loop thm until one of the status flags is true
		while (thm.isTaihen()==false && thm.isHentai()==false) {
//		while(thm.isTaihen()==false || thm.isHentai()==false){//use to test infinite loop
			//output delimiter unless this is the first pass
			if(isFirst) isFirst = false;
			else {
				System.out.print(DELIM);
				//crlf if multiple of LINELEN
				if(LINELEN!=0 && thm.getArrLen()%LINELEN==0)
					System.out.println();
			}
			
			//advance thm by one unit, and display last char
			System.out.print(thm.advance().getLastChar());
			if(DEBUG) System.out.println(" / "+thm.getStr());//TODO comment out when privatizing getStr()
		}
		
		//output result
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append(name);
		sb.append("さんは");
		if(thm.isTaihen()) sb.append("たいへん");
		else if(thm.isHentai()) sb.append("へんたい");
		else throw new IllegalStateException("THM terminated without satisfying either state");//JIC
		sb.append("です！\n（");
		sb.append(thm.getArrLen());
		sb.append("文字で結果が出ました。）");
		System.out.println(sb.toString());
		
	}
}
