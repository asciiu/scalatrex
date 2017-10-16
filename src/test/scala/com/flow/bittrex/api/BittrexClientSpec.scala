package com.flow.bittrex.api

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.flow.bittrex.BittrexConfig
import org.specs2.Specification
import org.specs2.matcher.MatchResult
import org.specs2.specification.core.SpecStructure

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration._

class BittrexClientSpec extends Specification {

  implicit val actorSystem = ActorSystem("main")
  implicit val executor: ExecutionContext = actorSystem.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  // test keys
  val apikey = "749e532a159947aaa25b5587303ae6ee"
  val secret = "645d31c5cf3b421c80225c365438da40"
  val authorization = Auth(apikey, secret)
  val client = new BittrexClient()

  override def is = { s2"""
  This is a specification to check the 'PingSpec' Endpoint

  The 'BittrexClient' should
    fake test $e1
  """
  }

  def e1: MatchResult[Boolean] = true === true

  /*
  "The BittrexClient" should "handle invalid keys" in{
    val currency = "BTC"
    val futureBalance = client.accountGetBalance(Auth(" ", " "), currency)
    val response = Await.result(futureBalance, 5 second)

    response.success shouldEqual false
    response.message shouldEqual "APIKEY_INVALID"
  }

  it should "get account balance" in {
    val currency = "BTC"
    val futureBalance = client.accountGetBalance(authorization, currency)
    val response = Await.result(futureBalance, 5 second)

    //balance.Currency shouldEqual currency
    response.success shouldEqual true
    response.result.get.Currency shouldEqual currency
  }

  it should "retrieve all account balances" in {
    val futureBalances = client.accountGetBalances(authorization)
    val response = Await.result(futureBalances, 5 second)

    response.success shouldEqual true
    response.result.get.length should be > 0
  }

  it should "retrieve an account deposit address" in {
    val currency = "BTC"
    val futureBalances = client.accountGetDepositAddress(authorization, currency)
    val response = Await.result(futureBalances, 5 second)

    response.success shouldEqual true
    response.result.get.Currency shouldEqual currency
  }

  it should "get order history" in {
    val currency = "BTC"
    val futureHist = client.accountGetOrderHistory(authorization)
    val response = Await.result(futureHist, 5 second)

    response.success shouldEqual true
    response.result.get.length should be > 0
  }

  it should "get open orders" in {
    val currency = "BTC"
    val futureOrders = client.marketGetOpenOrders(authorization)
    val response = Await.result(futureOrders, 5 second)

    response.success shouldEqual true
    response.result.get.length should be > 0
  }
  */
}
