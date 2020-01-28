package sorts

import scala.collection.mutable
import scala.util.Random

object MergeSortMain extends App{
  val l1 = List.fill(20)(Random.nextInt(1000))

  println(l1)

  println(MergeSort.mergeSort(l1))
}
object MergeSort {

  def mergeSort(list: List[Int]): List[Int] = {
    def mergeSortedLists(l1: List[Int], l2: List[Int]): List[Int] = {
      if (l1.isEmpty) {
        l2
      }else if (l2.isEmpty){
        l1
      }else{
        if (l1.head > l2.head){
          l1.head :: mergeSortedLists(l1.tail, l2)
        }else{
          l2.head :: mergeSortedLists(l1, l2.tail)
        }
      }

    }

    def mergeSort(toSort: mutable.Queue[List[Int]]): List[Int] ={
      if (toSort.size == 1){
        toSort.head
      }else{
        val l1 = toSort.dequeue()
        val l2 = toSort.dequeue()
        toSort.enqueue(mergeSortedLists(l1, l2))

        mergeSort(toSort)
      }
    }
    val listQueue = mutable.Queue.empty[List[Int]]

    list.foreach(listQueue += List(_))
    mergeSort(listQueue)
  }

}
