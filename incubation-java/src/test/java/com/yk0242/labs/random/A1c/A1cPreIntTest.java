package com.yk0242.labs.random.A1c;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class A1cPreIntTest {
	
	/* 
	 * 入力: String[] array = new String[] {"abc", "def", "ghi"}
	 * 出力: String result = "abc,def,ghi"
	 */
	
	private String concatStrArrA(String[] array){
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<array.length; i++){
			sb.append(array[i]);
			sb.append(",");
		}
		//remove terminating comma and return string
		return sb.substring(0, sb.length()-1);
	}
	
	
	/**Test case for concatStrArrA(). 
	 * @assert 
	 * @expected 
	 */
	@Test
	public void concatStrArrATest() {
		String[] arr = new String[] {"abc", "def", "ghi"};
		String result = concatStrArrA(arr);
		assertEquals("abc,def,ghi",result);
		System.out.println(result);
		
		arr = new String[] {"12", "defg", "789"};
		result = concatStrArrA(arr);
		assertEquals("12,defg,789",result);
		System.out.println(result);
	}
	
	private String concatStrArrB(String[] array){
		String result = "";
		for(int i=0; i<array.length; i++){
			result += array[i];
			//append comma UNLESS this is the last element in array
			if(i!=array.length-1) result += ",";
		}
		return result;
	}
	
	
	/**Test case for concatStrArrB(). 
	 * @assert 
	 * @expected 
	 */
	@Test
	public void concatStrArrBTest() {
		String[] arr = new String[] {"abc", "def", "ghi"};
		String result = concatStrArrB(arr);
		assertEquals("abc,def,ghi",result);
		System.out.println(result);
		
		arr = new String[] {"12", "defg", "789"};
		result = concatStrArrB(arr);
		assertEquals("12,defg,789",result);
		System.out.println(result);
	}
	
	//=======================================================
	
	//上記の実装だと、リスト末尾に着いた事が外部から直接認識できないため、
	//回避策を含めた実装を３つ提示します。
	
	/**
	 * 回避策その１：poslをリキャストして追加メソッドを使う
	 * 　　（可能ならインターフェース設計者との相談の上、そちらに追加してもらいたい）
	 * 　　（あるいはリスト実装挿げ替えの可能性が無い場合、一行目の初期定義から
	 * PreOriSimpleList<String> posl = new PreOriSimpleList<String>();
	 * 　　　とする方法もあるが、あまり好ましくない）
	 */
	@Test
	public void POSLPrependDispTest1() {
		SimpleList<String> posl = new PreOriSimpleList<String>();
		
		//prepend strings
		posl.prepend("a");
		posl.prepend("b");
		posl.prepend("c");
		
		//display elements in posl in order
		SimpleList<String> tempposl = posl;
		while(((PreOriSimpleList<String>)tempposl).isEmpty()==false){
			System.out.println(tempposl.getFirst());
			tempposl = tempposl.getRest();
		}
	}
	
	/**
	 * 回避策その２：NullPointerExceptionをキャッチして利用する
	 * 　　（POSLの実装上、NPEがposl.first==nullの場合のみ起こる事を利用した実装）
	 * 　　（クラスを増やしても問題ないなら、新マーカーExceptionを実装して、それをthrow-catchしたい）
	 */
	@Test
	public void POSLPrependDispTest2() {
		SimpleList<String> posl = new PreOriSimpleList<String>();
		
		//prepend strings
		posl.prepend("a");
		posl.prepend("b");
		posl.prepend("c");
		
		//display elements in posl in order
		SimpleList<String> tempposl = posl;
		try{
			while(true){//Exceptionで出されるまで無限ループする
				System.out.println(tempposl.getFirst());
				tempposl = tempposl.getRest();
			}
		}catch(NullPointerException npex){
			//tempposl.first がnullになったらここに飛ぶ
			//do nothing and exit
		}
	}
	
	/**
	 * 回避策その３：使用側でリスト長を把握しておく
	 * 　　（実装上一番シンプルだが、カウンタのミスでバグりやすそうな上、
	 * 　　　例えばprepend者と表示者が違う場合、カウンタ受け渡しの必要がある）
	 */
	@Test
	public void POSLPrependDispTest3() {
		SimpleList<String> posl = new PreOriSimpleList<String>();
		int posllen = 0; //posl のリスト長
		//prepend strings
		posl.prepend("a"); posllen++;
		posl.prepend("b"); posllen++;
		posl.prepend("c"); posllen++;
		
		//display elements in posl in order
		SimpleList<String> tempposl = posl;
		for(int i=0; i<posllen; i++){
			System.out.println(tempposl.getFirst());
			tempposl = tempposl.getRest();
		}
	}
	
	//=======================================================
	/**Test case for singleton Foo implementation. 
	 * @assert 
	 * @expected 
	 */
	@Test
	public void singletonFooTest() {
//		Foo sgt = new Foo();
		Foo sgt = Foo.getInstance();
//		Foo sgt = (new Foo()).getInstance();
		sgt.getClass();
	}
	//=======================================================
	/**Test case for ArrayList Construction. 
	 * @assert 
	 * @expected 
	 */
	@Test
	public void ArrListConstTest() {
		ArrayList<String> list = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		LinkedList<String> list3 = new LinkedList<String>();
		
		list.add("test");
		list2.add("test");
		list3.add("test");
		
		list.ensureCapacity(5);
		
	}

	
}
