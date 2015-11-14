package tf.customize.sdrsg;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class for Sylvie Dressing Room Scenario Generator (sdrsg) package.
 * Creates a custom .ks scenario file for use in conjunction with 
 *   custom scenarios viewer (sp81314 etc.)
 * Requires a valid product version of TeachingFeeling to run.
 * @author yk0242
 * @see work_log https://github.com/yk0242/incubation/issues/45
 */
public class SylvieDressingRoomScenarioGeneratorMain {
	/* *** constants *** */
	private static final String[] LAYERLABELS = {"clothes","ribbon","pin","glasses","socks","ring","face","body"};
	private static final String[] LAYERNAMES = {"服","髪型（リボン）","ｱｸｾｻﾘｰ（ヘアピン）","ｱｸｾｻﾘｰ（メガネ・頭）","他（靴下）","他（ピアス系）","顔","体"};
	private static final String[] LAYERCHDISPS = {"_　（服変更）","（リボン変更）","（ヘアピン変更）","（メガネ変更）","（靴下変更）","（ピアス変更）","_　（顔変更）","_　（体変更）"};
	private static final int RIBBONIDX = 1;
	private static final int PINIDX = 2;
	private static final String INITTFPATH = "D:/path/of/TeachingFeeling/";
	private static final String BRLEFT = "【";
	private static final String BRRIGHT = "】";
	private static final String RPEXDRESS = "/data/fgimage/Exdress/";
	private static final String RPEXDRESS2 = "Exdress/";
	private static final String OUTFILENAME = "__customx__.ks";
	private static final String OUTENC = "UTF-8";
	private static final int LOGSIZE = 104857600;
	private static final int LOG_ROTATION_COUNT = 1;
	private static final String LOGFILENAME = "log.txt";
	private static final String INIFILENAME = "sdrsg_properties.txt";
	private static final String TFPATHPN = "TFpath";
	private static final String FS = File.separator;
	private static final int MAXDISPLEN = 42;
	/** logger */
	private static final Logger LOGGER = Logger.getLogger(SylvieDressingRoomScenarioGeneratorMain.class.getName());
	
