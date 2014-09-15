package com.yk0242.labs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.yk0242.labs.taihenn.TaIHeNnManager;

/**
 * Main class for TaIHeNn suite - for taking stats of occurrences of taihen and hentai. 
 *  (Based on code of TaIHeNnStatsMain.)
 *  This version takes stats of taihen OR hentai occurring, simulating the actual main run.
 *  Since we want to simulate the real run, will reset thm each hit.  
 * @author yk242
 */
public class TaIHeNnStatsActualMain {
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
	private static List<Integer> taihenLTO = new ArrayList<Integer>();//keeps track of Letters taken To Occurrence of taihen
	private static List<Integer> hentaiLTO = new ArrayList<Integer>();//keeps track of Letters taken To Occurrence of hentai
	
	public static void main(String [ ] args){
		int nLetters=100000;
		
		//initialize thm
		TaIHeNnManager thm = new TaIHeNnManager();
		if(!DISPLIST) thm.setNoHistory();//speeds up process and prevents Java heap space error
		
		//*** input num of letters to produce
//		nLetters = 1000*1000000/1;//temp input num letters to produce
//		                       //NB: console buffer size currently 1000000
		System.out.print("テスト用文字数を入力してください（デフォルト100000）：");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			nLetters = Integer.parseInt(br.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			//if user name input is empty or NaN, replace by default value
			nLetters=100000;
		} finally {
			try{
				if(br!=null) br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println();
		
		//*** loop thm for nLetters
		for(int lctr=0; lctr<nLetters; lctr++){
			//advance thm by one unit
			thm.advance();
			
			//process occurrence of taihen or hentai 
			if(thm.isTaihen()){
				if(DISPLIST) taihenLTO.add(thm.getArrLen());
				taihenCnt++;
				//initialize thm
				thm = new TaIHeNnManager();
				if(!DISPLIST) thm.setNoHistory();//speeds up process and prevents Java heap space error

			}
			if(thm.isHentai()){
				if(DISPLIST) hentaiLTO.add(thm.getArrLen());
				hentaiCnt++;
				//initialize thm
				thm = new TaIHeNnManager();
				if(!DISPLIST) thm.setNoHistory();//speeds up process and prevents Java heap space error

			}
		}
		
		//*** calculate mean occurrence
		double meanTaihentaiLTO = 0;
		int totalCnt = 0;
		
		if(DISPLIST){//calculate mean as mean of each LTO (true value)
			for(int i : taihenLTO) meanTaihentaiLTO += i;
			for(int i : hentaiLTO) meanTaihentaiLTO += i;
			totalCnt = taihenLTO.size()+hentaiLTO.size();
			if (totalCnt!=0) meanTaihentaiLTO/=totalCnt;
		}
		else{//calculate mean as nLetters/occurrenceCount (approx value)
			totalCnt = taihenCnt+hentaiCnt;
			if(totalCnt!=0) meanTaihentaiLTO = (double)nLetters/totalCnt;
		}
//		System.out.println(meanTaihenLTO);//debug
		
		//round answers to 2dp
		meanTaihentaiLTO = (double)Math.round(100*meanTaihentaiLTO)/100;
		
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
		
		sb.append("\nへんたい： ");
		sb.append(hentaiCnt);
		sb.append(" 回 出現");
		if(DISPLIST){
			sb.append("、出現履歴：\n");
			sb.append(hentaiLTO.toString());
		}
		
		sb.append("\n\n「たいへん」または「へんたい」が平均で ");
		sb.append(meanTaihentaiLTO);
		sb.append(" 文字に一回出現。");
		
		System.out.print(sb.toString());
		
	}
}
