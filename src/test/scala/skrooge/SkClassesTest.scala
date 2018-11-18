package skrooge

import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, FunSuite, Matchers}
import skrooge.SkClasses.SkCategories

import scala.io.StdIn

class SkClassesTest extends FunSuite
  with BeforeAndAfterEach
  with BeforeAndAfterAll
  with Matchers {





  test("readLine"){
    println("please enter cat:")
    println()
    val res = StdIn.readf2("{0},{1}")

    println(res._1)
    println(res._2)
  }

}
