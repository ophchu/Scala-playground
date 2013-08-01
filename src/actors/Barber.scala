package actors

import scala.actors.Actor
import util.Random

/**
 * @author TLV\ophirc
 * @version 0.0.1
 * @since 8/1/13, 16:01
 */
class Barber extends Actor {
  private val rnd = new Random()


  def helpCustomer(cust: Customer) {
    if (mailboxSize >= 3) {
      println("[b] not enough seats, turning customer " + cust.id + " away")
    } else {
      println("[b] cutting hear of customer " + cust.id)
      Thread.sleep(100 + rnd.nextInt(400))
      cust ! Haircut
    }
  }

  def act() {
    loop {
      react {
        case customer: Customer => helpCustomer(customer)
      }
    }
  }
}
