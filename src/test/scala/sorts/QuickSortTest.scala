package sorts

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

import scala.util.Random


class QuickSortTest extends AnyFunSpec
  with Matchers{

  describe("Pivot should work"){
    it ("sss"){
      val l = List(3, 1, 20, 8)

      val res =  QuickSort.pivot(1, l)

      println(res._1)
      println(res._2)
    }
    it("asdsa"){
      val l = List(3, 1, 20, 8)

      println(QuickSort.quickSort(l))
    }

    it("asasddsa"){
      val l = List.fill(20)(Random.nextInt(1000))

      println(l)
      println(QuickSort.quickSort(l))
    }
  }


}
