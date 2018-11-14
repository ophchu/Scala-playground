package skrooge

import java.io.{BufferedWriter, FileWriter, OutputStreamWriter, PrintWriter}

import scala.util.{Failure, Success, Try}

object SkCreation {

  case class SkRes(account: String, date: String, desc: String, amount: String, category: String, subCategory: String)

  case class Category(category: String, subCategory: String)

  object EmptyCategory extends Category("אחר", "אחר")

  def saveResults(outPath: String, values: List[SkRes]) = {
    val writer = new PrintWriter(outPath)

    writer.println("חשבון,תאריך,תיאור,סכום,קטגוריה,קטגוריית משנה")
    values.foreach(
      trn =>
        writer.println(
          s"${trn.account},${trn.date},${trn.desc},${trn.amount},${trn.category},${trn.subCategory}")
    )

    writer.close()
  }

  def loadCategoryMap(inPath: String) = {
    val catSource = io.Source.fromFile(inPath)

    val catList = catSource.getLines().toList.tail.map(line => {
      val fields = line.split(",")
      fields(0) -> Category(fields(1), Try(fields(2)).getOrElse(""))
    }
    )
    catSource.close()
    catList.toMap
  }

  def readAndUpdate(inPath: String, catPath: String): List[SkRes] = {
    val categories = loadCategoryMap(catPath)

    def readAndUpdate(inList: List[String], account: String): List[SkRes] = {
      inList match {
        case Nil => Nil
        case line :: tail => {
          val fields = line.split(",")
          if (fields.nonEmpty) {
            if (fields(0).matches(".*- \\d{4}")) {
              val acc = fields(0).split(" ").reverse.head.trim
              readAndUpdate(inList.tail, acc)
            } else if (fields(0).matches("\\d{2}/\\d{2}/\\d{4}")) {
              SkRes(
                account,
                fields(0),
                fields(1),
                fields(4),
                categories.getOrElse(fields(1), EmptyCategory).category,
                categories.getOrElse(fields(1), EmptyCategory).subCategory
              ) :: readAndUpdate(tail, account)
            } else {
              readAndUpdate(inList.tail, account)
            }
          } else {
            readAndUpdate(inList.tail, account)
          }
        }
      }
    }

    val inSource = io.Source.fromFile(inPath)
    val res = readAndUpdate(inSource.getLines().toList, "None")
    inSource.close()
    res
  }
}
