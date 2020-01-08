package electronics.akka

import org.scalatest.matchers.should.Matchers
import org.scalatest.funspec.AnyFunSpec


class ElectroSimulatorTest extends AnyFunSpec with Matchers{

  describe("Elctor Simulator"){
    describe("And Gate"){
      it("Work with other and gates"){
        val w1,w2, w3, w4, wac, wbc, wcout = Wire()

        val aAndGate = AndGate(w1, w2, wac)
        val bAndGate = AndGate(w3, w4, wac)
        val cAndGate = AndGate(wac, wbc, wcout)

        wac.inGate = aAndGate
        wac.outGate = bAndGate

        wbc.inGate = bAndGate
        wbc.outGate = cAndGate

        w1.updateState(true)
        w2.updateState(true)
        w3.updateState(true)
        w4.updateState(true)

        cAndGate.outWire.state shouldBe true
      }

    }
  }
}
