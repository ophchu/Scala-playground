package skrooge


object SkMain{

  def processFile(transactionFile: String, skOutFile: String, catMappingFile: String) = {

    val skProcTrans = SkCreation.readAndUpdate(transactionFile, catMappingFile) //, "src/test/resources/out/map12.csv")
    skProcTrans
  }

  def main(args: Array[String]): Unit = {
    //    val res = SkCreation.readAndUpdate("/home/ophchu/Downloads/exp12.csv", "src/test/resources/in/map12.csv")//, "src/test/resources/out/map12.csv")
    val tnsInFile = "/home/ophchu/Downloads/exp12.csv"
    val catMapFile = "src/test/resources/in/map12.csv"

    val res = processFile(tnsInFile, "", catMapFile)

    println(res.mkString("\n"))
  }

}
