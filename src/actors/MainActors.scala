package actors

import scala.actors.Actor
import scala.actors.Actor._

/**
 * @author TLV\ophirc
 * @version 0.0.1
 * @since 8/1/13, 15:22
 */

object MainActors {
  def main(args: Array[String]) {
    val red = new Redford
    red start
    val newman = actor {
      println("To be an actor, you have to be a child.")
    }

    val fussyActor = actor {
      loop {
        receive {
          case str: String => println("I got a String: " + str)
          case i: Int => println("I got an Int: " + i.toString)
          case _ => println("I have no idea what I just got")
        }
      }
    }
    fussyActor ! "Hi!"
    fussyActor ! 23
    fussyActor ! 33.33

    val countActor = actor {
          loop {
            react{
              case "how many?" => {
                println("I've got " + mailboxSize.toString + " messages in my mailbox.")
              }
            }
          }
    }
    countActor ! 1
    countActor ! 2
    countActor ! 3
    countActor ! "how many?"
    countActor ! "how many?"

    countActor ! 4
    countActor ! "how many?"


  }
}
