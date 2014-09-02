package com.yk0242.labs.taihenn;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Test for TaIHeNnManager class
 * @author yk242
 */
public class TaIHeNnManagerTest {
	private TaIHeNnManager thm = new TaIHeNnManager();
	private static final boolean DEBUG = true; //enable/disable debug mode
	
	@Before
	public void doBefore(){
		thm = new TaIHeNnManager();
		
	}
	
	/**
	 * assert that advance() increases arr length by 1 (10 times)
	 *  ( depends on getArrLen() working properly )
	 * optionally displays string representation of arr each time (if DEBUG)
	 */
	@Test
	public void advanceTest(){
		assertEquals(0, thm.getArrLen());
		for(int i=1; i<=10; i++){
			thm.advance();
			assertEquals(i, thm.getArrLen());
			if(DEBUG) System.out.println(thm.getStr());
		}
	}
	
	/**
	 * assert that getLastChar() gets full string properly
	 *  ( depends on getStr() working properly )
	 *  optionally displays string representation of arr each time (if DEBUG)
	 */
	@Test
	public void getLastCharTest(){
		
	}
}
