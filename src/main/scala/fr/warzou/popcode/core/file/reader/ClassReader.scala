package fr.warzou.popcode.core.file.reader

import fr.warzou.popcode.core.classfile.ClassInfo
import fr.warzou.popcode.core.classfile.constantpool.ClassConstantPool

class ClassReader(val reader: ByteByByteReader) {

  private val _classInfo: ClassInfo = new ClassInfo(reader)
  private val _classConstantPool: ClassConstantPool = new ClassConstantPool(reader)

  println(_classConstantPool.pool(0).value)

  def classInfo: ClassInfo = _classInfo

}
