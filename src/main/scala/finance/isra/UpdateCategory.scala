package finance.isra

import java.io.PrintWriter

import finance.isra.Category.Category

case class TnsEntry(
                     date: String,
                     description: String,
                     total: Double,
                     account: String,
                     category: Category,
                     month: String
                   )


object UpdateCategory {

  def readRawCsv(inPath: String, month: String) = {
    val inSource = io.Source.fromFile(inPath)

    val res = inSource.getLines.toList.tail
      .map(_.split(",").map(_.trim
        .replace(""""""", "")
        .replace("?", "")
      ))
      .map(e => TnsEntry(e(0), e(1), e(4).toDouble, e(8), null, month))

    inSource.close
    res
  }

  def attacheCategory(tnsList: List[TnsEntry], categoryMap: CategoryMap) = {
    tnsList.zipWithIndex.map(tns => {
      if (tns._2 % 10 == 0){
        println(s"${tns._2} of ${tnsList.size}")
        categoryMap.writeList
      }

      tns._1.copy(category = categoryMap.getCategory(tns._1.description, tns._1.total))
    })
  }

  def writeOut(tnsList: List[TnsEntry], outPath: String) = {
    val writer = new PrintWriter(outPath)
    writer.println(s""""Date","Description","Total", "Account", "Category", "Month"""")
    tnsList.foreach(entry => writer.println(entry.toCSV()))
    writer.close()
  }


}
