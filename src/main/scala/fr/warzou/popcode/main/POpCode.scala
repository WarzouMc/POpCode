package fr.warzou.popcode.main

import fr.warzou.popcode.core.file.reader.{ByteByByteReader, ClassReader}

import java.io.File

object POpCode {

  def main(args: Array[String]): Unit = {
    val file: File = new File(getClass.getClassLoader.getResource("SpigotPlayer.class").toURI)
    val reader: ByteByByteReader = new ByteByByteReader(file)
    val classReader: ClassReader = new ClassReader(reader)
  }
}
