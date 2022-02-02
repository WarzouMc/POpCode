package fr.warzou.popcode.core.classfile.flag

object flags {

  sealed abstract class AccessFlag(value: Int)
  case class Public() extends AccessFlag(0x0001)
  case class Final() extends AccessFlag(0x0010)
  case class Super() extends AccessFlag(0x0020)
  case class Interface() extends AccessFlag(0x0200)
  case class Abstract() extends AccessFlag(0x0400)
  case class Synthetic() extends AccessFlag(0x1000)
  case class Annotation() extends AccessFlag(0x2000)
  case class Enum() extends AccessFlag(0x4000)
  case class Module() extends AccessFlag(0x8000)

}
