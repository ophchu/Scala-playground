package finance.isra

import org.scalatest.{FunSpec, FunSuite, Matchers}

class UpdateCategoryTest extends FunSpec
  with Matchers {

  val inFile = "/home/ophircohen/customers/personal/repos/Scala-playground/src/test/resources/in/Master-tmp.csv"
  describe("Update Category") {
    describe("attacheCategory should attache category"){
      it("sas"){
        val transRes = UpdateCategory.readRawCsv(inFile)

        transRes should have size 190

        val catMap = CategoryMap()
        val updateTransRes = UpdateCategory.attacheCategory(transRes, catMap)

        updateTransRes should have size 190
      }
    }
    describe("testReadRawCsv") {
      it("Should read file and return the right iterator") {
        val transRes = UpdateCategory.readRawCsv(inFile)

        transRes.size shouldEqual 190

        val groupByAccount = transRes.groupBy(_.account)
        groupByAccount("3521").size shouldEqual 2
        groupByAccount("6588").size shouldEqual 3
        groupByAccount("5902").size shouldEqual 82
        groupByAccount("7522").size shouldEqual 58
        groupByAccount("4797").size shouldEqual 45
      }
    }
  }

}
object Main extends App{
  val inFile = "/home/ophircohen/customers/personal/repos/Scala-playground/src/test/resources/in/Master-tmp.csv"
  val catFile = "/home/ophircohen/customers/personal/repos/Scala-playground/src/test/resources/out/cat-list.out"
  val transRes = UpdateCategory.readRawCsv(inFile).take(20)

  val catMap = CategoryMap(catFile)
  val updateTransRes = UpdateCategory.attacheCategory(transRes, catMap)

  println(updateTransRes.mkString("\n"))

  catMap.writeList(catFile)

}