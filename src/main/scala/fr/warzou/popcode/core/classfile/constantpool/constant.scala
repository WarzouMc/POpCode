package fr.warzou.popcode.core.classfile.constantpool

import fr.warzou.popcode.core.file.reader.ByteByByteReader
import fr.warzou.popcode.utils.{ByteSequence, Utils}

case object constant {
  abstract class Constant[A](val rawConstant: RawConstant) {

    protected val reader: ByteByByteReader = rawConstant.reader
    protected var _value: A = _
    protected var _size = 1

    protected def readValue(): A

    def tag: Int = rawConstant.tag.bytes(0)

    def value: A = _value

    def size: Int = _size

    override def equals(obj: Any): Boolean = {
      if (obj == null || !obj.isInstanceOf[this.type]) {
        false
      } else {
        val that: Constant[_] = obj.asInstanceOf[this.type]
        that._value.equals(_value) && that.tag == tag
      }
    }
  }

  case class Utf8Constant(override val rawConstant: RawConstant) extends Constant[(Int, String)](rawConstant) {
    private val _length: ByteSequence = new ByteSequence("", rawConstant.reader.next(2), false)
    private val byteValue: ByteSequence = new ByteSequence("",
      reader.next(Integer.valueOf(_length.bytes(0) + "" + _length.bytes(1))), false)

    _value = readValue()

    def length: Int = Integer.valueOf(_length.bytes(0) + "" + _length.bytes(1))

    override protected def readValue(): (Int, String) = {
      var string: String = ""
      var count: Int = 0
      while (count < length) {
        string += byteValue.bytes(count).asInstanceOf[Char]
        count += 1
      }
      (length, string)
    }
  }

  case class IntegerConstant(override val rawConstant: RawConstant) extends Constant[Int](rawConstant) {
    _value = readValue()

    override protected def readValue(): Int = {
      var stringValue: String = ""
      val byteSequence: ByteSequence = new ByteSequence("", reader.next(4), false)
      val sequence = byteSequence.sequence
      Utils.hexToSignedInt(sequence)
    }
  }

  case class FloatConstant(override val rawConstant: RawConstant) extends Constant[Float](rawConstant) {
    _value = readValue()

    override protected def readValue(): Float = {
      var stringValue: String = ""
      val byteSequence: ByteSequence = new ByteSequence("", reader.next(4), false)
      val sequence = byteSequence.sequence
      Utils.hexToSignedFloat(sequence)
    }
  }

  case class LongConstant(override val rawConstant: RawConstant) extends Constant[Long](rawConstant) {
    _value = readValue()

    override protected def readValue(): Long = {
      var stringValue: String = ""
      val byteSequence: ByteSequence = new ByteSequence("", reader.next(8), false)
      val sequence = byteSequence.sequence
      Utils.hexToSignedLong(sequence)
    }
  }

  case class DoubleConstant(override val rawConstant: RawConstant) extends Constant[Double](rawConstant) {
    _value = readValue()

    override protected def readValue(): Double = {
      var stringValue: String = ""
      val byteSequence: ByteSequence = new ByteSequence("", reader.next(8), false)
      val sequence = byteSequence.sequence
      Utils.hexToSignedDouble(sequence)
    }
  }

  case class NameIndexConstant(override val rawConstant: RawConstant) extends Constant[Int](rawConstant) {
    _value = readValue()

    override protected def readValue(): Int = {
      var stringValue: String = ""
      val byteSequence: ByteSequence = new ByteSequence("", reader.next(2), false)
      byteSequence.foreach(stringValue += Integer.toHexString(_))
      Integer.parseInt(stringValue, 16)
    }
  }

  case class RefConstant(override val rawConstant: RawConstant) extends Constant[Array[Int]](rawConstant) {
    _size = 2
    _value = readValue()

    override protected def readValue(): Array[Int] = {
      val array = Array(_size)
      val classIndexByteSequence: ByteSequence = new ByteSequence("", reader.next(2), false)
      val nameAndTypeIndexByteSequence: ByteSequence = new ByteSequence("", reader.next(2), false)
      array(0) = Integer.parseInt(classIndexByteSequence.sequence, 16)
      array(1) = Integer.parseInt(nameAndTypeIndexByteSequence.sequence, 16)

      array
    }
  }
}
