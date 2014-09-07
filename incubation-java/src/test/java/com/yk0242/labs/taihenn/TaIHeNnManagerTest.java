package com.yk0242.labs.taihenn;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Test class for TaIHeNnManager class
 * @author yk242
 */
public class TaIHeNnManagerTest {
	private TaIHeNnManager thm = new TaIHeNnManager();
	private static final boolean DEBUG = false; //enable/disable debug mode
	private static final int HENTAIHEN_TESTNUM = 1000; //10 for DEBUG, 1000 if not
	
	/** reset thm for each test. */
	@Before
	public void doBefore(){
		thm = new TaIHeNnManager();
	}
	
	/**Test case for advance() and getArrLen(). 
	 * @assert advance() increases arr length by 1 (10 times)
	 * @expected advance() increases getArrLen() value by 1 (10 times)
	 * @assert getArrLen() increases for each advance() call (10 times)
	 * @expected advance() increases getArrLen() value by 1 (10 times)
	 * @expected optionally displays string representation of arr each time (if DEBUG)
	 */
	@Test
	public void advance_getArrLen_Test(){
		assertEquals(0, thm.getArrLen());
		for(int i=1; i<=10; i++){
			thm.advance();
			assertEquals(i, thm.getArrLen());
			if(DEBUG) System.out.println(thm.getStr());
		}
	}
	
	/**Test case for getLastChar(). 
	 * @assert getLastChar() gets last char string properly (10 times)
	 *  ( depends on getStr() working properly )
	 * @expected getLastChar() called before advance() called throws an IndexOutOfBoundsException
	 * @expected getLastChar() gets same char as last char of thm.getStr() (10 times)
	 * @expected optionally displays string representation of arr each time (if DEBUG)
	 */
	@Test
	public void getLastCharTest(){
		try{
			thm.getLastChar(); //should throw IndexOutOfBoundsException
			throw new AssertionError("initial call to thm.getLastChar() didn't throw an IOOBEx");
		}catch(IndexOutOfBoundsException e){
			if(DEBUG) System.out.println("initial call to thm.getLastChar() threw an IOOBEx as expected.");
		}
		for(int i=1; i<=10; i++){
			thm.advance();
			assertEquals(thm.getStr().substring(i-1).charAt(0), thm.getLastChar());
			if(DEBUG) System.out.println(thm.getStr());
		}
	}
	
	/**Test case for isTaihen(). 
	 * @assert isTaihen() returns expected value properly 10/1000 times.
	 * @expected isTaihen() returns false whenever advance() doesn't make isTaihen() return true (tautology?)
	 * @expected when isTaihen() returns true, then last four letters' substr of thm.getStr() is "たいへん"
	 * @expected when advance() is called after isTaihen() returns true, isTaihen() becomes false again
	 * @expected optionally displays string representation of arr each time (if DEBUG)

	 */
	@Test(timeout=60000)
	public void isTaihenTest(){
		for(int i=1; i<=HENTAIHEN_TESTNUM; i++){
			while(!thm.isTaihen()){
				assertFalse(thm.isTaihen());//redundant tautology, but include for clarity's sake
				thm.advance();
				if(DEBUG) System.out.println(thm.getStr());
			}
			assertTrue(thm.isTaihen());//redundant tautology, but include for clarity's sake
			assertEquals("たいへん",thm.getStr().substring(thm.getArrLen()-4));
			if(DEBUG) System.out.println("たいへんです！");
			
			thm.advance();
			if(DEBUG) System.out.println(thm.getStr());
			assertFalse(thm.isTaihen());
		}
	}
	
	/**Test case for isHentai(). 
	 * @assert isHentai() returns expected value properly 10/1000 times.
	 * @expected isHentai() returns false whenever advance() doesn't make thm.hentaiCtr==4
	 * @expected when isHentai() returns true, then last four letters' substr of thm.getStr() is "へんたい"
	 * @expected when advance() is called after isHentai() returns true, isHentai() becomes false again
	 * @expected optionally displays string representation of arr each time (if DEBUG)
	 */
	@Test(timeout=60000)
	public void isHentaiTest(){
		for(int i=1; i<=HENTAIHEN_TESTNUM; i++){
			while(!thm.isHentai()){
				assertFalse(thm.isHentai());//redundant tautology, but include for clarity's sake
				thm.advance();
				if(DEBUG) System.out.println(thm.getStr());
			}
			assertTrue(thm.isHentai());//redundant tautology, but include for clarity's sake
			assertEquals("へんたい",thm.getStr().substring(thm.getArrLen()-4));
			if(DEBUG) System.out.println("へんたいです！");
			
			thm.advance();
			if(DEBUG) System.out.println(thm.getStr());
			assertFalse(thm.isHentai());
		}
	}
	
