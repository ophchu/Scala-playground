package sorts

import scala.util.Random

object QuickSortMain extends App {
  val l1 = List.fill(20)(Random.nextInt(1000))

  println(l1)

  println(QuickSort.quickSort(l1))
}

object QuickSort {
  def pivot(pivotOn: Int, list: List[Int]): (List[Int], List[Int]) = {
    list match {
      case Nil => (List.empty[Int], List.empty[Int])

      case head :: tail =>
        val pTail = pivot(pivotOn, tail)

        if (head == pivotOn) {
          (pTail._1, pTail._2)
        } else if (head > pivotOn) {
          (head :: pTail._1, pTail._2)
        } else {
          (pTail._1, head :: pTail._2)
        }
    }
  }


  def quickSort(list: List[Int]): List[Int] = {

    val res = list match {
      case Nil => List.empty[Int]
      case head :: tail =>
        val pivotedList = pivot(head, tail)

        quickSort(pivotedList._2) ::: quickSort(pivotedList._1).::(head)
    }


    res
  }

}
