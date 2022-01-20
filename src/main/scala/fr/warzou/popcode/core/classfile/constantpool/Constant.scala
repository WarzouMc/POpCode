package fr.warzou.popcode.core.classfile.constantpool

import fr.warzou.popcode.core.file.reader.ByteByByteReader

abstract class Constant[A](val rawConstant: RawConstant) {

  protected val reader: ByteByByteReader = rawConstant.reader
  protected var _value: A = _

  protected def readValue(): A

  def tag: Int = rawConstant.tag.bytes(0)

  def value: A = _value

  override def equals(obj: Any): Boolean = {
    if (obj == null || !obj.isInstanceOf[this.type]) {
      false
    } else {
      val that: Constant[_] = obj.asInstanceOf[this.type]
      that._value.equals(_value) && that.tag == tag
    }
  }
}
