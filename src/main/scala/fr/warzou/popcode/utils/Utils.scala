package fr.warzou.popcode.utils

object Utils {

  def hexToSignedInt(hex: String): Int = {
    val length = hex.length
    var long = 0L
    var count = 0
    while ((length - 1) - count >= 0) {
      long = (long << 4) ^ charToHex(hex.charAt(count))
      count += 1
    }
    val sign = if ((long >> 15) == 0) 1 else -1
    sign * (~long + 1).toInt
  }

  def hexToSignedFloat(hex: String): Float = {
    val length = hex.length
    var longValue = 0L
    var count = 0
    while ((length - 1) - count >= 0) {
      longValue = (longValue << 4) ^ charToHex(hex.charAt(count))
      count += 1
    }

    val sign = if ((longValue >> 31) == 0) 1 else -1
    val exponent = (longValue >> 23) & 0xff
    val fraction = if (exponent == 0) (longValue & 0x7fffff) << 1 else (longValue & 0x7fffff) | 0x800000

    println(sign + " " + fraction + " " +  (exponent - 150.0d) + " " + (sign * fraction * math.pow(2.0d, exponent - 150.0d)))
    (sign * fraction * math.pow(2.0d, exponent - 150.0d)).toFloat
  }

  def hexToSignedLong(hex: String): Long = {
    val length = hex.length
    var long: BigInt = 0L
    var count = 0
    while ((length - 1) - count >= 0) {
      long = (long << 4) ^ charToHex(hex.charAt(count))
      count += 1
    }
    val sign = if ((long >> 31) == 0) 1 else -1
    sign * (~long + 1).toLong
  }

  def hexToSignedDouble(hex: String): Double = {
    val length = hex.length
    var bigIntValue: BigInt = 0L
    var count = 0
    while ((length - 1) - count >= 0) {
      bigIntValue = (bigIntValue << 4) ^ charToHex(hex.charAt(count))
      count += 1
    }

    val sign = if ((bigIntValue >> 63) == 0) 1 else -1
    val exponent: Long = ((bigIntValue >> 52) & 0x7ffL).toLong
    val fraction: Long = (if (exponent == 0) (bigIntValue & 0xfffffffffffffL) << 1
    else (bigIntValue & 0xfffffffffffffL) | 0x10000000000000L).toLong

    (sign * fraction * math.pow(2.0d, exponent - 1075.0d))
  }

  def toU4Hex(int: Int): String = {
    var string: String = int.toHexString
    while (string.length < 8) string = (if (int >= 0) "0" else "f") + string
    string
  }

  def toU4Hex(float: Float): String = {
    toU4Hex(java.lang.Float.floatToIntBits(float))
  }

  def toU8Hex(long: Long): String = {
    var string: String = long.toHexString
    while (string.length < 16) string = (if (long >= 0) "0" else "f") + string
    string
  }

  def toU8Hex(double: Double): String = {
    toU8Hex(java.lang.Double.doubleToLongBits(double))
  }

  private def charToHex(c: Char): Int = {
    c.toLower match {
      case '0' => 0x0
      case '1' => 0x1
      case '2' => 0x2
      case '3' => 0x3
      case '4' => 0x4
      case '5' => 0x5
      case '6' => 0x6
      case '7' => 0x7
      case '8' => 0x8
      case '9' => 0x9
      case 'a' => 0xA
      case 'b' => 0xB
      case 'c' => 0xC
      case 'd' => 0xD
      case 'e' => 0xE
      case 'f' => 0xF
      case _ => throw new IllegalArgumentException("Cannot pass char '" + c + "' to hex !")
    }
  }

  private def hexToChar(int: Int): Char = {
    if (int < 10)
      (int + '0').toChar
    else if (int < 16)
      (int - 10 + 'a').toChar
    else throw new IllegalArgumentException("Cannot pass '" + int + "' to a hex value !")
  }

  private def isValidInt(float: Float, exponent: Int): Boolean = (float / math.pow(2.0d, exponent - 150.0d)).isValidInt
}
