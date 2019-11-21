package finance.isra

object IsraMain extends App {
  val inFile = "/home/ophircohen/customers/personal/repos/Scala-playground/src/test/resources/in/Master-tmp.csv"
  val outFile = "/home/ophircohen/customers/personal/repos/Scala-playground/src/test/resources/out/Master.csv"
  val catFile = "/home/ophircohen/customers/personal/repos/Scala-playground/src/test/resources/in/cat-list.out"

  val month = "07-2019"
  val take = 10000
  println(s"Read raw file. Month = $month")
  val transRes = UpdateCategory.readRawCsv(inFile, month).take(take)

  println("Load category map")
  val catMap = CategoryMap(catFile)

  println("Update categories")
  val updateTransRes = UpdateCategory.attacheCategory(transRes, catMap)

  println(updateTransRes.take(20).mkString("\n"))

  println("Writeout updated list")
  UpdateCategory.writeOut(updateTransRes, outFile)

  catMap.writeList()

}
