package finance.isra

import java.io.{File, PrintWriter}
import java.util.Scanner

import finance.isra.Category.Category

object Category extends Enumeration {
  type Category = Value
  val Food = Value(0)
  val Home = Value(1)
  val Children = Value(2)
  val Recreation = Value(3)
  val Health = Value(4)
  val Communication = Value(5)
  val Car = Value(6)
  val Work = Value(7)
  val Bank = Value(8)
  val Else = Value(9)

  override def toString(): String = {
    Category.values.map(cat => s"${cat.id} --> $cat").mkString("\n")
  }
}


case class CategoryMap(inPath: String = null) {
  var catMap = readCatList(inPath)

  def getCategory(desc: String): Category = {
    catMap.getOrElse(desc, readCategory(desc))
  }

  private def readCategory(desc: String): Category = {
    val scanner = new Scanner(System.in)
    println(s"""Categories:\n${Category.toString()}""")
    println()
    println(s"Description: $desc")
    var catId = scanner.nextInt()

    while (catId < 0 || catId >= Category.maxId){
      println(s"Category id should be between 0 to ${Category.maxId - 1}")
      catId = scanner.nextInt()
    }
    val cat = Category(catId)

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
      val inFile  = new File(inPath)
      if (inFile.exists) {
        val inSource = io.Source.fromFile(inPath)

        val catRes = inSource.getLines.map(cat => {
          val splitted = cat.split("\\|\\|\\|")
          splitted(0) -> Category.withName(splitted(1))
        }).toMap
        inSource.close()
        catRes
      }else {
        Map.empty[String, Category]
      }
    }
  }
}
