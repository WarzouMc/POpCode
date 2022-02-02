package fr.warzou.popcode.core.file.reader

import java.io.{BufferedReader, ByteArrayInputStream, ByteArrayOutputStream, File, FileInputStream, FileOutputStream, StringReader}
import java.nio.file.Files

class ByteByByteReader(val file: File) {

  private val _array: Array[Int] = toArray()
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

  private def toArray(): Array[Int] = {
    val input = new FileInputStream(file)
    val length = Files.size(file.toPath)
    val result: Array[Int] = new Array(length.toInt)
    var i = 0
    while (i < length) {
      result(i) = input.read()
      i += 1
    }
    result
  }
}
