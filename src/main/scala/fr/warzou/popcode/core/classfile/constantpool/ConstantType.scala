package fr.warzou.popcode.core.classfile.constantpool

sealed abstract class ConstantType(tag: Int) {
  override def toString: String = getClass.getSimpleName
}

object ConstantType {

  case class Utf8() extends ConstantType(1)
  case class Integer() extends ConstantType(3)
  case class Float() extends ConstantType(4)
  case class Long() extends ConstantType(5)
  case class Double() extends ConstantType(6)
  case class Class() extends ConstantType(7)
  case class String() extends ConstantType(8)
  case class FieldRef() extends ConstantType(9)
  case class MethodRef() extends ConstantType(10)
  case class InterfaceMethodRef() extends ConstantType(11)
  case class NameAndType() extends ConstantType(12)
  case class MethodHandle() extends ConstantType(15)
  case class MethodType() extends ConstantType(16)
  case class Dynamic() extends ConstantType(17)
  case class InvokeDynamic() extends ConstantType(18)
  case class Module() extends ConstantType(19)
  case class Package() extends ConstantType(20)

  def fromTag(tag: Int): Option[ConstantType] = {
    tag match {
      case 1 => Some(Utf8())
      case 3 => Some(Integer())
      case 4 => Some(Float())
      case 5 => Some(Long())
      case 6 => Some(Double())
      case 7 => Some(Class())
      case 8 => Some(String())
      case 9 => Some(FieldRef())
      case 10 => Some(MethodRef())
      case 11 => Some(InterfaceMethodRef())
      case 12 => Some(NameAndType())
      case 15 => Some(MethodHandle())
      case 16 => Some(MethodType())
      case 17 => Some(Dynamic())
      case 18 => Some(InvokeDynamic())
      case 19 => Some(Module())
      case 20 => Some(Package())
      case _ => None
    }
  }
}
