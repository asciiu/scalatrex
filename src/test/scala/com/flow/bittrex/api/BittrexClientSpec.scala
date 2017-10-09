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
  val apikey = "749e532a159947aaa25b5587303ae6ee"
  val secret = "645d31c5cf3b421c80225c365438da40"
  val authorization = Auth(apikey, secret)
  val client = new BittrexClient()

  "The BittrexClient" should "get account balance" in {
    val currency = "BTC"
    val futureBalance = client.accountGetBalance(authorization, currency)
    val balance = Await.result(futureBalance, 5 second)

    balance.Currency shouldEqual currency
  }

  it should "retrieve all account balances" in {
    val futureBalances = client.accountGetBalances(authorization)
    val balances = Await.result(futureBalances, 5 second)

    balances.length should be > 0
  }

  it should "retrieve an account deposit address" in {
    val currency = "BTC"
    val futureBalances = client.accountGetDepositAddress(authorization, currency)
    val address = Await.result(futureBalances, 5 second)

    address.Currency shouldEqual currency
  }

  it should "get order history" in {
    val currency = "BTC"
    val futureHist = client.accountGetOrderHistory(authorization)
    val history = Await.result(futureHist, 5 second)

    history.length should be > 0
  }
}
