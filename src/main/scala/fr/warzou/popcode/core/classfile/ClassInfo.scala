package fr.warzou.popcode.core.classfile

import fr.warzou.popcode.core.file.reader.ByteByByteReader
import fr.warzou.popcode.utils.ByteSequence

class ClassInfo(val reader: ByteByByteReader) {

  private val _signature: ByteSequence = new ByteSequence(reader.next(4))
  private val _minorVersion: ByteSequence = new ByteSequence(reader.next(2))
  private val _majorVersion: ByteSequence = new ByteSequence(reader.next(2))

  def signature: String = _signature.sequence
  def minorVersion: Int = Integer.valueOf(_minorVersion.bytes(0) + "" + _minorVersion.bytes(1))
  def majorVersion: Int = Integer.valueOf(_majorVersion.bytes(0) + "" + _majorVersion.bytes(1))

  override def toString: String = "ClassInfo{" +
    "signature=" + _signature + "," +
    "minorVersion=" + _minorVersion + "," +
    "majorVersion=" + _majorVersion + "," +
    "}"
}
