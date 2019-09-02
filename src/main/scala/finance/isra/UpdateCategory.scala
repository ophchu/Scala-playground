package finance.isra

import java.io.PrintWriter

import finance.isra.Category.Category

case class TnsEntry(date: String, description: String, subtotal: Double, account: String, category: Category)


object UpdateCategory {

  def readRawCsv(inPath: String) = {
    val inSource = io.Source.fromFile(inPath)

    val res = inSource.getLines.toList.tail
      .map(_.split(",").map(_.trim
        .replace(""""""", "")
        .replace("?", "")
      ))
      .map(e => TnsEntry(e(0), e(1), e(4).toDouble, e(8), null))

    inSource.close
    res
  }

  def attacheCategory(tnsList: List[TnsEntry], categoryMap: CategoryMap) = {
    tnsList.map(tns => tns.copy(category = categoryMap.getCategory(tns.description)))
  }

  def writeOut(tnsList: List[TnsEntry], outPath: String) = {
    val writer = new PrintWriter(outPath)
    writer.println(s""""Date","Description","Sum", "Account", "Category"""")
    tnsList.foreach(entry => writer.println(entry.toCSV()))
    writer.close()
  }


}
