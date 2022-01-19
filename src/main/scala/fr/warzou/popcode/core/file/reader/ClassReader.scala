package fr.warzou.popcode.core.file.reader

import fr.warzou.popcode.core.classfile.ClassInfo

class ClassReader(val reader: ByteByByteReader) {

  private val _classInfo: ClassInfo = new ClassInfo(reader)

  def classInfo: ClassInfo = _classInfo

}
