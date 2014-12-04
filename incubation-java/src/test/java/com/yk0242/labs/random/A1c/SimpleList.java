package com.yk0242.labs.random.A1c;

interface SimpleList<E> {
  /**
   * get first element.
   * @current [A, B, C, D]
   * @return A
   */
  public E getFirst();

  /**
   * get rest elements.
   * @current [A, B, C, D]
   * @return [B, C, D]
   */
  public SimpleList<E> getRest();

  /**
   * @current [B, C, D]
   * @param A
   * @return [A, B, C, D]
   */
  public SimpleList<E> prepend(E value);
}
