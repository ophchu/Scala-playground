package skrooge

import java.io.PrintWriter

import skrooge.SkClasses.{Category, EmptyCategory, SkCategories, SkOperation}

import scala.util.Try

object SkCreation {


  def saveNonCatItems(outPath: String, values: List[SkOperation]) = {
      val writer = new PrintWriter(outPath)


  }

  def saveResults(outPath: String, values: List[SkOperation]) = {
    val writer = new PrintWriter(outPath)

    writer.println(SkOperation.toTitle)

    values.foreach(ops => writer.println(ops.toCsv))

    writer.close()
  }



  def readAndUpdate(inPath: String, catPath: String): List[SkOperation] = {
    val categories = SkCategories(catPath)

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
                categories.getOrCreate(fields(1)),
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
    categories.saveResults()
    res
  }
}
