package stringutils

/**
 * @author TLV\ophirc
 * @version 0.0.1
 * @since 8/1/13, 14:48
 */

object Factorial {
  def factorial(i: Int): Int = {
    def fact(i: Int, accum: Int): Int = {
      if (i <= 1)
        accum
      else
        fact(i - 1, i * accum)
    }
    fact(i, 1)
  }

  def main(args: Array[String]) {
    println(factorial(1))
    println(factorial(2))
    println(factorial(3))
    println(factorial(4))
    println(factorial(5))
    println(factorial(6))
  }
}
