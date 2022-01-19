package fr.warzou.popcode.main

import fr.warzou.popcode.core.file.reader.{ByteByByteReader, ClassReader}

import java.io.{BufferedReader, File, FileReader, Reader}

object POpCode {

  def main(args: Array[String]): Unit = {
    val file: File = new File(getClass.getClassLoader.getResource("OpCode.class").toURI)
    val fileReader: Reader = new FileReader(file)
    val reader: ByteByByteReader = new ByteByByteReader(new BufferedReader(fileReader))
    val classReader: ClassReader = new ClassReader(reader)
    println(classReader.classInfo.minorVersion + " " + classReader.classInfo.majorVersion)
  }

}
