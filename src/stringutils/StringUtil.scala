package stringutils

/**
 * @author TLV\ophirc
 * @version 0.0.1
 * @since 8/1/13, 14:35
 */

object StringUtil {
  def joiner(strings: List[String], sep: String): String =
    strings.mkString(sep)

  def joiner(strings: List[String]): String =
    joiner(strings, " ")

  def betterJoiner(strings: List[String], sep: String = " "): String =
  strings.mkString(sep)
  def main(args: Array[String]) {
    println(joiner(List("ophir", "cohen"), "-"))
    println(betterJoiner(List("ophir", "cohen"), "-"))
    println(betterJoiner(List("ophir", "cohen")))
  }
}
