package fr.warzou.popcode.core.classfile.constantpool

import fr.warzou.popcode.core.classfile.constantpool.ConstantType.Skipped
import fr.warzou.popcode.core.file.reader.ByteByByteReader
import fr.warzou.popcode.utils.{ByteSequence, Utils}

case object constant {

  abstract class Constant[A](val rawConstant: RawConstant) {

    protected val reader: ByteByByteReader = rawConstant.reader
    protected var _value: A = _
    protected var _size = 1

    protected def readValue(): A

    def tag: ConstantType = ConstantType.fromTag(rawConstant.tag.bytes(0)).get

    def value: A = _value

    def size: Int = _size

    override def equals(obj: Any): Boolean = {
      if (obj == null || !obj.isInstanceOf[this.type]) {
        false
      } else {
        val that: Constant[_] = obj.asInstanceOf[this.type]
        that._value == _value && that.tag == tag && that._size == _size
      }
    }
  }

  case class Utf8Constant(override val rawConstant: RawConstant) extends Constant[(Int, String)](rawConstant) {
    private val _length: ByteSequence = new ByteSequence(rawConstant.reader.next(2))
    private val byteValue: ByteSequence = new ByteSequence(reader.next(length))

    _value = readValue()

    def length: Int = Integer.valueOf(_length.bytes(0).toString + "" + _length.bytes(1).toString)

    override protected def readValue(): (Int, String) = {
      var string: String = ""
      var count: Int = 0
      while (count < length) {
        string += byteValue.bytes(count).asInstanceOf[Char]
        count += 1
      }
      (length, string)
    }

    override def toString: String = "Utf8Constant{" +
      "tag=" + tag + ", " +
      "value=" + value +
      "}"
  }

  case class IntegerConstant(override val rawConstant: RawConstant) extends Constant[Int](rawConstant) {
    _value = readValue()

    override protected def readValue(): Int = {
      val byteSequence: ByteSequence = new ByteSequence(reader.next(4))
      val sequence = byteSequence.sequence
      Utils.hexToSignedInt(sequence)
    }

    override def toString: String = "IntegerConstant{" +
      "tag=" + tag + ", " +
      "value=" + value +
      "}"
  }

  case class FloatConstant(override val rawConstant: RawConstant) extends Constant[Float](rawConstant) {
    _value = readValue()

    override protected def readValue(): Float = {
      val byteSequence: ByteSequence = new ByteSequence(reader.next(4))
      val sequence = byteSequence.sequence
      Utils.hexToSignedFloat(sequence)
    }

    override def toString: String = "FloatConstant{" +
      "tag=" + tag + ", " +
      "value=" + value +
      "}"
  }

  case class LongConstant(override val rawConstant: RawConstant) extends Constant[Long](rawConstant) {
    _value = readValue()

    override protected def readValue(): Long = {
      val byteSequence: ByteSequence = new ByteSequence(reader.next(8))
      val sequence = byteSequence.sequence
      Utils.hexToSignedLong(sequence)
    }

    override def toString: String = "LongConstant{" +
      "tag=" + tag + ", " +
      "value=" + value +
      "}"
  }

  case class DoubleConstant(override val rawConstant: RawConstant) extends Constant[Double](rawConstant) {
    _value = readValue()

    override protected def readValue(): Double = {
      val byteSequence: ByteSequence = new ByteSequence(reader.next(8))
      val sequence = byteSequence.sequence
      Utils.hexToSignedDouble(sequence)
    }

    override def toString: String = "DoubleConstant{" +
      "tag=" + tag + ", " +
      "value=" + value +
      "}"
  }

  case class IndexConstant(override val rawConstant: RawConstant) extends Constant[Int](rawConstant) {
    _value = readValue()

    override protected def readValue(): Int = {
      var stringValue: String = ""
      val byteSequence: ByteSequence = new ByteSequence(reader.next(2))

      byteSequence.foreach(stringValue += Integer.toHexString(_))
      Integer.parseInt(stringValue, 16)
    }

    override def toString: String = "IndexConstant{" +
      "tag=" + tag + ", " +
      "value=" + value +
      "}"
  }

  case class BiIndexConstant(override val rawConstant: RawConstant) extends Constant[Array[Int]](rawConstant) {
    _size = 2
    _value = readValue()

    override protected def readValue(): Array[Int] = {
      val array = new Array[Int](_size)
      val first: ByteSequence = new ByteSequence(reader.next(2))
      val second: ByteSequence = new ByteSequence(reader.next(2))
      array(0) = Integer.parseInt(first.sequence, 16)
      array(1) = Integer.parseInt(second.sequence, 16)

      array
    }

    override def toString: String = "BiIndexConstant{" +
      "tag=" + tag + ", " +
      "value=" + value.mkString("Array(", ", ", ")") +
      "}"
  }

  case class MethodHandleConstant(override val rawConstant: RawConstant) extends Constant[Array[Int]](rawConstant) {
    _size = 2
    _value = readValue()

    override protected def readValue(): Array[Int] = {
      val array = new Array[Int](_size)
      val referenceKindByteSequence: ByteSequence = new ByteSequence(reader.next(1))
      val referenceIndexByteSequence: ByteSequence = new ByteSequence(reader.next(2))
      array(0) = Integer.parseInt(referenceKindByteSequence.sequence, 16)
      array(1) = Integer.parseInt(referenceIndexByteSequence.sequence, 16)

      array
    }

    override def toString: String = "MethodHandleConstant{" +
      "tag=" + tag + ", " +
      "value=" + value.mkString("Array(", ", ", ")") +
      "}"
  }

  case class SkippedConstant(override val rawConstant: RawConstant) extends Constant[Unit](rawConstant) {

    override def tag: ConstantType = Skipped()

    override protected def readValue(): Unit = ()

    override def toString: String = "SkippedConstant{" +
      "tag=" + tag + ", " +
      "value=()" +
      "}"
  }
}
