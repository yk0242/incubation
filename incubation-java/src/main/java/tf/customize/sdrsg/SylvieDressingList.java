package tf.customize.sdrsg;

import java.util.ArrayList;
import java.util.List;
/**
 * Container class for Sylvie Dressings
 * @author yk0242
 */
public class SylvieDressingList {
	private String label;
	private String relpath;//path relative to TF base; for use in scanning
	private String relpath2;//path relative to fgimage; for use in printing links
	private List<String> filenames;
	private List<String> filepaths;
	private int ptr;//pointer within list
	private int idx;//index of label in outside list
	
	/* *** default constructor ***/
	public SylvieDressingList(int index, String label){
		this.label = label;
		this.relpath = "data/fgimage/Exdress/";
		this.relpath2 = "Exdress/";
		this.filenames = new ArrayList<String>();
		this.filepaths = new ArrayList<String>();
		this.ptr = 0;
		this.idx = index;
	}
	
	/* ******************/
	/* Public Functions */
	/* ******************/
	/**
	 * get label.
	 * @return String label
	 */
	public String getLabel(){
		return this.label;
	}
	
	/**
	 * get index of label in outside list.
	 * @return int idx
	 */
	public int getIndex(){
		return this.idx;
	}
	
	/**
	 * get pointer position.
	 * @return int ptr
	 */
	public int getPtr(){
		return this.ptr;
	}
	
	/**
	 * set relpath.
	 * @param String relpath
	 */
	public void setRelpath(String relpath){
		this.relpath = relpath;
	}
	
	/**
	 * get relpath.
	 * @return String relpath
	 */
	public String getRelpath(){
		return this.relpath;
	}
	
	/**
	 * set relpath2.
	 * @param String relpath2
	 */
	public void setRelpath2(String relpath2){
		this.relpath2 = relpath2;
	}
	
	/**
	 * get relpath2.
	 * @return String relpath2
	 */
	public String getRelpath2(){
		return this.relpath2;
	}
	
	/**
	 * get size of filenames list.
	 * @return int size
	 */
	public int size(){
		return this.filenames.size();
	}
	
	/**
	 * add new dressing
	 * @param String filename
	 * @param String filepath corresponding to filename: relative to relpath
	 */
	public void add(String filename, String filepath){
		this.filenames.add(filename);
		this.filepaths.add(filepath);
	}
	
	/**
	 * get filename of current pointer position.
	 * @return String corresponding filename
	 */
	public String getFilename(){
		return filenames.get(ptr);
	}
	
	/**
	 * get filepath of current pointer position.
	 * @return String corresponding filepath relative to relpath
	 */
	public String getFilepath(){
		return filepaths.get(ptr);
	}
	
	/**
	 * reset pointer
	 */
	public void resetPointer(){
		this.ptr = 0;
	}
	
	/**
	 * advances pointer by one.
	 * @return this (SDL) (for method chaining)
	 */
	public SylvieDressingList next(){
		this.ptr += 1;
		return this;
	}
	/**
	 * rewinds pointer by one.
	 * @return this (SDL) (for method chaining)
	 */
	public SylvieDressingList prev(){
		this.ptr -= 1;
		return this;
	}
	
	/**
	 * Checks to see if the pointer has a next value.
	 * @return boolean true if pointer not at end of list
	 */
	public boolean hasNext(){
		if( ptr >= filenames.size()-1 ){
			return false;
		}
		return true;
	}
	
	/**
	 * debug; print list contents
	 */
	public void print(){
		System.out.println("SDL label: " + label);
		for(int i=0; i<filenames.size(); i++){
			System.out.print(filenames.get(i));
			System.out.print(" : ");
			System.out.println(filepaths.get(i));
		}
	}
}
