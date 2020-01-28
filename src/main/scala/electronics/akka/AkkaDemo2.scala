package electronics.akka

import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import electronics.akka.GreeterMain.Start

object AkkaQuickstart extends App {
  val greeterMain: ActorSystem[GreeterMain.Start] = ActorSystem(GreeterMain(), "AkkaQuickStart")

  greeterMain ! Start("Charles")
}



object GreeterMain {

  final case class Start(name: String)

  def apply(): Behavior[Start] =
    Behaviors.setup { context =>
      val greeter = context.spawn(Greeter(), "greeter")

      Behaviors.receiveMessage { message =>
        val replyTo = context.spawn(GreeterBot(max = 3), message.name)
        greeter ! Greeter.Greet(message.name, replyTo)
        Behaviors.same
      }
    }
}

object Greeter{
  final case class Greet(whom: String, replyTo: ActorRef[Greeted])
  final case class Greeted(whom: String, replyTo: ActorRef[Greet])

  def apply(): Behavior[Greet] = Behaviors.receive { (context, message) =>
    context.log.info("Hello {}!", message.whom)
    println("Hello {}!", message.whom)
    message.replyTo ! Greeted(message.whom, context.self)
    Behaviors.same
  }

}

object GreeterBot {

  def apply(max: Int): Behavior[Greeter.Greeted] = {
    bot(0, max)
  }

  private def bot(greetingCounter: Int, max: Int): Behavior[Greeter.Greeted] =
    Behaviors.receive { (context, message) =>
      val n = greetingCounter + 1
      context.log.info("Greeting {} for {}", n, message.whom)
      if (n == max) {
        Behaviors.stopped
      } else {
        message.replyTo ! Greeter.Greet(message.whom, context.self)
        bot(n, max)
      }
    }
}