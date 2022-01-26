package fr.warzou.popcode.utils

import org.junit.Assert.assertEquals
import org.junit.Test

import scala.util.Random

class UtilsTest {

  @Test
  def hexToSignedIntTest(): Unit = {
    val random = Random.between(Int.MinValue, Int.MaxValue)
    val hex = Utils.toU4Hex(random)
    assertEquals(random, Utils.hexToSignedInt(hex))
  }

  @Test
  def hexToSignedLongTest(): Unit = {
    val random = Random.between(Long.MinValue, Long.MaxValue)
    val hex = Utils.toU8Hex(random)
    assertEquals(random, Utils.hexToSignedLong(hex))
  }

  @Test
  def hexToSignedFloatTest(): Unit = {
    val random = Random.between(Float.MinValue, Float.MaxValue)
    val hex = Utils.toU4Hex(random)
    assertEquals(random, Utils.hexToSignedFloat(hex), 0)
  }

  @Test
  def hexToSignedDoubleTest(): Unit = {
    val random = Random.between(Double.MinValue, Double.MaxValue)
    val hex = Utils.toU8Hex(random)
    assertEquals(random, Utils.hexToSignedDouble(hex), 0)
  }

  @Test
  def toU4HexTest(): Unit = {
    assertEquals("FFFFFFE7".toLowerCase, Utils.toU4Hex(-25))
    assertEquals("84307911".toLowerCase, Utils.toU4Hex(-2077198063))
    assertEquals("FFFFF1CA".toLowerCase, Utils.toU4Hex(-3638))
    assertEquals("0B7D474C".toLowerCase, Utils.toU4Hex(192759628))
    assertEquals("10C068BD".toLowerCase, Utils.toU4Hex(281045181))

    assertEquals("4572147b", Utils.toU4Hex(3873.28f))
    assertEquals("c7b552de", Utils.toU4Hex(-92837.737f))
  }

  @Test
  def toU8HexTest(): Unit = {
    assertEquals("000D3FC14008C784".toLowerCase, Utils.toU8Hex(3729273932793732L))
    assertEquals("FFFFFC8CF5157B40".toLowerCase, Utils.toU8Hex(-3792639263936L))
    assertEquals("00000000000002E0".toLowerCase, Utils.toU8Hex(736))

    assertEquals("4126791c9144fa19", Utils.toU8Hex(736398.2837293773))
    assertEquals("c0e1b94913be22e6", Utils.toU8Hex(-36298.28366))
  }
}
