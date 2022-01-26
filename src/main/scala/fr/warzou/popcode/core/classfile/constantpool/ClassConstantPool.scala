package fr.warzou.popcode.core.classfile.constantpool

import fr.warzou.popcode.core.classfile.constantpool.constant.Constant
import fr.warzou.popcode.core.file.reader.ByteByByteReader
import fr.warzou.popcode.utils.ByteSequence

class ClassConstantPool(val reader: ByteByByteReader) {

  private val length: ByteSequence = new ByteSequence("", reader.next(2), false)
  val pool: Array[Constant[_]] = new Array[Constant[_]](Integer.valueOf(length.bytes(0) + "" + length.bytes(1)))
  readConstants()

  private def readConstants(): Unit = {
    (0 until Integer.valueOf(length.bytes(0) + "" + length.bytes(1)))
      .foreach(i => pool(i) = ConstantType.of(new RawConstant(reader)))
  }

}
