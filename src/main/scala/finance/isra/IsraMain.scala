package finance.isra

object IsraMain extends App {
  val inFile = "/home/ophircohen/customers/personal/repos/Scala-playground/src/test/resources/in/Master-tmp.csv"
  val outFile = "/home/ophircohen/customers/personal/repos/Scala-playground/src/test/resources/out/Master.csv"
  val catFile = "/home/ophircohen/customers/personal/repos/Scala-playground/src/test/resources/in/cat-list.out"
  val transRes = UpdateCategory.readRawCsv(inFile).take(10)

  val catMap = CategoryMap(catFile)
  val updateTransRes = UpdateCategory.attacheCategory(transRes, catMap)

  println(updateTransRes.mkString("\n"))

  UpdateCategory.writeOut(updateTransRes, outFile)

  catMap.writeList(catFile)

}
