package skrooge

import java.io.PrintWriter

import skrooge.SkClasses.{Category, EmptyCategory, SkOperation}

import scala.util.Try

object SkCreation {


  def saveResults(outPath: String, values: List[SkOperation]) = {
    val writer = new PrintWriter(outPath)

    writer.println(SkOperation.toTitle)

    values.foreach(ops => writer.println(ops.toCsv))

    writer.close()
  }

  def loadCategoryMap(inPath: String) = {
    val catSource = io.Source.fromFile(inPath)

    val catList = catSource.getLines().toList.tail.map(line => {
      val fields = line.split(",")
      fields(0) -> Category(fields(1), Try(fields(2)).getOrElse("אחר"))
    }
    )
    catSource.close()
    catList.toMap
  }

  def readAndUpdate(inPath: String, catPath: String): List[SkOperation] = {
    val categories = loadCategoryMap(catPath)

    def readAndUpdate(inList: List[String], account: String): List[SkOperation] = {
      inList match {
        case Nil => Nil
        case line :: tail => {
          val fields = line.split(",")
          if (fields.nonEmpty) {
            if (fields(0).matches(".*- \\d{4}")) {
              val acc = fields(0).split(" ").reverse.head.trim
              readAndUpdate(inList.tail, acc)
            } else if (fields(0).matches("\\d{2}/\\d{2}/\\d{4}")) {
              SkOperation(
                account,
                fields(1),
                categories.getOrElse(fields(1), EmptyCategory),
                fields(0),
                "Credit Card",
                "",
                fields(4),
                Try(fields(7)).getOrElse("")
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
