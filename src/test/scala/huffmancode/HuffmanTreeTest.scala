package huffmancode

import org.scalatest.{FunSpec, FunSuite, Matchers}

class HuffmanTreeTest extends FunSpec
  with Matchers {

  describe("huffman code tree"){
    describe("Tree should be built right"){
      val ht = HuffmanTree.createFullHTree("aaaabbc".iterator)
      println(ht)
    }

    describe("Ordering should be reversed"){
      it("Should order things from small to big"){
        val hcList = List (
          HLeaf('a', 20),
          HLeaf('b', 11),
          HLeaf('c', 15),
          HLeaf('d', 10)
        ).sorted

        hcList(0) should equal(HLeaf('d', 10))
        hcList(1) should equal(HLeaf('b', 11))
        hcList(2) should equal(HLeaf('c', 15))
        hcList(3) should equal(HLeaf('a', 20))
      }
    }
    describe("Frequency creation"){
      it("should work on long sentence"){
        val fMap = HuffmanTree.createFreqMap("hello huffman code")

        fMap should contain theSameElementsAs Map(
          'h' -> 2,
          'e' -> 2,
          'l' -> 2,
          'o' -> 2,
          'u' -> 1,
          'f' -> 2,
          'm' -> 1,
          'a' -> 1,
          'n' -> 1,
          'c' -> 1,
          'd' -> 1,
          ' ' -> 2)
      }

      it("should work on simple string"){
        val fMap = HuffmanTree.createFreqMap("aaa bbb")

        fMap should contain theSameElementsAs Map('a' -> 3, 'b' -> 3, ' ' -> 1)
      }
      it("should work on iterator"){
        val fMap = HuffmanTree.createFreqMap("aaa bbb".iterator)

        fMap should contain theSameElementsAs Map('a' -> 3, 'b' -> 3, ' ' -> 1)
      }
    }

  }

}
