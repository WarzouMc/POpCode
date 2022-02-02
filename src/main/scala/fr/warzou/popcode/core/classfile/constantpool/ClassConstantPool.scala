package fr.warzou.popcode.core.classfile.constantpool

import fr.warzou.popcode.core.classfile.constantpool.constant._
import fr.warzou.popcode.core.file.reader.ByteByByteReader
import fr.warzou.popcode.utils.ByteSequence

class ClassConstantPool(val reader: ByteByByteReader) {

  private val length: ByteSequence = new ByteSequence(reader.next(2))
  private val intLength = java.lang.Integer.valueOf(length.bytes(0).toString + length.bytes(1).toString)
  private val pool: Array[Constant[_]] = new Array[Constant[_]](intLength - 1)
  readConstants()

  private def readConstants(): Unit = {
    (0 until intLength - 1).foreach(i => {
      println(reader.position)
      pool(i) = of(new RawConstant(reader))
      println(pool(i))
    })
    println(pool.mkString("Array(", ", ", ")"))
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