	/**Test case for setNoHistory(). 
	 * @assert setNoHistory() works as expected
	 * @expected setNoHistory() resets thm (test using getArrLen())
	 * @expected after setNoHistory(), getArrLen() and getLastChar() work as expected (simplified test 10 times)
	 * @expected setNoHistory() makes getStr() to always return "" (test before and after each advance() call)
	 * @expected optionally displays string representation of arr each time (if DEBUG)
	 */
	@Test
	public void setNoHistoryTest() {
		thm.advance();
		thm.advance();
		assertEquals(2,thm.getArrLen());
		assertEquals(2,thm.getStr().length());
		
		thm.setNoHistory();
		assertEquals(0,thm.getArrLen());
		assertEquals(0,thm.getStr().length());
		
		for(int i=1; i<=10; i++){
			thm.advance();
			assertEquals(i,thm.getArrLen());
			assertTrue(thm.getLastChar()=='た'||
					       thm.getLastChar()=='い'||
					       thm.getLastChar()=='へ'||
					       thm.getLastChar()=='ん');
			assertEquals(0,thm.getStr().length());
			if(DEBUG) System.out.println(thm.getStr());
		}
	}
	
	/**Test case for advance() and getArrLen() in noHistory mode. 
	 * @assert advance() increases getArrLen() value by 1 (10 times) in noHistory mode
	 * @expected advance() increases getArrLen() value by 1 (10 times)
	 * @assert getArrLen() increases for each advance() call (10 times) in noHistory mode
	 * @expected advance() increases getArrLen() value by 1 (10 times)
	 * @expected optionally displays string representation of arr (if DEBUG)
	 */
	@Test
	public void advance_getArrLen_NoHistoryTest(){
		thm.setNoHistory();
		assertEquals(0, thm.getArrLen());
		for(int i=1; i<=10; i++){
			thm.advance();
			assertEquals(i, thm.getArrLen());
			if(DEBUG) System.out.print(thm.getLastChar());
		}
		if(DEBUG) System.out.println();
	}
	
	/**Test case for getLastChar() in noHistory mode. 
	 * @assert getLastChar() gets last char string properly (10 times) in noHistory mode
	 *  ( depends on getStr() working properly )
	 * @expected getLastChar() called before advance() called throws an IndexOutOfBoundsException
	 * @expected getLastChar() gets 'た' or 'い' or 'へ' or 'ん' (10 times)
	 * @expected optionally displays string representation of arr (if DEBUG)
	 */
	@Test
	public void getLastCharNoHistoryTest(){
		thm.setNoHistory();
		try{
			thm.getLastChar(); //should throw IndexOutOfBoundsException
			throw new AssertionError("initial call to thm.getLastChar() didn't throw an IOOBEx");
		}catch(IndexOutOfBoundsException e){
			if(DEBUG) System.out.println("initial call to thm.getLastChar() threw an IOOBEx as expected.");
		}
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
	
	/**Test case for isTaihen() in noHistory mode 10/1000 times. 
	 * @assert isTaihen() returns expected value properly in noHistory mode
	 * @expected isTaihen() returns false whenever advance() doesn't make isTaihen() return true (tautology?)
	 * @expected when isTaihen() returns true, then last four letters' history of getLastChar() is "たいへん"
	 * @expected when advance() is called after isTaihen() returns true, isTaihen() becomes false again
	 * @expected optionally displays string representation of arr (if DEBUG)

	 */
	@Test(timeout=10000)
	public void isTaihenNoHistoryTest(){
		for(int i=1; i<=HENTAIHEN_TESTNUM; i++){
			String lastfour="abcd";//any four letters should do
			thm.setNoHistory();
			while(!thm.isTaihen()){
				assertFalse(thm.isTaihen());//redundant tautology, but include for clarity's sake
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
	
	/**Test case for isHentai() in noHistory mode 10/1000 times. 
	 * @assert isHentai() returns expected value properly in noHistory mode
	 * @expected isHentai() returns false whenever advance() doesn't make thm.hentaiCtr==4
	 * @expected when isHentai() returns true, then last four letters' history of getLastChar() is "へんたい"
	 * @expected when advance() is called after isHentai() returns true, isHentai() becomes false again
	 * @expected optionally displays string representation of arr (if DEBUG)
	 */
	@Test(timeout=10000)
	public void isHentaiNoHistoryTest(){
		for(int i=1; i<=HENTAIHEN_TESTNUM; i++){
			String lastfour="abcd";//any four letters should do
			thm.setNoHistory();
			while(!thm.isHentai()){
				assertFalse(thm.isHentai());//redundant tautology, but include for clarity's sake
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
	
}
