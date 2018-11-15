package skrooge

import java.io.PrintWriter

import scala.util.Try
import scala.io.StdIn.readLine


object SkClasses {


  case class SkCategories(inPath: String, outPath: String) {
    var catMap = loadCategoryMap(inPath)

    def getOrCreate(name: String): Category= {
      catMap.getOrElse(name, {
          println(s"For name: $name we don't have category.")
          val cat = readLine("Category: ")
          val subcat = readLine("Sub Category: ")
          val category = Category(cat, subcat)
          catMap = catMap + (name -> category)
          category
        })
    }

    def saveResults(outPath: String) = {
      val writer = new PrintWriter(outPath)


      writer.println("name,category,sub category")

      catMap.foreach(cat => writer.println(s"${cat._1},${cat._2.category},${cat._2.subCategory}"))

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


  }

  case class Category(category: String, subCategory: String) {
    def toCat = s"$category > $subCategory"
  }

  object EmptyCategory extends Category("אחר", "אחר")


  case class SkOperation(
                          account: String,
                          payee: String,
                          category: Category,
                          date: String,
                          mode: String,
                          tracker: String,
                          amount: String,
                          comment: String
                        ) {

    def toCsv = {
      s"$account,$payee,${category.toCat},$date,$mode,$tracker,$amount,$comment"
    }
  }

  object SkOperation {
    val operationFields = List("account", "Payee", "Category", "Date", "Mode", "Tracker", "Amount", "Comment")

    def toTitle = operationFields.mkString(",")
  }

}