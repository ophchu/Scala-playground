package finance.isra

import java.io.PrintWriter
import java.util.Scanner

sealed trait Category

case object Car extends Category

case object Food extends Category

case object Home extends Category

case object Children extends Category

case object Recreation extends Category

case object Communication extends Category

case object Work extends Category

case object Bank extends Category

case object Else extends Category


trait CategoryList {
  def resolveCategory(catStr: String): Category =
    catStr.trim.toLowerCase match {
      case "car" => Car
      case "food" => Food
      case "home" => Home
      case "children" => Children
      case "recreation" => Recreation
      case "communication" => Communication
      case "work" => Work
      case "bank" => Bank
      case "else" => Else
    }
  val categoryList = List(
    Car, Food, Home, Children, Recreation, Communication, Work, Bank, Else
  )
  val categoryString =
    "\n" +
    "*"*20 + "\n" +
      categoryList.zipWithIndex.map(x => s"${x._2} --> ${x._1}").mkString("\n") + "\n" +
      "*"*20 + "\n"
}

case class CategoryMap(inPath: String = null) extends CategoryList {
  var catMap = readCatList(inPath)

  def getCategory(desc: String): Category = {
    catMap.getOrElse(desc, readCategory(desc))
  }

  private def readCategory(desc: String): Category = {
    val scanner = new Scanner(System.in)
    println(s"""Categories:\n $categoryString""")
    println(s"Description: $desc")
    val nextInt = scanner.nextInt()
    val cat = categoryList(nextInt)

    catMap = catMap + (desc -> cat)
    cat
  }

  def writeList(outPath: String) = {

    val writer = new PrintWriter(outPath)
    catMap.foreach(entry => writer.println(s"""${entry._1}|||${entry._2}"""))
    writer.close()
  }


  def readCatList(inPath: String): Map[String, Category] = {
    if (inPath == null) {
      Map.empty[String, Category]
    } else {
      val inSource = io.Source.fromFile(inPath)

      val catRes = inSource.getLines.map(cat => {
        val splitted = cat.split("\\|\\|\\|")
        splitted(0) -> resolveCategory(splitted(1))
      }).toMap
      inSource.close()
      catRes
    }
  }
}
object Main extends App{
  val catMap = CategoryMap()
  println(catMap.getCategory("asdasd"))
}
