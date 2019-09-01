package finance.isra

case class TransEntry(date: String, description: String, subtotal: Double, account: String, category: Category)


object UpdateCategory {

  def readRawCsv(inPath: String) = {
    val inSource = io.Source.fromFile(inPath)

    val res = inSource.getLines.toList.tail
      .map(_.split(",").map(_.trim
        .replace(""""""", "")
        .replace("?", "")
        .replace("/", "")
      ))
      .map(e => TransEntry(e(0), e(1), e(4).toDouble, e(8), null))

    inSource.close
    res
  }

  def attacheCategory(tnsList: List[TransEntry], categoryMap: CategoryMap) = {
    tnsList.map(tns => tns.copy(category = categoryMap.getCategory(tns.description)))
  }


}
