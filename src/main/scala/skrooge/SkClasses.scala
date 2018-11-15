package skrooge

object SkClasses {


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