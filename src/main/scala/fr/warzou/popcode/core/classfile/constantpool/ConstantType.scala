package fr.warzou.popcode.core.classfile.constantpool

import fr.warzou.popcode.core.classfile.constantpool.constant._

object ConstantType {

  def of(rawConstant: RawConstant): Constant[_] = {
    val tag: Int = rawConstant.tag.bytes(0)
    tag match {
      case 1 => utf8(rawConstant)
      case 3 => integer(rawConstant)
      case 4 => float(rawConstant)
      case 5 => long(rawConstant)
      case 6 => double(rawConstant)
      case 7 | 8 => nameIndex(rawConstant)
      case 9 | 10 | 11 => ref(rawConstant)
      case _ => throw new IllegalStateException("Unknown tag '" + tag + "' !")
    }
  }

  def utf8(rawConstant: RawConstant): Utf8Constant = Utf8Constant(rawConstant)
  def integer(rawConstant: RawConstant): IntegerConstant = IntegerConstant(rawConstant)
  def float(rawConstant: RawConstant): FloatConstant = FloatConstant(rawConstant)
  def long(rawConstant: RawConstant): LongConstant = LongConstant(rawConstant)
  def double(rawConstant: RawConstant): DoubleConstant = DoubleConstant(rawConstant)
  def nameIndex(rawConstant: RawConstant): NameIndexConstant = NameIndexConstant(rawConstant)
  def ref(rawConstant: RawConstant): RefConstant = RefConstant(rawConstant)
}
