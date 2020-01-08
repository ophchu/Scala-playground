package electronics.akka


class Gate {
  def stateChanged(newState: Boolean) {}
}


final case class Wire(){
  private var _state = false

  var inGate: Gate = new Gate()
  var outGate: Gate = new Gate()

  def updateState(newState: Boolean) = {
    _state = newState
    outGate.stateChanged(_state)
    this
  }

  def state = _state
}

case class InGate(initState: Boolean, outGate: Gate) extends Gate{
  override def stateChanged(newState: Boolean): Unit = {
    outGate.stateChanged(newState)
  }
}

case class AndGate(leftWire: Wire, rightWire: Wire, outWire: Wire) extends Gate  {
  override def stateChanged(newState: Boolean): Unit = {
    outWire.updateState(leftWire.state && rightWire.state)
  }

}