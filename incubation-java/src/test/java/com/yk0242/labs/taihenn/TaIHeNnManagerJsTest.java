package com.yk0242.labs.taihenn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for TaIHeNnManagerJs class
 * @author yk242
 */
public class TaIHeNnManagerJsTest {
	private TaIHeNnManagerJs thm;
	private static final boolean DEBUG = false; //enable/disable debug mode
	private static final int HENTAIHEN_TESTNUM = 50000; //5000 for DEBUG, 50000 if not
	
	/** reset thm for each test. */
	@Before
	public void doBefore(){
		thm = new TaIHeNnManagerJs();
	}
	
	/**Test case for advance() and getCtr(). 
	 * @assert advance() increases getCtr() value by 1 (10 times)
	 * @expected advance() increases getCtr() value by 1 (10 times)
	 * @assert getCtr() increases for each advance() call (10 times)
	 * @expected advance() increases getCtr() value by 1 (10 times)
	 * @expected optionally displays string representation of arr (if DEBUG)
	 */
	@Test
	public void advance_getCtr_Test(){
		assertEquals(0, thm.getCtr());
		for(int i=1; i<=10; i++){
			thm.advance();
			assertEquals(i, thm.getCtr());
			if(DEBUG) System.out.print(thm.getLastChar());
		}
		if(DEBUG) System.out.println();
	}
	
	/**Test case for getLastChar(). 
	 * @assert getLastChar() gets last char string properly (10 times)
	 * @expected getLastChar() called before advance() gets ' '
	 * @expected getLastChar() gets 'た' or 'い' or 'へ' or 'ん' (10 times)
	 * @expected optionally displays string representation of arr (if DEBUG)
	 */
	@Test
	public void getLastCharTest(){
		assertEquals(' ',thm.getLastChar());
		for(int i=1; i<=10; i++){
			thm.advance();
			assertTrue(thm.getLastChar()=='た'||
			           thm.getLastChar()=='い'||
			           thm.getLastChar()=='へ'||
			           thm.getLastChar()=='ん');
			if(DEBUG) System.out.print(thm.getLastChar());
		}
		if(DEBUG) System.out.println();
	}
	
	/**Test case for isTaihen() 10/1000 times. 
	 * @assert isTaihen() returns expected value properly
	 * @expected isTaihen() returns false whenever advance() doesn't make isTaihen() return true (tautology?)
	 * @expected when isTaihen() returns false, last four letters' history of getLastChar() is not "たいへん"
	 * @expected when isTaihen() returns true, then last four letters' history of getLastChar() is "たいへん"
	 * @expected when advance() is called after isTaihen() returns true, isTaihen() becomes false again
	 * @expected optionally displays string representation of arr (if DEBUG)

	 */
	@Test(timeout=10000)
	public void isTaihenTest(){
		for(int i=1; i<=HENTAIHEN_TESTNUM; i++){
			thm = new TaIHeNnManagerJs();
			String lastfour="abcd";//any four letters should do
			while(!thm.isTaihen()){
				assertFalse(thm.isTaihen());//redundant tautology, but include for clarity's sake
				assertNotEquals("たいへん",lastfour);
				thm.advance();
				lastfour = lastfour.substring(1)+thm.getLastChar();
				if(DEBUG) System.out.print(thm.getLastChar());
			}
			
			if(DEBUG) System.out.println();
			assertTrue(thm.isTaihen());//redundant tautology, but include for clarity's sake
			assertEquals("たいへん",lastfour);
			if(DEBUG) System.out.println("たいへんです！");
			
			thm.advance();
			if(DEBUG) System.out.println(thm.getLastChar());
			assertFalse(thm.isTaihen());
		}
	}
	
	/**Test case for isHentai() 10/1000 times. 
	 * @assert isHentai() returns expected value properly
	 * @expected isHentai() returns false whenever advance() doesn't make thm.hentaiCtr==4
	 * @expected when isHentai() returns false, last four letters' history of getLastChar() is not "へんたい"
	 * @expected when isHentai() returns true, then last four letters' history of getLastChar() is "へんたい"
	 * @expected when advance() is called after isHentai() returns true, isHentai() becomes false again
	 * @expected optionally displays string representation of arr (if DEBUG)
	 */
	@Test(timeout=10000)
	public void isHentaiTest(){
		for(int i=1; i<=HENTAIHEN_TESTNUM; i++){
			thm = new TaIHeNnManagerJs();
			String lastfour="abcd";//any four letters should do
			while(!thm.isHentai()){
				assertFalse(thm.isHentai());//redundant tautology, but include for clarity's sake
				assertNotEquals("へんたい",lastfour);
				thm.advance();
				lastfour = lastfour.substring(1)+thm.getLastChar();
				if(DEBUG) System.out.print(thm.getLastChar());
			}
			
			if(DEBUG) System.out.println();
			assertTrue(thm.isHentai());//redundant tautology, but include for clarity's sake
			assertEquals("へんたい",lastfour);
			if(DEBUG) System.out.println("へんたいです！");
			
			thm.advance();
			if(DEBUG) System.out.println(thm.getLastChar());
			assertFalse(thm.isHentai());
		}
	}
	
	//omit tests for basic getters and setters. 
	
}
