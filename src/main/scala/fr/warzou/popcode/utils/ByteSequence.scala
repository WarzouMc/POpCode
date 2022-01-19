package fr.warzou.popcode.utils

class ByteSequence(val sequence: String, val bytes: Array[Int], val parseString: Boolean) extends Iterable[Int] {

  private var _sequence: String = ""
  private var _bytes: Array[Int] = _

  initVars()

  private def initVars(): Unit = {
    if (parseString) {
      _sequence = sequence.replace(" ", "")
      _bytes = hexStringToInts(_sequence)
      if (_sequence.length % 2 == 1) {
        throw new IllegalArgumentException("sequence without the ' ' must have an even number of char")
      }
    } else {
      _bytes = bytes
      _bytes.foreach(_sequence += Integer.toHexString(_))
    }
  }

  private def sequence_=(string: String): Unit = ()

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
      (that.parseString == parseString && that._sequence.equals(_sequence)
        && Array.equals(that._bytes.asInstanceOf[Array[AnyRef]], _bytes.asInstanceOf[Array[AnyRef]]))
    }
  }

  private def hexStringToInts(sequence: String): Array[Int] = {
    val clearedString: String = sequence.replace(" ", "")
    val split: Array[String] = splitString(clearedString)
    val array: Array[Int] = new Array[Int](split.length)
    array.indices.foreach(i => array(i) = Integer.parseInt(split(i), 16))
    array
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
