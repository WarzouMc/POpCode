package fr.warzou.popcode.core.classfile.constantpool

import fr.warzou.popcode.core.classfile.constantpool.ConstantType.Skipped
import fr.warzou.popcode.core.classfile.constantpool.constant._
import fr.warzou.popcode.core.file.reader.ByteByByteReader
import fr.warzou.popcode.utils.ByteSequence

import scala.annotation.tailrec

class ClassConstantPool(val reader: ByteByByteReader) {

  private val length: ByteSequence = new ByteSequence(reader.next(2))
  private val intLength = java.lang.Integer.valueOf(length.bytes(0).toString + length.bytes(1).toString)
  private val pool: Array[Constant[_]] = new Array[Constant[_]](intLength - 1)
  readConstants(0)
  println(pool.mkString("Array(", ", ", ")"))

  @tailrec
  private def readConstants(i: Int): Unit = {
    val bool = addConstant(i)
    val max: Int = intLength - 2
    if (i == max) ()
    else readConstants(i + (if (bool) 2 else 1))
  }

  private def addConstant(i: Int): Boolean = {
    val rawConstant = new RawConstant(reader)
    pool(i) = of(rawConstant)
    val tag = pool(i).tag.tag
    if (tag == 5 || tag == 6) {
      pool(i + 1) = SkippedConstant(rawConstant)
      true
    } else false
  }

  def of(rawConstant: RawConstant): Constant[_] = {
    val tag: Int = rawConstant.tag.bytes(0)
    println("tag = " + tag)
    tag match {
      case 1 => Utf8Constant(rawConstant)
      case 3 => IntegerConstant(rawConstant)
      case 4 => FloatConstant(rawConstant)
      case 5 => LongConstant(rawConstant)
      case 6 => DoubleConstant(rawConstant)
      case 7 | 8 | 16 | 19 | 20 => IndexConstant(rawConstant)
      case 9 | 10 | 11 | 12 | 17 | 18 => BiIndexConstant(rawConstant)
      case 15 => MethodHandleConstant(rawConstant)
      case _ => throw new IllegalStateException("Unknown tag '" + tag + "' !")
    }
  }
}
