package skrooge

import java.io.PrintWriter
import java.nio.file.{Files, Paths}
import java.time.{LocalDate, LocalDateTime}

import scala.io.StdIn
import scala.util.Try


object SkClasses {


  case class SkCategories(inPath: String = "") {
    var catMap = inPath match {
      case "" => Map.empty[String, Category]
      case _ => loadCategoryMap(inPath)
    }


    def getOrCreate(name: String): Category = {
      catMap.getOrElse(name, {
        val updatedCat = updateEmptyCat(name)
        catMap = catMap + (name -> updatedCat)
        updatedCat
      })

    }

    def updateEmptyCat(name: String) = {
      val catList = uniqueIndexedCategoryList()
      println(catList.mkString("\n"))
      println(s"For [$name] \nChoose category.")
      println("For new category: Category, Sub category")
      println()

      val lineArr = StdIn.readLine().split(",")

      val cat = if (lineArr.size == 1){
        catList.find(cat => cat._2 == lineArr(0).toInt).get._1
      }else{
        Category(lineArr(0), lineArr(1))
      }

      println(s"Chossed category: $cat")
      cat
    }

    def saveResults() = {
      val inPathPath = Paths.get(inPath)
      val ldt = LocalDateTime.now
      Files.move(inPathPath, Paths.get(s"${inPath}_${ldt.toString}"))

      val writer = new PrintWriter(inPath)


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


    override def toString: String = {
      catMap.mkString("\n")
    }

    def uniqueIndexedCategoryList() = {
      catMap
        .values
        .toList.distinct.sortBy(cat => (cat.category, cat.subCategory)).zipWithIndex
    }
  }

  case class Category(category: String, subCategory: String) {
    def toCat = s"$category > $subCategory"

    override def toString: String = s"[$category] --> [$subCategory]"
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