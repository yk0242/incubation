package com.yk0242.labs;

import java.util.ArrayList;
import java.util.List;

import com.yk0242.labs.taihenn.TaIHeNnManager;

/**
 * Main class for TaIHeNn suite - for taking stats of occurrences of taihen and hentai. 
 *  (Based on code of TaIHeNnMain.)
 * @author yk242
 */
public class TaIHeNnStatsMain {
	private static final boolean DEBUG = false; //enable/disable debug mode
  /** enable/disable display of list of LTOs in end results display. <br><br>
   *  note - if DISPLIST is false, then the program doesn't bother with the LTO lists, 
   *   allowing for longer nLetters attempts and speeding up processing time. 
   *   However, the resulting mean values are approximations calculated by 
   *   nLetters / occurrences. 
   *   Also, thm.setNoHistory() is called, so thm speeds up internally and the
   *   Java heap space Error can be avoided, for large nLetters. 
   */
	private static final boolean DISPLIST = false;

	private static int taihenCnt = 0; //counts occurrences of taihen
	private static int hentaiCnt = 0; //counts occurrences of hentai
	private static int tLastIndex = 0; //last index value of taihen
	private static int hLastIndex = 0; //last index value of hentai
	private static List<Integer> taihenLTO = new ArrayList<Integer>();//keeps track of Letters taken To Occurrence of taihen
	private static List<Integer> hentaiLTO = new ArrayList<Integer>();//keeps track of Letters taken To Occurrence of hentai
	
	public static void main(String [ ] args){
		int nLetters;
		
		//initialize thm
		TaIHeNnManager thm = new TaIHeNnManager();
		if(!DISPLIST) thm.setNoHistory();//speeds up process and prevents Java heap space error
		
		//*** input num of letters to produce
		nLetters = 1000*1000000/1;//TODO FIXME input num letters to produce
		                       //NB: console buffer size currently 1000000
		
		//*** loop thm for nLetters
		for(int lctr=0; lctr<nLetters; lctr++){
			//advance thm by one unit
			thm.advance();
			
			//process occurrence of taihen or hentai 
			if(thm.isTaihen()){
				if(DISPLIST) taihenLTO.add(thm.getArrLen()-tLastIndex);
				tLastIndex = thm.getArrLen();
				taihenCnt++;
			}
			if(thm.isHentai()){
				if(DISPLIST) hentaiLTO.add(thm.getArrLen()-hLastIndex);
				hLastIndex = thm.getArrLen();
				hentaiCnt++;
			}
		}
		
		//*** calculate mean occurrences
		double meanTaihenLTO = 0;
		double meanHentaiLTO = 0;
		
		if(DISPLIST){//calculate mean as mean of each LTO (true value)
			for(int i : taihenLTO) meanTaihenLTO += i;
			for(int i : hentaiLTO) meanHentaiLTO += i;
			if (taihenLTO.size()!=0) meanTaihenLTO/=taihenLTO.size();
			if (hentaiLTO.size()!=0) meanHentaiLTO/=hentaiLTO.size();
		}
		else{//calculate mean as nLetters/occurrenceCount (approx value)
			if(taihenCnt!=0) meanTaihenLTO = (double)nLetters/taihenCnt;
			if(hentaiCnt!=0) meanHentaiLTO = (double)nLetters/hentaiCnt;
		}
//		System.out.println(meanTaihenLTO);//debug
		
		//round answers to 2dp
		meanTaihenLTO = (double)Math.round(100*meanTaihenLTO)/100;
		meanHentaiLTO = (double)Math.round(100*meanHentaiLTO)/100;
		
		//*** output result
		StringBuilder sb = new StringBuilder();
		if(DEBUG){
			sb.append("raw string: \n");
//			sb.append(thm.getStr());//switch to use - necc. to publicize THM.getStr()
			sb.append("[disabled]");  //switch on for hiding THM.getStr()
			sb.append("\n\n");
		}
		sb.append(nLetters);
		sb.append("文字を使って、以下の結果が出ました：\n\n");
		
		sb.append("たいへん： ");
		sb.append(taihenCnt);
		sb.append(" 回 出現");
		if(DISPLIST){
			sb.append("出現履歴：\n");
			sb.append(taihenLTO.toString());
		}
		sb.append("。\n平均で ");
		sb.append(meanTaihenLTO);
		sb.append(" 文字に一回出現。\n\n");
		
		sb.append("へんたい： ");
		sb.append(hentaiCnt);
		sb.append(" 回 出現");
		if(DISPLIST){
			sb.append("、出現履歴：\n");
			sb.append(hentaiLTO.toString());
		}
		sb.append("。\n平均で ");
		sb.append(meanHentaiLTO);
		sb.append(" 文字に一回出現。");
		
		System.out.print(sb.toString());
		
	}
}