	public static void main(String[] args){
		/** absolute path to TF: normally set in ini */
		String tfAbsPath = INITTFPATH;
		/** path of layer base under data/fgimage/Exdress */
		String[] layerBasePath = LAYERLABELS;
		/** list of SDLs where data is stored */
		List<SylvieDressingList> sdlList = new ArrayList<SylvieDressingList>();
		/** file output contents */
		StringBuilder sb = new StringBuilder();
		
		//prepare logger
		Handler fhandler = null;
		try {
			fhandler = new FileHandler(LOGFILENAME, LOGSIZE, LOG_ROTATION_COUNT);
		} catch (SecurityException e) {
			System.err.println("Security Exception");
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.err.println("ファイルIO中に例外が発生しました： "+LOGFILENAME);
			e.printStackTrace();
			System.exit(1);
		}
		fhandler.setFormatter(new SdrsgLogFormatter());
		LOGGER.addHandler(fhandler);
		LOGGER.setLevel(Level.INFO);
		if( args.length > 0 ){
			if ("FINE".equalsIgnoreCase(args[0])){
				LOGGER.setLevel(Level.FINE);
			}else if("FINEST".equalsIgnoreCase(args[0])){
				LOGGER.setLevel(Level.FINEST);
			}else if("ALL".equalsIgnoreCase(args[0])){
				LOGGER.setLevel(Level.ALL);
			}
		}
		
		//check internal arrays' lengths match
		if( LAYERLABELS.length != LAYERNAMES.length ||
		    LAYERLABELS.length != LAYERCHDISPS.length ||
		    LAYERLABELS.length != layerBasePath.length ){
			LOGGER.severe("プログラム内のレイヤー指定の数が違います。\n");
			System.exit(1);
		}
		
		//read properties file
		StringBuilder tfap = new StringBuilder(tfAbsPath);
		processPropertiesRead(tfap, layerBasePath);
		tfAbsPath = tfap.toString();
		
		//check existence and validity of TF folder
		LOGGER.info("TeachingFeelingの検出開始...");
		if(!isValidTf(tfAbsPath)){
			LOGGER.severe("\n"
			  + "********************************************************************\n"
			  + "******* 指定パスにTeachingFeeling製品版を検出できません。\n"
			  + "******* "+tfAbsPath+"\n"
			  + "******* シルヴィちゃん試着室シナリオファイル作成を中断します。\n"
			  + "********************************************************************\n");
			System.exit(1);
		}
		LOGGER.info("TeachingFeelingの検出成功：\n"+tfAbsPath);
		
		//initialize SDLs
		for(int i=0; i<LAYERLABELS.length; i++){
			SylvieDressingList sdltemp = new SylvieDressingList(i, LAYERLABELS[i]);
			sdltemp.setRelpath(RPEXDRESS+layerBasePath[i]);
			sdltemp.setRelpath2(RPEXDRESS2+layerBasePath[i]+"/");
			sdlList.add(sdltemp);
		}
		
		//scan directories and store data into SDLs
		processFileRead(sdlList, tfAbsPath);
		
		//start building file output contents
		LOGGER.info("ファイル文作成開始");
		sb.append("[tb_start_tyrano_code]\n");
		sb.append("\n");
		sb.append("; ******* カスタムシナリオ開始固定文：ここから *******\n");
		sb.append("*start\n");
		sb.append("[cm]\n");
		sb.append("[show_skip]\n");
		sb.append("[cancelskip]\n");
		sb.append("[fadeoutbgm time=500]\n");
		sb.append("[black]\n");
		sb.append("[tb_show_message_window]\n");
		sb.append("[if exp=\"f.step<5\"]\n");
		sb.append("#\n");
		sb.append("（申し訳ありません。[r]\n");
		sb.append("_　このカスタムシナリオは体験版の範囲以降の状態から実行してください。）[p]\n");
		sb.append("[jump target=\"*return_memory\"]\n");
		sb.append("[endif]\n");
		sb.append("; ******* カスタムシナリオ開始固定文：ここまで *******\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("[bg time=100 method=crossfade storage=\"bg-room.jpg\" ]\n");
		sb.append("[playbgm loop=true storage=\"Silver_Glass.ogg\"]\n");
		sb.append("\n");
		sb.append("[set_sit]\n");
		sb.append("[chara_mod name=window time=0 storage=\"chara/1/00.png\"]\n");
		sb.append("[chara_mod name=man time=0 storage=\"chara/1/00.png\"]\n");
		sb.append("[s_s]\n");
		sb.append("[show_sit]\n");
		sb.append("\n");
		sb.append("[nowait]\n");
		sb.append("[call target=*sub_hide_hidewindow]\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("*typeselect\n");
		sb.append("[cm]\n");
		sb.append("#_　（試着室）\n");
		
		appendTypeselString(sb, sdlList.get(0), false, false);
		appendTypeselString(sb, sdlList.get(1), true, true);
		appendTypeselString(sb, sdlList.get(2), false, false);
		appendTypeselString(sb, sdlList.get(3), true, true);
		appendTypeselString(sb, sdlList.get(4), false, false);
		appendTypeselString(sb, sdlList.get(5), true, true);
		appendTypeselString(sb, sdlList.get(6), false, false);
		appendTypeselString(sb, sdlList.get(7), true, false);
		
		sb.append("_　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　[link target=*close_scenario]");
		sb.append(BRLEFT).append("回想一覧に戻る").append(BRRIGHT).append("[endlink][r]\n");
		sb.append("[s]\n");
		sb.append("\n");
		sb.append("[jump target=*typeselect]\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		
		appendMenuString(sb, sdlList.get(0));
		appendChangeString(sb, sdlList.get(0));
		appendMenuString(sb, sdlList.get(1));
		appendChangeString(sb, sdlList.get(1));
		appendMenuString(sb, sdlList.get(2));
		appendChangeString(sb, sdlList.get(2));
		appendMenuString(sb, sdlList.get(3));
		appendChangeString(sb, sdlList.get(3));
		appendMenuString(sb, sdlList.get(4));
		appendChangeString(sb, sdlList.get(4));
		appendMenuString(sb, sdlList.get(5));
		appendChangeString(sb, sdlList.get(5));
		appendMenuString(sb, sdlList.get(6));
		appendChangeString(sb, sdlList.get(6));
		appendMenuString(sb, sdlList.get(7));
		appendChangeString(sb, sdlList.get(7));
		
		sb.append("\n");
		sb.append("\n");
		sb.append("*sub_disp\n");
		sb.append("[call target=*sub_show_hidewindow]\n");
		sb.append("[cm]\n");
		sb.append("変更しました。[p]\n");
		sb.append("[call target=*sub_hide_hidewindow]\n");
		sb.append(".\n");
		sb.append("[return]\n");
		sb.append("\n");
		sb.append("*sub_hide_hidewindow\n");
		sb.append("@clearfix name=\"role_button\"\n");
		sb.append("[button name=\"role_button\" role=\"title\" graphic=\"b-title.png\" x=1247 y=850]\n");
		sb.append("[return]\n");
		sb.append("\n");
		sb.append("*sub_show_hidewindow\n");
		sb.append("@clearfix name=\"role_button\"\n");
		sb.append("[button name=\"role_button\" role=\"window\" graphic=\"b-win.png\" x=1131 y=850]\n");
		sb.append("[button name=\"role_button\" role=\"title\" graphic=\"b-title.png\" x=1247 y=850]\n");
		sb.append("[return]\n");
		sb.append("\n");
		sb.append("*close_scenario\n");
		sb.append("[endnowait]\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append(";回想一覧に戻る用スクリプト\n");
		sb.append("*return_memory\n");
		sb.append("[cm]\n");
		sb.append("[fadeoutbgm time=500]\n");
		sb.append("[cancelskip]\n");
		sb.append("[black]\n");
		sb.append("#\n");
		sb.append("[playbgm  loop=\"true\" storage=\"Silver_Glass.ogg\"]\n");
		sb.append("[jump storage=\"memory.ks\" target=\"*memory\"]\n");
		sb.append("\n");
		sb.append("[_tb_end_tyrano_code]\n");
		LOGGER.info("ファイル文作成完了");
		//end building file output contents
		
		//output to file
		fileout(sb.toString(), OUTFILENAME);
		
	}//end main
	
	/* ******************/
	/*     Functions    */
	/* ******************/
	/**
	 * Appends strings for typeselect for given sdl data
	 * @param sb StringBuffer to append to
	 * @param sdl SylvieDressingList to print string for
	 * @param startUnderscore start with underscore if true
	 * @param endNewline end with newline([r]) if true
	 */
	private static void appendTypeselString(StringBuilder sb, SylvieDressingList sdl, boolean startUnderscore, boolean endNewline){
		if(startUnderscore){
			sb.append("_");
		}else{
			sb.append(" ");
		}
		sb.append("　[link target=*list").append(sdl.getLabel()).append("012]");
		sb.append(BRLEFT).append(LAYERNAMES[sdl.getIndex()]).append(BRRIGHT).append("[endlink]");
		if(endNewline){
			sb.append("[r]\n");
		}else{
			sb.append("\n");
		}
	}
	
	/**
	 * Appends strings for menu for given sdl data
	 * @param sb StringBuffer to append to
	 * @param sdl SylvieDressingList to print string for
	 */
	private static void appendMenuString(StringBuilder sb, SylvieDressingList sdl){
		sdl.resetPointer();
		
		while( sdl.getPtr() < sdl.size() ){
			sb.append("*list").append(sdl.getLabel());
			sb.append(sdl.getPtr()).append(sdl.getPtr()+1).append(sdl.getPtr()+2).append("\n");
			sb.append("[cm]\n");
			sb.append("#").append(LAYERCHDISPS[sdl.getIndex()]).append("\n");
			
			//print changelinks
			for(int i=0; i<3; i++){
				sb.append("[link target=*").append(sdl.getLabel()).append(sdl.getPtr()).append("]");
				sb.append(BRLEFT).append(sdl.getFilename()).append(BRRIGHT).append("[endlink][r]\n");
				if(!sdl.hasNext()){
					sdl.next();
					break;
				}
				sdl.next();
			}
			//print empty lines if necessary
			if( sdl.getPtr() >= sdl.size()-1 ){
				while( sdl.getPtr()%3 != 0 ){
					sb.append("[r]\n");
					sdl.next();
				}
			}
			//print navigator line
			//【＜】
			if( sdl.getPtr() < 6 ){
				sb.append("_　　 .\n");
			}else{
				sb.append(" 　　　[link target=*list").append(sdl.getLabel());
				sb.append(sdl.getPtr()-6).append(sdl.getPtr()-5).append(sdl.getPtr()-4);
				sb.append("]");
				sb.append(BRLEFT).append("＜").append(BRRIGHT).append("[endlink]\n");
			}
			//【↑】
			sb.append("_　　　[link target=*typeselect]");
			sb.append(BRLEFT).append("↑").append(BRRIGHT).append("[endlink]\n");
			
			//【＞】
			if( sdl.getPtr() < sdl.size() ){
				sb.append("_　　　[link target=*list").append(sdl.getLabel());
				sb.append(sdl.getPtr()).append(sdl.getPtr()+1).append(sdl.getPtr()+2);
				sb.append("]");
				sb.append(BRLEFT).append("＞").append(BRRIGHT).append("[endlink]\n");
			}else{
				sb.append("_　　　　　 .\n");
			}
			//(n/n)
			sb.append("_　　　(").append(sdl.getPtr()/3).append("/");
			sb.append((int)(Math.ceil(sdl.size()/3.))).append(")\n");
			sb.append("[r][s]\n");
			sb.append("\n");
		}
		
		if(sdl.size()==0){ //fixed output for exception case with no files
			sb.append("*list").append(sdl.getLabel()).append("012\n");
			sb.append("[cm]\n");
			sb.append("#").append(LAYERCHDISPS[sdl.getIndex()]).append("\n");
			sb.append("[r]\n");
			sb.append("[r]\n");
			sb.append("[r]\n");
			sb.append("_　　 .\n");
			sb.append("_　　　[link target=*typeselect]");
			sb.append(BRLEFT).append("↑").append(BRRIGHT).append("[endlink]\n");
			sb.append("_　　　　　 .　　　(データ無し)\n");
			sb.append("[r][s]\n");
			sb.append("\n");
		}
		sb.append("\n");
	}
	
	/**
	 * Appends strings for changing dressing for given sdl data
	 * @param sb StringBuffer to append to
	 * @param sdl SylvieDressingList to print string for
	 */
	private static void appendChangeString(StringBuilder sb, SylvieDressingList sdl){
		sdl.resetPointer();
		sdl.prev();
		while(sdl.hasNext()){
			sdl.next();
			sb.append("*").append(sdl.getLabel()).append(sdl.getPtr()).append("\n");
			
			if(sdl.getIndex()==RIBBONIDX){//ribbon
				sb.append("[chara_mod name=body time=0 storage=\"chara/2/body-b.png\"]\n");
			}
			if(sdl.getIndex()==PINIDX){//pin
				sb.append("[chara_mod name=face time=0 storage=\"chara/4/s-s-.png\"]\n");
			}
			
			sb.append("[chara_mod name=").append(sdl.getLabel());
			sb.append(" time=50 storage=\"").append(sdl.getFilepath()).append("\"]\n");
			sb.append("[call target=*sub_disp]\n");
			sb.append("[jump target=*list").append(sdl.getLabel());
			sb.append((int)(Math.floor(sdl.getPtr()/3)*3));
			sb.append((int)(Math.floor(sdl.getPtr()/3)*3+1));
			sb.append((int)(Math.floor(sdl.getPtr()/3)*3+2));
			sb.append("]\n");
			sb.append("\n");
		}
		sb.append("\n");
		sb.append("\n");
	}
	
	/**
	 * read all files in target directory, and set up sdll
	 * @param sdll list of SDLs where file data is to be stored
	 * @param tfpath absolute path of TF
	 */
	private static void processFileRead(List<SylvieDressingList> sdll, String tfpath){
		int sdllsize = sdll.size();
		SylvieDressingList sdl;
		String scanpath;
		List<String> filepaths;
		
		for(int i=0; i<sdllsize; i++){
			sdl = sdll.get(i);
			scanpath = tfpath + sdl.getRelpath();
			LOGGER.info("ディレクトリ内のファイルを検索します...\n"+scanpath);
			filepaths = getFileList(scanpath);
			int fpslen = filepaths.size();
			for(int j=0; j<fpslen; j++){
				String fp = filepaths.get(j);
//				String[] parts = fp.split("/");
//				String fn = parts[parts.length-1];
				if(fp.substring(0, 1).equals("\\")){
					fp = fp.substring(1, fp.length());//trim leading backslash
				}
				LOGGER.fine("== adding to list: "+fp);
				String fpstore = sdl.getRelpath2() + fp;
				
				//prevent display overflow by using last n letters if too long
				if(fp.length()>MAXDISPLEN){
					fp = fp.substring(fp.length()-MAXDISPLEN, fp.length());
				}
				
				//TyranoScript has a problem with spaces in file names, even if
				//surrounded by double quotes... replacing space with %20 seems to work...
				fpstore = fpstore.replaceAll(" ","%20");
				
				sdl.add(fp, fpstore);
			}
		}
	}
	
	/**
	 * use to get file listing recursively
	 * from http://stackoverflow.com/a/14676464
	 * @param directoryName name of directory to search recursively
	 * @param files list of files to which we add found files
	 */
	private static void listf(String directoryName, List<File> files) {
		File directory = new File(directoryName);
		// get all the files from a directory
		File[] fList = directory.listFiles();
		if( fList == null ){
			LOGGER.warning("******* ディレクトリが見つかりません： *******\n"+directoryName );
			return;
		}
		for (File file : fList) {
			if (file.isFile()) {
					files.add(file);
			} else if (file.isDirectory()) {
					listf(file.getAbsolutePath(), files);
			}
		}
	}
	
	/**
	 * get file listing of given abspath
	 * @param abspath absolute path to scan
	 * @return List<String> list of filepaths
	 */
	private static List<String> getFileList(String abspath){
		List<String> filenamelist = new ArrayList<String>();
		List<File> filelist = new ArrayList<File>();
		
		listf(abspath, filelist);
		for(int i=0; i<filelist.size(); i++){
			String fn = filelist.get(i).getAbsolutePath();
			//use relative filepath after abspath
			fn = fn.substring(abspath.length());
			filenamelist.add(fn);
		}
		return filenamelist;
	}
	
	/**
	 * Read info from properties file, and apply as necessary
	 * backslash omission fix based on http://stackoverflow.com/a/5785128
	 * ISR to String based on http://stackoverflow.com/a/14253523
	 * @param tfpath path of TF - to change if ini specifies it
	 * @param layerbasepaths paths of layer base - to change if ini specifies them
	 */
	private static void processPropertiesRead(StringBuilder tfpath, String[] layerbasepaths){
		String str;
		Properties props = new Properties();
		InputStream is = null;
		InputStreamReader isr = null;
		StringBuilder sb = new StringBuilder();;
		try {
			is = new FileInputStream(INIFILENAME);
			isr = new InputStreamReader(is, StandardCharsets.UTF_8);
			char[] buffer = new char[4096];
			for(int len; (len = isr.read(buffer)) > 0;)sb.append(buffer, 0, len);
			props.load(new StringReader(sb.toString().replace("\\","\\\\")));
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.WARNING, "******* "+INIFILENAME+" ファイルが無いので作成します...", e);
//			e.printStackTrace();
			makePropertiesFile();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "ファイルIO中に例外が発生しました： "+INIFILENAME, e);
//			e.printStackTrace();
		} finally {
			try{isr.close();}catch(Exception ex){/*ignore*/}
			finally{try{is.close();}catch(Exception exc){/*ignore*/}}
		}
		
		str = props.getProperty(TFPATHPN);
		if( str != null ){
			tfpath.setLength(0);
			tfpath.append(str);
		}
		for(int i=0; i<LAYERLABELS.length; i++){
			str = props.getProperty(LAYERLABELS[i]+"_relpath");
			if( str != null ){
				layerbasepaths[i] = str;
			}
		}
	}
	
	/**
	 * Print contents to file
	 * @param str text to print
	 * @param filename output file name
	 */
	private static void fileout(String str, String filename){
		LOGGER.info("ファイル出力開始： "+filename);
		//output to file
		Writer writer = null;
		try{
			writer = new BufferedWriter(new OutputStreamWriter(
			           new FileOutputStream(filename), OUTENC));
			writer.write(str);
		}catch(FileNotFoundException e){
			LOGGER.log(Level.SEVERE, "書き込み先のファイルが見つかりません： "+filename, e);
//			e.printStackTrace();
		}catch(UnsupportedEncodingException e){
			LOGGER.log(Level.SEVERE, "ファイルエンコード方式がサポートされていません： "+OUTENC, e);
//			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "ファイルIO中に例外が発生しました： "+filename, e);
//			e.printStackTrace();
		}finally{
			try{writer.close();}catch(Exception ex){/*ignore*/}
		}
		LOGGER.info("ファイル出力完了： "+filename);
	}
	
	/**
	 * Print out initial state of properties file
	 */
	private static void makePropertiesFile(){
		StringBuilder sb = new StringBuilder();
		sb.append("##### TeachingFeeling までのファイルパス：変更必須 #####\n");
		sb.append("\n");
		sb.append(TFPATHPN).append("=").append(INITTFPATH).append("\n");
		sb.append("\n");
		sb.append("########################################################\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("\n");
		sb.append("### 以下はTeachingFeelingフォルダ内の ").append(RPEXDRESS).append(" 配下のフォルダ指定\n");
		
		int pnmaxlen = 0;
		for(int i=0; i<LAYERLABELS.length; i++){
			if(LAYERLABELS[i].length()>pnmaxlen) pnmaxlen=LAYERLABELS[i].length();
		}
		for(int i=0; i<LAYERLABELS.length; i++){
			sb.append("# ").append(LAYERNAMES[i]).append("\n");
			String spacer = "                                                     ";
			spacer = spacer.substring(0, pnmaxlen-LAYERLABELS[i].length());
			sb.append(spacer).append(LAYERLABELS[i]).append("_relpath=").append(LAYERLABELS[i]).append("\n");
		}
		
		fileout(sb.toString(), INIFILENAME);
	}
	
	private static boolean isValidTf(String tfabspath){
		if(!(new File(tfabspath)).exists()) {
			LOGGER.severe("\n"
			  +"********************************************************************\n"
			  +"******* 指定されたTeachingFeelingディレクトリが存在しません：\n"
			  +"******* "+tfabspath+"\n"
			  +"******* "+INIFILENAME+" の "+TFPATHPN+" の指定を確認してください。\n"
			  +"********************************************************************");
			return false;
		}
		String check;
		//=== check existence of product-specific files
		check = tfabspath+FS+"TeachingFeeling.exe";
		if(!(new File(check)).exists()) return false;
		//don't check md5 since the file will change with updates
		check = tfabspath+FS+"data"+FS+"scenario"+FS+"step6.ks";
		if(!(new File(check)).exists()) return false;
		//don't check md5 since the file may change with updates
		check = tfabspath+FS+"data"+FS+"image"+FS+"m"+FS+"tea.png";
		if(!(new File(check)).exists()) return false;
		if(!CT.equals(dig(check))){LOGGER.severe("not "+CT);return false;}
		check = tfabspath+FS+"data"+FS+"image"+FS+"c"+FS+"non copy 2.png";
		if((new File(check)).exists())  return false;
		check = tfabspath+FS+"data"+FS+"bgimage"+FS+"sf-14.jpg";
		if(!(new File(check)).exists()) return false;
		//don't check md5 since the file may change with updates
		check = tfabspath+FS+"data"+FS+"bgimage"+FS+"t1.jpg";
		if((new File(check)).exists())  return false;
		check = tfabspath+FS+"data"+FS+"fgimage"+FS+"chara"+FS+"10"+FS+"sw-a1.png";
		if(!(new File(check)).exists()) return false;
		if(!CS.equals(dig(check))){LOGGER.severe("not "+CS);return false;}
		return true;
	}
	/*
	 * a mix of several sources including http://stackoverflow.com/a/304350
	 */
	private static String dig(String fp){
		MessageDigest md=null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.log(Level.SEVERE, "MD5失敗", e);
//			e.printStackTrace();
		}
		InputStream is=null;
		try {
			is = Files.newInputStream(Paths.get(fp));
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "IO失敗"+fp, e);
//			e.printStackTrace();
		}
		DigestInputStream dis = new DigestInputStream(is, md);
		byte[] buffer = new byte[4096];
		int len=0;
		try {
			while((len = dis.read(buffer)) >= 0){}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "IO失敗2"+fp, e);
//			e.printStackTrace();
		}
		buffer[0]=(byte)len;
		byte[] digest = md.digest();
		try {dis.close();is.close();}catch(IOException e){/*ignore*/}
		return bytesToHex(digest);
	}
	final protected static String CT = "FD5DF6FC33ACC0895346464C52713C16";
	final protected static String CS = "7AC57C235A1E545B73C47E48630B6CD2";
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	/*
	 * from http://stackoverflow.com/a/9855338
	 */
	private static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for ( int j = 0; j < bytes.length; j++ ) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
}
