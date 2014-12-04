package com.yk0242.labs.random.A1c;


public final class Foo {
  private static Foo myInstance;
  private Foo(){
  }
  public static Foo getInstance() {
      if (myInstance == null) {
        myInstance = new Foo();
    }
    return myInstance;
  }
  //省略
}



//public class Foo {
//  private Foo myInstance;
//  private Foo() {
//  }
//  public Foo getInstance() {
//      if (this.myInstance == null) {
//          this.myInstance = new Foo();
//      }
//      return this.myInstance;
//  }
//  //省略
//}


