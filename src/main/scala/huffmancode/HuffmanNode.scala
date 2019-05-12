package huffmancode

import scala.collection.mutable

abstract sealed class HuffmanNode(frequency: Long) {
  def getFrequncy = frequency



}

object HuffmanNode {
  implicit def orderingByName[A <: HuffmanNode]: Ordering[A] =
    Ordering.by(e => -e.getFrequncy)
}


case class HNode(frequency: Long, left: HuffmanNode, right: HuffmanNode) extends HuffmanNode(frequency)
case class HLeaf(ch: Char, frequency: Long) extends HuffmanNode(frequency)

object HuffmanTree{

  def createFullHTree(chars: Iterator[Char]) = {
    //    implicit val ord = HuffmanNode
    val freqMap = createFreqMap(chars)
    val tree = createTree(freqMap)

    createCodesMap(tree)
  }

  def createCodesMap(root: HuffmanNode) : Map[Char, String] = {
    def createCodesMap(root: HuffmanNode, code: List[Char]) : List[(Char, String)] = {
      root match {
        case HLeaf(ch, _) => List((ch, code.mkString))
        case HNode(_, left, right) => createCodesMap(left, '0' :: code) ++ createCodesMap(right, '1' :: code)
      }
    }
    createCodesMap(root, List.empty[Char]).toMap
  }



  def createTree(freqMap: Map[Char, Long]) = {
    def createTree(pQueue: mutable.PriorityQueue[HuffmanNode]): HuffmanNode ={
      if (pQueue.size == 1){
        pQueue.dequeue
      }else{
        val l = pQueue.dequeue
        val r = pQueue.dequeue
        pQueue += HNode(l.getFrequncy + r.getFrequncy, l, r)
        createTree(pQueue)
      }
    }
    val hcPQueue = mutable.PriorityQueue[HuffmanNode]()
    hcPQueue ++= createHNodes(freqMap)
    createTree(hcPQueue)
  }


  def createHNodes(freqMap: Map[Char, Long]): List[HuffmanNode] = {
    freqMap.map(fc => HLeaf(fc._1, fc._2)).toList
  }

  def createFreqMap(str: String): Map[Char, Long] = createFreqMap(str.iterator)

  def createFreqMap(chars: Iterator[Char]): Map[Char, Long] = {
    val countMap = mutable.Map.empty[Char, Long]

    chars.foreach(ch => countMap.put(ch, countMap.getOrElseUpdate(ch, 0) + 1))
    countMap.toMap
  }
}
