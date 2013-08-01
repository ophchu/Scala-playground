package actors

import scala.actors.Actor

/**
 * @author TLV\ophirc
 * @version 0.0.1
 * @since 8/1/13, 17:09
 */
class Shop extends Actor{
  val barber = new Barber()
  barber.start

  def act(){
    println("[s] the shop is open ")

    loop{
      react{
        case customer: Customer => barber ! customer
      }
    }
  }
}
