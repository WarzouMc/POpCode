package fr.warzou.popcode.utils

class ByteSequence(val bytes: Array[Int]) extends Iterable[Int] {

  private var _sequence: String = ""
  private var _bytes: Array[Int] = _
  private var _source: String = ""

  initVars()

  private def initVars(): Unit = {
    _bytes = bytes
    _bytes.foreach(_sequence += Integer.toHexString(_))
  }

  private def sequence_=(string: String): Unit = ()

  def sequence: String = _sequence

  def sequenceLength: Int = _sequence.length / 2

  override def iterator: Iterator[Int] = {
    _bytes.iterator
  }

  override def toString: String = "ByteSequence{" +
    "sequence='" + _sequence + "'," +
    "bytes=" + _bytes.mkString("Array(", ", ", ")") +
    "}"

  override def equals(obj: Any): Boolean = {
    if (obj == null || !obj.isInstanceOf[this.type]) false
    else {
      val that: ByteSequence = obj.asInstanceOf[this.type]
      (Array.equals(that._bytes.asInstanceOf[Array[AnyRef]], _bytes.asInstanceOf[Array[AnyRef]])
        && that._sequence.equals(_sequence))
    }
  }

  private def splitString(string: String): Array[String] = {
    val size = 2
    val array: Array[String] = new Array[String]((string.length + size - 1) / size)
    var position: Int = 0
    array.indices
      .foreach(i => {
        var split: String = ""
        while (split.length < size) {
          split += string.charAt(position)
          position += 1
        }
        array(i) = split
      })
    array
  }
}
