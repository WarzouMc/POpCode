package fr.warzou.popcode.main

class OpCode {

  private val l: Long = 3837390283393L
  private val l1: Long = -38827393829020L

  def calcule(): Unit = {
    val a: Int = 3
    val b: Int = 93
    val add: Int = a + b
    println(add)
    print(l + " " + l1)
  }

}
