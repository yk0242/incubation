package com.yk0242.labs.random.A1c;

class PreOriSimpleList<E> implements SimpleList<E>{
	/* 
	 * 実装メソッドの計算量がO(1)にするべく指定有り。
	 * リスト前方への処理はO(1)にし、後方への処理はO(n)のままな構造を考える。（単純化のため）
	 * 
	 * 初期実装イメージ：右枝のみ存在するツリー。（入れ子構造）
	 * {1,{2,{3,{4,{5,{}}}}}}
	 * 
	 * 改善イメージ：
	 * 移動可能なhead実装のため、よりLinkedList的実装を目指す。（一方向LinkedList的考え）
	 * head->[1|-]->[2|-]->[3|-]->[4|-]->[5|-]->null
	 */
	
	//============================================================
	/** 内部クラスとして、後方ポインタのみあるMyNode実装（本来は別クラス実装したい）*/
	private class MyNode<F>{
		/** この節のエレメントF */
		private F value;
		/** この節の次エレメントへのポインタ */
		private MyNode<F> next;
		
		/** Default Constructor */
		private MyNode(){
			this.value = null;
			this.next = null;
		}
		/** Constructor given value of F */
		private MyNode(F val){
			this.value = val;
			this.next = null;
		}
	}
	//============================================================
	
	/** このリストの第一節 */
	private MyNode<E> first;
	
	//（外部からの）newに掛かる時間は一定とするため、デフォルトコンストラクタ以外は呼ばせない
	/** Default Constructor */
	public PreOriSimpleList(){
		this.first = null;
	}
	/** Constructor specifying first (internal use only) */
	private PreOriSimpleList(MyNode<E> newfirst){
		this.first = newfirst;
	}
	
	/**
	 * get first element.
	 * @current [A, B, C, D]
	 * @return A
	 */
	public E getFirst(){
		return this.first.value;
	}

	/**
	 * get rest elements.
	 * @current [A, B, C, D]
	 * @return [B, C, D]
	 */
	public SimpleList<E> getRest(){
		return new PreOriSimpleList<E>(this.first.next);
	}

	/**
	 * （特にするなとの指定が無いため、より直感的使用を可能とするため以下を実装）<br>
	 * 呼ばれた場合、この呼び元のリスト自体も戻り値と同じくprepend後の値に変わる；
	 * このためリストposlに対して、<br><code>posl.prepend("a");</code><br>
	 * とするだけで、posl自体も左側に"a"をprependした状態となる。
	 * 
	 * @current [B, C, D]
	 * @param A
	 * @return [A, B, C, D]
	 */
	public SimpleList<E> prepend(E value){
		MyNode<E> prenode = new MyNode<E>(value);
		prenode.next = this.first;
		
		this.first = prenode; 
		return this; 
		//JavaDocコメントの動作をさせたくないなら上二行コメントアウトの上で以下を使える
//		return new PreOriSimpleList<E>(prenode);
	}
	
	//!!! 実装してもSimpleListインターフェース経由だと見えない!!!
	public boolean isEmpty(){
		return (first==null);
	}
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
}