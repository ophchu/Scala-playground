package actors

import scala.actors.Actor

/**
 * @author TLV\ophirc
 * @version 0.0.1
 * @since 8/1/13, 15:53
 */
case object Haircut
class Customer (val id: Int) extends Actor{
  var shorn = false
  def act() = {
    loop {
      react{
        case Haircut => {
          shorn = true
          println("[c] customer " + id + " got a hairvcut")
        }
      }
    }
  }
}
