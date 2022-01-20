package fr.warzou.popcode.core.classfile.constantpool

import fr.warzou.popcode.utils.ByteSequence

object ConstantType {

  def of(rawConstant: RawConstant): Constant[_] = {
    val tag: Int = rawConstant.tag.bytes(0)
    tag match {
      case 1 => utf8(rawConstant)
      case 3 => integer(rawConstant)
      case 4 => float(rawConstant)
      case 5 => long(rawConstant)
      case 6 => double(rawConstant)
      case 7 => clazz(rawConstant)
      case _ => throw new IllegalStateException("Unknown tag '" + tag + "' !")
    }
  }

  def utf8(rawConstant: RawConstant): Constant[String] = {
    new Constant[String](rawConstant) {
      private val _length: ByteSequence = new ByteSequence("", rawConstant.reader.next(2), false)
      private val byteValue: ByteSequence = new ByteSequence("",
        reader.next(Integer.valueOf(_length.bytes(0) + "" + _length.bytes(1))), false)

      _value = readValue()

      def length: Int = Integer.valueOf(_length.bytes(0) + "" + _length.bytes(1))

      override protected def readValue(): String = {
        var string: String = ""
        var count: Int = 0
        while (count < length) {
          string += byteValue.bytes(count).asInstanceOf[Char]
          count += 1
        }
        string
      }
    }
  }

  //todo : sign bit
  def integer(rawConstant: RawConstant): Constant[Int] = {
    new Constant[Int](rawConstant) {

      _value = readValue()

      override protected def readValue(): Int = {
        var stringValue: String = ""
        val byteSequence: ByteSequence = new ByteSequence("", reader.next(4), false)
        byteSequence.foreach(stringValue += Integer.toHexString(_))
        Integer.parseInt(stringValue, 16)
      }
    }
  }

  def float(rawConstant: RawConstant): Constant[Float] = {
    new Constant[Float](rawConstant) {

      _value = readValue()

      override protected def readValue(): Float = {
        var stringValue: String = ""
        val byteSequence: ByteSequence = new ByteSequence("", reader.next(4), false)
        val intValue = Integer.parseInt(stringValue, 16)
        val signe = if ((intValue >> 31) == 0) 1 else -1
        val exponent = (intValue >> 23) & 0xff
        val mantisse = if (exponent == 0) (intValue & 0x7fffff) << 1 else (intValue & 0x7fffff) | 0x800000
        (signe * mantisse * math.pow(2, exponent - 150)).toFloat
      }
    }
  }

  //todo ...
  def long(rawConstant: RawConstant): Constant[Long] = {
    new Constant[Long](rawConstant) {

      _value = readValue()

      override protected def readValue(): Long = {
        var stringValue: String = ""
        val byteSequence: ByteSequence = new ByteSequence("", reader.next(4), false)
        byteSequence.foreach(stringValue += Integer.toHexString(_))
        java.lang.Long.parseLong(stringValue, 16)
      }
    }
  }

  def double(rawConstant: RawConstant): Constant[Double] = {
    new Constant[Double](rawConstant) {

      _value = readValue()

      override protected def readValue(): Double = {
        var stringValue: String = ""
        val byteSequence: ByteSequence = new ByteSequence("", reader.next(4), false)
        val intValue = Integer.parseInt(stringValue, 16)
        val signe = if ((intValue >> 31) == 0) 1 else -1
        val exponent = (intValue >> 23) & 0xff
        val mantisse = if (exponent == 0) (intValue & 0x7fffff) << 1 else (intValue & 0x7fffff) | 0x800000
        (signe * mantisse * math.pow(2, exponent - 150)).toFloat
      }
    }
  }

  def clazz(rawConstant: RawConstant): Constant[Int] = {
    new Constant[Int](rawConstant) {

      _value = readValue()

      override protected def readValue(): Int = {
        var stringValue: String = ""
        val byteSequence: ByteSequence = new ByteSequence("", reader.next(2), false)
        byteSequence.foreach(stringValue += Integer.toHexString(_))
        Integer.parseInt(stringValue, 16)
      }
    }
  }
}
