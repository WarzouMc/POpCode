package fr.warzou.popcode.core.classfile.constantpool

import fr.warzou.popcode.core.file.reader.ByteByByteReader
import fr.warzou.popcode.utils.ByteSequence

class RawConstant(val reader: ByteByByteReader) {

  private val _tag: ByteSequence = new ByteSequence(reader.next())

  def tag: ByteSequence = _tag

}
