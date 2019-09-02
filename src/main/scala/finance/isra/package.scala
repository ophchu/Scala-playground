package finance

package object isra {
  implicit class CSVWrapper(val prod: Product) extends AnyVal{
    def toCSV() = prod.productIterator.map{
      case Some(value) => value
      case None => ""
      case str: String => s""""$str""""
      case rest => rest
    }.mkString(",")
  }

}
