package actors

import collection.parallel.mutable
import util.Random
import collection.mutable.ArrayBuffer

/**
 * @author TLV\ophirc
 * @version 0.0.1
 * @since 8/1/13, 17:06
 */

object BarberShopSimulator {
  private val rnd = new Random()
  private val customers = new ArrayBuffer[Customer]

  private val shop = new Shop

  def generateCustomers {
    for (i <- 1 to 20) {
      val cust = new Customer(i)
      cust.start
      customers += cust
    }
    println("[!] generated " + customers.size + " customers")
  }

  def trickleCustomers {
    for (cust <- customers) {
      shop ! cust
      Thread.sleep(rnd.nextInt(450))
    }
  }

  def tallyCuts {
    Thread.sleep(2000)

    val shornCount = customers.filter(c => c.shorn).size
    println("[!]" + shornCount + " customers got haircut today")
  }

  def main(args: Array[String]) {
    println("[!] starting barbershop simulation")
    shop.start()
    generateCustomers
    trickleCustomers
    tallyCuts
    System.exit(0)
  }

}
