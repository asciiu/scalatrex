package com.flow.bittrex.api

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.scalatest._

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration._

class BittrexClientSpec extends FlatSpec with Matchers {

  implicit val actorSystem = ActorSystem("main")
  implicit val executor: ExecutionContext = actorSystem.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  // test keys
  val apikey = "21f18e3a51df4cedb4e9dd388ce298f6"
  val secret = "0119247481504014858d1ecdf00f2f50"
  val client = new BittrexClient(apikey, secret)

  "The BittrexClient" should "get account balance" in {
    val currency = "BTC"
    val futureBalance = client.accountGetBalance(currency)
    val balance = Await.result(futureBalance, 5.second)

    balance.Currency shouldEqual currency
  }
}
