package fr.warzou.popcode.core.file.reader

import java.io.{BufferedReader, Reader}

class ByteByByteReader(val reader: BufferedReader) {

  private val _array: Array[Int] = toArray
  private val size = _array.length
  private var _position: Int = 0

  def position: Int = _position

  def next(): Array[Int] = {
    next(1)
  }

  def next(count: Int): Array[Int] = {
    val array: Array[Int] = new Array(count)
    val i: Int = _position + count
    while (_position < i) {
      if (_position >= size) {
        array((_position + count) - i) = -1
      } else {
        array((_position + count) - i) = _array(_position)
        _position += 1
      }
    }
    array
  }

  def reset(): Unit = reset(0)

  def reset(position: Int): Unit = {
    if (position < 0) {
      throw new IllegalArgumentException("Position cannot be less than 0 !")
    } else if (position >= size) {
      throw new IllegalArgumentException("Position cannot be more than reader size ! (" + position + " >= " + size + ")")
    } else {
      _position = position
    }
  }

  private def toArray: Array[Int] = {
    reader.mark(Short.MaxValue)
    reader.read()
    val size: Int = count()
    val array: Array[Int] = new Array(size)
    var _count: Int = 0
    while (_count < size) {
      array(_count) = nextByte()
      _count += 1
    }
    reader.reset()
    array
  }

  private def nextByte(): Int = {
    reader.read()
  }

  private def count(): Int = {
    var count: Int = 0
    while (nextByte() != -1) count += 1
    reader.reset()
    count
  }
}
