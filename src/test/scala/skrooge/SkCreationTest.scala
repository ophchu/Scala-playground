package skrooge

import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, FunSuite, Matchers}

class SkCreationTest extends FunSuite
  with BeforeAndAfterEach
  with BeforeAndAfterAll
  with Matchers {


  test("saveIt"){
    val res = SkCreation.readAndUpdate("/home/ophchu/Downloads/exp12.csv", "src/test/resources/in/map12.csv", "src/test/resources/out/map12.csv")
    SkCreation.saveResults("src/test/resources/out/save_data.csv", res)
  }

//  test("loadCategoryMap"){
//    val categories = SkCreation.loadCategoryMap("/home/ophchu/Downloads/map12.csv")
//
//    categories should have size 35
//  }
//
//
//  test ("readAndUpdate"){
//    val res = SkCreation.readAndUpdate("/home/ophchu/Downloads/exp12.csv", "/home/ophchu/Downloads/map12.csv")
//    println(res.mkString("\n"))
//    res should have size 60
//    val count = res.groupBy(_.account).map(kv => (kv._1, kv._2.size))
//    count("4797") shouldBe  34
//    count("7522") shouldBe 26
//  }
}
